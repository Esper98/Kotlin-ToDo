package esper.todo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoDao {
    @Query("SELECT * FROM toDo")
    fun getAll(): List<ToDo>

    @Insert
    fun insert(toDo: ToDo)

    @Query("DELETE FROM toDo WHERE id = :id")
    fun deleteById(id : Int)
}