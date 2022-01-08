package com.shiraj.network.service.device

import com.shiraj.network.response.DevicesResponse
import retrofit2.http.GET

interface RetrofitDeviceWebService {

    @GET("devices")
    suspend fun getDeviceWebService(): DevicesResponse

}