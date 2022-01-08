package com.shiraj.core.webservice

import com.shiraj.core.model.DeviceDetailModel

interface DeviceInfoWS {

    suspend fun getDeviceInfoWS(): List<DeviceDetailModel>

}