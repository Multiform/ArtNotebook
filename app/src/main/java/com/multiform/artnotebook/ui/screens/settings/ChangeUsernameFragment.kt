package com.multiform.artnotebook.ui.screens.settings

import com.multiform.artnotebook.R
import com.multiform.artnotebook.database.*
import com.multiform.artnotebook.utilits.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_change_username.*
import java.util.*

class ChangeUsernameFragment : BaseChangeFragment(R.layout.fragment_change_username) {

    private lateinit var username: String

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.mainTitle.text = "CHANGE USERNAME"
        inputUsername()
    }

    private fun inputUsername() {
        if (USER.username.isNotEmpty()) {
            settings_input_new_username.setText(USER.username)
        }
    }

    override fun change() {
        username = settings_input_new_username.text.toString().toLowerCase(Locale.getDefault())
        if (username.isEmpty()) {
            showToast(getString(R.string.settings_toast_empty_username))
        } else if (username.contains(" ")) {
            showToast("Имя пользователя не может содержать пробелы")
        } else {
            REF_DATABASE_ROOT.child(
                NODE_USERNAMES
            )
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if (it.hasChild(username)) {
                        showToast("Такое имя пользователя уже занято")
                    } else {
                        updateDataUsername()
                    }
                })
        }
    }

    private fun updateDataUsername() {
        REF_DATABASE_ROOT.child(
            NODE_USERNAMES
        ).child(username).setValue(UID)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUsername()
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(
            NODE_USERS
        ).child(UID).child(CHILD_USERNAME)
            .setValue(username)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    deleteOldUsername()
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    private fun deleteOldUsername() {
        REF_DATABASE_ROOT.child(
            NODE_USERNAMES
        ).child(USER.username).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    USER.username = username
                    fragmentManager?.popBackStack()
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }
}
