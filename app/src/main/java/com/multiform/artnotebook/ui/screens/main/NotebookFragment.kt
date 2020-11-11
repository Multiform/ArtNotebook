package com.multiform.artnotebook.ui.screens.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.multiform.artnotebook.R
import com.multiform.artnotebook.databinding.FragmentNotebookBinding
import com.multiform.artnotebook.ui.screens.viewBinding
import com.multiform.artnotebook.utilits.APP_ACTIVITY
import com.multiform.artnotebook.viewmodels.main.MainScreenViewModel
import kotlinx.android.synthetic.main.activity_main.*

class NotebookFragment : Fragment(R.layout.fragment_notebook) {

    private val binding by viewBinding { FragmentNotebookBinding.bind(it) }

    private val viewModel by viewModels<MainScreenViewModel>()

    private val adapter = MainScreenAdapter()

    override fun onResume() {
        super.onResume()
        setHeader()
        setFooter()
    }

    private fun setHeader() {
        APP_ACTIVITY.apply {
            btnFind.visibility = View.VISIBLE
            btnMenu.visibility = View.VISIBLE
            btnNotesMap.visibility = View.VISIBLE
            mainHeaderBar.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorAccent))
            // btnNotesMap.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_map_primary))
            mainTitle.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorBlack))
            mainTitle.text = "ВСЕ ЗАПИСИ"
        }
    }

    private fun setFooter() {
        APP_ACTIVITY.apply {
            btnNotesImage.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_assignment_blue))
            btnPeopleImage.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_people_primary))
            btnSettingsImage.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_settings_primary))
            btnNotes.isEnabled = false
            btnPeople.isEnabled = true
            btnSettings.isEnabled = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            notebookRecyclerView.adapter = adapter

            viewModel.data.observe(viewLifecycleOwner, Observer {
                adapter.items = it
            })
        }
    }
}
