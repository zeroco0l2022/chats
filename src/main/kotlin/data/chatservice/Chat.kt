package ru.netology.data.charservice

open class ChartService(
    var charts: MutableMap<Int, Chat>
) {
    fun add() {

    }
}

class Chat(
    private var nextId: Int = 0,
    var messages: MutableMap<Int, Message> = mutableMapOf<Int, Message>(),
    var chats: MutableMap<Int, MutableMap<Int, Message>> = mutableMapOf<Int, MutableMap<Int, Message>>()
) {
    fun add(message: Message): Boolean {
        if (message.text != null) {
            nextId++
            message.id = nextId
            messages[nextId] = message
            chats[message.companionId!!] =
                messages.filterValues { it.companionId == message.companionId }.toMutableMap()
            return true
        }
        return false
    }

    fun deleteChat(companionId: Int): Boolean {
        if (chats.delete(companionId)){
        messages = messages.filterValues { it.companionId != companionId}.toMutableMap()
        return true
        }
        return false
    }
    fun getChatsList() = if (chats.isNotEmpty()) chats.values else throw EmptyChatException("Нет сообщений")
    fun editMessage(newMessage: Message) : Boolean {
        if(messages.delete(newMessage.id!!)) {
        messages.add(newMessage.id!!, newMessage)
            chats.add(newMessage.companionId!!, messages)
            return true
        }
        return false

    }
    fun deleteMessage(id: Int) : Boolean {
        if (messages.containsKey(id)) {
            val chatId = messages[id]!!.companionId
            messages.delete(id)
            chats.add(chatId!!, messages)
            return true
        }
        return false
    }

    fun getUnreadChatsCount() = messages.filterValues { !it.isRead }.mapKeys { it.value.companionId }.count()

    fun getLastMessages(): List<Pair<Int?, Message>> {
        if(messages.isNotEmpty()){
            return messages.mapKeys { it.value.companionId }.toList()
        }
        throw EmptyChatException("Нет сообщений")
    }

    fun getChartMessages (id: Int, readToMessage: Int): List<Pair<Int, Message>> {
        messages.filterValues { it.companionId == id}.filterValues { !it.isRead }.readTo(readToMessage).mapValues { it.value.isRead = true }
        chats.add(id, messages)
        return chats[id]!!.toList()
    }
}

data class Message(
    var id: Int? = null,
    val userId: Int? = null,
    val companionId: Int? = null,
    var isRead: Boolean = false,
    val text: String? = null
)


fun <K, V> MutableMap<K, V>.delete(toDelete: K): Boolean {
    if (this.contains(toDelete)) {
        this.remove(toDelete)
        return true
    }
    return false
}

fun <K,V> MutableMap<K,V>.add(id: K, toAdd: V) {
    this[id] = toAdd
}

fun <K, V> Map<out K, V>.readTo(range: Int): Map<K, V> {
    val result = LinkedHashMap<K, V>()
    var count = 0
    for (entry in this) {
        count ++
            result.put(entry.key, entry.value)
            if(count == range) break
        }

    return result
}