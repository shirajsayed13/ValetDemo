package com.shiraj.core.usecase

import com.shiraj.core.model.DeviceDetailModel
import com.shiraj.core.webservice.DeviceInfoWS
import javax.inject.Inject

class GetDeviceInfoUseCase @Inject constructor(
    private val deviceInfoWS: DeviceInfoWS
) {

    suspend operator fun invoke(): List<DeviceDetailModel> {
        return deviceInfoWS.getDeviceInfoWS()
    }
}