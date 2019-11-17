package com.uk.androidrecruitmentapp

import com.uk.androidrecruitmentapp.data.local.Episodes
import com.uk.androidrecruitmentapp.data.remote.ApiService
import com.uk.androidrecruitmentapp.data.remote.RequestExecutor
import com.uk.androidrecruitmentapp.data.remote.response.ApiResponse
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class RequestExecutorTest {

    private val service = mockk<ApiService> {
        every { loadEpisodesAsync() } returns CompletableDeferred()
    }
    private val response = mockk<Episodes>()

    private val requestExecutor = RequestExecutor()

    @Test
    fun `success callback is called`() = runBlockingTest {
        every { service.loadEpisodesAsync() } returns CompletableDeferred(
                Response.success(response)
        )

        val response = requestExecutor.execute(service.loadEpisodesAsync())

        assert(response is ApiResponse.Success)
    }
}
