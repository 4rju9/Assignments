package app.netlify.dev4rju9.enginifyassignment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    val userId: Int,
    @PrimaryKey val id: Int,
    var title: String,
    var body: String
)