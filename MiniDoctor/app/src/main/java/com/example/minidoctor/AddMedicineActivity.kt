package com.example.medicinereminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicinereminder.databinding.ActivityAddMedicineBinding
import java.text.SimpleDateFormat
import java.util.*

class AddMedicineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMedicineBinding
    private lateinit var medicineDatabase: MedicineDatabase
    private val timesList = mutableListOf<Long>()
    private val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        medicineDatabase = MedicineDatabase(this)
        setupSpinners()
        setupTimePicker()

        binding.btnSave.setOnClickListener {
            saveMedicine()
        }

        binding.btnAddTime.setOnClickListener {
            showTimePicker()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun setupSpinners() {
        val frequencies = arrayOf("Once Daily", "Twice Daily", "Thrice Daily", "As Needed")
        val frequencyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, frequencies)
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFrequency.adapter = frequencyAdapter

        val units = arrayOf("mg", "ml", "tablet", "capsule", "drop")
        val unitAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerUnit.adapter = unitAdapter
    }

    private fun setupTimePicker() {
        binding.tvTimes.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val cal = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, selectedHour)
                    set(Calendar.MINUTE, selectedMinute)
                    set(Calendar.SECOND, 0)
                }

                timesList.add(cal.timeInMillis)
                updateTimesDisplay()
            },
            hour,
            minute,
            false
        )
        timePicker.show()
    }

    private fun updateTimesDisplay() {
        val timesString = timesList.joinToString("\n") { timeFormat.format(Date(it)) }
        binding.tvTimes.text = if (timesString.isNotEmpty()) timesString else "Select times"
    }

    private fun saveMedicine() {
        val name = binding.etMedicineName.text.toString().trim()
        val dosage = binding.etDosage.text.toString().trim()
        val frequency = binding.spinnerFrequency.selectedItem.toString()
        val unit = binding.spinnerUnit.selectedItem.toString()
        val notes = binding.etNotes.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter medicine name", Toast.LENGTH_SHORT).show()
            return
        }

        if (dosage.isEmpty()) {
            Toast.makeText(this, "Please enter dosage", Toast.LENGTH_SHORT).show()
            return
        }

        if (timesList.isEmpty()) {
            Toast.makeText(this, "Please add at least one time", Toast.LENGTH_SHORT).show()
            return
        }

        val medicine = Medicine(
            id = 0,
            name = name,
            dosage = "$dosage $unit",
            frequency = frequency,
            times = timesList.joinToString(","),
            notes = notes,
            isActive = true
        )

        val id = medicineDatabase.addMedicine(medicine)

        // Schedule alarms for each time
        timesList.forEachIndexed { index, time ->
            scheduleAlarm(id.toInt(), medicine, time, index)
        }

        Toast.makeText(this, "Medicine added successfully", Toast.LENGTH_SHORT).show()
        setResult(RESULT_OK)
        finish()
    }

    private fun scheduleAlarm(medicineId: Int, medicine: Medicine, timeMillis: Long, requestCode: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("medicine_id", medicineId)
            putExtra("medicine_name", medicine.name)
            putExtra("dosage", medicine.dosage)
            putExtra("time", timeFormat.format(Date(timeMillis)))
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            medicineId * 100 + requestCode, // Unique request code
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = timeMillis
            if (System.currentTimeMillis() > timeMillis) {
                add(Calendar.DAY_OF_YEAR, 1) // Schedule for tomorrow if time has passed
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }
}