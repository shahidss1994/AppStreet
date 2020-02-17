package com.example.appstreetshahid.utils.imagemanger

import android.graphics.Bitmap
import androidx.appcompat.widget.AppCompatImageView

class ImageManager(private val imageCache: ImageCache) {

    fun loadImage(imageUrl: String,
                  imageView: AppCompatImageView?,
                  imageLoadingCallBack: ImageLoadingCallBack) {
        ImageManagerList.hashCodeUrlMap[(imageView?.hashCode() ?: 0)] = imageUrl
        val bitmap = imageCache.getImage(imageUrl)
        if (bitmap == null) {
            if (!ImageManagerList.urlDownloadMap.containsKey(imageUrl)) {
                val downloadImageTask = DownloadImageTask(imageUrl, object : ImageLoadingCallBack {
                    override fun onSuccess(bitmap: Bitmap, url: String) {
                        imageCache.addImage(imageUrl, bitmap)
                        if (ImageManagerList.hashCodeUrlMap[(imageView?.hashCode()
                                        ?: 0)]?.equals(imageUrl) == true) {
                            imageView?.apply {
                                post { setImageBitmap(bitmap) }
                            }
                        }
                        imageLoadingCallBack.onSuccess(bitmap, url)
                        ImageManagerList.urlDownloadMap.remove(imageUrl)
                    }

                    override fun onFail() {
                        imageLoadingCallBack.onFail()
                        ImageManagerList.urlDownloadMap.remove(imageUrl)
                    }

                })
                ImageManagerList.urlDownloadMap[imageUrl] = downloadImageTask
                downloadImageTask.execute()
            }
        } else {
            ImageManagerList.urlDownloadMap.remove(imageUrl)
            imageView?.apply {
                post { setImageBitmap(bitmap) }
            }
            imageLoadingCallBack.onSuccess(bitmap, imageUrl)
        }
    }

}