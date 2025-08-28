package app.netlify.dev4rju9.taskmanager.model.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.netlify.dev4rju9.taskmanager.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getTasks () : Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask (task: Task)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTask (id: Int)

    @Query("SELECT * FROM tasks WHERE date = :time")
    suspend fun getTask (time: Long) : Task

}