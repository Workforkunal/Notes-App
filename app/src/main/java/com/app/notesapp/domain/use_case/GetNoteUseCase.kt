package com.app.notesapp.domain.use_case

import com.app.notesapp.domain.model.Note
import com.app.notesapp.domain.repository.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id:Int): Note?{
        return repository.getNoteById(id)
    }
}