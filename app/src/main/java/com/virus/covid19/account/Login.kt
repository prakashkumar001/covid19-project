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
import com.virus.covid19.location.UserLocationDialog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.email
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.signup
import kotlinx.android.synthetic.main.activity_signup.*


class Login :AppCompatActivity(), View.OnClickListener{
    var fbHelper: FacebookLoginHelper? = null
    var googleLoginHelper: GoogleLoginHelper? = null
    var TAG="Login"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        fb.setOnClickListener(this)
        google.setOnClickListener(this)
        signup.setOnClickListener(this)
        login.setOnClickListener(this)

       // showLocationDialog()

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
                    AppExecutors.getInstance().diskIO().execute(Runnable {
                        insertOrUpdateUser(fbInfo)
                    })

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
                    AppExecutors.getInstance().diskIO().execute(Runnable {
                        insertOrUpdateUser(info)
                    })
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
             user=AppDatabase.getInstance(this).userDao().getUserInfo(email.text.toString(),password.text.toString())
            if(user!=null)
            {
               /* val intent = Intent(this@Login, HomeActivity::class.java)
                // start your next activity
                startActivity(intent)*/
                showLocationDialog()
            }else{
                AppExecutors.getInstance().mainThread().execute(Runnable {
                    Toast.makeText(this,"Invalid User or Password",Toast.LENGTH_SHORT).show()
                })
            }

        })


    }

    private fun showLocationDialog()
    {
        var userLocationDialog=UserLocationDialog(this,"Prakash")
        userLocationDialog.show(supportFragmentManager,TAG)

    }

    private fun insertOrUpdateUser(obj:Any) {
        var fbInfo: FBInfo? = null
        var googleInfo: GoogleInfo? = null
        var user: User? = null
        if (obj is FBInfo) {
            fbInfo = obj as FBInfo

            user = AppDatabase.getInstance(this).userDao().loadUserByEmail(fbInfo?.email!!)
            if (user == null) {
                var userInfo = User()
                userInfo.name = fbInfo.fname
                userInfo.mobile = ""
                userInfo.email = fbInfo.email
                userInfo.address = ""
                userInfo.password = fbInfo.id
                userInfo.isSocialLogin=true

                AppDatabase.getInstance(this).userDao().insertPerson(userInfo)
                AppExecutors.getInstance().mainThread().execute(Runnable {
                    showLocationDialog()

                })
            } else {
                AppExecutors.getInstance().mainThread().execute(Runnable {
                    showLocationDialog()

                })
            }
        } else if (obj is GoogleInfo) {
            googleInfo = obj as GoogleInfo

            user = AppDatabase.getInstance(this).userDao().loadUserByEmail(googleInfo?.email!!)
            if (user == null) {
                var userInfo = User()
                userInfo.name = googleInfo.fname
                userInfo.mobile = ""
                userInfo.email = googleInfo?.email!!
                userInfo.address = ""
                userInfo.password = googleInfo?.accesstoken
                userInfo.isSocialLogin=true

                AppDatabase.getInstance(this).userDao().insertPerson(userInfo)
                AppExecutors.getInstance().mainThread().execute(Runnable {
                    showLocationDialog()

                })
            } else {
                AppExecutors.getInstance().mainThread().execute(Runnable {
                    showLocationDialog()

                })
            }

        } else {
            return
        }



    }
}