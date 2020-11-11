package com.multiform.artnotebook.ui.screens.settings

import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.multiform.artnotebook.R
import com.multiform.artnotebook.database.*
import com.multiform.artnotebook.ui.screens.register.RegisterPhoneNumberFragment
import com.multiform.artnotebook.utilits.APP_ACTIVITY
import com.multiform.artnotebook.utilits.downloadAndSetImage
import com.multiform.artnotebook.utilits.replaceFragment
import com.multiform.artnotebook.utilits.showToast
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        setHeader()
        setFooter()
        initFields()
    }

    private fun setHeader() {
        APP_ACTIVITY.apply {
            btnFind.visibility = View.GONE
            btnMenu.visibility = View.GONE
            btnNotesMap.visibility = View.GONE
            mainHeaderBar.setBackgroundColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.colorPrimary
                )
            )
            // btnNotesMap.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_map_accent))
            mainTitle.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorAccent))
            mainTitle.text = "НАСТРОЙКИ"
        }
    }

    private fun setFooter() {
        APP_ACTIVITY.apply {
            btnNotesImage.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_assignment_primary
                )
            )
            btnPeopleImage.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_people_primary
                )
            )
            btnSettingsImage.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_settings_blue
                )
            )
            btnNotes.isEnabled = true
            btnPeople.isEnabled = true
            btnSettings.isEnabled = false
        }
    }

    private fun initFields() {
        settings_text_bio.text = USER.bio
        settings_text_user_name.text = USER.username
        settings_user_phone_number.text = USER.phone
        if (USER.fullname.isEmpty()) {
            settings_full_name.text = USER.username
        } else {
            settings_full_name.text = USER.fullname
        }
        settings_user_project_name.text = "Project Name"
        if (USER.photoUrl != "") {
            settings_user_photo.downloadAndSetImage(USER.photoUrl)
        }
        settings_btn_change_user_name.setOnClickListener { replaceFragment(ChangeUsernameFragment()) }
        settings_btn_change_bio.setOnClickListener { replaceFragment(ChangeBioFragment()) }
        settings_btn_change_user_photo.setOnClickListener { changeUserPhoto() }
    }

    private fun changeUserPhoto() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(APP_ACTIVITY, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == Activity.RESULT_OK && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(
                FOLDER_PROFILE_IMAGES
            ).child(UID)

            putImageToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDatabase(it) {
                        settings_user_photo.downloadAndSetImage(it)
                        showToast(getString(R.string.toast_data_update))
                        USER.photoUrl = it
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                replaceFragment(RegisterPhoneNumberFragment(), false)
            }
            R.id.settings_menu_change_name -> {
                replaceFragment(ChangeNameFragment())
            }
        }
        return true
    }
}
