package com.pureblacksoft.gradinfo.service

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.app.JobIntentService
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.pureblacksoft.gradinfo.data.Grad
import com.pureblacksoft.gradinfo.function.LogFun
import org.json.JSONException
import java.io.ByteArrayOutputStream
import java.net.URL

class GradDataService : JobIntentService()
{
    companion object {
        private const val URL_GRADINFO = "https://pureblack.000webhostapp.com/gradinfo/"
        private const val URL_DATA_GRAD = URL_GRADINFO + "script/db_grad_data.php"
        private const val URL_IMAGE_GRAD = URL_GRADINFO + "image/story/"

        var gradList = mutableListOf<Grad>()

        var onSuccess: (() -> Unit)? = null
        var onFailure: (() -> Unit)? = null

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, GradDataService::class.java, 1, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        val requestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, URL_DATA_GRAD, null,
            { response ->
                LogFun.logDataI("(Connection Successful) -> $URL_DATA_GRAD")

                try {
                    val resultArray = response.getJSONArray("results")
                    val raLength = resultArray.length()
                    for (i in 0 until raLength) {
                        val jsonObject = resultArray.getJSONObject(i)
                        val gradNumber = jsonObject.getString("grad_number").toInt()
                        val gradName = jsonObject.getString("grad_name")
                        val degreeName = jsonObject.getString("degree_name")
                        val gradYear = jsonObject.getString("grad_year").toInt()

                        //region Grad Image
                        //region Get Bitmap
                        val imageName = jsonObject.getString("grad_image")
                        val imageURL = URL(URL_IMAGE_GRAD + imageName)
                        val conn = imageURL.openConnection()
                        conn.doInput = true
                        conn.connect()
                        val inputStream = conn.getInputStream()
                        val imageBitmap = BitmapFactory.decodeStream(inputStream)
                        //endregion

                        //region Convert Bitmap to ByteArray
                        val outputStream = ByteArrayOutputStream()
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        val gradImage = outputStream.toByteArray()
                        imageBitmap.recycle()
                        //endregion
                        //endregion

                        LogFun.logDataI("Grad $gradNumber -> Add")

                        gradList.add(
                            Grad(
                                number = gradNumber,
                                name = gradName,
                                degree = degreeName,
                                year = gradYear,
                                image = gradImage
                            )
                        )
                    }

                    onSuccess?.invoke()
                }
                catch (e: JSONException) {
                    LogFun.logDataE(e.toString())

                    onFailure?.invoke()
                }
            },
            { error ->
                LogFun.logDataI("(Connection Failed) -> $URL_DATA_GRAD")
                LogFun.logDataE(error.toString())

                onFailure?.invoke()
            })
        requestQueue.add(jsonObjectRequest)
    }
}