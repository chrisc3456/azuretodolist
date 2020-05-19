package com.azure.todolist.ui

import com.azure.todolist.model.ToDoItem

interface ItemListCallback {
    fun onItemToggleDone(item: ToDoItem, isChecked: Boolean)
    fun onItemDeleteClick(item: ToDoItem)
    fun onItemEditClick(item: ToDoItem)
}