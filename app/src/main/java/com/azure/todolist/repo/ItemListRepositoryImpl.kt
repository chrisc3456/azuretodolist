package com.azure.todolist.repo

import android.util.Log
import com.azure.todolist.model.ToDoItem

class ItemListRepositoryImpl: ItemListRepository {

    override fun getToDoList(): List<ToDoItem> {
        return listOf(
            ToDoItem(1, "First note", false),
            ToDoItem(2, "Second note is a bit longer", false),
            ToDoItem(3, "Third is even longer and is also complete", true)
        )
    }

    override fun addItem(item: ToDoItem) {
        Log.d("##Repo", "Add item ${item.id}/${item.content}")
    }

    override fun updateItem(item: ToDoItem) {
        Log.d("##Repo", "Update item ${item.id}/${item.content}")
    }

    override fun deleteItem(item: ToDoItem) {
        Log.d("##Repo", "Delete item ${item.id}/${item.content}")
    }
}