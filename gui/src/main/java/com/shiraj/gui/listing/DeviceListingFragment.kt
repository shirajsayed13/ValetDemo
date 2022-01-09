package com.shiraj.gui.listing

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
class DeviceListingFragment : BaseFragment(), SearchView.OnQueryTextListener {

    override val layoutResId: Int
        get() = R.layout.fragment_device_listing

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        get() = FragmentDeviceListingBinding::inflate

    override val binding: FragmentDeviceListingBinding
        get() = super.binding as FragmentDeviceListingBinding

    @Inject
    lateinit var listingAdapter: DeviceInfoListingAdapter

    private lateinit var listingInfo: List<DeviceDetailModel>

    private var newInfoList: MutableSet<DeviceDetailModel> = mutableSetOf()

    private val viewModel: DeviceListingViewModel by viewModels()

    override fun onInitView() {

        viewModel.apply {
            failure(failure, ::handleFailure)
            observe(deviceInfo, ::showDeviceInfo)
            loadDeviceInfo()
        }

        listingAdapter.onDeviceInfoClickListener = {
            findNavController().navigate(DeviceListingFragmentDirections.toDeviceDetailFragment(it.copy()))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.listing_menu, menu)
        val searchItem = menu.findItem(R.id.search)

        searchItem?.let {
            val searchView = it.actionView as SearchView
            searchView.queryHint = "Search Devices"
            searchView.setOnQueryTextListener(this)
        }
    }

    private fun showDeviceInfo(deviceInfo: List<DeviceDetailModel>) {
        listingInfo = deviceInfo
        binding.rvDeviceInfo.apply {
            this.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listingAdapter
            listingAdapter.info = deviceInfo
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            if (it.length > 2) {
                for (info in listingInfo) {
                    if (info.title.contains(newText, ignoreCase = true)) {
                        newInfoList.add(info)
                    }
                }
                listingAdapter.info = newInfoList.toList()
            } else {
                listingAdapter.info = listingInfo
            }
        }
        return true
    }

}