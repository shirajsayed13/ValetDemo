package com.shiraj.gui.listing

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shiraj.base.layoutInflater
import com.shiraj.core.model.DeviceDetailModel
import com.shiraj.gui.databinding.ItemDeviceInfoBinding
import com.shiraj.gui.loadUrl
import javax.inject.Inject
import kotlin.properties.Delegates

class DeviceInfoListingAdapter @Inject constructor() :
    RecyclerView.Adapter<DeviceInfoListingAdapter.DeviceInfoViewHolder>() {

    var onDeviceInfoClickListener: (DeviceDetailModel) -> Unit = {}

    var info: List<DeviceDetailModel> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    class DeviceInfoViewHolder(private val binding: ItemDeviceInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(info: DeviceDetailModel) {
            binding.apply {
                tvDeviceName.text = info.title
                tvDeviceStatus.text = info.status
                ivDeviceImage.loadUrl(info.imageUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceInfoViewHolder =
        DeviceInfoViewHolder(
            ItemDeviceInfoBinding.inflate(parent.layoutInflater, parent, false)
        ).apply {
            itemView.setOnClickListener {
                onDeviceInfoClickListener(info[adapterPosition])
            }
        }

    override fun onBindViewHolder(holder: DeviceInfoViewHolder, position: Int) =
        holder.bind(info[position])

    override fun getItemCount(): Int = info.size
}