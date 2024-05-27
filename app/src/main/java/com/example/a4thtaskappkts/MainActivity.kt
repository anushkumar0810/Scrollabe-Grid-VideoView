package com.example.a4thtaskappkts

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.a4thtaskappkts.Fragments.ExploreFragment
import com.example.a4thtaskappkts.Fragments.HomeFragment
import com.example.a4thtaskappkts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnTouchListener {

    private lateinit var binding: ActivityMainBinding
    private var xAxis: Float = 0f
    private var yAxis: Float = 0f
    private var lastAction: Int = 0
    private var progressDialog: ProgressDialog? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Please Wait")
        progressDialog!!.show()
        progressDialog!!.setCancelable(false)
        Log.d("TAG", "onCreateView: $progressDialog")

        binding.floatingButton.setOnTouchListener(this)

        binding.bottomNavigationView.menu.findItem(R.id.nav_home)
            .setIcon(R.drawable.ic_home_filled)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            val itemId = item.itemId

            if (itemId == R.id.nav_home) {
                loadFragment(HomeFragment(), false)
                binding.bottomNavigationView.menu.findItem(R.id.nav_home)
                    .setIcon(R.drawable.ic_home_filled)
                binding.bottomNavigationView.menu.findItem(R.id.nav_search)
                    .setIcon(R.drawable.ic_search_outline)
            } else { // explore fragment
                loadFragment(ExploreFragment(), false)
                binding.bottomNavigationView.menu.findItem(R.id.nav_search)
                    .setIcon(R.drawable.ic_search_filled)
                binding.bottomNavigationView.menu.findItem(R.id.nav_home)
                    .setIcon(R.drawable.ic_home_outlined)
            }
            false
        }
        loadFragment(HomeFragment(), true)

        Thread {
            try {
                Thread.sleep(5000)
            } catch (_: Exception) {
            }
            progressDialog!!.dismiss()
        }.start()

    }


    private fun loadFragment(fragment: Fragment, isAppInitialized: Boolean) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (isAppInitialized) {
            fragmentTransaction.add(R.id.frame_container, fragment)
        } else {
            fragmentTransaction.replace(R.id.frame_container, fragment)
        }

        fragmentTransaction.commit()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                xAxis = v!!.x - event.rawX
                yAxis = v.y - event.rawY
                lastAction = MotionEvent.ACTION_DOWN
            }

            MotionEvent.ACTION_MOVE -> {
                v!!.x = event.rawX + xAxis
                v.y = event.rawY + yAxis
                lastAction = MotionEvent.ACTION_MOVE
            }

            MotionEvent.ACTION_UP -> if (lastAction == MotionEvent.ACTION_DOWN) {
                // Set On Click Event
                Toast.makeText(this, "Btn Clicked", Toast.LENGTH_SHORT).show()
            }

            else -> return false
        }
        return false
    }
}