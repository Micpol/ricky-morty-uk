package com.uk.androidrecruitmentapp

import com.uk.androidrecruitmentapp.data.model.Episode
import com.uk.androidrecruitmentapp.data.network.response.RickyAndMortyResponse
import com.uk.androidrecruitmentapp.data.network.ApiService
import com.uk.androidrecruitmentapp.data.util.RequestExecutor
import com.uk.androidrecruitmentapp.data.network.response.ApiResponse
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.internal.EMPTY_RESPONSE
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class RequestExecutorTest {

    private val service = mockk<ApiService> {
        every { loadEpisodesAsync() } returns CompletableDeferred()
    }
    private val response = mockk<RickyAndMortyResponse<Episode>>()

    private val requestExecutor = RequestExecutor()

    @Test
    fun `success callback is called`() = runBlockingTest {
        every { service.loadEpisodesAsync() } returns CompletableDeferred(
            Response.success(response)
        )

        val response = requestExecutor.execute(service.loadEpisodesAsync())

        assert(response is ApiResponse.Success)
    }

    @Test
    fun `error callback is called`() = runBlockingTest {
        every { service.loadEpisodesAsync() } returns CompletableDeferred(
            Response.error(401, EMPTY_RESPONSE)
        )

        val response = requestExecutor.execute(service.loadEpisodesAsync())

        assert(response is ApiResponse.Error)
    }

    @Test(expected = HttpException::class)
    fun `connection error`() = runBlockingTest {
        every { service.loadEpisodesAsync() } throws HttpException(
            Response.error<Episode>(401, EMPTY_RESPONSE)
        )

        val response = requestExecutor.execute(service.loadEpisodesAsync())

        assert(response is ApiResponse.Error)
    }

    @Test(expected = Throwable::class)
    fun `response cannot be parsed error`() = runBlockingTest {
        every { service.loadEpisodesAsync() } throws Throwable()

        val response = requestExecutor.execute(service.loadEpisodesAsync())

        assert(response is ApiResponse.Error)
    }
}
