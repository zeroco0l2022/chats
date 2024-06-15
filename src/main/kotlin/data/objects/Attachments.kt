package ru.netology.data.objects

/**/

sealed class Attachments() {
    abstract val type: AttachmentsEnum
}


data class PhotoAttachment(val photo: Photo) : Attachments() {
    override val type: AttachmentsEnum = AttachmentsEnum.PHOTO
}

data class AudioAttachment(val audio: Audio) : Attachments() {
    override val type: AttachmentsEnum = AttachmentsEnum.AUDIO
}

data class VideoAttachment(val video: Video) : Attachments() {
    override val type: AttachmentsEnum = AttachmentsEnum.VIDEO
}

data class FiletAttachment(val file: File) : Attachments() {
    override val type: AttachmentsEnum = AttachmentsEnum.FILE
}

data class LinkAttachment(val link: Link) : Attachments() {
    override val type: AttachmentsEnum = AttachmentsEnum.LINK
}

class Photo(
    val id: Int,
    val albumId: Int,
    val ownerId: Int,
    val userId: Int,
    val text: String,
    val date: Int
)

class Audio(
    val id: Int,
    val ownerId: Int,
    val artist: String,
    val title: String,
    val duration: Int,
    val url: String,
    val lyricsId: Int,
    val albumId: Int,
    val genreId: Int,
    val date: Int,
    val noSearch: Boolean,
    val isHq: Boolean
)

class Video(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val description: String,
    val duration: Int,
    val url: String,
    val date: Int,
    val player: String
)

class File(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val size: Int,
    val ext: String,
    val url: String,
    val date: Int
)

class Link(
    val url: String,
    val title: String,
    val description: String,
    val photo: Photo,

    )

