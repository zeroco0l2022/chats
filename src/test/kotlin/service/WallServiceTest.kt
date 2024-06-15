package service

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import ru.netology.data.Comment
import ru.netology.data.Post
import ru.netology.data.objects.PostType
import ru.netology.service.PostNotFoundException
import ru.netology.service.WallService

class WallServiceTest {

    @Test
    fun shouldNotThrow() {
        val service = WallService()
        val post = service.add(Post(text = "Text"))
        val comment = Comment()
        val result = service.createComment(post.id, comment)
        assertEquals(comment, result)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val service = WallService()
        val result = service.createComment(1, Comment(1))
    }

    @Test
    fun testAdd() {
        val service = WallService()
        val post = Post(ownerId = 111, fromId = 111, createdBy = 111, text = "Text")
        val result = service.add(post)
        assertEquals(post.id, result.id)
    }


    @Test
    fun updateExisting() {
        val service = WallService()
        val post = service.add(Post(ownerId = 111, fromId = 111, createdBy = 111, text = "Text"))
        service.add(Post(ownerId = 111, fromId = 111, createdBy = 111))
        service.add(Post(ownerId = 111, fromId = 111, createdBy = 111, text = "Message"))
        val update = Post(post.id, 111, 111, 111, text = "Updated Text")
        val result = service.update(update)

        assertTrue(result)
    }

    @Test
    fun updateExistingFailed() {
        val service = WallService()
        service.add(Post(ownerId = 111, fromId = 111, createdBy = 111, text = "Text"))
        service.add(Post(ownerId = 111, fromId = 111, createdBy = 111))
        service.add(Post(ownerId = 111, fromId = 111, createdBy = 111, text = "Message"))
        val update = Post(4, 111, 111, 111, text = "Updated Text")
        val result = service.update(update)
        assertFalse(result)
    }


}