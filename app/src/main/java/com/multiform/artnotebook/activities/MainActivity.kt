package com.multiform.artnotebook.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.multiform.artnotebook.database.AUTH
import com.multiform.artnotebook.database.initFirebase
import com.multiform.artnotebook.database.initUser
import com.multiform.artnotebook.databinding.ActivityMainBinding
import com.multiform.artnotebook.ui.screens.main.NotebookFragment
import com.multiform.artnotebook.ui.screens.register.RegisterPhoneNumberFragment
import com.multiform.artnotebook.ui.screens.settings.SettingsFragment
import com.multiform.artnotebook.utilits.APP_ACTIVITY
import com.multiform.artnotebook.utilits.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initFirebase()
        initUser {
            initFields()
            initFuns()
        }
    }

    private fun initFuns() {
        if (AUTH.currentUser != null) {
            replaceFragment(NotebookFragment(), false)
            btnSettings.setOnClickListener {
                replaceFragment(SettingsFragment(), false)
            }
            btnNotes.setOnClickListener {
                replaceFragment(NotebookFragment(), false)
            }
            btnMenu.setOnClickListener {
                AUTH.signOut()
                replaceFragment(RegisterPhoneNumberFragment(), false)
            }
        } else {
            replaceFragment(RegisterPhoneNumberFragment(), false)
        }
    }

    private fun initFields() {
        APP_ACTIVITY = this
    }
}
