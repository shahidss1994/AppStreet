package com.example.appstreetshahid.ui.home.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubTrending(
    val username: String? = null,
    val name: String? = null,
    val type: String? = null,
    val url: String? = null,
    val avatar: String? = null,
    val repo: Repo? = null
) : Parcelable {
    companion object{
        const val TAG = "GithubTrending"
    }
}