package com.justforfun.bottlerocket

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.room.Room
import com.justforfun.bottlerocket.extensions.addFragment
import com.justforfun.bottlerocket.extensions.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

            val fragment = supportFragmentManager.findFragmentById(R.id.contentId)
            if(fragment is MapFragment)
            {
                fragment.manualRefresh()
            }
            else if (fragment is StoreListFragment){
                fragment.manualRefresh()
            }
            else{
                Log.d("MainActivity","Empty Fragment")
            }
            Snackbar.make(view, "Updating list of stores and saving in cache", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        var fragment = StoreListFragment.newInstance()
        replaceFragment(fragment,R.id.contentId)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        var fragment: Fragment?
        when (item.itemId) {
            R.id.nav_list -> {
                // Handle the camera action
                fragment = StoreListFragment.newInstance()
            }
            R.id.nav_map -> {
                fragment = MapFragment.newInstance()

            }
            else ->
            {
                fragment = StoreListFragment.newInstance()
            }

        }
        replaceFragment(fragment,R.id.contentId)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}
