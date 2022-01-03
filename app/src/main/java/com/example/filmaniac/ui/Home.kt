package com.example.filmaniac.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.filmaniac.HomeFragment
import com.example.filmaniac.R
import com.example.filmaniac.SettingsFragment
import com.example.filmaniac.fragments.extras.ExtrasFragment
import com.example.filmaniac.fragments.myLists.MyListsFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import androidx.fragment.app.FragmentManager


class Home : AppCompatActivity() {

    private lateinit var navigationBar: ChipNavigationBar

    private val manager = supportFragmentManager
    private val settingsFragment = SettingsFragment()
    private val extrasFragment = ExtrasFragment()
    private val homeFragment = HomeFragment()
    private val myListsFragment = MyListsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.main_container, homeFragment)
            .commit()

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
