package com.azure.todolist.repo

import com.azure.todolist.model.ToDoItem
import com.azure.todolist.model.Result

interface ItemListRepository {
    fun getToDoList(): Result<List<ToDoItem>>
    fun addItem(item: ToDoItem)
    fun updateItem(item: ToDoItem): Result<ToDoItem>
    fun deleteItem(item: ToDoItem)
}