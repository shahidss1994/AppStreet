package com.example.appstreetshahid.utils.imagemanger

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.net.HttpURLConnection
import java.net.URL


class DownloadImageTask(private val imageUrl: String,
                        private val imageLoadingCallBack: ImageLoadingCallBack) : AsyncTask<Void, Void, Bitmap>() {

    private var inSampleSize = 0

    override fun doInBackground(vararg params: Void?): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        options.inSampleSize = inSampleSize

        return try {
            val url = URL(imageUrl)

            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val input = connection.inputStream
                val bmp = BitmapFactory.decodeStream(input)
                input.close()
                bmp
            } else {
                null
            }

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        if (result != null) {
            imageLoadingCallBack.onSuccess(result)
        } else {
            imageLoadingCallBack.onFail()
        }
    }

}