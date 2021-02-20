package com.uk.androidrecruitmentapp.presentation.utils

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

private class LifecycleBoundObserver(private val flow: Flow<*>) : DefaultLifecycleObserver {
    private var job: Job? = null

    override fun onStart(owner: LifecycleOwner) {
        job = flow.launchIn(owner.lifecycleScope)
    }

    override fun onStop(owner: LifecycleOwner) {
        cancelJob()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        owner.lifecycle.removeObserver(this)
        cancelJob()
    }

    private fun cancelJob() {
        job?.cancel()
        job = null
    }
}

fun <T> Flow<T>.addsLifecycleOwner(owner: LifecycleOwner) {
    if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
        // ignore
        return
    }
    owner.lifecycle.addObserver(LifecycleBoundObserver(this))
}
