package com.myapp.openmic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.myapp.openmic.fragments.admin.AdminFragment
import com.myapp.openmic.fragments.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  private lateinit var homeFragment: HomeFragment
  private lateinit var adminFragment: AdminFragment
  private lateinit var fragmentManager: FragmentManager
  private var fragmentList: ArrayList<Fragment> = ArrayList()
  private lateinit var activeFragment: Fragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    instantiate()
    initialize()
    listen()
    load()
  }

  private fun initialize() {
    fragmentManager = supportFragmentManager
    fragmentList = ArrayList()
    homeFragment = HomeFragment()
    adminFragment = AdminFragment()
    activeFragment = homeFragment
  }

  private fun instantiate() {

  }

  private fun listen() {
    bottomNavigation.setOnItemSelectedListener { item ->

      when (item.itemId) {
        R.id.admin -> {
          fragmentManager.beginTransaction().replace(R.id.fragmentContainer,adminFragment).commit()
          true
        }
        R.id.home -> {
          fragmentManager.beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit()
          true
        }
        else -> false
      }

    }

  }

  private fun load() {

  }
}