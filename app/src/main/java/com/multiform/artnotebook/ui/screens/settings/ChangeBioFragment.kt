package com.multiform.artnotebook.ui.screens.settings

import com.multiform.artnotebook.R
import com.multiform.artnotebook.database.*
import com.multiform.artnotebook.utilits.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_change_bio.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    private lateinit var bio: String

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.mainTitle.text = "CHANGE BIO"
        inputBioPreview()
    }

    private fun inputBioPreview() {
        if (USER.bio.isNotEmpty()) {
            settings_text_new_bio.text = USER.bio
        }
    }

    override fun change() {
        bio = settings_input_new_bio.text.toString()
        if (bio.isEmpty()) {
            showToast("Поле с информацией пустое")
        } else {
            REF_DATABASE_ROOT.child(
                NODE_USERS
            ).child(UID).child(
                CHILD_BIO
            ).setValue(bio)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_update))
                        USER.bio = bio
                        fragmentManager?.popBackStack()
                    } else {
                        showToast(it.exception?.message.toString())
                    }
                }
        }

    }
}
