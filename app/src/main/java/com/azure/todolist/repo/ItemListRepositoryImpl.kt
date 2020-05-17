package com.azure.todolist.repo

import android.util.Log
import com.azure.todolist.api.AzureToDoResponse.toInternalModel
import com.azure.todolist.api.AzureToDoService
import com.azure.todolist.model.ToDoItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemListRepositoryImpl: ItemListRepository {

    private val azureService: AzureToDoService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://exampletodolist.azurewebsites.net/")
        .build()
        .create(AzureToDoService::class.java)

    override fun getToDoList(): List<ToDoItem> {
        /*return listOf(
            ToDoItem("1", "First note", false),
            ToDoItem("2", "Second note is a bit longer", false),
            ToDoItem("3", "Third is even longer and is also complete", true)
        )*/
        val serviceCall = azureService.getToDoList()

        return try {
            val response = serviceCall.execute()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.items.toInternalModel()
            } else {
                Log.d("##Repo", "No response from service")
                emptyList<ToDoItem>()
            }
        } catch (e: Exception) {
            Log.d("##Repo", "Exception occurred: $e")
            emptyList<ToDoItem>()
        }
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