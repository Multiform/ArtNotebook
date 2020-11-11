package com.multiform.artnotebook.ui.screens.register

import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.auth.PhoneAuthProvider
import com.multiform.artnotebook.R
import com.multiform.artnotebook.database.*
import com.multiform.artnotebook.utilits.APP_ACTIVITY
import com.multiform.artnotebook.utilits.AppTextWatcher
import com.multiform.artnotebook.utilits.restartActivity
import com.multiform.artnotebook.utilits.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_register_code.*

class RegisterCodeFragment(private val phoneNumber: String, private val id: String) : Fragment(R.layout.fragment_register_code) {

    override fun onStart() {
        super.onStart()

        APP_ACTIVITY.mainHeaderBar.visibility = View.GONE
        APP_ACTIVITY.mainFooterBar.visibility = View.GONE

        registerInputCode.addTextChangedListener(AppTextWatcher {
            if (registerInputCode.text.toString().length == 6) verificationCode()
        })
    }

    private fun verificationCode() {
        val code = registerInputCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uId = AUTH.currentUser?.uid.toString()
                val dataMap = mutableMapOf<String, Any>()
                dataMap[CHILD_ID] = uId
                dataMap[CHILD_PHONE] = phoneNumber

                REF_DATABASE_ROOT.child(
                    NODE_USERS
                ).child(uId).updateChildren(dataMap)
                    .addOnCompleteListener {task2 ->
                        if (task2.isSuccessful) {
                            showToast(getString(R.string.register_welcome))
                            restartActivity()
                        } else showToast(task2.exception?.message.toString())
                    }
            } else showToast(task.exception?.message.toString())
        }
    }

}
