package com.azure.todolist.model

data class ToDoItem(
    val id: Int,
    var content: String,
    var isDone: Boolean
)