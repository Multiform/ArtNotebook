package com.multiform.artnotebook.ui.screens.main

import android.view.View
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.multiform.artnotebook.databinding.ItemNoteBinding
import com.multiform.artnotebook.databinding.ItemNotesTitleBinding
import com.multiform.artnotebook.models.main.ListNote
import com.multiform.artnotebook.models.main.NoteItem
import com.multiform.artnotebook.models.main.NotesTitleItem

object NotebookScreenDelegates {

    val notesVerticalDelegate = adapterDelegateViewBinding<NotesTitleItem, ListNote, ItemNotesTitleBinding>(
        { inflater, container -> ItemNotesTitleBinding.inflate(inflater, container, false) }
    ) {
        // onCreateViewHolder
        val adapter = NoteItemsAdapter()
        binding.blocRecyclerView.adapter = adapter

        // onBindViewHolder
        bind {
            binding.title = item.title
            binding.apply {
                titleNotesBloc.setOnClickListener{
                    if (blocRecyclerView.visibility == View.VISIBLE) {
                        blocRecyclerView.visibility = View.GONE
                    } else blocRecyclerView.visibility = View.VISIBLE
                }
            }
            adapter.items = item.notes
        }

        // onViewRecycled
        onViewRecycled {
            //...
        }
    }

    val noteDelegate = adapterDelegateViewBinding<NoteItem, ListNote, ItemNoteBinding>(
        { inflater, container -> ItemNoteBinding.inflate(inflater, container, false)}
    ) {
        bind {
            binding.title = item.title
            binding.text = item.text
            binding.executePendingBindings()
        }
    }
}