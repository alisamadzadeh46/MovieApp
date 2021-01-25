package com.example.movie.network

import com.example.movie.ZoneDateTimeProvider
import com.example.movie.ZoneDateTimeProvider.loadTimeZone
import com.example.movie.model.adapter.ZoneDateTimeAdapter
import com.squareup.moshi.Moshi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


@RunWith(JUnit4::class)
class MovieServiceTest {

    private lateinit var service: MovieService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun init() {
        ZoneDateTimeProvider.loadTimeZone()
        mockWebServer = MockWebServer()
        val moshi = Moshi.Builder()
            .add(ZoneDateTimeAdapter())
            .build()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @After
    fun cleanup() {
        mockWebServer.shutdown()
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {

        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)))
    }

}