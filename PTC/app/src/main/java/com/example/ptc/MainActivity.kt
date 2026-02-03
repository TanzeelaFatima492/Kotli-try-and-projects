package com.example.ptc

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PhoneToCall"
        private const val REQUEST_CODE_PERMISSIONS = 100
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE
        )
    }

    private lateinit var previewView: PreviewView
    private lateinit var btnScan: Button
    private lateinit var processingOverlay: CardView

    private var imageCapture: ImageCapture? = null
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private val textRecognizer: TextRecognizer by lazy {
        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        previewView = findViewById(R.id.previewView)
        btnScan = findViewById(R.id.btnScan)
        processingOverlay = findViewById(R.id.processingOverlay)


        btnScan.setOnClickListener {
            captureAndProcessImage()
        }

        checkPermissions()
    }

    private fun checkPermissions() {
        val permissionsToRequest = REQUIRED_PERMISSIONS.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(),
                REQUEST_CODE_PERMISSIONS)}else {
            startCamera()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                startCamera()
            } else {
                Toast.makeText(this, "Camera and call permissions are required", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Camera initialization failed", exc)
                Toast.makeText(this, "Camera failed to start", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun captureAndProcessImage() {
        val imageCapture = imageCapture ?: return

        showProcessing(true)

        imageCapture.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: androidx.camera.core.ImageProxy) {
                    super.onCaptureSuccess(image)

                    try {
                        val mediaImage = image.image ?: return
                        val inputImage = InputImage.fromMediaImage(
                            mediaImage,
                            image.imageInfo.rotationDegrees
                        )

                        textRecognizer.process(inputImage)
                            .addOnSuccessListener { visionText ->
                                val extractedText = visionText.text
                                val phoneNumber = extractPhoneNumber(extractedText)

                                runOnUiThread {
                                    showProcessing(false)
                                    if (phoneNumber != null) {
                                        showNumberDialog(phoneNumber)
                                    } else {
                                        showNoNumberFoundDialog(extractedText)
                                    }
                                }
                            }
                            .addOnFailureListener { e ->
                                Log.e(TAG, "Text recognition failed", e)
                                runOnUiThread {
                                    showProcessing(false)
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Failed to recognize text. Please try again.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                            .addOnCompleteListener {
                                image.close()
                            }
                    } catch (e: Exception) {
                        Log.e(TAG, "Image processing error", e)
                        runOnUiThread {
                            showProcessing(false)
                            Toast.makeText(
                                this@MainActivity,
                                "Error processing image",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        image.close()
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "Capture failed", exception)
                    runOnUiThread {
                        showProcessing(false)
                        Toast.makeText(
                            this@MainActivity,
                            "Capture failed. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )
    }

    private fun extractPhoneNumber(text: String): String? {
        val cleanedText = text
            .replace(Regex("[Oo]"), "0")
            .replace(Regex("[lIi]"), "1")
            .replace(Regex("[Ss]"), "5")
            .replace(Regex("[Bb]"), "8")
            .replace(Regex("[^\\d\\s+\\-.]"), " ")
            .replace(Regex("\\s+"), " ")

        val patterns = listOf(
            Regex("""0\d{3}\s\d{7}"""),
            Regex("""0\d{10}"""),
            Regex("""0\d{3}[-.]\d{7}"""),
            Regex("""\d{4}\s\d{7}"""),
            Regex("""\d{11}""")
        )

        for (pattern in patterns) {
            val match = pattern.find(cleanedText)
            if (match != null) {
                val candidate = match.value
                if (isValidPakistaniNumber(candidate)) {
                    return formatNumber(candidate)
                }
            }
        }

        val allDigits = cleanedText.replace(Regex("[^\\d]"), "")
        if (allDigits.length >= 11) {
            for (i in 0..allDigits.length - 11) {
                val candidate = allDigits.substring(i, i + 11)
                if (candidate.startsWith("03") || candidate.startsWith("3")) {
                    val formatted = if (candidate.startsWith("3")) "0$candidate" else candidate
                    if (isValidPakistaniNumber(formatted)) {
                        return formatNumber(formatted)
                    }
                }
            }
        }

        return null
    }

    private fun isValidPakistaniNumber(number: String): Boolean {
        val clean = number.replace(Regex("[^\\d]"), "")
        if (clean.length != 11 || !clean.startsWith("03")) return false

        val prefix = clean.substring(0, 4).toIntOrNull()
        return prefix != null && prefix in 300..359
    }

    private fun formatNumber(number: String): String {
        val clean = number.replace(Regex("[^\\d]"), "")
        val formatted = if (clean.length == 11 && clean.startsWith("0")) {
            clean
        } else if (clean.length == 11 && clean.startsWith("3")) {
            "0$clean"
        } else {
            return number
        }   111111

        return "${formatted.substring(0, 4)}-${formatted.substring(4, 7)}-${formatted.substring(7)}"
    }

    private fun showProcessing(show: Boolean) {
        processingOverlay.visibility = if (show) android.view.View.VISIBLE else android.view.View.GONE
        btnScan.isEnabled = !show
    }

    private fun showNumberDialog(phoneNumber: String) {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_number_extracted, null)

        val tvNumber = dialogView.findViewById<TextView>(R.id.tvExtractedNumber)
        val btnCall = dialogView.findViewById<Button>(R.id.btnCall)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        tvNumber.text = phoneNumber

        val dialog = AlertDialog.Builder(this)
            .setTitle("Phone Number Detected")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        btnCall.setOnClickListener {
            makePhoneCall(phoneNumber)
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showNoNumberFoundDialog(recognizedText: String) {
        val message = if (recognizedText.isNotBlank()) {
            """
            No phone number found in the image.
            
            Text recognized:
            "${if (recognizedText.length > 100) recognizedText.substring(0, 100) + "..." else recognizedText}"
            
            Tips:
            • Write numbers clearly
            • Use dark ink on white paper
            • Ensure good lighting
            • Write as: 03XX-XXX-XXXX
            """.trimIndent()
        } else {
            "No text found in the image. Please try again with clearer text."
        }

        AlertDialog.Builder(this)
            .setTitle("No Phone Number Found")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun makePhoneCall(phoneNumber: String) {
        try {
            val cleanNumber = phoneNumber.replace(Regex("[^\\d+]"), "")

            val callIntent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$cleanNumber")
            }

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED) {
                startActivity(callIntent)
            } else {
                Toast.makeText(this, "Call permission is required", Toast.LENGTH_LONG).show()
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    101
                )
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to make call", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Call failed", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}