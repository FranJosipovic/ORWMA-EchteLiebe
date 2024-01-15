package ormwa.projekt.fran_josipovic.echteliebe.data.utils

data object Formater {
    fun formatDuration(durationInMs: Long): String {
        val seconds = durationInMs / 1000
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return "$minutes:${String.format("%02d", remainingSeconds)}"
    }
}