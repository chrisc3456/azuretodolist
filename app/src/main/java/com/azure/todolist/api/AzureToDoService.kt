package com.azure.todolist.api

import retrofit2.Call
import retrofit2.http.*

interface AzureToDoService {

    @GET("api/todo")
    fun getToDoList(): Call<List<AzureToDoResponse.ToDoItemResponse>>

    @GET("api/todo/{id}")
    fun getToDoItem(
        @Path("id") id: String
    ): Call<AzureToDoResponse.ToDoItemResponse>

    @POST("api/todo")
    fun createToDoItem(): Call<AzureToDoResponse.ToDoItemResponse>

    @PUT("api/todo/{id}")
    fun updateToDoItem(
        @Path("id") id: String
    ): Call<AzureToDoResponse.ToDoItemResponse>

    @DELETE("api/todo/{id}")
    fun deleteToDoItem(
        @Path("id") id: String
    ): Call<AzureToDoResponse.ToDoItemResponse>
}