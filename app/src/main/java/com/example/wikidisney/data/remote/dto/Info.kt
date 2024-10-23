package com.example.wikidisney.data.remote.dto

data class Info(
    val count: Int,
    val nextPage: String?,
    val previousPage: Any?,
    val totalPages: Int
)