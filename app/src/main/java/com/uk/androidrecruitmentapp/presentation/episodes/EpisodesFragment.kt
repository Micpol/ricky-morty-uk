package com.uk.androidrecruitmentapp.presentation.episodes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uk.androidrecruitmentapp.databinding.EpisodesFragmentBinding
import com.uk.androidrecruitmentapp.presentation.base.BaseFragment
import com.uk.androidrecruitmentapp.presentation.episodes.list.EpisodeAdapter
import com.uk.androidrecruitmentapp.presentation.utils.addOnScrolledEvent
import com.uk.androidrecruitmentapp.presentation.utils.addsLifecycleOwner
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class EpisodesFragment : BaseFragment() {

    private lateinit var binding: EpisodesFragmentBinding

    @FlowPreview
    private val viewModel: EpisodesVM by viewModels<EpisodesVMImpl>()
    private val intentChannel = Channel<EpisodesIntent>(Channel.BUFFERED)

    private val episodesAdapter by lazy { EpisodeAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = EpisodesFragmentBinding.inflate(inflater)
        observe()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
    }

    private fun setupList() {
        binding.episodesRV.apply {
            adapter = episodesAdapter
            val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            layoutManager = linearLayoutManager
            addOnScrolledEvent {
//                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == episodesAdapter.itemCount - 1) {
//                    runBlocking { viewModel.processIntent(EpisodesIntent.LoadNextEpisodes) }
//                }
            }
        }
    }

    private fun observe() {
        viewModel.singleEvent
            .onEach { handleSingleEvent(it) }
            .addsLifecycleOwner(viewLifecycleOwner)

        viewModel.viewState
            .onEach { render(it) }
            .addsLifecycleOwner(viewLifecycleOwner)

        intents()
            .onEach { viewModel.processIntent(it) }
            .launchIn(lifecycleScope)
    }

    private fun intents() = merge(
        flowOf(EpisodesIntent.Initial),
        intentChannel.consumeAsFlow()
    )

    private fun sentIntent(episodesIntent: EpisodesIntent) = intentChannel.safeOffer(episodesIntent)

    private fun render(it: EpisodesViewState) {
        Log.d("LogChannel", "EpisodesFragment render: ${it.episodes.joinToString(separator = "\t")}");
        episodesAdapter.submitData(it.episodes.toMutableList())
    }

    private fun handleSingleEvent(event: EpisodesEvent) {
        when (event) {
            is EpisodesEvent.GetEpisodes.Failure -> {
                Log.d("LogChannel", "handleSingleEvent: ${event.error.stackTrace}")
            }
            is EpisodesEvent.GetEpisodes.Success -> {
                Log.d("LogChannel", "handleSingleEvent: ${event.episodes.joinToString()}")
            }
        }
    }
}

fun <T> SendChannel<T>.safeOffer(element: T): Boolean {
    return runCatching { offer(element) }.getOrDefault(false)
}