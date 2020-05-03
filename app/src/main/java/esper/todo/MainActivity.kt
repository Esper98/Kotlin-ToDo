package esper.todo

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todo_item.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : InputListener, AppCompatActivity()  {

    var completed : Int = 0
    private lateinit var db: ToDoDatabase
    var toDos = ArrayList<ToDo>()
    val obj_adapter = CustomAdapter(toDos,
        onDeleteListener = { view, toDo -> removeToDo(view, toDo) },
        onClickListener = { view, toDo -> completeToDo(view, toDo) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)

        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        recyclerView.adapter = obj_adapter

        var calender = Calendar.getInstance()
        val month = calender.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()).capitalize()
        val day = calender.get(Calendar.DAY_OF_MONTH)

        date.text = "$day $month"

        initFab()

        db = Room.databaseBuilder(
            applicationContext,
            ToDoDatabase::class.java, "todo.db"
        ).build()

        GlobalScope.launch {
            toDos = db.toDoDao().getAll() as ArrayList<ToDo>
            obj_adapter.userList = toDos
            runOnUiThread {
                obj_adapter.notifyDataSetChanged()
                setCompleted()
            }
        }
    }

    fun initFab() {
        floating_action_button.setOnClickListener {
            addToDo()
        }
    }

    fun addToDo() {
        val bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    override fun getInput(myString: String) {
        if (myString.isNotEmpty()) {
            val todo = ToDo(0, myString, false)
            toDos.add(todo)
            obj_adapter.notifyDataSetChanged()
            GlobalScope.launch {
                db.toDoDao().insert(todo)
            }
            setCompleted()
        }
    }

    private fun removeToDo(view: View, toDo: ToDo) {
        toDos.remove(toDo)
        obj_adapter.notifyDataSetChanged()
        GlobalScope.launch {
            db.toDoDao().deleteById(toDo.id)
        }
        setCompleted()
    }

    private fun completeToDo(view: View, toDo: ToDo) {
        toDo.completed = !toDo.completed
        view.completed.toggle()
        obj_adapter.notifyDataSetChanged()
        GlobalScope.launch {
            db.toDoDao().update(toDo)
        }
        setCompleted()
    }

    private fun setCompleted() {
        if (toDos.isEmpty()) {
            nothing.visibility = View.VISIBLE
        } else {
            nothing.visibility = View.GONE
        }
        val count = toDos.count { it.completed }
        completedTextView.text = "Completed: $count/${toDos.size}"
    }
}
