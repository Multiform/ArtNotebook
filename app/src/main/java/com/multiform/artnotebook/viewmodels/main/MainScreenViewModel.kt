package com.multiform.artnotebook.viewmodels.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.multiform.artnotebook.models.main.ListNote
import com.multiform.artnotebook.models.main.NoteItem
import com.multiform.artnotebook.models.main.NotesTitleItem
import com.multiform.artnotebook.viewmodels.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenViewModel : BaseViewModel() {

    private val _data = MutableLiveData<List<ListNote>>()
    val data: LiveData<List<ListNote>> = _data

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val items = getItems()
            _data.postValue(items)
        }
    }

    private suspend fun getItems(): List<ListNote> {
        return listOf(
            NotesTitleItem(0, "Блок 1",
                IntRange(1, 20).map {
                    NoteItem(
                        it.toLong(),
                        "Title ${factorial(it)}",
                        "Text text text ${factorial(it)}"
                    )
                }),
            NotesTitleItem(1, "Блок 2",
                IntRange(1, 20).map {
                    NoteItem(
                        it.toLong(),
                        "Title ${factorial(it)}",
                        "Text text text ${factorial(it)}"
                    )
                })
        )
    }

    private fun factorial(num: Int): Long {
        var result = 1L
        for (i in 2..num) result *= i
        return result
    }
}