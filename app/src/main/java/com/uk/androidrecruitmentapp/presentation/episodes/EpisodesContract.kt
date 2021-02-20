package com.uk.androidrecruitmentapp.presentation.episodes

import com.uk.androidrecruitmentapp.data.model.Episode

sealed class EpisodesIntent {
    object Initial : EpisodesIntent()
    object LoadNextEpisodes : EpisodesIntent()
}

data class EpisodesViewState(
    val episodes: List<Episode>,
    val isLoading: Boolean,
    val error: Throwable?
) {
    companion object {
        fun initial() = EpisodesViewState(
            episodes = emptyList(),
            isLoading = true,
            error = null
        )
    }
}

sealed class EpisodesPartialChange {

    abstract fun reduce(vs: EpisodesViewState): EpisodesViewState

    sealed class GetEpisodes : EpisodesPartialChange() {

        override fun reduce(vs: EpisodesViewState): EpisodesViewState = when (this) {
            Loading -> vs.copy(
                isLoading = true,
                error = null
            )
            is Success -> vs.copy(
                isLoading = false,
                error = null,
                episodes = users
            )
            is Error -> vs.copy(
                isLoading = false,
                error = error
            )
        }

        object Loading : GetEpisodes()
        data class Success(val users: List<Episode>) : GetEpisodes()
        data class Error(val error: Throwable) : GetEpisodes()
    }
}

sealed class EpisodesEvent {

    sealed class GetEpisodes : EpisodesEvent() {
        data class Success(val episodes: List<Episode>) : GetEpisodes()
        data class Failure(val error: Throwable) : GetEpisodes()
    }
}
