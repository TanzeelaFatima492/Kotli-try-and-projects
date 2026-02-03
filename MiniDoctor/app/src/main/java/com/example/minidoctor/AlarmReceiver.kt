package com.example.medicinereminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val medicineName = intent.getStringExtra("medicine_name") ?: "Medicine"
        val dosage = intent.getStringExtra("dosage") ?: ""
        val time = intent.getStringExtra("time") ?: ""
        val medicineId = intent.getIntExtra("medicine_id", 0)

        // Create notification channel for Android 8.0+
        createNotificationChannel(context)

        // Create intent for notification actions
        val takeIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = "TAKE_ACTION"
            putExtra("medicine_id", medicineId)
            putExtra("medicine_name", medicineName)
            putExtra("dosage", dosage)
            putExtra("time", System.currentTimeMillis())
        }

        val dismissIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = "DISMISS_ACTION"
            putExtra("medicine_id", medicineId)
            putExtra("medicine_name", medicineName)
            putExtra("time", System.currentTimeMillis())
        }

        val takePendingIntent = PendingIntent.getBroadcast(
            context,
            Random().nextInt(),
            takeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val dismissPendingIntent = PendingIntent.getBroadcast(
            context,
            Random().nextInt(),
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Create notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_pill)
            .setContentTitle("Time to take your medicine!")
            .setContentText("$medicineName - $dosage at $time")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_check, "Taken", takePendingIntent)
            .addAction(R.drawable.ic_close, "Dismiss", dismissPendingIntent)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("It's time to take:\n\n$medicineName\nDosage: $dosage\nTime: $time"))

        with(NotificationManagerCompat.from(context)) {
            notify(medicineId, builder.build())
        }

        // Reschedule alarm for next day
        rescheduleAlarm(context, medicineId, intent)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Medicine Reminder"
            val description = "Channel for medicine reminders"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                this.description = description
            }

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun rescheduleAlarm(context: Context, medicineId: Int, originalIntent: Intent) {
        // This would reschedule the alarm for the next day
        // Implementation depends on your scheduling logic
    }

    companion object {
        const val CHANNEL_ID = "medicine_reminder_channel"
    }
}