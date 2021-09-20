package com.myapp.openmic

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class Utils {


  companion object {
    public val PICK_IMAGE = 1

    fun navigate(context: Context, fragment: Fragment, name: String) {

      val fragmentManager = (context as FragmentActivity).supportFragmentManager
      val fragmentTransaction = fragmentManager.beginTransaction()
      fragmentTransaction.replace(R.id.fragmentContainer, fragment)
      fragmentTransaction.addToBackStack(name)
      fragmentTransaction.commit()
    }
  }
}