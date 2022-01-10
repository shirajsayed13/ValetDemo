package com.shiraj.gui.detail

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.shiraj.base.fragment.BaseFragment
import com.shiraj.gui.R
import com.shiraj.gui.ValetActivity
import com.shiraj.gui.databinding.FragmentDeviceDetailBinding
import com.shiraj.gui.loadUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceDetailFragment : BaseFragment() {

    override val layoutResId: Int
        get() = R.layout.fragment_device_detail

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        get() = FragmentDeviceDetailBinding::inflate

    override val binding: FragmentDeviceDetailBinding
        get() = super.binding as FragmentDeviceDetailBinding

    private val deviceInfo: DeviceDetailFragmentArgs by navArgs()

    override fun onInitView() {

        binding.apply {
            deviceInfo.deviceInfo?.let {
                (activity as ValetActivity).supportActionBar?.title = it.title
                val title = "Name - ${it.title}"
                tvDeviceName.text = title
                val type = "OS - ${it.type}"
                tvDeviceOS.text = type
                val status = "Status - ${it.status}"
                tvDeviceStatus.text = status
                val price = "Price - \$${it.price}"
                tvDeviceSize.text = price
                rdReview.rating = it.review.toFloat()
                ivDeviceImage.loadUrl(it.imageUrl)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        val menuIcon = menu.findItem(R.id.favorite)
        deviceInfo.deviceInfo?.let {
            if (it.isFavorite) {
                menuIcon.setIcon(R.drawable.ic_favorite)
            } else menuIcon.setIcon(R.drawable.ic_not_favorite)
        }
    }
}