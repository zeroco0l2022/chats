package ru.netology.data.objects

data class Geo(
    val type: String,        // тип места
    val coordinates: String, // координаты места
    val place: String        // описание места
)