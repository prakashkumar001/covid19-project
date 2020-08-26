package com.virus.covid19.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.socialauth.facebook.FBInfo
import com.example.socialauth.facebook.FacebookLoginHelper
import com.example.socialauth.google.GoogleInfo
import com.example.socialauth.google.GoogleLoginHelper
import com.example.socialauth.result.SocialResultListener
import com.virus.covid19.R
import com.virus.covid19.database.AppDatabase
import com.virus.covid19.database.AppExecutors
import com.virus.covid19.database.entities.User
import com.virus.covid19.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*


class Login :AppCompatActivity(), View.OnClickListener{
    var fbHelper: FacebookLoginHelper? = null
    var googleLoginHelper: GoogleLoginHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        fb.setOnClickListener(this)
        google.setOnClickListener(this)
        signup.setOnClickListener(this)
        login.setOnClickListener(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            googleLoginHelper?.onActivityResult(requestCode,resultCode,data);
        }else if(requestCode==64206)
        {
            fbHelper?.onActivityResult(requestCode,resultCode,data);
        }

    }

    override fun onClick(v: View?) {
        if (v == fb) {
            onFBLogin();

        } else if (v == google) {
            onGoogleLogin();
        }else if(v== signup)
        {
            val intent = Intent(this@Login, SignUp::class.java)
            // start your next activity
            startActivity(intent)
        }else if(v == login)
        {
            getUser()

        }
    }

    fun onFBLogin() {
        fbHelper = FacebookLoginHelper(
            this@Login,
            resources.getString(R.string.facebook_app_id),
            object : SocialResultListener {
                override fun onSignInFail(errorMessage: String) {}
                override fun onSignInSuccess(
                    authToken: Any,
                    userId: String,
                    user: String?
                ) {
                    val fbInfo = authToken as FBInfo
                    // showMessage(fbInfo.fname +fbInfo.email);
                }

                override fun onSignOut() {}
            })
        fbHelper?.performSignIn()
    }

    fun onGoogleLogin() {
        //AIzaSyAgKu_LvQWQ9n7hiORUEvWo-60fbdSbnrc
        googleLoginHelper = GoogleLoginHelper(
            this@Login,
            "675851265283-2bkc6gb1vaktfnirn1l3ldhd3jlfqn82.apps.googleusercontent.com",
            object : SocialResultListener {
                override fun onSignInFail(errorMessage: String) {
                    showMessage(errorMessage)
                }

                override fun onSignInSuccess(
                    authToken: Any,
                    userId: String,
                    user: String
                ) {
                    val info = authToken as GoogleInfo

                    // showMessage(info.email);
                }

                override fun onSignOut() {}
            })
        googleLoginHelper!!.performSignIn()
    }
    fun showMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun getUser(){
        var user:User?=null
        AppExecutors.getInstance().networkIO().execute(Runnable {
            var list=AppDatabase.getInstance(this).userDao().loadAllUser()
             user=AppDatabase.getInstance(this).userDao().getUserInfo(email.text.toString(),password.text.toString())
            if(user!=null)
            {
                val intent = Intent(this@Login, HomeActivity::class.java)
                // start your next activity
                startActivity(intent)
            }else{
                AppExecutors.getInstance().mainThread().execute(Runnable {
                    Toast.makeText(this,"Invalid User or Password",Toast.LENGTH_SHORT).show()
                })
            }

        })


    }
}