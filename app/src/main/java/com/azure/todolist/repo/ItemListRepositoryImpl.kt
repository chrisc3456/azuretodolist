package com.azure.todolist.repo

import android.util.Log
import com.azure.todolist.api.AzureToDoResponse
import com.azure.todolist.api.AzureToDoResponse.toInternalModel
import com.azure.todolist.api.AzureToDoService
import com.azure.todolist.model.ToDoItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.azure.todolist.model.Result

class ItemListRepositoryImpl: ItemListRepository {

    private val azureService: AzureToDoService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://exampletodolist.azurewebsites.net/")
        .build()
        .create(AzureToDoService::class.java)

    override fun getToDoList(): Result<List<ToDoItem>> {
        val serviceCall = azureService.getToDoList()

        return try {
            val response = serviceCall.execute()
            if (response.isSuccessful && response.body() != null) {
                Result.completeWithSuccess(response.body()!!.toInternalModel())
            } else {
                Result.completeWithError("No response from service", emptyList())
            }
        } catch (e: Exception) {
            Result.completeWithError("Exception occurred", emptyList())
        }
    }

    override fun addItem(item: ToDoItem) {
        Log.d("##Repo", "Add item ${item.id}/${item.content}")
    }

    override fun updateItem(item: ToDoItem): Result<ToDoItem> {
        val serviceCall = azureService.updateToDoItem(item.id, AzureToDoResponse.ToDoItemResponse("", "", item.content, item.isDone))

        return try {
            val response = serviceCall.execute()
            if (response.isSuccessful && response.body() != null) {
                Result.completeWithSuccess(response.body()!!.toInternalModel())
            } else {
                Result.completeWithError("No response from service", null)
            }
        } catch (e: Exception) {
            Result.completeWithError("Exception occurred", null)
        }
    }

    override fun deleteItem(item: ToDoItem) {
        Log.d("##Repo", "Delete item ${item.id}/${item.content}")
    }
}