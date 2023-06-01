package com.nacarseven.series.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.nacarseven.series.R
import com.nacarseven.series.presentation.serieslist.SeriesListFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflateFragment()
    }

    private fun inflateFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, SeriesListFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commitAllowingStateLoss()
    }
}


