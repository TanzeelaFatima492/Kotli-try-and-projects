package com.example.medicinereminder

data class Medicine(
    val id: Int,
    val name: String,
    val dosage: String,
    val frequency: String,
    val times: String, // Comma-separated time millis
    val notes: String,
    val isActive: Boolean
)

data class MedicineHistory(
    val id: Int,
    val medicineId: Int,
    val medicineName: String,
    val dosage: String,
    val time: Long,
    val status: String // "TAKEN" or "MISSED"
)