package com.uk.androidrecruitmentapp.feature.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uk.androidrecruitmentapp.BaseFragment
import com.uk.androidrecruitmentapp.databinding.EpisodesFragmentBinding
import com.uk.androidrecruitmentapp.feature.episodes.list.EpisodeAdapter
import com.uk.androidrecruitmentapp.utils.addOnScrolledEvent

class EpisodesFragment : BaseFragment() {

    private lateinit var binding: EpisodesFragmentBinding

    private val viewModel: EpisodesVM by viewModels<EpisodesVMImpl>()

    private val episodesAdapter by lazy { EpisodeAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = EpisodesFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observe()
    }

    private fun setupList() {
        binding.episodesRV.apply {
            adapter = episodesAdapter
            val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            layoutManager = linearLayoutManager
            addOnScrolledEvent {
                viewModel.onListScrolled(linearLayoutManager.findLastCompletelyVisibleItemPosition(), episodesAdapter.itemCount)
            }
        }
    }

    private fun observe() {
        viewModel.progressVisibility.observe(viewLifecycleOwner, {
            if (it) {
                binding.listLoaderPB.visibility = View.VISIBLE
            } else {
                binding.listLoaderPB.visibility = View.GONE
            }
        })
        viewModel.episodesList.observe(viewLifecycleOwner, {
            episodesAdapter.submitData(it.toMutableList())
        })
        viewModel.toastMessage.observe(viewLifecycleOwner, { errorMsgResId ->
            context?.let { Toast.makeText(it, errorMsgResId, Toast.LENGTH_SHORT).show() }
        })
        viewModel.loadingMoreVisibility.observe(viewLifecycleOwner, {
            if (it) {
                episodesAdapter.showLoading()
            } else {
                episodesAdapter.hideLoading()
            }
        })
    }
}
