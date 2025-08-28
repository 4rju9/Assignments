package app.netlify.dev4rju9.taskmanager.model.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import app.netlify.dev4rju9.taskmanager.model.Task

@Database(
    entities = [Task::class],
    version = 2

)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun getDao () : TaskDao

}