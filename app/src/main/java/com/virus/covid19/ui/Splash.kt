package com.virus.covid19.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.virus.covid19.R
import com.virus.covid19.account.Login
import com.virus.covid19.application.GlobalClass
import com.virus.covid19.database.AppDatabase
import com.virus.covid19.database.AppExecutors
import com.virus.covid19.home.HomeActivity
import kotlinx.android.synthetic.main.splash.*

class Splash :AppCompatActivity(){
    private val SPLASH_TIME_OUT:Long=3000 // 3 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        SplashAnimation(splash,300)
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            AppExecutors.getInstance().diskIO().execute(Runnable {
                var user=AppDatabase.getInstance(this).userDao().getUser()
                if(user!=null){
                        startActivity(Intent(this,HomeActivity::class.java))
                }else
                {
                    startActivity(Intent(this,Login::class.java))

                }
            })
            finish()

            // close this activity
        }, SPLASH_TIME_OUT)
    }

    private fun SplashAnimation(view: View, delay: Long) {
        val animFadeIn = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.splash_slide_in_up
        )
        animFadeIn.startOffset = delay
        view.startAnimation(animFadeIn)
    }
}