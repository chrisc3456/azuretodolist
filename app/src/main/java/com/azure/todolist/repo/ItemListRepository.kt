package com.azure.todolist.repo

import com.azure.todolist.model.ToDoItem

interface ItemListRepository {
    fun getToDoList(): List<ToDoItem>
    fun addItem(item: ToDoItem)
    fun updateItem(item: ToDoItem)
    fun deleteItem(item: ToDoItem)
}