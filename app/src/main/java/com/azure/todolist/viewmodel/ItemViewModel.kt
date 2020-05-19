package com.azure.todolist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azure.todolist.model.ToDoItem
import com.azure.todolist.model.Result
import com.azure.todolist.repo.ItemListRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel: ViewModel() {

    private val itemListRepository = ItemListRepositoryImpl()
    val toDoItemList = MutableLiveData<Result<List<ToDoItem>>>()
    val toDoItem = MutableLiveData<Result<ToDoItem>>()
    val updatedToDoItem = MutableLiveData<Result<ToDoItem>>()

    fun refreshToDoList() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoItemList.postValue(
                itemListRepository.getToDoList()
            )
        }
    }

    fun refreshToDoItem(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoItem.postValue(
                itemListRepository.getToDoItem(id)
            )
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

    fun createItem(content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updatedToDoItem.postValue(
                itemListRepository.addItem(ToDoItem("", content, false))
            )
        }
    }

    private fun updateItem(item: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            updatedToDoItem.postValue(
                itemListRepository.updateItem(item)
            )
        }
    }
}