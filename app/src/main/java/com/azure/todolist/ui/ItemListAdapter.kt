package com.azure.todolist.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.azure.todolist.R
import com.azure.todolist.model.ToDoItem
import com.azure.todolist.databinding.ItemTodoBinding

class ItemListAdapter(private val itemListCallback: ItemListCallback): RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>() {

    private val itemList: MutableList<ToDoItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemTodoBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.item_todo, parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(itemList[position])
    }

    fun setItemList(results: List<ToDoItem>) {
        itemList.clear()
        itemList.addAll(results)
        notifyDataSetChanged()
    }

    fun deleteItem(item: ToDoItem) {
        val position = itemList.indexOf(item)
        itemList.remove(item)
        notifyItemRemoved(position)
    }

    fun updateItem(item: ToDoItem) {
        val existingItem = itemList.firstOrNull { it.id == item.id }
        if (existingItem == null) {
            itemList.add(item)
            notifyItemInserted(itemList.size)
        } else {
            val position = itemList.indexOf(existingItem)
            itemList.removeAt(position)
            itemList.add(position, item)
            notifyItemChanged(position)
        }
    }

    inner class ItemViewHolder(private val binding: ItemTodoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: ToDoItem) {
            binding.item = item
            binding.itemCallback = itemListCallback

            if (item.isDone) {
                binding.textViewContent.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.textViewContent.paintFlags = 0
            }
        }
    }
}