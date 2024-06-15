package ru.netology.service

import ru.netology.data.Comment
import ru.netology.data.Post
import ru.netology.service.PostNotFoundException
import ru.netology.data.objects.PostType


class WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var nextId: Long = 1
    //private var nextCommentId: Long = 1


    fun createComment(postId: Long, comment: Comment): Comment {

        for (post in posts) if (post.id == postId) {
            comment.id = nextId
            comment.replyPostId = postId
            comment.replyToUser = post.fromId
            nextId++
            comments += comment
        }
        if (comments.isEmpty() || postId != comments.last().replyPostId) throw PostNotFoundException("Записи не существует")
        return comments.last()
    }

    fun add(post: Post): Post {
        post.id = nextId
        posts += post
        nextId++
        return posts.last()
    }

    fun update(updatedPost: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (post.id == updatedPost.id) {
                posts[index] = updatedPost
                return true
            }
        }
        return false
    }

    fun removeById(id: Long): Boolean {
        for (post in posts) {
            if (post.id == id) {
                posts = posts.filter { it != post }.toTypedArray()
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
        nextId = 1
    }

    fun likeById(id: Long) {
        for ((index, post) in posts.withIndex()) {
            if (post.id == id) {
                post.likes.count++
            }
        }
    }
}