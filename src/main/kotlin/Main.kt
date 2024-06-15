package ru.netology

import ru.netology.data.Comment
import ru.netology.data.Post
import ru.netology.data.notesevice.Note
import ru.netology.data.notesevice.NoteComment
import ru.netology.data.objects.*
import ru.netology.data.objects.Attachments
import ru.netology.service.WallService


fun main() {

    val service = WallService()
    val post1 = Post(ownerId = 111, fromId = 111, createdBy = 111, text = "Text")
    val post2 = Post(ownerId = 111, fromId = 111, createdBy = 111)
    val post3 = Post(ownerId = 111, fromId = 111, createdBy = 111, text = "Message")
    service.add(post1)
    val result = service.createComment(post1.id, Comment(1))


}
