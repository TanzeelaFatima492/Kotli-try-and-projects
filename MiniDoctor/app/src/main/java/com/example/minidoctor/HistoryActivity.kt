package com.example.medicinereminder

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicinereminder.databinding.ActivityHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var medicineDatabase: MedicineDatabase
    private val dateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(b.root)

        medicineDatabase = MedicineDatabase(this)

        setupRecyclerView()
        loadHistory()

        binding.btnClearHistory.setOnClickListener {
            // Clear all history (you'd need to implement this in database)
            Toast.makeText(this, "Clear history feature", Toast.LENGTH_SHORT).show()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter { history ->
            showHistoryDetails(history)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = historyAdapter
        }
    }

    private fun loadHistory() {
        val history = medicineDatabase.getAllHistory()
        historyAdapter.submitList(history)

        // Calculate statistics
        val taken = history.count { it.status == "TAKEN" }
        val missed = history.count { it.status == "MISSED" }

        binding.tvStats.text = "Taken: $taken | Missed: $missed | Total: ${history.size}"
    }

    private fun showHistoryDetails(history: MedicineHistory) {
        val timeString = dateFormat.format(Date(history.time))
        val message = """
            Medicine: ${history.medicineName}
            Dosage: ${history.dosage}
            Time: $timeString
            Status: ${history.status}
        """.trimIndent()

        android.app.AlertDialog.Builder(this)
            .setTitle("History Details")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}