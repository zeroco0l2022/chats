package data.chatservice

import ru.netology.data.charservice.EmptyChatException
import java.util.LinkedList

class Chat(
    var messages: MutableList<Message> = mutableListOf()
) {
    fun add(message: Message): Boolean {
        if (message.text != null) {
            message.id = if (messages.lastOrNull() == null) message.id else messages
                .lastOrNull()?.id!!
                .plus(1)
            messages += message
            return true
        }
        return false
    }

    fun deleteChat(companionId: Int) {
        messages = messages
            .asSequence()
            .filter { it.companionId != companionId }
            .ifEmpty { throw EmptyChatException("Чат не найден") }
            .toMutableList()
    }


    fun getChats() = messages
        .asSequence()
        .ifEmpty { throw EmptyChatException("Нет чатов") }
        .map { it.companionId }
        .toSet()


    fun editMessage(newMessage: Message) {
        messages[messages
            .indexOf(messages
                .asSequence()
                .ifEmpty { throw EmptyChatException("Нет сообщений") }
                .find { it.id == newMessage.id }
            ).also { if (it == -1) throw EmptyChatException("Сообщение не найдено") }] = newMessage
    }


    fun deleteMessage(id: Int) {
        messages.remove(messages
            .asSequence()
            .ifEmpty { throw EmptyChatException("Нет сообщений") }
            .find { it.id == id }
            .also { if (it == null) throw EmptyChatException("Сообщение не найдено") })
    }

    fun getUnreadChatsCount() = messages
        .asSequence()
        .filter { !it.isRead }
        .ifEmpty { throw EmptyChatException("Нет непрочитанных чатов") }
        .map { it.companionId }
        .toSet()
        .count()

    fun getLastMessages() = messages
        .asSequence()
        .ifEmpty { throw EmptyChatException("Нет сообщений") }
        .groupBy { it.companionId }
        .mapValues { it.value.last().text }
        .values
        .toString()

    fun getChartMessages(id: Int, readMessage: Int) = messages
        .asSequence()
        .filter { it.companionId == id }
        .filter { !it.isRead }
        .ifEmpty { throw EmptyChatException("Нет сообщений") }
        .readTo(readMessage)
        .forEach { it.isRead = true }
        .toString()

}


data class Message(
    var id: Int = 1,
    val userId: Int? = null,
    val companionId: Int? = null,
    var isRead: Boolean = false,
    val text: String? = null
)


fun <E> Sequence<E>.readTo(range: Int): List<E> {
    val result = LinkedList<E>()
    var count = 0
    for (entry in this) {
        count++
        result.add(entry)
        if (count == range) break
    }
    return result
}