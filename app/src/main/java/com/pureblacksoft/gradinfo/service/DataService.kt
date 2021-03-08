package com.pureblacksoft.gradinfo.service

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.pureblacksoft.gradinfo.R
import com.pureblacksoft.gradinfo.data.Grad
import org.json.JSONException

class DataService : JobIntentService()
{
    companion object {
        private const val TAG = "DataService"

        private const val URL_GRADINFO = "https://pureblack.000webhostapp.com/gradinfo/"
        private const val URL_DATA_GRAD = URL_GRADINFO + "script/db_data.php"
        private const val URL_IMAGE_GRAD = URL_GRADINFO + "image/grad/"

        var degreeList = mutableListOf<String>()
        var yearList = mutableListOf<String>()
        var gradList = mutableListOf<Grad>()

        var onSuccess: (() -> Unit)? = null
        var onFailure: (() -> Unit)? = null

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, DataService::class.java, 1, intent)
        }
    }

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate: Running")
    }

    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "onHandleWork: Running")

        degreeList.add(getString(R.string.Filter_Degree_All))
        yearList.add(getString(R.string.Filter_Year_All))

        val stringRequest = JsonObjectRequest(Request.Method.GET, URL_DATA_GRAD, null,
            { response ->
                Log.d(TAG, "Connection successful: $URL_DATA_GRAD")

                try {
                    //region Degree Data
                    val degreeArray = response.getJSONArray("degree_array")
                    val daLength = degreeArray.length()
                    for (i in 0 until daLength) {
                        if (isStopped) return@JsonObjectRequest

                        val degreeObject = degreeArray.getJSONObject(i)
                        degreeList.add(degreeObject.getString("degree_name"))

                        Log.d(TAG, "Degree ${i + 1}: Added")
                    }
                    //endregion

                    //region Year Data
                    val yearArray = response.getJSONArray("year_array")
                    val yaLength = yearArray.length()
                    for (i in 0 until yaLength) {
                        if (isStopped) return@JsonObjectRequest

                        val yearObject = yearArray.getJSONObject(i)
                        yearList.add(yearObject.getString("year_name"))

                        Log.d(TAG, "Year ${i + 1}: Added")
                    }
                    //endregion

                    //region Grad Data
                    val gradArray = response.getJSONArray("grad_array")
                    val gaLength = gradArray.length()
                    for (i in 0 until gaLength) {
                        if (isStopped) return@JsonObjectRequest

                        val gradObject = gradArray.getJSONObject(i)
                        gradList.add(
                            Grad(
                                number = gradObject.getInt("grad_number"),
                                name = gradObject.getString("grad_name"),
                                degree = gradObject.getString("degree_name"),
                                year = gradObject.getString("year_name"),
                                image = URL_IMAGE_GRAD + gradObject.getString("grad_image")
                            )
                        )

                        Log.d(TAG, "Grad ${i + 1}: Added")
                    }
                    //endregion

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