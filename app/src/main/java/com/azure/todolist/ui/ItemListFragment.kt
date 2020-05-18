package com.azure.todolist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azure.todolist.viewmodel.ItemListViewModel
import com.azure.todolist.R
import com.azure.todolist.model.ToDoItem
import com.azure.todolist.model.Result
import kotlinx.android.synthetic.main.fragment_item_list.*

class ItemListFragment : Fragment(), ItemListCallback {

    private val itemListAdapter = ItemListAdapter(this)
    private lateinit var itemListViewModel: ItemListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        setupViewModel()
        addObservers()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun setupViewModel() {
        itemListViewModel = ViewModelProvider(this).get(ItemListViewModel::class.java)
        itemListViewModel.refreshToDoList()
    }

    private fun setupRecycler() {
        recyclerItems.adapter = itemListAdapter
        recyclerItems.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun addObservers() {
        itemListViewModel.toDoItemList.observe(viewLifecycleOwner, Observer { result -> displayToDoList(result) })
        itemListViewModel.toDoItemList.observe(viewLifecycleOwner, Observer { result -> checkResultForError(result) })
    }

    private fun displayToDoList(result: Result<List<ToDoItem>>) {
        if (result.state == Result.State.COMPLETE_SUCCESS) {
            result.resultData?.let { itemListAdapter.setItemList(it) }
        } else {
            checkResultForError(result)
        }
    }

    private fun checkResultForError(result: Result<*>) {
        if (result.state == Result.State.COMPLETE_ERROR) {
            val toast = Toast.makeText(requireContext(), "Error: ${result.message}", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    override fun onItemDeleteClick(item: ToDoItem) {
        itemListViewModel.deleteItem(item)
        itemListAdapter.deleteItem(item)
    }

    override fun onItemToggleDone(item: ToDoItem, isChecked: Boolean) {
        if (isChecked != item.isDone) {
            itemListViewModel.toggleItemDone(item)
            itemListAdapter.updateItem(item)
        }
    }
}
