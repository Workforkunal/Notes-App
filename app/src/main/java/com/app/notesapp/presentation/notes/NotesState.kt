package com.app.notesapp.presentation.notes

import com.app.notesapp.domain.model.Note
import com.app.notesapp.domain.util.NoteOrder
import com.app.notesapp.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
