package com.example.medicinereminder

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MedicineDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "medicine_reminder.db"
        private const val DATABASE_VERSION = 1

        // Medicine table
        private const val TABLE_MEDICINES = "medicines"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DOSAGE = "dosage"
        private const val COLUMN_FREQUENCY = "frequency"
        private const val COLUMN_TIMES = "times"
        private const val COLUMN_NOTES = "notes"
        private const val COLUMN_IS_ACTIVE = "is_active"

        // History table
        private const val TABLE_HISTORY = "history"
        private const val COLUMN_MEDICINE_ID = "medicine_id"
        private const val COLUMN_MEDICINE_NAME = "medicine_name"
        private const val COLUMN_TIME = "time"
        private const val COLUMN_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create medicines table
        val createMedicinesTable = """
            CREATE TABLE $TABLE_MEDICINES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DOSAGE TEXT NOT NULL,
                $COLUMN_FREQUENCY TEXT,
                $COLUMN_TIMES TEXT,
                $COLUMN_NOTES TEXT,
                $COLUMN_IS_ACTIVE INTEGER DEFAULT 1
            )
        """.trimIndent()
        db.execSQL(createMedicinesTable)

        // Create history table
        val createHistoryTable = """
            CREATE TABLE $TABLE_HISTORY (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MEDICINE_ID INTEGER,
                $COLUMN_MEDICINE_NAME TEXT,
                $COLUMN_DOSAGE TEXT,
                $COLUMN_TIME INTEGER,
                $COLUMN_STATUS TEXT,
                FOREIGN KEY ($COLUMN_MEDICINE_ID) REFERENCES $TABLE_MEDICINES($COLUMN_ID)
            )
        """.trimIndent()
        db.execSQL(createHistoryTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MEDICINES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
        onCreate(db)
    }

    // Medicine CRUD operations
    fun addMedicine(medicine: Medicine): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, medicine.name)
            put(COLUMN_DOSAGE, medicine.dosage)
            put(COLUMN_FREQUENCY, medicine.frequency)
            put(COLUMN_TIMES, medicine.times)
            put(COLUMN_NOTES, medicine.notes)
            put(COLUMN_IS_ACTIVE, if (medicine.isActive) 1 else 0)
        }
        return db.insert(TABLE_MEDICINES, null, values)
    }

    fun getAllMedicines(): List<Medicine> {
        val medicines = mutableListOf<Medicine>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_MEDICINES,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_ID DESC"
        )

        with(cursor) {
            while (moveToNext()) {
                val medicine = Medicine(
                    id = getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(COLUMN_NAME)),
                    dosage = getString(getColumnIndexOrThrow(COLUMN_DOSAGE)),
                    frequency = getString(getColumnIndexOrThrow(COLUMN_FREQUENCY)),
                    times = getString(getColumnIndexOrThrow(COLUMN_TIMES)),
                    notes = getString(getColumnIndexOrThrow(COLUMN_NOTES)),
                    isActive = getInt(getColumnIndexOrThrow(COLUMN_IS_ACTIVE)) == 1
                )
                medicines.add(medicine)
            }
        }
        cursor.close()
        return medicines
    }

    fun deleteMedicine(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_MEDICINES, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    // History operations
    fun addHistory(history: MedicineHistory): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MEDICINE_ID, history.medicineId)
            put(COLUMN_MEDICINE_NAME, history.medicineName)
            put(COLUMN_DOSAGE, history.dosage)
            put(COLUMN_TIME, history.time)
            put(COLUMN_STATUS, history.status)
        }
        return db.insert(TABLE_HISTORY, null, values)
    }

    fun getAllHistory(): List<MedicineHistory> {
        val history = mutableListOf<MedicineHistory>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_HISTORY,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_TIME DESC"
        )

        with(cursor) {
            while (moveToNext()) {
                val historyItem = MedicineHistory(
                    id = getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    medicineId = getInt(getColumnIndexOrThrow(COLUMN_MEDICINE_ID)),
                    medicineName = getString(getColumnIndexOrThrow(COLUMN_MEDICINE_NAME)),
                    dosage = getString(getColumnIndexOrThrow(COLUMN_DOSAGE)),
                    time = getLong(getColumnIndexOrThrow(COLUMN_TIME)),
                    status = getString(getColumnIndexOrThrow(COLUMN_STATUS))
                )
                history.add(historyItem)
            }
        }
        cursor.close()
        return history
    }
}