package com.uk.androidrecruitmentapp.presentation.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> Fragment.getVM(factory: ViewModelProvider.Factory) =
    ViewModelProviders.of(this, factory).get(T::class.java)

inline fun <reified T : ViewModel> FragmentActivity.getVM(factory: ViewModelProvider.Factory) =
    ViewModelProviders.of(this, factory).get(T::class.java)
