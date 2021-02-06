package com.uk.androidrecruitmentapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.uk.androidrecruitmentapp.BaseActivity
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.feature.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val viewModel: SplashVM by viewModels<SplashVMImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        observe()
    }

    private fun observe() {
        viewModel.splashLoading.observe(this, Observer {
            if (it) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        })
    }
}