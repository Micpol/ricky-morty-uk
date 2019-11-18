package com.uk.androidrecruitmentapp.feature.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uk.androidrecruitmentapp.BaseFragment
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.feature.episodes.list.EpisodeAdapter
import com.uk.androidrecruitmentapp.utils.addOnScrolledEvent
import com.uk.androidrecruitmentapp.utils.getVM
import kotlinx.android.synthetic.main.fragment_episodes.*
import javax.inject.Inject

class EpisodesFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: EpisodesVM by lazy { getVM<EpisodesVM>(viewModelFactory) }

    private val episodesAdapter by lazy { EpisodeAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_episodes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observe()
    }

    private fun setupList() {
        episodesRV.apply {
            adapter = episodesAdapter
            val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            layoutManager = linearLayoutManager
            addOnScrolledEvent {
                viewModel.onListScrolled(linearLayoutManager.findLastCompletelyVisibleItemPosition(), episodesAdapter.itemCount)
            }
        }
    }

    private fun observe() {
        viewModel.progressVisibility.observe(this, Observer {
            if (it) {
                listLoaderPB.visibility = View.VISIBLE
            } else {
                listLoaderPB.visibility = View.GONE
            }
        })
        viewModel.episodesList.observe(this, Observer {
            episodesAdapter.submitData(it.toMutableList())
        })
        viewModel.toastMessage.observe(this, Observer { errorMsgResId ->
            context?.let { Toast.makeText(it, errorMsgResId, Toast.LENGTH_SHORT).show() }
        })
        viewModel.loadingMoreVisibility.observe(this, Observer {
            if (it) {
                episodesAdapter.showLoading()
            } else {
                episodesAdapter.hideLoading()
            }
        })
    }
}
