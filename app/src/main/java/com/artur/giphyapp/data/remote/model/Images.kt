package com.artur.giphyapp.data.remote.model

import com.google.gson.annotations.SerializedName


data class Images(
   @SerializedName("downsized_medium") val downsized: Downsized
)