package com.shiraj.network.service.device

import com.shiraj.core.model.DeviceDetailModel
import com.shiraj.core.webservice.DeviceInfoWS
import com.shiraj.network.networkCall
import com.shiraj.network.response.toDevicesDetails
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