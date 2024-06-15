package ru.netology.data.objects

data class Like(
    var count: Int,              // число пользователей, которым понравилась запись
    val userLikes: Boolean,      // наличие отметки «Мне нравится» от текущего пользователя
    val canLike: Boolean,        // информация о том, может ли текущий пользователь поставить отметку «Мне нравится»
    val canPublish: Boolean      // информация о том, может ли текущий пользователь сделать репост записи

)
