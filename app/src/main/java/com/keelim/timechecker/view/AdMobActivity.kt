package com.keelim.timechecker.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.keelim.timechecker.R
import kotlinx.android.synthetic.main.activity_ad_mob.*


class AdMobActivity : AppCompatActivity() {

    private var currentLevel: Int = 0
    private lateinit var interstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_mob)

        next_level_button.isEnabled = false
        next_level_button.setOnClickListener { showInterstitial() }

        interstitialAd = newInterstitialAd()
        loadInterstitial()

        Toast.makeText(this, "hello", Toast.LENGTH_LONG).show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_ad_mob, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }

    private fun newInterstitialAd(): InterstitialAd {
        return InterstitialAd(this).apply {
            adUnitId = getString(R.string.interstitial_ad_unit_id)
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    next_level_button.isEnabled = true
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    next_level_button.isEnabled = true
                }

                override fun onAdClosed() {
                    // Proceed to the next level.
                    goToNextLevel()
                }
            }
        }
    }

    private fun showInterstitial() {
        if (interstitialAd.isLoaded) {
            interstitialAd.show()
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show()
            goToNextLevel()
        }
    }

    private fun loadInterstitial() {
        // Disable the next level button and load the ad.
        next_level_button.isEnabled = false
        val adRequest = AdRequest.Builder()
            .setRequestAgent("android_studio:ad_template")
            .build()
        interstitialAd.loadAd(adRequest)
    }

    private fun goToNextLevel() {
        // Show the next level and reload the ad to prepare for the level after.
        level.text = "Level " + (++currentLevel)
        interstitialAd = newInterstitialAd()
        loadInterstitial()
    }
}
