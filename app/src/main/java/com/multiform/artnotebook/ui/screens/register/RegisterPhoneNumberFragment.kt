package com.multiform.artnotebook.ui.screens.register

import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.multiform.artnotebook.R
import com.multiform.artnotebook.database.AUTH
import com.multiform.artnotebook.utilits.APP_ACTIVITY
import com.multiform.artnotebook.utilits.replaceFragment
import com.multiform.artnotebook.utilits.restartActivity
import com.multiform.artnotebook.utilits.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_register_phone_number.*
import java.util.concurrent.TimeUnit

class RegisterPhoneNumberFragment : Fragment(R.layout.fragment_register_phone_number) {

    private lateinit var mPhoneNumber: String
    private lateinit var mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onStart() {
        APP_ACTIVITY.mainHeaderBar.visibility = View.GONE
        APP_ACTIVITY.mainFooterBar.visibility = View.GONE

        super.onStart()
        mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.register_welcome))
                        restartActivity()
                    } else showToast(it.exception?.message.toString())
                }
            }

            override fun onVerificationFailed(fe: FirebaseException) {
                showToast(fe.message.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(
                    RegisterCodeFragment(
                        mPhoneNumber,
                        id
                    )
                )
            }
        }
        registerBtnNext.setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        if (registerInputPhoneNumber.text.toString().isEmpty()) {
            showToast(getString(R.string.register_toast_empty_phone_number))
        } else {
            authUser()
        }
    }

    private fun authUser() {
        mPhoneNumber = registerInputPhoneNumber.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mPhoneNumber,
            60,
            TimeUnit.SECONDS,
            APP_ACTIVITY,
            mCallBack
        )
    }
}
