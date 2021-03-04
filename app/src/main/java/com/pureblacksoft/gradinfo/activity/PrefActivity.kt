package com.pureblacksoft.gradinfo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pureblacksoft.gradinfo.R
import com.pureblacksoft.gradinfo.databinding.ActivityPrefBinding
import com.pureblacksoft.gradinfo.function.PrefFun
import com.pureblacksoft.gradinfo.function.StoreFun
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PrefActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityPrefBinding
    private lateinit var storeFun: StoreFun

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrefBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storeFun = StoreFun(this)

        checkTheme()

        //region Buttons
        binding.imgToolbarBackPA.setOnClickListener {
            onBackPressed()
        }

        binding.rltDefaultThemePA.setOnClickListener {
            changeTheme(PrefFun.ID_THEME_DEFAULT)
        }

        binding.rltLightThemePA.setOnClickListener {
            changeTheme(PrefFun.ID_THEME_LIGHT)
        }

        binding.rltDarkThemePA.setOnClickListener {
            changeTheme(PrefFun.ID_THEME_DARK)
        }
        //endregion
    }

    private fun checkTheme() {
        when (PrefFun.currentThemeId) {
            PrefFun.ID_THEME_DEFAULT -> binding.imgDefaultThemePA.setImageResource(R.drawable.ic_check_v2)
            PrefFun.ID_THEME_LIGHT -> binding.imgLightThemePA.setImageResource(R.drawable.ic_check_v2)
            PrefFun.ID_THEME_DARK -> binding.imgDarkThemePA.setImageResource(R.drawable.ic_check_v2)
        }
    }

    private fun changeTheme(themeId: Int) {
        if (PrefFun.currentThemeId != themeId) {
            GlobalScope.launch {
                storeFun.storePref(themeId)
                finish()
            }
        }
    }
}