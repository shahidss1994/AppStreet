package com.example.appstreetshahid.utils.imagemanger

import android.graphics.Bitmap
import androidx.appcompat.widget.AppCompatImageView

class ImageManager(private val imageCache: ImageCache) {

    private var imageView: AppCompatImageView? = null

    fun loadImage(imageUrl: String,
                  imageView: AppCompatImageView?,
                  imageLoadingCallBack: ImageLoadingCallBack) {
        this.imageView = imageView
        val bitmap = imageCache.getImage(imageUrl)
        if (bitmap == null) {
            if (!ImageManagerList.imageUrlList.containsKey(imageUrl)) {
                val downloadImageTask = DownloadImageTask(imageUrl, object : ImageLoadingCallBack {
                    override fun onSuccess(bitmap: Bitmap) {
                        imageCache.addImage(imageUrl, bitmap)
                        this@ImageManager.imageView?.setImageBitmap(bitmap)
                        imageLoadingCallBack.onSuccess(bitmap)
                        ImageManagerList.imageUrlList.remove(imageUrl)
                    }

                    override fun onFail() {
                        imageLoadingCallBack.onFail()
                        ImageManagerList.imageUrlList.remove(imageUrl)
                    }

                })
                ImageManagerList.imageUrlList[imageUrl] = downloadImageTask
                downloadImageTask.execute()
            }
        } else {
            ImageManagerList.imageUrlList.remove(imageUrl)
            this@ImageManager.imageView?.setImageBitmap(bitmap)
            imageLoadingCallBack.onSuccess(bitmap)
        }
    }

    fun clearView() {
        imageView = null
    }

}