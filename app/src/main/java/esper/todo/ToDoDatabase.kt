package esper.todo

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ToDo::class), version = 1)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}