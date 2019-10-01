package com.uk.androidrecruitmentapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.uk.androidrecruitmentapp.R

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
    }

    override fun onResume() {
        super.onResume()
        startActivity(Intent(this, EpisodesActivity::class.java))
    }
}