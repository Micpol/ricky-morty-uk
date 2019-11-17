package com.uk.androidrecruitmentapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.uk.androidrecruitmentapp.BaseActivity
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.feature.HomeActivity
import com.uk.androidrecruitmentapp.feature.locations.list.LocationsAdapter
import com.uk.androidrecruitmentapp.utils.getVM
import javax.inject.Inject

class SplashActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: SplashVM by lazy { getVM<SplashVM>(viewModelFactory) }

    private val locationsAdapter by lazy { LocationsAdapter() }

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