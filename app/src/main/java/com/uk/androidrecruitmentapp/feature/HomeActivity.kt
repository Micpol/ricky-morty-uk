package com.uk.androidrecruitmentapp.feature

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.uk.androidrecruitmentapp.BaseActivity
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.databinding.MainActivityBinding

class HomeActivity : BaseActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        NavigationUI.setupWithNavController(
            binding.mainBottomNavBar,
            findNavController(R.id.mainNavFragment)
        )
    }
}