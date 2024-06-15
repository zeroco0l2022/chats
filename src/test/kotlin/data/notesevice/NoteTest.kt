package data.notesevice

import org.junit.Test

import org.junit.Assert.*
import ru.netology.data.notesevice.*
import ru.netology.data.notesevice.Note

class NoteTest {

    @Test
    fun addNodePassed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val note2 = notes.add(Note(text = "Text2"))
        assertEquals(2L, note2)
    }

    @Test(expected = EmptyNoteException::class)
    fun addNodeFailed() {
        val notes = Note()
        val note = notes.add(Note())
    }

    @Test
    fun deleteNotePassed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val note2 = notes.add(Note(text = "Text2"))
        assertTrue(notes.delete(notes.getById(note)))
    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteNoteFailed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val note2 = notes.add(Note(text = "Text2"))
        notes.delete(notes.getById(note2))
        notes.delete(notes.getById(note2))
    }

    @Test
    fun editNotePassed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val note2 = notes.add(Note(text = "Text2"))
        notes.edit(Note(id = note2, text = "Text1"))
        assertTrue(notes.edit(Note(id = 1, text = "Text1")))

    }

    @Test
    fun editNoteFailed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val note2 = notes.add(Note(text = "Text2"))
        assertFalse(notes.edit(Note(id = 3, text = "Text1")))
    }

    @Test
    fun readNotePassed() {

        val notes = Note()
        val note: Long = notes.add(Note(text = "Text1"))
        val note2 = notes.add(Note(text = "Text2"))
        val list = listOf<NoteBase<Note>>(notes.getById(note), notes.getById(note2))
        assertEquals(notes.read(), list)
    }

    @Test
    fun readNoteFailed() {

        val notes = Note()
        val note: Long = notes.add(Note(text = "Text1"))
        val note2 = notes.add(Note(text = "Text2"))
        val note3 = notes.add(Note(text = "Text3"))
        val list = listOf<NoteBase<Note>>(notes.getById(note), notes.getById(note2))
        assertNotEquals(notes.read(), list)
    }

    @Test
    fun getByIdNotePassed() {

        val notes = Note()
        val note: Long = notes.add(Note(text = "Text1"))
        val note2 = notes.add(Note(text = "Text2"))
        assertEquals(notes.read()[0], notes.getById(note))
    }

    @Test(expected = NoteNotFoundException::class)
    fun getByIdNoteFailed() {

        val notes = Note()
        notes.read()
    }

    @Test
    fun restoreNotePassed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val note2 = notes.add(Note(text = "Text2"))
        notes.delete(notes.getById(note))
        assertTrue(notes.restore(note))
    }

    @Test
    fun restoreNoteFailed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val note2 = notes.add(Note(text = "Text2"))
        assertFalse(notes.restore(note))
    }

    @Test
    fun addCommentPassed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val comments = NoteComment()
        val comment = comments.add(NoteComment(text = "Comment", noteId = note))
        val comment2 = comments.add(NoteComment(text = "Comment2", noteId = note))
        assertEquals(2L, comment2)
    }

    @Test(expected = EmptyNoteException::class)
    fun addCommentFailed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val comments = NoteComment()
        val comment = comments.add(NoteComment())
    }

    @Test
    fun deleteCommentPassed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val comments = NoteComment()
        val comment = comments.add(NoteComment(text = "Comment", noteId = note))
        val comment2 = comments.add(NoteComment(text = "Comment2", noteId = note))
        val bool: Boolean = note == comments.noteId
        assertTrue(comments.delete(comments.getById(comment)))
    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteCommentFailed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val comments = NoteComment()
        val comment = comments.add(NoteComment(text = "Comment", noteId = note))
        val comment2 = comments.add(NoteComment(text = "Comment2", noteId = note))
        comments.delete(comments.getById(comment))
        comments.delete(comments.getById(comment))
    }

    @Test
    fun editCommentPassed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val comments = NoteComment()
        val comment = comments.add(NoteComment(text = "Comment", noteId = note))
        val comment2 = comments.add(NoteComment(text = "Comment2", noteId = note))
        comments.edit(NoteComment(id = comment2, text = "Comment3"))
        assertTrue(comments.getById(comment2).text == "Comment3")
    }

    @Test
    fun editCommentFailed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val comments = NoteComment()
        val comment = comments.add(NoteComment(text = "Comment", noteId = note))
        val comment2 = comments.add(NoteComment(text = "Comment2", noteId = note))
        comments.delete(comments.getById(comment2))
        assertFalse(notes.edit(Note(id = comment2, text = "Comment3")))
    }


    @Test
    fun readCommentsPassed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val comments = NoteComment()
        val comment = comments.add(NoteComment(text = "Comment", noteId = note))
        val comment2 = comments.add(NoteComment(text = "Comment2", noteId = note))
        val list = listOf<NoteBase<NoteComment>>(comments.getById(comment), comments.getById(comment2))
        assertEquals(comments.read(), list)
    }

    @Test
    fun readCommentsFailed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val comments = NoteComment()
        val comment = comments.add(NoteComment(text = "Comment", noteId = note))
        val comment2 = comments.add(NoteComment(text = "Comment2", noteId = note))
        val comment3 = comments.add(NoteComment(text = "Comment3", noteId = note))
        val list = listOf<NoteBase<NoteComment>>(comments.getById(comment), comments.getById(comment2))
        assertNotEquals(comments.read(), list)
    }


    @Test
    fun getByIdCommentPassed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val note2 = notes.add(Note(text = "Text2"))
        val comments = NoteComment()
        val comment = comments.add(NoteComment(text = "Comment", noteId = note))
        val comment2 = comments.add(NoteComment(text = "Comment2", noteId = note))
        val comment3 = comments.add(NoteComment(text = "Comment3", noteId = note))
        assertEquals(comments.read()[0], comments.getById(comment))
    }

    @Test(expected = NoteNotFoundException::class)
    fun getByIdCommentFailed() {
        val comments = Note()
        comments.read()
    }

    @Test
    fun restoreCommentPassed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val comments = NoteComment()
        val comment = comments.add(NoteComment(text = "Comment", noteId = note))
        val comment2 = comments.add(NoteComment(text = "Comment2", noteId = note))
        comments.delete(comments.getById(comment2))
        assertTrue(comments.restore(comment2))
    }

    @Test
    fun restoreCommentFailed() {
        val notes = Note()
        val note = notes.add(Note(text = "Text1"))
        val comments = NoteComment()
        val comment = comments.add(NoteComment(text = "Comment", noteId = note))
        val comment2 = comments.add(NoteComment(text = "Comment2", noteId = note))
        assertFalse(comments.restore(comment2))
    }
}

