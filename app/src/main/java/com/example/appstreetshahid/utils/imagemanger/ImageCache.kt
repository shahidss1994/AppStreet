package com.example.appstreetshahid.utils.imagemanger

import android.graphics.Bitmap
import androidx.collection.LruCache
import javax.inject.Singleton


@Singleton
class ImageCache(private val lruCache: LruCache<String, Bitmap>) {

    fun addImage(key: String?, value: Bitmap) {
        if (key != null && lruCache.get(key) == null) {
            lruCache.put(key, value)
        }
    }

    fun getImage(key: String): Bitmap? {
        return lruCache.get(key)
    }

    fun getLruCache(): LruCache<String, Bitmap> {
        return lruCache
    }

    /*fun removeImage(key: String) {
        lruCache.remove(key)
    }

    fun clearCache() {
        lruCache.evictAll()
    }*/

}