package com.app.notesapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.notesapp.ui.theme.Blue
import com.app.notesapp.ui.theme.Green
import com.app.notesapp.ui.theme.Pink
import com.app.notesapp.ui.theme.Red
import com.app.notesapp.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val description: String,
    val timeZone: Long,
    val color: Int,

    @PrimaryKey val id: Int? = null
){
    companion object{
        val noteColors = listOf(
            Red,
            Blue,
            Violet,
            Pink,
            Green
        )
    }
}

class InvalidNoteException(message: String): Exception(message)