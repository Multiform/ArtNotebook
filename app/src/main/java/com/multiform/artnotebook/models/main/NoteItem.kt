package com.multiform.artnotebook.models.main

data class NoteItem(
    val id: Long,
    var title: String,
    var text: String = "",
    var imageUrl: String = ""
) : ListNote {
    override val itemId: Long = id
}