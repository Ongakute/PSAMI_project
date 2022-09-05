package com.example.psamiproject.data

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class UserActivity(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    var count: Int = 0,
    val date: Long = 0
): Serializable {

    fun stringDate(): String {
        val sdf = SimpleDateFormat("dd.MM.yyy HH:mm", Locale.ENGLISH)
        return sdf.format(Date(date))

    }
}
