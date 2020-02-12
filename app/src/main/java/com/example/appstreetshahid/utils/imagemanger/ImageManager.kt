package com.example.appstreetshahid.utils.imagemanger

import android.graphics.Bitmap
import androidx.appcompat.widget.AppCompatImageView

class ImageManager(private val imageCache: ImageCache) {

    private val imageUrlList: HashMap<Int, DownloadImageTask> = HashMap()


    fun loadImage(imageUrl: String,
                  imageView: AppCompatImageView?,
                  imageLoadingCallBack: ImageLoadingCallBack) {
        if (!imageUrlList.containsKey(imageView.hashCode())) {
            val bitmap = imageCache.getImage(imageUrl)
            if (bitmap == null) {
                val downloadImageTask = DownloadImageTask(imageUrl, imageView, object : ImageLoadingCallBack {
                    override fun onSuccess(bitmap: Bitmap) {
                        imageCache.addImage(imageUrl, bitmap)
                        imageLoadingCallBack.onSuccess(bitmap)
                    }

                    override fun onFail() {
                        imageLoadingCallBack.onFail()
                    }

                })
                downloadImageTask.execute()
            } else {
                imageView?.setImageBitmap(bitmap)
                imageLoadingCallBack.onSuccess(bitmap)
            }
        }
    }

    fun clearView(imageView: AppCompatImageView?) {
        if (imageView != null) {
            imageUrlList[imageView.hashCode()]?.imageView = null
        }
    }

}