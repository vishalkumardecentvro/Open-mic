package com.myapp.openmic

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class Utils {

  companion object {
    val PICK_IMAGE = 1
    val TOAST_LENGTH_SHORT = 2;
    val TOAST_LENGTH_LONG = 3;

    fun navigate(context: Context, fragment: Fragment, name: String) {

      val fragmentManager = (context as FragmentActivity).supportFragmentManager
      val fragmentTransaction = fragmentManager.beginTransaction()
      fragmentTransaction.replace(R.id.fragmentContainer, fragment)
      fragmentTransaction.addToBackStack(name)
      fragmentTransaction.commit()
    }

    fun toast(context: Context, text: String, toastLength: Int) {
      if (toastLength.equals(2))
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
      else
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
  }
}