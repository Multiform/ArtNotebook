package com.multiform.artnotebook.models.main

data class NotesTitleItem(
    val id: Long,
    var title: String,
    val notes: List<ListNote>
) : ListNote {
    override val itemId: Long = id
}