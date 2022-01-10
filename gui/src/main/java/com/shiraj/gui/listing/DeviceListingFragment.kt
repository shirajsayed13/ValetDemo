package com.shiraj.gui.listing

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.shiraj.base.failure
import com.shiraj.base.fragment.BaseFragment
import com.shiraj.base.observe
import com.shiraj.core.model.DeviceDetailModel
import com.shiraj.gui.R
import com.shiraj.gui.databinding.FragmentDeviceListingBinding
import com.shiraj.gui.handleFailure
import dagger.hilt.android.AndroidEntryPoint
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
        viewModel.listingInfo = deviceInfo
        binding.rvDeviceInfo.apply {
            this.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listingAdapter
            listingAdapter.info = deviceInfo
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        listingAdapter.info = viewModel.getSearchData(newText)
        newText?.let { query ->
            if (query.length < 3)
                listingAdapter.info = viewModel.listingInfo
        }
        return true
    }
}