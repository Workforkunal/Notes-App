package com.app.notesapp.presentation.notes

import com.app.notesapp.domain.model.Note
import com.app.notesapp.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    data object RestoreNote: NotesEvent()
    data object ToggleSection: NotesEvent()
}