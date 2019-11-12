package com.uk.androidrecruitmentapp.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.uk.androidrecruitmentapp.BaseActivity
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject

class EpisodesActivity : BaseActivity() {

    @Inject
    lateinit var service: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initAdapter()
        fetchEpisodeFromRemote()
    }

    private fun fetchEpisodeFromRemote() {
        service.getPeople()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    list.adapter = EpisodeAdapter(it)
                    (list.adapter as EpisodeAdapter).notifyDataSetChanged()
                }, {})
    }

    private fun initAdapter() {
        list.layoutManager = LinearLayoutManager(this)
    }
}