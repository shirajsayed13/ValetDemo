package com.shiraj.network.response


import com.shiraj.core.model.DeviceDetailModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

internal fun DevicesResponse.Device.toDevicesDetails() = DeviceDetailModel(
    currency = currency,
    description = description,
    id = id,
    imageUrl = imageUrl,
    isFavorite = isFavorite,
    price = price,
    title = title,
    status = status,
    type = type,
    review = review
)

@JsonClass(generateAdapter = true)
data class DevicesResponse(
    @Json(name = "devices")
    val devices: List<Device>
) {
    @JsonClass(generateAdapter = true)
    data class Device(
        @Json(name = "Currency")
        val currency: String,
        @Json(name = "Description")
        val description: String,
        @Json(name = "Id")
        val id: String,
        @Json(name = "imageUrl")
        val imageUrl: String,
        @Json(name = "isFavorite")
        val isFavorite: Boolean,
        @Json(name = "Price")
        val price: Int,
        @Json(name = "Title")
        val title: String,
        @Json(name = "status")
        val status: String,
        @Json(name = "Type")
        val type: String,
        @Json(name = "review")
        val review: Int
    )
}