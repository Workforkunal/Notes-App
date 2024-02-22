package com.app.notesapp.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.notesapp.domain.model.InvalidNoteException
import com.app.notesapp.domain.model.Note
import com.app.notesapp.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter Title..."
    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteDescription = mutableStateOf(NoteTextFieldState(
        hint = "Enter some description..."
    ))
    val noteDescription: State<NoteTextFieldState> = _noteDescription

    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let {noteId ->
            if (noteId != -1){
                viewModelScope.launch {
                    noteUseCase. getNoteUseCase(noteId)?.also {note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteDescription.value = noteDescription.value.copy(
                            text = note.description,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangedTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredDescription -> {
                _noteDescription.value = noteDescription.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangedDescriptionFocus -> {
                _noteDescription.value = noteDescription.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteDescription.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangedColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCase.addNoteUseCase(
                            Note(
                                title = noteTitle.value.text,
                                description = noteDescription.value.text,
                                timeZone = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId

                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch ( e: InvalidNoteException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note..."
                            )
                        )
                    }
                }
            }

        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}