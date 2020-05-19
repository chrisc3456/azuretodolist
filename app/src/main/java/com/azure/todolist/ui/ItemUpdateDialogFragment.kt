package com.azure.todolist.ui

import androidx.appcompat.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.azure.todolist.R
import com.azure.todolist.databinding.FragmentItemUpdateDialogBinding
import com.azure.todolist.model.ToDoItem
import com.azure.todolist.model.Result
import com.azure.todolist.viewmodel.ItemViewModel

class ItemUpdateDialogFragment: DialogFragment() {

    private var itemId: String? = null
    private val itemViewModel: ItemViewModel by navGraphViewModels(R.id.nav_main)
    private lateinit var binding: FragmentItemUpdateDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemId = ItemUpdateDialogFragmentArgs.fromBundle(requireArguments()).itemId
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_item_update_dialog, null, false)

        val builder = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(getTitle())
            .setNegativeButton(resources.getString(R.string.dialog_button_negative), null)
            .setPositiveButton(resources.getString(R.string.dialog_button_positive)) { _, _ -> saveItem() }

        return builder.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item_update_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        itemId?.let { itemViewModel.refreshToDoItem(itemId!!) }
        itemViewModel.toDoItem.observe(viewLifecycleOwner, Observer { result -> displayItem(result) })
    }

    private fun getTitle(): String =
        when (itemId) {
            null -> resources.getString(R.string.dialog_title_add)
            else -> resources.getString(R.string.dialog_title_edit)
        }

    private fun displayItem(result: Result<ToDoItem>) {
        if (result.state == Result.State.COMPLETE_SUCCESS) {
            binding.item = result.resultData
        }
    }

    private fun saveItem() {
        if (itemId == null) {
            itemViewModel.createItem(requireDialog().findViewById<EditText>(R.id.editTextItemContent).text.toString())
        } else {
            itemViewModel.updateItemContent(binding.item!!, requireDialog().findViewById<EditText>(R.id.editTextItemContent).text.toString())
        }
    }
}