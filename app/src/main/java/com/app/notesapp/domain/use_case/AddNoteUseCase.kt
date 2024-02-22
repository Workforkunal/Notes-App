package com.app.notesapp.domain.use_case

import com.app.notesapp.domain.model.InvalidNoteException
import com.app.notesapp.domain.model.Note
import com.app.notesapp.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if (note.title.isBlank()){
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if (note.description.isBlank()){
            throw InvalidNoteException("The description of the note can't be empty.")
        }
        repository.insertNote(note)
    }
}