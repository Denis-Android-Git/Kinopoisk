package com.example.kinopoisk

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kinopoisk.databinding.ActivityMainBinding
import com.example.kinopoisk.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), HomeFragment.BottomNavBarVisibilityListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val sharedPrefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val isFirstRun = sharedPrefs.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            navController.navigate(R.id.onboardingFragment)
            val editor = sharedPrefs.edit()
            editor.putBoolean("isFirstRun", false)
            editor.apply()
        } else {
            navController.navigate(R.id.navigation_home)
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        supportActionBar?.hide()

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                    true
                }

                R.id.navigation_dashboard -> {
                    navController.navigate(R.id.navigation_dashboard)
                    true
                }

                R.id.navigation_notifications -> {
                    navController.navigate(R.id.navigation_notifications)
                    true
                }

                else -> false
            }
        }
    }

    override fun setBottomNavBarVisibility(isVisible: Boolean) {
        if (::navView.isInitialized) {
            navView.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }
}