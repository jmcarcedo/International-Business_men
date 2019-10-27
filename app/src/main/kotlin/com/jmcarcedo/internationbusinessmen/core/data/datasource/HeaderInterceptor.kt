package com.jmcarcedo.internationbusinessmen.core.data.datasource

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.net.SocketTimeoutException

class HeaderInterceptor : Interceptor {

    companion object {
        private const val ACCEPT_HEADER = "Accept"
        private const val ACCEPTED_FORMAT = "application/json"
    }
    override fun intercept(chain: Chain): Response {
        val builder = chain.request().newBuilder()

        builder.addHeader(ACCEPT_HEADER, ACCEPTED_FORMAT)

        val request = builder.build()
        try {
            return chain.proceed(request)
        } catch (error: SocketTimeoutException) {
            throw error
        }
    }
}