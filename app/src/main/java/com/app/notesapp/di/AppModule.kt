package com.app.notesapp.di

import android.app.Application
import androidx.room.Room
import com.app.notesapp.data.repository.NoteRepositoryImpl
import com.app.notesapp.data.source.NoteDatabase
import com.app.notesapp.domain.repository.NoteRepository
import com.app.notesapp.domain.use_case.AddNoteUseCase
import com.app.notesapp.domain.use_case.DeleteNoteUseCase
import com.app.notesapp.domain.use_case.GetNoteUseCase
import com.app.notesapp.domain.use_case.GetNotesUseCase
import com.app.notesapp.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteUseCase = GetNoteUseCase(repository)
        )
    }
}