package com.pureblacksoft.gradinfo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pureblacksoft.gradinfo.R
import com.pureblacksoft.gradinfo.databinding.ActivityMainBinding
import com.pureblacksoft.gradinfo.function.PrefFun
import com.pureblacksoft.gradinfo.function.StoreFun
import com.pureblacksoft.gradinfo.service.GradDataService

class MainActivity : AppCompatActivity()
{
    companion object {
        var onSuccessfulService: (() -> Unit)? = null
    }

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var storeFun: StoreFun

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storeFun = StoreFun(this)
        observeData()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.containerMA) as NavHostFragment
        navController = navHostFragment.findNavController()

        //region Bottom Navigation
        binding.bottomNavMA.setupWithNavController(navController)
        binding.bottomNavMA.itemIconTintList = null
        //endregion

        //region Get Grad Data
        startGradDataService()
        //endregion

        //region Events
        GradDataService.onSuccess = {
            binding.spinKitMA.visibility = View.GONE

            onSuccessfulService?.invoke()
        }

        GradDataService.onFailure = {
            binding.spinKitMA.visibility = View.GONE
        }
        //endregion
    }

    private fun observeData() {
        storeFun.themeIdFlow.asLiveData().observe(this, {
            PrefFun.currentThemeId = it
            PrefFun.setAppTheme()
        })
    }

    private fun startGradDataService() {
        binding.spinKitMA.visibility = View.VISIBLE

        val intent = Intent(this, GradDataService::class.java)
        GradDataService.enqueueWork(this, intent)
    }
}