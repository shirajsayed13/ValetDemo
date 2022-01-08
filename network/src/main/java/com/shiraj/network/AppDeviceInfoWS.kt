package com.shiraj.network

import com.shiraj.core.DeviceDetailModel
import com.shiraj.core.DeviceInfoWS
import javax.inject.Inject

class AppDeviceInfoWS @Inject constructor(
    private val retrofitDeviceWebService: RetrofitDeviceWebService
) : DeviceInfoWS {

    override suspend fun getDeviceInfoWS(): List<DeviceDetailModel> = networkCall(
        {
            retrofitDeviceWebService.getDeviceWebService()
        },
        { response ->
            response.devices.map { it.toDevicesDetails() }
        }
    )
}