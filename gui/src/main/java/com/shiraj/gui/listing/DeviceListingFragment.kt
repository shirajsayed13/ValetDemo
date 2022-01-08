package com.shiraj.gui.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.shiraj.base.failure
import com.shiraj.base.fragment.BaseFragment
import com.shiraj.base.observe
import com.shiraj.core.model.DeviceDetailModel
import com.shiraj.core.webservice.WebServiceFailure
import com.shiraj.gui.AppToast
import com.shiraj.gui.R
import com.shiraj.gui.databinding.FragmentDeviceListingBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DeviceListingFragment : BaseFragment() {

    override val layoutResId: Int
        get() = R.layout.fragment_device_listing

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        get() = FragmentDeviceListingBinding::inflate

    override val binding: FragmentDeviceListingBinding
        get() = super.binding as FragmentDeviceListingBinding

    @Inject
    lateinit var deviceInfoListingAdapter: DeviceInfoListingAdapter

    private val viewModel: DeviceListingViewModel by viewModels()

    override fun onInitView() {

        viewModel.apply {
            failure(failure, ::handleFailure)
            observe(deviceInfo, ::showDeviceInfo)
            loadDeviceInfo()
        }

        deviceInfoListingAdapter.onDeviceInfoClickListener = {
            findNavController().navigate(DeviceListingFragmentDirections.toDeviceDetailFragment(it.copy()))
        }
    }

    private fun showDeviceInfo(deviceInfo: List<DeviceDetailModel>) {
        binding.rvDeviceInfo.apply {
            this.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = deviceInfoListingAdapter
            deviceInfoListingAdapter.info = deviceInfo
        }
    }

    private fun handleFailure(e: Exception?) {
        Timber.v("handleFailure: IN")
        Timber.e(e)
        when (e) {
            is WebServiceFailure.NoNetworkFailure -> showErrorToast("No internet connection!")
            is WebServiceFailure.NetworkTimeOutFailure, is WebServiceFailure.NetworkDataFailure -> showErrorToast(
                "Internal server error!"
            )
            else -> {
                showErrorToast("Data Error")
            }
        }
        Timber.v("handleFailure: OUT")
    }


    private fun Fragment.showErrorToast(msg: String) {
        AppToast.show(requireContext(), msg, Toast.LENGTH_SHORT)
    }

}