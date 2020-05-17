package com.azure.todolist.model

data class ToDoItem(
    val id: String,
    var content: String,
    var isDone: Boolean
)