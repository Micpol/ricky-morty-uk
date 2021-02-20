package com.uk.androidrecruitmentapp.presentation.episodes

import android.util.Log
import androidx.lifecycle.*
import com.uk.androidrecruitmentapp.domain.usecase.GetEpisodeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

abstract class EpisodesVM : ViewModel() {

    abstract val singleEvent: Flow<EpisodesEvent>
    abstract val viewState: StateFlow<EpisodesViewState>

    abstract suspend fun processIntent(intent: EpisodesIntent)
}

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class EpisodesVMImpl @Inject constructor(
    private val getEpisodesUseCase: GetEpisodeListUseCase
) : EpisodesVM() {

    private val _eventChannel = Channel<EpisodesEvent>(Channel.BUFFERED)
    private val _intentFlow = MutableSharedFlow<EpisodesIntent>(extraBufferCapacity = 64)

    override val viewState: StateFlow<EpisodesViewState>
    override val singleEvent: Flow<EpisodesEvent> get() = _eventChannel.receiveAsFlow()

    override suspend fun processIntent(intent: EpisodesIntent) = _intentFlow.emit(intent)

    init {
        val initialViewState = EpisodesViewState.initial()

        viewState = merge(
            _intentFlow.filterIsInstance<EpisodesIntent.Initial>().take(1),
            _intentFlow.filterNot { it is EpisodesIntent.Initial }
        )
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
            .toPartialChangeFlow()
            .sendSingleEvent()
            .scan(initialViewState) { vs, change -> change.reduce(vs) }
            .catch { Log.d("LogChannel", "[EpisodesVMImpl] Throwable: $it") }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                initialViewState
            )
    }

    @FlowPreview
    private fun Flow<EpisodesIntent>.toPartialChangeFlow(): Flow<EpisodesPartialChange> {
        val getUserChanges = getEpisodesUseCase.getEpisodes(1)
            .onEach { Log.d("LogChannel", "EpisodesVMImpl toPartialChangeFlow onEach ${it.joinToString { episode -> "${episode.id}" }}") }
            .map {
                Log.d("LogChannel", "EpisodesVMImpl toPartialChangeFlow: mapping");
                EpisodesPartialChange.GetEpisodes.Success(it) as EpisodesPartialChange.GetEpisodes
            }
            .onStart {
                Log.d("LogChannel", "EpisodesVMImpl toPartialChangeFlow: onStart")
                emit(EpisodesPartialChange.GetEpisodes.Loading)
            }
            .catch {
                Log.d("LogChannel", "EpisodesVMImpl toPartialChangeFlow: catch ${it.stackTrace}")
                emit(EpisodesPartialChange.GetEpisodes.Error(it))
            }

        return merge(
            filterIsInstance<EpisodesIntent.Initial>()
                .onEach { Log.d("LogChannel", "EpisodesVMImpl filterIsInstance<ViewIntent.Initial>(): ${it::class.java.name}") }
                .flatMapConcat { getUserChanges },
            filterIsInstance<EpisodesIntent.LoadNextEpisodes>()
                .filter { viewState.value.let { !it.isLoading && it.error === null } }
                .onEach { Log.d("LogChannel", "EpisodesVMImpl filterIsInstance<ViewIntent.LoadNextEpisodes>(): ${it::class.java.name}") }
                .flatMapFirst { getUserChanges }
        )
    }

    private fun Flow<EpisodesPartialChange>.sendSingleEvent(): Flow<EpisodesPartialChange> {
        return onEach {
            Log.d("LogChannel", "EpisodesVMImpl sendSingleEvent: $it")
            val event = when (it) {
                EpisodesPartialChange.GetEpisodes.Loading -> return@onEach
                is EpisodesPartialChange.GetEpisodes.Success -> return@onEach
                is EpisodesPartialChange.GetEpisodes.Error -> EpisodesEvent.GetEpisodes.Failure(it.error)
            }
            _eventChannel.send(event)
        }
    }
}

@ExperimentalCoroutinesApi
fun <T, R> Flow<T>.flatMapFirst(transform: suspend (value: T) -> Flow<R>): Flow<R> =
    map(transform).flattenFirst()

@ExperimentalCoroutinesApi
fun <T> Flow<Flow<T>>.flattenFirst(): Flow<T> = channelFlow {
    val outerScope = this
    val busy = AtomicBoolean(false)
    collect { inner ->
        if (busy.compareAndSet(false, true)) {
            launch {
                try {
                    inner.collect { outerScope.send(it) }
                    busy.set(false)
                } catch (e: CancellationException) {
                    // cancel outer scope on cancellation exception, too
                    outerScope.cancel(e)
                }
            }
        }
    }
}

