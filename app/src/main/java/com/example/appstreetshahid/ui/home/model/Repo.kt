package com.example.appstreetshahid.ui.home.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repo(
    val name: String? = null,
    val description: String? = null,
    val url: String? = null
) : Parcelable