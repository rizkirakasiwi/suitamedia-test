package com.example.suitamedia.model.entity

data class GuestData(
    val `data`: List<Data>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)