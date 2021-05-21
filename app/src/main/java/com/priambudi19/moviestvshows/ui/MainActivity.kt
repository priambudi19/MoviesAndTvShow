package com.priambudi19.moviestvshows.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import com.priambudi19.moviestvshows.R
import com.priambudi19.moviestvshows.databinding.ActivityMainBinding
import com.priambudi19.moviestvshows.ui.favorite.FavoriteFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.main_containter, MainFragment())
            }
        }
        val toolbar : Toolbar = binding.includeToolbar.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.includeToolbar.toolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btn_favorite_toolbar -> {
                supportFragmentManager.commit {
                    replace(R.id.main_containter, FavoriteFragment())
                    addToBackStack(null)
                }
            }
        }
        return true
    }



}