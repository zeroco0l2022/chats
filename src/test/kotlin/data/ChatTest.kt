package data


import org.junit.Test
import org.junit.Assert.*
import ru.netology.data.charservice.Chat
import ru.netology.data.charservice.EmptyChatException
import ru.netology.data.charservice.Message


class ChatTest {

    @Test
    fun addPassed() {
        val chats = Chat()
        chats.add(Message(companionId = 1, text = "Text"))
        chats.add(Message(companionId = 1, text = "Text"))
        chats.add(Message(companionId = 2, text = "Text"))
        assertTrue(chats.add(Message(companionId = 3, text = "Text")))
    }

    @Test
    fun addFailed() {
        val chats = Chat()
        chats.add(Message(companionId = 1, text = "Text"))
        chats.add(Message(companionId = 1, text = "Text"))
        chats.add(Message(companionId = 2, text = "Text"))
        assertFalse(chats.add(Message(companionId = 3)))
    }

    @Test
    fun deletePassed() {
        val chats = Chat()
        chats.add(Message(companionId = 1, text = "Text"))
        chats.add(Message(companionId = 1, text = "Text"))
        chats.add(Message(companionId = 2, text = "Text"))
        assertTrue(chats.deleteChat(1))
    }

    @Test
    fun readChart() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        val message3 = Message(companionId = 1, text = "Text3")
        chat.add(message1)
        chat.add(message2)
        chat.add(message3)
        val map = chat.chats.values
        assertEquals(map, chat.getChatsList())
    }

    @Test(expected = EmptyChatException::class)
    fun readChartFailed() {
        val chat = Chat()
        chat.getChatsList()
    }

    @Test
    fun editMessagePassed() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        chat.add(message1)
        chat.add(message2)
        val message3 = Message(id = message1.id, companionId = 1, text = "Text3")
        assertTrue(chat.editMessage(message3))
    }

    @Test
    fun editMessageFailed() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        chat.add(message1)
        chat.add(message2)
        val message3 = Message(id = 0, companionId = 1, text = "Text3")
        assertFalse(chat.editMessage(message3))
    }

    @Test
    fun deleteMessagePassed() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        chat.add(message1)
        chat.add(message2)
        assertTrue(chat.deleteMessage(message1.id!!))
    }

    @Test
    fun deleteMessageFailed() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        chat.add(message1)
        chat.add(message2)
        assertFalse(chat.deleteMessage(4))
    }

    @Test
    fun getUnreadChatsPassed() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        val message3 = Message(companionId = 2, text = "Text2")
        chat.add(message1)
        chat.add(message2)
        chat.add(message3)
        assertEquals(2, chat.getUnreadChatsCount())
    }

    @Test
    fun getLastMessagesPassed() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        val message3 = Message(companionId = 2, text = "Text2")
        chat.add(message1)
        chat.add(message2)
        chat.add(message3)
        val result =
            listOf<Pair<Int?, Message>>(Pair(message2.companionId, message2), Pair(message3.companionId, message3))
        assertEquals(result, chat.getLastMessages())
    }

    @Test(expected = EmptyChatException::class)
    fun getLastMessagesFailed() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        val message3 = Message(companionId = 2, text = "Text2")
        chat.add(message1)
        chat.add(message2)
        chat.add(message3)
        chat.deleteMessage(message1.id!!)
        chat.deleteMessage(message2.id!!)
        chat.deleteMessage(message3.id!!)
        chat.getLastMessages()
    }

    @Test
    fun getChartMessagesPassed() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        val message3 = Message(companionId = 1, text = "Text3")
        chat.add(message1)
        chat.add(message2)
        chat.add(message3)
        chat.getChartMessages(1, 3)
        assertTrue(chat.messages[1]!!.isRead)
    }

    @Test
    fun getChartMessagesFailed() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        val message3 = Message(companionId = 1, text = "Text3")
        chat.add(message1)
        chat.add(message2)
        chat.add(message3)
        chat.getChartMessages(1, 1)
        assertFalse(chat.messages[2]!!.isRead)
    }

}