package com.app.notesapp.presentation.util

sealed class Screen(
    val route: String
){
    data object NotesScreen: Screen("notes_screen")
    data object AddEditNoteScreen: Screen("adD_edit_note_screen")
}