package id.myone.firestore_project_example.utils

import java.util.concurrent.TimeUnit

object TimeUtils {

    fun timeAgo(timestamp: Long): String {
        val currentTime = System.currentTimeMillis()
        val diffInMillis = currentTime - timestamp

        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
        val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)

        return when {
            days > 0 -> "$days day${if (days > 1) "s" else ""} ago"
            hours > 0 -> "$hours hour${if (hours > 1) "s" else ""} ago"
            minutes > 0 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
            else -> "just now"
        }
    }
}