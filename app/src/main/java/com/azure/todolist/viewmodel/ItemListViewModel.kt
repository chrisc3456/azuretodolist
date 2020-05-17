package com.azure.todolist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azure.todolist.model.ToDoItem
import com.azure.todolist.repo.ItemListRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemListViewModel: ViewModel() {

    private val itemListRepository = ItemListRepositoryImpl()
    val toDoItems = MutableLiveData<List<ToDoItem>>()

    fun refreshToDoList() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoItems.postValue(itemListRepository.getToDoList())
        }
    }

    fun toggleItemDone(item: ToDoItem) {
        item.isDone = !item.isDone
        updateItem(item)
    }

    fun updateItemContent(item: ToDoItem, newContent: String) {
        item.content = newContent
        updateItem(item)
    }

    fun deleteItem(item: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            itemListRepository.deleteItem(item)
        }
    }

    private fun updateItem(item: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            itemListRepository.updateItem(item)
        }
    }
}