package com.example.appstreetshahid.utils.imagemanger

import android.graphics.Bitmap
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView

class ImageManager(private val imageCache: ImageCache) {

    private val imageUrlList: HashMap<String, DownloadImageTask> = HashMap()
    private var imageView: AppCompatImageView? = null

    fun loadImage(imageUrl: String,
                  imageView: AppCompatImageView?,
                  imageLoadingCallBack: ImageLoadingCallBack) {
        this.imageView = imageView
        if (!imageUrlList.containsKey(imageUrl)) {
            val bitmap = imageCache.getImage(imageUrl)
            if (bitmap == null) {
                if (!imageUrlList.containsKey(imageUrl)) {
                    val downloadImageTask = DownloadImageTask(imageUrl, object : ImageLoadingCallBack {
                        override fun onSuccess(bitmap: Bitmap) {
                            imageCache.addImage(imageUrl, bitmap)
                            this@ImageManager.imageView?.setImageBitmap(bitmap)
                            imageLoadingCallBack.onSuccess(bitmap)
                        }

                        override fun onFail() {
                            imageLoadingCallBack.onFail()
                        }

                    })
                    downloadImageTask.execute()
                }
            } else {
                this@ImageManager.imageView?.setImageBitmap(bitmap)
                imageLoadingCallBack.onSuccess(bitmap)
            }
        }
    }

    fun clearView() {
        imageView = null
    }

}