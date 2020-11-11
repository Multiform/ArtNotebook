package com.multiform.artnotebook.ui.screens.settings

import com.multiform.artnotebook.R
import com.multiform.artnotebook.database.*
import com.multiform.artnotebook.utilits.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_change_name.*

class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.mainTitle.text = "CHANGE NAME"
        inputFullName()
    }

    private fun inputFullName() {
        if (USER.fullname.isNotEmpty() && USER.fullname.contains(" ")) {
            val fullnameSplit = USER.fullname.split(" ")
            settings_input_new_name.setText(fullnameSplit[0])
            settings_input_new_surname.setText(fullnameSplit[1])
        } else if (USER.fullname.isNotEmpty()) {
            settings_input_new_name.setText(USER.fullname)
        }
    }

    override fun change() {
        val name = settings_input_new_name.text.toString()
        val surname = settings_input_new_surname.text.toString()
        if (name.isEmpty()) {
            showToast(getString(R.string.settings_toast_empty_name))
        } else if (name.contains("[\\p{L}| ]+".toRegex()) || surname.contains("[\\p{L}| ]+".toRegex())) {
            showToast("Имя и фамилия не могут содержать пробелы и специальные символы")
        } else {
            val fullname = "$name $surname"
            REF_DATABASE_ROOT.child(
                NODE_USERS
            ).child(UID)
                .child(CHILD_FULLNAME).setValue(fullname).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_update))
                        USER.fullname = fullname
                        fragmentManager?.popBackStack()
                    } else {
                        showToast(it.exception?.message.toString())
                    }
                }
        }
    }
}
