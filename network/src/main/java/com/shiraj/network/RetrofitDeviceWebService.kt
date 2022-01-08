package com.shiraj.network

import retrofit2.http.GET

interface RetrofitDeviceWebService {

    @GET("devices")
    suspend fun getDeviceWebService(): DevicesResponse

}