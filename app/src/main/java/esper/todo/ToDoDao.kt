package esper.todo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ToDoDao {
    @Query("SELECT * FROM toDo")
    fun getAll(): List<ToDo>

    @Insert
    fun insert(toDo: ToDo)

    @Update
    fun update(toDo: ToDo)

    @Query("DELETE FROM toDo WHERE id = :id")
    fun deleteById(id : Int)
}