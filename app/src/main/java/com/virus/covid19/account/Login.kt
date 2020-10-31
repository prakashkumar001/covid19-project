package com.virus.covid19.account

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.socialauth.facebook.FBInfo
import com.example.socialauth.facebook.FacebookLoginHelper
import com.example.socialauth.google.GoogleInfo
import com.example.socialauth.google.GoogleLoginHelper
import com.example.socialauth.result.SocialResultListener
import com.virus.covid19.R
import com.virus.covid19.application.GlobalClass
import com.virus.covid19.database.AppDatabase
import com.virus.covid19.database.AppExecutors
import com.virus.covid19.database.entities.User
import com.virus.covid19.gmail.MailSender
import com.virus.covid19.location.UserLocationDialog
import com.virus.covid19.textview.CustomEditText
import com.virus.covid19.textview.CustomTextView
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class Login :AppCompatActivity(), View.OnClickListener{
    var fbHelper: FacebookLoginHelper? = null
    var googleLoginHelper: GoogleLoginHelper? = null
    var TAG="Login"
    private val sharedPrefFile = "Login"
    var sharedPreferences: SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences=getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        fb.setOnClickListener(this)
        google.setOnClickListener(this)
        signup.setOnClickListener(this)
        login.setOnClickListener(this)
        forgotpassword.setOnClickListener(this)

       // showLocationDialog()
        getKeyHash()
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

        }else if(v == forgotpassword)
        {
            showForgetPasswordDialog()
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

                AppExecutors.getInstance().diskIO().execute(Runnable {
                   var listUser= AppDatabase.getInstance(this).userDao().loadAllUser()
                    if(listUser!=null && listUser.size>0){
                        for(i in 0 until listUser!!.size){
                            var usr=listUser[i]
                            usr?.loggedOut=false
                            AppDatabase.getInstance(this).userDao().updatePerson(usr)
                        }
                    }

                })


                val editor:SharedPreferences.Editor =  sharedPreferences!!.edit()
                editor.putInt("userId",user?.id!!)
                editor.commit()
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
        var userLocationDialog=UserLocationDialog(this)
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
                userInfo.profileImage="https://graph.facebook.com/"+fbInfo.id+"/picture?type=large"
                userInfo.password = fbInfo.id
                userInfo.isSocialLogin=true
                userInfo.loggedOut=false

                var userId=AppDatabase.getInstance(this).userDao().insertPerson(userInfo)
                val editor:SharedPreferences.Editor =  sharedPreferences!!.edit()
                editor.putInt("userId",userId.toInt())
                editor.commit()
                AppExecutors.getInstance().mainThread().execute(Runnable {
                    showLocationDialog()

                })
            } else {

                user.loggedOut=false
                AppDatabase.getInstance(this).userDao().updatePerson(user)
                val editor:SharedPreferences.Editor =  sharedPreferences!!.edit()
                editor.putInt("userId",user.id)
                editor.commit()
                AppExecutors.getInstance().mainThread().execute(Runnable {
                    showLocationDialog()

                })
            }

            AppExecutors.getInstance().diskIO().execute(Runnable {
                var listUser= AppDatabase.getInstance(this).userDao().loadAllUser()
                if(listUser!=null && listUser.size>0){
                    for(i in 0 until listUser!!.size){
                        var usr=listUser[i]
                        usr?.loggedOut=false
                        AppDatabase.getInstance(this).userDao().updatePerson(usr)
                    }
                }

            })

        } else if (obj is GoogleInfo) {
            googleInfo = obj as GoogleInfo

            user = AppDatabase.getInstance(this).userDao().loadUserByEmail(googleInfo?.email!!)
            if (user == null) {
                var userInfo = User()
                userInfo.name = googleInfo.fname
                userInfo.mobile = ""
                userInfo.email = googleInfo?.email!!
                userInfo.address = ""
                userInfo.profileImage=googleInfo?.profileUrl
                userInfo.password = googleInfo?.accesstoken
                userInfo.isSocialLogin=true
                userInfo.loggedOut=false

                var userId=AppDatabase.getInstance(this).userDao().insertPerson(userInfo)
                val editor:SharedPreferences.Editor =  sharedPreferences!!.edit()
                editor.putInt("userId",userId.toInt())
                editor.commit()
                AppExecutors.getInstance().mainThread().execute(Runnable {
                    showLocationDialog()

                })
            } else {
                user.loggedOut=false

                AppDatabase.getInstance(this).userDao().updatePerson(user)
                val editor:SharedPreferences.Editor =  sharedPreferences!!.edit()
                editor.putInt("userId",user.id)
                editor.commit()
                AppExecutors.getInstance().mainThread().execute(Runnable {
                    showLocationDialog()

                })
            }

            AppExecutors.getInstance().diskIO().execute(Runnable {
                var listUser= AppDatabase.getInstance(this).userDao().loadAllUser()
                if(listUser!=null && listUser.size>0){
                    for(i in 0 until listUser!!.size){
                        var usr=listUser[i]
                        usr?.loggedOut=false
                        AppDatabase.getInstance(this).userDao().updatePerson(usr)
                    }
                }

            })

        } else {
            return
        }



    }

   fun getKeyHash()
    {
        try {
            val info = packageManager.getPackageInfo(
                "com.virus.covid19",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }

    fun showForgetPasswordDialog()
    {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.forget_pass)
        val lp: WindowManager.LayoutParams = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog.getWindow()?.setAttributes(lp);
        var email: CustomEditText = dialog.findViewById(R.id.email) as CustomEditText

        var dialogSubmit: CustomTextView = dialog.findViewById(R.id.submit) as CustomTextView
        var dialogCancel: CustomTextView = dialog.findViewById(R.id.cancel) as CustomTextView

                dialogCancel.setOnClickListener(View.OnClickListener {
                    dialog.dismiss()
                })

        dialogSubmit.setOnClickListener(View.OnClickListener {
            AppExecutors.getInstance().diskIO().execute(
                Runnable {
                    var user = AppDatabase.getInstance(this).userDao()
                        .loadUserByEmail(email.text.toString())
                    if (user != null && user.password!=null && !user.isSocialLogin!!) {
                        Thread(Runnable {
                            try {
                                val sender = MailSender(
                                    "akshav00@gmail.com",
                                    "Baiu123@"
                                )
                                sender.sendMail(
                                    "Password Reset", "Your Password is : "+user.password,
                                    "akshav00@gmail.com", user.email
                                )
                                AppExecutors.getInstance().mainThread().execute(Runnable {
                                    Toast.makeText(Login@this,"Password sent your email address",Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()

                                })
                            } catch (e: Exception) {
                                Log.e("SendMail", e.message, e)
                            }
                        }).start()
                    }else
                    {
                        AppExecutors.getInstance().mainThread().execute(Runnable {
                            email.error="Invalid email address"

                        })
                    }
                })
        })


        dialog.show()

    }
}