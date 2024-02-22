package com.app.notesapp.domain.use_case

import com.app.notesapp.domain.model.Note
import com.app.notesapp.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
)  {
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}