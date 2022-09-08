package com.example.psamiproject.data

import java.io.Serializable

data class Point(
    val username: String,
    val value: Int = 0
): Serializable