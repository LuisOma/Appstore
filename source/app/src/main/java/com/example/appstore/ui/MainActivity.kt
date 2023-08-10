package com.example.appstore.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.appstore.R
import com.example.appstore.base.BaseActivity
import com.example.appstore.data.local.Prefs
import com.example.appstore.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initComponents() {
        Prefs.init(applicationContext)
    }
}