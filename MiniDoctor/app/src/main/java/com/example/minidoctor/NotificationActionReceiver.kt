package com.example.medicinereminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class NotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val medicineId = intent.getIntExtra("medicine_id", 0)
        val medicineName = intent.getStringExtra("medicine_name") ?: ""
        val dosage = intent.getStringExtra("dosage") ?: ""
        val time = intent.getLongExtra("time", System.currentTimeMillis())

        val database = MedicineDatabase(context)

        when (intent.action) {
            "TAKE_ACTION" -> {
                // Mark as taken in history
                val history = MedicineHistory(
                    id = 0,
                    medicineId = medicineId,
                    medicineName = medicineName,
                    dosage = dosage,
                    time = time,
                    status = "TAKEN"
                )
                database.addHistory(history)
                Toast.makeText(context, "Marked as taken: $medicineName", Toast.LENGTH_SHORT).show()
            }
            "DISMISS_ACTION" -> {
                // Mark as missed in history
                val history = MedicineHistory(
                    id = 0,
                    medicineId = medicineId,
                    medicineName = medicineName,
                    dosage = dosage,
                    time = time,
                    status = "MISSED"
                )
                database.addHistory(history)
                Toast.makeText(context, "Marked as missed: $medicineName", Toast.LENGTH_SHORT).show()
            }
        }

        // Cancel the notification
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        notificationManager.cancel(medicineId)
    }
}