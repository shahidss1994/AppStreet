package com.example.appstreetshahid.utils.imagemanger

import android.graphics.Bitmap
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView

class ImageManager(private val imageCache: ImageCache) {

    private val imageUrlList: HashMap<String, DownloadImageTask> = HashMap()
    private val imageViewHashMap: HashMap<Int, String> = HashMap()

    fun loadImage(imageUrl: String,
                  imageView: AppCompatImageView?,
                  imageLoadingCallBack: ImageLoadingCallBack) {
        imageViewHashMap[imageView?.hashCode() ?: 0] = imageUrl
        if (!imageUrlList.containsKey(imageUrl)) {
            val bitmap = imageCache.getImage(imageUrl)
            if (bitmap == null) {
                Log.d("Download Initiated", "hashcode " + imageView?.hashCode())
                Log.d("Download Initiated", "imageUrl " + imageUrl)
                if (!imageUrlList.containsKey(imageUrl)) {
                    val downloadImageTask = DownloadImageTask(imageUrl, object : ImageLoadingCallBack {
                        override fun onSuccess(bitmap: Bitmap) {
                            imageCache.addImage(imageUrl, bitmap)
                            val exactImageUrl = imageViewHashMap[imageView?.hashCode() ?: 0]
                            Log.d("Download Done", "hashcode " + imageView?.hashCode())
                            Log.d("Download Done", "imageUrl " + imageUrl)
                            Log.d("Download Done", "exactImageUrl " + exactImageUrl)
                            if (exactImageUrl?.equals(imageUrl) == true) {
                                imageView?.setImageBitmap(bitmap)
                            }
                            imageLoadingCallBack.onSuccess(bitmap)
                        }

                        override fun onFail() {
                            imageLoadingCallBack.onFail()
                        }

                    })
                    downloadImageTask.execute()
                }
            } else {
                imageView?.setImageBitmap(bitmap)
                imageLoadingCallBack.onSuccess(bitmap)
            }
        }
    }

    fun clearView(url: String?) {
        if (url != null) {
        }
    }

}