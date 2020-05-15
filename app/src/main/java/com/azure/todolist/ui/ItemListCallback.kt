package com.azure.todolist.ui

import com.azure.todolist.model.ToDoItem

interface ItemListCallback {
    fun onItemToggleDone(item: ToDoItem)
    fun onItemDeleteClick(item: ToDoItem)
}