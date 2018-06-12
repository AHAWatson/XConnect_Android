package com.xpanxion.directory

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.xpanxion.architecture.*
import com.xpanxion.benchreport.BenchFragment
import com.xpanxion.profile.ProfileFragment
import kotlinx.android.synthetic.main.directory_layout.*

class DirectoryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, BenchFragment.BenchFragmentManager, TitledFragmentManager, TitledBackHandlerFragment.BackHandler {
    override var activeFragment: TitledBackHandlerFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.directory_layout)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()
        val fragment = BenchFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, BenchFragment.TAG)
                .commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (activeFragment == null) {
                supportFragmentManager.popBackStack()
            } else {
                activeFragment?.let {
                    if (!it.onBackPressed()) {
                        supportFragmentManager.popBackStack()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bench_report, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: TitledBackHandlerFragment? = null
        when (item.itemId) {
            R.id.nav_bench_report -> {
                fragment = BenchFragment()
            }
        }
        fragment?.let { frag ->
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, frag, BenchFragment.TAG)
                    .commit()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onListFragmentInteraction(item: BenchItem?) {
        when (item) {
            is Person -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment.newInstance(item))
                        .addToBackStack(null)
                        .commit()
            }
            is RoleItem -> {
            }
        }
    }

    override fun onTitleUpdated(title: String?) {
        toolbar.title = title
    }
}
