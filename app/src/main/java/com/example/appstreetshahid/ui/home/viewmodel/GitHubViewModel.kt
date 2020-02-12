package com.example.appstreetshahid.ui.home.viewmodel

import android.app.Activity
import android.graphics.Bitmap
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.MutableLiveData
import com.example.appstreetshahid.di.BaseViewModel
import com.example.appstreetshahid.ui.home.model.GithubTrending
import com.example.appstreetshahid.utils.imagemanger.DownloadImageTask
import com.example.appstreetshahid.utils.imagemanger.ImageCache
import com.example.appstreetshahid.utils.imagemanger.ImageLoadingCallBack
import com.example.appstreetshahid.utils.imagemanger.ImageManager
import javax.inject.Inject

class GitHubViewModel(activity: Activity) : BaseViewModel(activity) {

    @Inject
    lateinit var imageManager: ImageManager

    private val name = MutableLiveData<String>()
    private val username = MutableLiveData<String>()
    private val description = MutableLiveData<String>()
    private val url = MutableLiveData<String>()
    private val imageBitmap = MutableLiveData<Bitmap>()
    private val reponame = MutableLiveData<String>()
    private val repoUrl = MutableLiveData<String>()

    fun bind(githubTrending: GithubTrending) {
        name.value = githubTrending.name
        username.value = "@${githubTrending.username}"
        description.value = githubTrending.repo?.description ?: ""
        url.value = githubTrending.url
        reponame.value = githubTrending.repo?.name ?: ""
        repoUrl.value = githubTrending.repo?.url
    }

    fun getName(): MutableLiveData<String> {
        return name
    }

    fun getUsername(): MutableLiveData<String> {
        return username
    }

    fun getReponame(): MutableLiveData<String> {
        return reponame
    }

    fun getDescription(): MutableLiveData<String> {
        return description
    }

    fun getImageBitmap(): MutableLiveData<Bitmap> {
        return imageBitmap
    }

    fun getUrl(): MutableLiveData<String> {
        return url
    }

    fun getRepoUrl(): MutableLiveData<String> {
        return repoUrl
    }

    fun loadImage(url: String?, imageView: AppCompatImageView?, imageLoadingCallBack: ImageLoadingCallBack) {
        if (url != null && !TextUtils.isEmpty(url) && imageView != null) {
            imageManager.loadImage(url, imageView, object : ImageLoadingCallBack {
                override fun onSuccess(bitmap: Bitmap) {
                    imageLoadingCallBack.onSuccess(bitmap)
                }

                override fun onFail() {
                    imageLoadingCallBack.onFail()
                }

            })
        }
    }

    fun unBind(imageView: AppCompatImageView?) {
        imageManager.clearView(imageView)
    }

}