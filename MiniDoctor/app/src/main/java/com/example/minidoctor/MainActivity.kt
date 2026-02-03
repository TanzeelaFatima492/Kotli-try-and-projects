package com.example.medicinereminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicinereminder.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var medicineAdapter: MedicineAdapter
    private lateinit var medicineDatabase: MedicineDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        medicineDatabase = MedicineDatabase(this)

        setupRecyclerView()
        loadMedicines()

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddMedicineActivity::class.java)
            startActivityForResult(intent, ADD_MEDICINE_REQUEST)
        }

        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        medicineAdapter = MedicineAdapter { medicine ->
            // Handle medicine click
            showMedicineDetails(medicine)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = medicineAdapter
        }
    }

    private fun loadMedicines() {
        val medicines = medicineDatabase.getAllMedicines()
        medicineAdapter.submitList(medicines)

        if (medicines.isEmpty()) {
            binding.tvEmpty.visibility = android.view.View.VISIBLE
        } else {
            binding.tvEmpty.visibility = android.view.View.GONE
        }
    }

    private fun showMedicineDetails(medicine: Medicine) {
        val dialog = MedicineDetailDialog(medicine, {
            // Delete medicine
            deleteMedicine(medicine)
        }, {
            // Take medicine now
            markAsTaken(medicine)
        })
        dialog.show(supportFragmentManager, "medicine_detail")
    }

    private fun deleteMedicine(medicine: Medicine) {
        medicineDatabase.deleteMedicine(medicine.id)

        // Cancel alarm
        cancelAlarm(medicine.id)

        Toast.makeText(this, "Medicine deleted", Toast.LENGTH_SHORT).show()
        loadMedicines()
    }

    private fun markAsTaken(medicine: Medicine) {
        val history = MedicineHistory(
            id = 0,
            medicineId = medicine.id,
            medicineName = medicine.name,
            dosage = medicine.dosage,
            time = System.currentTimeMillis(),
            status = "TAKEN"
        )
        medicineDatabase.addHistory(history)
        Toast.makeText(this, "Marked as taken", Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarm(medicineId: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            medicineId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_MEDICINE_REQUEST && resultCode == RESULT_OK) {
            loadMedicines()
        }
    }

    companion object {
        const val ADD_MEDICINE_REQUEST = 100
    }
}