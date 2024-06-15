package ru.netology.data.notesevice

import java.nio.file.DirectoryStream.Filter

sealed interface CrudService<T> {
    fun add(note: T): Long
    fun delete(note: T): Boolean
    fun edit(note: T): Boolean
    fun read(): List<T>
    fun getById(id: Long): T
    fun restore(id: Long): Boolean
}


sealed class NoteBase<T>(
    var id: Long?,
    val ownerId: Long?,
    open var text: String?,
    val date: Long?,
    open var notes: MutableMap<Long, NoteBase<T>>,
    private var nextId: Long = 0,
    open var isDeleted: Boolean = false
) : CrudService<NoteBase<T>> {
    override fun add(note: NoteBase<T>): Long {
        if (note.text == null) {
            throw EmptyNoteException("Невозможно добавить пустую заметку или комментарий")
        }
        nextId++
        note.id = nextId
        notes.put(nextId, note)
        return note.id!!
    }

    override fun delete(note: NoteBase<T>): Boolean {
        if (notes.isNotEmpty()) {
            for (n in notes.keys) {
                if (n == note.id && !notes[n]!!.isDeleted) {
                    notes[n]!!.isDeleted = true
                    return true
                }
            }
        }
        return false
    }

    override fun edit(note: NoteBase<T>): Boolean {
        if (notes.isNotEmpty()) {
            for (n in notes.keys) {
                if (n == note.id && !notes[n]!!.isDeleted) {
                    notes[n] = note
                    return true
                }
            }
        }
        return false
    }

    override fun read(): List<NoteBase<T>> {
        val result = mutableListOf<NoteBase<T>>()
        if (notes.isNotEmpty()) {
            for (n in notes.keys) {
                if (!notes[n]!!.isDeleted) {
                    result += notes[n]!!
                }
            }
            if (result.isNotEmpty()) {
                return result
            } else throw NoteNotFoundException("Заметок не существует или удалены")
        } else throw NoteNotFoundException("Заметок не существует")
    }

    override fun getById(id: Long): NoteBase<T> {
        if (notes.isNotEmpty()) {
            for (n in notes.keys) {
                if (n == id && !notes[n]!!.isDeleted) {
                    return notes[n]!!
                }
            }
        }
        throw NoteNotFoundException("Замека не найдена")
    }

    override fun restore(id: Long): Boolean {
        if (notes.isNotEmpty()) {
            for (n in notes.keys) {
                if (n == id && notes[n]!!.isDeleted) {
                    notes[n]!!.isDeleted = false
                    return true
                }
            }
        }
        return false
    }
}

class Note(
    id: Long? = null,
    ownerId: Long? = null,
    text: String? = null,
    date: Long? = null,
    override var notes: MutableMap<Long, NoteBase<Note>> = mutableMapOf<Long, NoteBase<Note>>(),
    val title: String? = null,
    val privacy: Privacy = Privacy.ALL,
    val commentPrivacy: Privacy = Privacy.ALL

) : NoteBase<Note>(id, ownerId, text, date, notes) {
    override fun delete(note: NoteBase<Note>): Boolean {
        if (notes.isNotEmpty()) {
            for (n in notes.keys) {
                if (n == note.id && !notes[n]!!.isDeleted) {
                    notes[n]!!.isDeleted = true
                    return true
                }
            }
        }
        return false
    }
}


open class NoteComment(
    id: Long? = null,
    ownerId: Long? = null,
    text: String? = null,
    date: Long? = null,
    override var notes: MutableMap<Long, NoteBase<NoteComment>> = mutableMapOf<Long, NoteBase<NoteComment>>(),
    open var noteId: Long? = null,
    val replyTo: Long? = null,
) : NoteBase<NoteComment>(id, ownerId, text, date, notes) {

    override fun delete(note: NoteBase<NoteComment>): Boolean {
        if (notes.isNotEmpty()) {
            for (n in notes.keys) {
                if (n == note.id && !notes[n]!!.isDeleted) {
                    notes[n]!!.isDeleted = true
                    return true
                }
            }
        }
        return false
    }

    override fun edit(note: NoteBase<NoteComment>): Boolean {
        if (notes.isNotEmpty()) {
            for (n in notes.keys) {
                if (n == note.id && !notes[n]!!.isDeleted) {
                    notes[n] = note
                    return true
                }
            }
        }
        return false
    }

    override fun read(): List<NoteBase<NoteComment>> {
        val result = mutableListOf<NoteBase<NoteComment>>()
        if (notes.isNotEmpty()) {
            for (n in notes.keys) {
                if (!notes[n]!!.isDeleted) {
                    result += notes[n]!!
                }
            }
            if (result.isNotEmpty()) {
                return result
            } else throw NoteNotFoundException("Комментариев не существует или удалены")
        } else throw NoteNotFoundException("Комментариев не существует")
    }

    override fun getById(id: Long): NoteBase<NoteComment> {
        if (notes.isNotEmpty()) {
            for (n in notes.keys) {
                if (n == id && !notes[n]!!.isDeleted) {
                    return notes[n]!!
                }
            }
        }
        throw NoteNotFoundException("Комментарий не найден")
    }

    override fun restore(id: Long): Boolean {
        if (notes.isNotEmpty()) {
            for (n in notes.keys) {
                if (n == id && notes[n]!!.isDeleted) {
                    notes[n]!!.isDeleted = false
                    return true

                }
            }
        }
        return false
    }
}


