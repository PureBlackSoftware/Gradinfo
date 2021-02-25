package com.pureblacksoft.gradinfo.service

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.pureblacksoft.gradinfo.data.Grad
import org.json.JSONArray
import org.json.JSONException

class GradDataService : JobIntentService()
{
    companion object {
        private const val TAG = "GradDataService"

        private const val URL_GRADINFO = "https://pureblack.000webhostapp.com/gradinfo/"
        private const val URL_DATA_GRAD = URL_GRADINFO + "script/db_grad_data.php"
        private const val URL_IMAGE_GRAD = URL_GRADINFO + "image/grad/"

        var gradList = mutableListOf<Grad>()

        var onSuccess: (() -> Unit)? = null
        var onFailure: (() -> Unit)? = null

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, GradDataService::class.java, 1, intent)
        }
    }

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate: Running")
    }

    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "onHandleWork: Running")

        val stringRequest = StringRequest(Request.Method.GET, URL_DATA_GRAD,
            { response ->
                Log.d(TAG, "Connection successful: $URL_DATA_GRAD")

                try {
                    val jsonArray = JSONArray(response)
                    val jaLength = jsonArray.length()
                    for (i in 0 until jaLength) {
                        if (isStopped) return@StringRequest

                        val jsonObject = jsonArray.getJSONObject(i)
                        gradList.add(
                            Grad(
                                number = jsonObject.getInt("grad_number"),
                                name = jsonObject.getString("grad_name"),
                                degree = jsonObject.getString("degree_name"),
                                year = jsonObject.getInt("grad_year"),
                                image = URL_IMAGE_GRAD + jsonObject.getString("grad_image")
                            )
                        )

                        Log.d(TAG, "Grad $i: Added")
                    }

                    onSuccess?.invoke()
                } catch (e: JSONException) {
                    Log.e(TAG, e.toString())

                    onFailure?.invoke()
                }
            },
            { error ->
                Log.d(TAG, "Connection failed: $URL_DATA_GRAD")
                Log.e(TAG, error.toString())

                onFailure?.invoke()
            })

        Volley.newRequestQueue(this).add(stringRequest)
    }

    override fun onStopCurrentWork(): Boolean {
        Log.d(TAG, "onStopCurrentWork: Running")

        return super.onStopCurrentWork()
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy: Running")
    }
}