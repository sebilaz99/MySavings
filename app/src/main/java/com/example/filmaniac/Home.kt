package com.example.filmaniac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.filmaniac.databinding.ActivityMainBinding
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class Home : AppCompatActivity() {

    lateinit var navigationBar: ChipNavigationBar

    private val manager = supportFragmentManager
    private val settingsFragment = SettingsFragment()
    private val extrasFragment = ExtrasFragment()
    private val homeFragment = HomeFragment()
    private val myListsFragment = MyListsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.baby_blue)

        navigationBar = findViewById(R.id.bottomNavBar)

        navigationBar.setOnItemSelectedListener {
            when (it) {
                R.id.homeItem -> {
                    manager.beginTransaction()
                        .replace(R.id.main_container, homeFragment)
                        .commit()
                }

                R.id.settingsItem -> {
                    manager.beginTransaction()
                        .replace(R.id.main_container, settingsFragment)
                        .commit()
                }
                R.id.extrasItem -> {
                    manager.beginTransaction()
                        .replace(R.id.main_container, extrasFragment)
                        .commit()

                }
                R.id.listsItem -> {
                    manager.beginTransaction()
                        .replace(R.id.main_container, myListsFragment)
                        .commit()

                }
            }

        }
    }

}
