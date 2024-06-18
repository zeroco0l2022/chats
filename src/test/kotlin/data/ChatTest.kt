package data


import org.junit.Test
import org.junit.Assert.*
import data.chatservice.Chat
import ru.netology.data.charservice.EmptyChatException
import data.chatservice.Message


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
        chats.deleteChat(1)
        assertEquals(1, chats.messages.size)
    }

    @Test(expected = EmptyChatException::class)
    fun deleteChatFailed() {
        val chats = Chat()
        chats.deleteChat(1)
    }

    @Test
    fun readChatPassed() {
        val chat = Chat()
        chat.add(Message(companionId = 1, text = "Text"))
        chat.add(Message(companionId = 1, text = "Text2"))
        chat.add(Message(companionId = 1, text = "Text3"))
        chat.add(Message(companionId = 2, text = "Text3"))
        assertEquals(2, chat.getChats().size)
    }


    @Test(expected = EmptyChatException::class)
    fun readChartFailed() {
        val chat = Chat()
        chat.getChats()
    }

    @Test
    fun editMessagePassed() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        chat.add(message1)
        chat.add(message2)
        val message3 = Message(id = message1.id, companionId = 1, text = "Text3")
        chat.editMessage(message3)
        assertEquals(chat.messages[0], message3)
    }

    @Test(expected = EmptyChatException::class)
    fun editMessageFailed() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        chat.add(message1)
        chat.add(message2)
        val message3 = Message(id = 3, companionId = 1, text = "Text3")
        chat.editMessage(message3)
    }

    @Test
    fun deleteMessagePassed() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        chat.add(message1)
        chat.add(message2)
        chat.deleteMessage(message1.id)
        assertEquals(message2, chat.messages[0])
    }

    @Test(expected = EmptyChatException::class)
    fun deleteMessageFailed() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        chat.add(message1)
        chat.add(message2)
        chat.deleteMessage(4)
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

    @Test(expected = EmptyChatException::class)
    fun getUnreadChatsFailed() {
        val chat = Chat()
        val message1 = Message(companionId = 1, text = "Text")
        val message2 = Message(companionId = 1, text = "Text2")
        val message3 = Message(companionId = 2, text = "Text2")
        chat.add(message1)
        chat.add(message2)
        chat.add(message3)
        chat.getChartMessages(1, 3)
        chat.getChartMessages(2, 3)
        chat.getUnreadChatsCount()
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
        val result = listOf(message2.text!!, message2.text!!).toString()
        assertEquals(result, chat.getLastMessages())
    }

    @Test(expected = EmptyChatException::class)
    fun getLastMessagesFailed() {
        val chat = Chat()
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
        assertTrue(chat.messages[1].isRead)
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
        assertFalse(chat.messages[2].isRead)
    }

}