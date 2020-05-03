package esper.todo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_item.view.*

class CustomAdapter(var userList: ArrayList<ToDo>, private val onDeleteListener: (View, ToDo) -> Unit, private val onClickListener: (View, ToDo) -> Unit) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
        holder.itemView.delete.setOnClickListener { view ->
            onDeleteListener.invoke(view, userList[position])
        }
        holder.itemView.setOnClickListener { view ->
            onClickListener.invoke(view, userList[position])
        }
        holder.itemView.completed.setOnClickListener { view ->
            onClickListener.invoke(view, userList[position])
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    fun removeItem(position: Int) {
        userList.removeAt(position)
        notifyDataSetChanged()
    }

    fun setItems(toDos: ArrayList<ToDo>) {
        userList = toDos
        notifyDataSetChanged()
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(view: View?) {
            Log.wtf("test",view.toString())
        }

        fun bindItems(todo: ToDo) {
            itemView.title.text = todo.title
            itemView.completed.isChecked = todo.completed
        }
    }
}
