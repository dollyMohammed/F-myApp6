package com.example.f_myapp6.model

data class QuastionItem(
    val answer: String,
    val category: String,
    val choices: List<String>,
    val question: String
)