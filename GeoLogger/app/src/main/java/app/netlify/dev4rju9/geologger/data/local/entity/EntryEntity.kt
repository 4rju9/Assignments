package app.netlify.dev4rju9.geologger.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_time_table")
data class EntryEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val entry_time: Long
)