package com.uk.androidrecruitmentapp.presentation.utils

import android.content.Context
import android.view.LayoutInflater

val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)
