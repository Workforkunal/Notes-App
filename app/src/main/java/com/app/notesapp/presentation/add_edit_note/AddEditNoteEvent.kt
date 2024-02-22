package com.app.notesapp.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent{
    data class EnteredTitle(val value: String): AddEditNoteEvent()
    data class ChangedTitleFocus(val focusState: FocusState): AddEditNoteEvent()
    data class EnteredDescription(val value: String): AddEditNoteEvent()
    data class ChangedDescriptionFocus(val focusState: FocusState): AddEditNoteEvent()
    data class ChangedColor(val color: Int): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}
