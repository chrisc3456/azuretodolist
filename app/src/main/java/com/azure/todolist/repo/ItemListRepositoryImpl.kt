package com.azure.todolist.repo

import com.azure.todolist.api.AzureToDoResponse
import com.azure.todolist.api.AzureToDoResponse.toInternalModel
import com.azure.todolist.api.AzureToDoService
import com.azure.todolist.model.ToDoItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.azure.todolist.model.Result
import com.azure.todolist.BuildConfig
import retrofit2.Call

class ItemListRepositoryImpl: ItemListRepository {

    private val azureService: AzureToDoService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
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
            Result.completeWithError("Exception occurred: ${e.message}", emptyList())
        }
    }

    override fun addItem(item: ToDoItem): Result<ToDoItem> {
        val serviceCall = azureService.createToDoItem(AzureToDoResponse.ToDoItemResponse("", "", item.content, false))
        return handleToDoItemResponse(serviceCall)
    }

    override fun updateItem(item: ToDoItem): Result<ToDoItem> {
        val serviceCall = azureService.updateToDoItem(item.id, AzureToDoResponse.ToDoItemResponse("", "", item.content, item.isDone))
        return handleToDoItemResponse(serviceCall)
    }

    override fun deleteItem(item: ToDoItem): Result<ToDoItem> {
        val serviceCall = azureService.deleteToDoItem(item.id)
        return handleToDoItemResponse(serviceCall)
    }

    private fun handleToDoItemResponse(serviceCall: Call<AzureToDoResponse.ToDoItemResponse>): Result<ToDoItem> {
        return try {
            val response = serviceCall.execute()
            if (response.isSuccessful && response.body() != null) {
                Result.completeWithSuccess(response.body()!!.toInternalModel())
            } else {
                Result.completeWithError("No response from service", null)
            }
        } catch (e: Exception) {
            Result.completeWithError("Exception occurred: ${e.message}", null)
        }
    }
}