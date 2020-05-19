package com.azure.todolist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.azure.todolist.viewmodel.ItemViewModel
import com.azure.todolist.R
import com.azure.todolist.model.ToDoItem
import com.azure.todolist.model.Result
import kotlinx.android.synthetic.main.fragment_item_list.*

class ItemListFragment : Fragment(), ItemListCallback {

    private val itemListAdapter = ItemListAdapter(this)
    private val itemViewModel: ItemViewModel by navGraphViewModels(R.id.nav_main)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        setupViewModel()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        fabAddItem.setOnClickListener { onAddButtonClick() }
    }

    private fun setupViewModel() {
        itemViewModel.refreshToDoList()
        itemViewModel.toDoItemList.observe(viewLifecycleOwner, Observer { result -> displayToDoList(result) })
        itemViewModel.updatedToDoItem.observe(viewLifecycleOwner, Observer { result -> displayUpdatedItem(result) })
    }

    private fun setupRecycler() {
        recyclerItems.adapter = itemListAdapter
        recyclerItems.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun displayToDoList(result: Result<List<ToDoItem>>) {
        if (result.state == Result.State.COMPLETE_SUCCESS) {
            result.resultData?.let { itemListAdapter.setItemList(it) }
        } else {
            checkResultForError(result)
        }
    }

    private fun displayUpdatedItem(result: Result<ToDoItem>) {
        if (result.state == Result.State.COMPLETE_SUCCESS) {
            result.resultData?.let { itemListAdapter.updateItem(it) }
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
        itemViewModel.deleteItem(item)
        itemListAdapter.deleteItem(item)
    }

    override fun onItemToggleDone(item: ToDoItem, isChecked: Boolean) {
        if (isChecked != item.isDone) {
            itemViewModel.toggleItemDone(item)
            itemListAdapter.updateItem(item)
        }
    }

    override fun onItemEditClick(item: ToDoItem) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemUpdateDialogFragment(item.id)
        findNavController().navigate(action)
    }

    private fun onAddButtonClick() {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemUpdateDialogFragment(null)
        findNavController().navigate(action)
    }
}
