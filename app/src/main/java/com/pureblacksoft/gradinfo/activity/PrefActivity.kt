package com.pureblacksoft.gradinfo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.pureblacksoft.gradinfo.R
import com.pureblacksoft.gradinfo.databinding.ActivityPrefBinding

class PrefActivity : AppCompatActivity()
{
    companion object {
        private const val ID_THEME_DEFAULT = 0
        private const val ID_THEME_LIGHT = 1
        private const val ID_THEME_DARK = 2

        private var currentTheme = ID_THEME_DEFAULT
    }

    private lateinit var binding: ActivityPrefBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrefBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkCurrentTheme()

        //region Buttons
        binding.imgToolbarBackPA.setOnClickListener {
            onBackPressed()
        }

        binding.rltDefaultThemePA.setOnClickListener {
            currentTheme = ID_THEME_DEFAULT
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

        binding.rltLightThemePA.setOnClickListener {
            currentTheme = ID_THEME_LIGHT
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding.rltDarkThemePA.setOnClickListener {
            currentTheme = ID_THEME_DARK
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        //endregion
    }

    private fun checkCurrentTheme() {
        when (currentTheme) {
            ID_THEME_DEFAULT -> binding.imgDefaultThemePA.setImageResource(R.drawable.ic_check_v2)
            ID_THEME_LIGHT -> binding.imgLightThemePA.setImageResource(R.drawable.ic_check_v2)
            ID_THEME_DARK -> binding.imgDarkThemePA.setImageResource(R.drawable.ic_check_v2)
        }
    }
}