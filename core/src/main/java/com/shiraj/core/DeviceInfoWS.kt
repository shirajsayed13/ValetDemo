package com.shiraj.core

interface DeviceInfoWS {

    suspend fun getDeviceInfoWS(): List<DeviceDetailModel>

}