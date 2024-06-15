package ru.netology.data

import ru.netology.data.objects.*


open class Post(

    var id: Long = 0,
    val ownerId: Int? = null,
    val fromId: Int? = ownerId,
    val createdBy: Int? = ownerId,
    val date: Int = (System.currentTimeMillis() / 1000).toInt(),
    val text: String = "",
    val replyOwnerId: Int? = null,
    var replyPostId: Long? = null,
    val friendsOnly: Boolean = false,
    open val postType: Enum<PostType> = PostType.POST,
    val attachment: Attachments? = null,
    val geo: Geo? = null,
    val signerId: Int? = null,
    val original: Boolean? = null,
    val likes: Like = Like(0, userLikes = false, canLike = false, canPublish = false)
)

data class Comment(var replyToUser: Int? = null, val replyToComment: Int? = null) : Post() {
    override val postType: Enum<PostType> = PostType.REPLY
}
