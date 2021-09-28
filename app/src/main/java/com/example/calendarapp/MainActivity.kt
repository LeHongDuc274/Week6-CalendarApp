package com.example.calendarapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.calendarapp.adapter.FragmentCollectionAdapter
import com.example.calendarapp.fragment.CalendarFragment
import com.example.calendarapp.fragment.ConfigFragment
import com.example.calendarapp.fragment.NotesFragment
import com.example.calendarapp.viewmodels.MyViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var toolbar: Toolbar
    private lateinit var viewmodel: MyViewModel
    lateinit var navBottom: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomView()
        loadFragment(NotesFragment(), "1")
        viewmodel = ViewModelProvider(this)[MyViewModel::class.java]
        toolbar = findViewById(R.id.tool_bar_main)
        setupMenu(toolbar)
    }

    private fun setupBottomView() {
        navBottom = findViewById(R.id.bottom_bar)

        navBottom.setOnItemSelectedListener(
            object : NavigationBarView.OnItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.notesFragment -> {
                            loadFragment(NotesFragment(), "1")
                            return true
                        }
                        R.id.calendarFragment -> {
                            loadFragment(CalendarFragment(), "2")
                            return true
                        }
                        R.id.configFragment -> {
                            loadFragment(ConfigFragment(), "3")
                            return true
                        }
                    }
                    return false
                }

            }
        )
    }

    private fun loadFragment(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.addToBackStack(tag)
        transaction.commit()
    }

    private fun setupMenu(toolBar: androidx.appcompat.widget.Toolbar?) {
        toolBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.su -> {
                    viewmodel._startDay.value = 1

                }
                R.id.mo -> {
                    viewmodel._startDay.value = 2

                }
                R.id.tu -> {
                    viewmodel._startDay.value = 3
                    //  Log.e("viewmodel", viewmodel._startDay.value.toString())
                }
                R.id.we -> {
                    viewmodel._startDay.value = 4

                }
                R.id.th -> {
                    viewmodel._startDay.value = 5
                }
                R.id.fr -> {
                    viewmodel._startDay.value = 6

                }
                R.id.sa -> {
                    viewmodel._startDay.value = 7

                }
            }
            true
        }
    }

    override fun onBackPressed() {
        val selectedItemId = navBottom.selectedItemId
        val count = supportFragmentManager.backStackEntryCount
        if (count > 1) {
            if (selectedItemId != R.id.notesFragment) {
                supportFragmentManager.popBackStack("1", POP_BACK_STACK_INCLUSIVE)
                navBottom.selectedItemId = R.id.notesFragment
            } else super.onBackPressed()
        } else {
            AlertDialog.Builder(this)
                .setMessage("Are you sure exit app?")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { _, _ ->
                    finish()
                }
                .setNegativeButton("No", null)
                .create()
                .show()
        }
    }
}