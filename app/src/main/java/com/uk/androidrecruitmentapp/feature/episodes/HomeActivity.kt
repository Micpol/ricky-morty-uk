package com.uk.androidrecruitmentapp.feature.episodes

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.uk.androidrecruitmentapp.BaseActivity
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.remote.ApiService
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    @Inject
    lateinit var service: ApiService

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