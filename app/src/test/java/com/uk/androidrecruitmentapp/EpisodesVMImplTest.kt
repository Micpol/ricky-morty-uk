package com.uk.androidrecruitmentapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.uk.androidrecruitmentapp.data.local.Result
import com.uk.androidrecruitmentapp.data.source.Resource
import com.uk.androidrecruitmentapp.feature.episodes.EpisodesRepository
import com.uk.androidrecruitmentapp.feature.episodes.EpisodesVMImpl
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EpisodesVMImplTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private var loadEpisodesResponse: () -> Resource<List<Result>> = { Resource.Success(emptyList()) }
    private val repository = mockk<EpisodesRepository> {
        coEvery { loadEpisodes() } returns loadEpisodesResponse.invoke()
    }

    private val episodesListObserver = mockk<Observer<List<Result>>>(relaxed = true)
    private val toastMessageObserver = mockk<Observer<String>>(relaxed = true)
    private val progressVisibilityObserver = mockk<Observer<Boolean>>(relaxed = true)

    private lateinit var viewModel: EpisodesVMImpl
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        testDispatcher.pauseDispatcher()
        viewModel = EpisodesVMImpl(repository)
        viewModel.episodesList.observeForever(episodesListObserver)
        viewModel.toastMessage.observeForever(toastMessageObserver)
        viewModel.progressVisibility.observeForever(progressVisibilityObserver)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `progress is visible at start`() = runBlockingTest {
        verify { episodesListObserver.onChanged(emptyList()) }
        verify { progressVisibilityObserver.onChanged(true) }

        testDispatcher.resumeDispatcher()

        verify { progressVisibilityObserver.onChanged(false) }
    }

    @Test
    fun `progress disappear after success`() = runBlockingTest {
        verify { episodesListObserver.onChanged(emptyList()) }
        verify { progressVisibilityObserver.onChanged(true) }

        testDispatcher.resumeDispatcher()

        verify { episodesListObserver.onChanged(emptyList()) }
        verify { progressVisibilityObserver.onChanged(false) }
    }

    @Test
    fun `error occurred`() = runBlockingTest {
        coEvery { repository.loadEpisodes() } returns Resource.Error(Throwable("error"))

        testDispatcher.resumeDispatcher()

        verify { episodesListObserver.onChanged(emptyList()) }
        verify { progressVisibilityObserver.onChanged(false) }
        verify { toastMessageObserver.onChanged("error") }
    }
}
