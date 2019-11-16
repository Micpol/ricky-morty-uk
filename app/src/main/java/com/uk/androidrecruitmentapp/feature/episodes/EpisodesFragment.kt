package com.uk.androidrecruitmentapp.feature.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.uk.androidrecruitmentapp.BaseFragment
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.remote.ApiService
import com.uk.androidrecruitmentapp.utils.getVM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_episodes.*
import javax.inject.Inject

class EpisodesFragment : BaseFragment() {

    @Inject
    lateinit var service: ApiService

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: EpisodesVM by lazy { getVM<EpisodesVM>(viewModelFactory) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_episodes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        list.layoutManager = LinearLayoutManager(context)
    }
}
