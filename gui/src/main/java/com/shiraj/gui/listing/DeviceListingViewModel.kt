package com.shiraj.gui.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shiraj.base.viewmodel.BaseViewModel
import com.shiraj.core.model.DeviceDetailModel
import com.shiraj.core.usecase.GetDeviceInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DeviceListingViewModel @Inject constructor(
    private val getDeviceInfoUseCase: GetDeviceInfoUseCase
) : BaseViewModel() {

    internal lateinit var listingInfo: List<DeviceDetailModel>

    private var newInfoList: MutableSet<DeviceDetailModel> = mutableSetOf()

    internal fun getSearchData(newText: String?): List<DeviceDetailModel> {
        newInfoList.clear()
        newText?.let {
            if (it.length > 2) {
                for (info in listingInfo) {
                    if (info.title.contains(newText, ignoreCase = true)) {
                        newInfoList.add(info)
                    }
                }
            }
        }
        return newInfoList.toList()
    }

    private val _deviceInfo: MutableLiveData<List<DeviceDetailModel>> by lazy { MutableLiveData() }
    internal val deviceInfo: LiveData<List<DeviceDetailModel>> = _deviceInfo

    internal fun loadDeviceInfo() {
        Timber.d("loadDeviceInfo: ")
        launchUseCase {
            _deviceInfo.postValue(getDeviceInfoUseCase())
        }
    }
}