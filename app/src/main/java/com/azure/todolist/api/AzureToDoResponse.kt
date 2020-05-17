package com.azure.todolist.api

import com.azure.todolist.model.ToDoItem

object AzureToDoResponse {

    data class ToDoItemResponse(
        val id: String,
        val createdTime: String,
        val taskDescription: String,
        val isCompleted: Boolean
    )

    fun ToDoItemResponse.toInternalModel(): ToDoItem {
        return ToDoItem(
            id = this.id,
            content = this.taskDescription,
            isDone = this.isCompleted
        )
    }

    fun List<ToDoItemResponse>.toInternalModel(): List<ToDoItem> {
        return map {
            ToDoItem(
                id = it.id,
                content = it.taskDescription,
                isDone = it.isCompleted
            )
        }
    }
}