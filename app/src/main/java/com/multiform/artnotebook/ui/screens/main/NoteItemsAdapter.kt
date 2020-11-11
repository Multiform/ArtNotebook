package com.multiform.artnotebook.ui.screens.main

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.multiform.artnotebook.models.main.ListNote

class NoteItemsAdapter : AsyncListDifferDelegationAdapter<ListNote>(DIFF_CALLBACK) {
    private companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListNote>() {
            override fun areItemsTheSame(oldItem: ListNote, newItem: ListNote): Boolean =
                oldItem.itemId == newItem.itemId

            override fun areContentsTheSame(oldItem: ListNote, newItem: ListNote): Boolean =
                oldItem.equals(newItem)
        }
    }

    init {
        delegatesManager.addDelegate(NotebookScreenDelegates.noteDelegate)
    }
}