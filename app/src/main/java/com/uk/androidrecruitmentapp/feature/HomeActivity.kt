package com.uk.androidrecruitmentapp.feature

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.uk.androidrecruitmentapp.BaseActivity
import com.uk.androidrecruitmentapp.R
import kotlinx.android.synthetic.main.main_activity.*

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setupNavigation()
    }

    private fun setupNavigation() {
        NavigationUI.setupWithNavController(mainBottomNavBar,
                findNavController(R.id.mainNavFragment))
    }
}