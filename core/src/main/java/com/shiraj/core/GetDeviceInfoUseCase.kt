package com.shiraj.core

import javax.inject.Inject

class GetDeviceInfoUseCase @Inject constructor(
    private val deviceInfoWS: DeviceInfoWS
) {

    suspend operator fun invoke(): List<DeviceDetailModel> {
        return deviceInfoWS.getDeviceInfoWS()
    }
}