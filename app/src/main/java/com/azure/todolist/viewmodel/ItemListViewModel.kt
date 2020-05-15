package com.azure.todolist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azure.todolist.model.ToDoItem

class ItemListViewModel: ViewModel() {
    val toDoItems = MutableLiveData<List<ToDoItem>>()

    fun refreshToDoList() {
        toDoItems.value = listOf(
            ToDoItem(1, "First note", false),
            ToDoItem(2, "Second note is a bit longer", false),
            ToDoItem(3, "Third is even longer and is also complete", true)
        )
    }

    fun toggleItemDone(item: ToDoItem) {
        item.isDone = !item.isDone
        updateItem(item)
    }

    fun deleteItem(item: ToDoItem) {
        Log.d("##ViewModel", "Delete item ${item.id}")
    }

    fun updateItemContent(item: ToDoItem, newContent: String) {
        item.content = newContent
        updateItem(item)
    }

    private fun updateItem(item: ToDoItem) {

    }
}