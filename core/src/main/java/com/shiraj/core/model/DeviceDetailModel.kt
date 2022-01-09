package com.shiraj.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeviceDetailModel(
    val currency: String,
    val description: String,
    val id: String,
    val imageUrl: String,
    val isFavorite: Boolean,
    val price: Int,
    val title: String,
    val status: String,
    val type: String,
    val review: Int
) : Parcelable
