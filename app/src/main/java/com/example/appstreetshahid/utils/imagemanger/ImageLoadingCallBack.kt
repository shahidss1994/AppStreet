package com.example.appstreetshahid.utils.imagemanger

import android.graphics.Bitmap

interface ImageLoadingCallBack{
    fun onSuccess(bitmap: Bitmap)
    fun onFail()
}