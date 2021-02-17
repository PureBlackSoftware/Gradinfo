package com.pureblacksoft.gradinfo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.pureblacksoft.gradinfo.R
import com.pureblacksoft.gradinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.containerMA) as NavHostFragment
        navController = navHostFragment.findNavController()

        //region Toolbar
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.searchFragment, R.id.userFragment))
        setSupportActionBar(binding.toolbarMA)
        setupActionBarWithNavController(navController, appBarConfiguration)
        //endregion

        //region Bottom Navigation
        binding.bottomNavMA.setupWithNavController(navController)
        binding.bottomNavMA.itemIconTintList = null
        //endregion
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}