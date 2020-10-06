package com.virus.covid19.account;

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.virus.covid19.R
import com.virus.covid19.database.AppDatabase
import com.virus.covid19.database.AppExecutors
import com.virus.covid19.database.entities.User
import kotlinx.android.synthetic.main.activity_signup.*

class SignUp : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        signup.setOnClickListener(this)
    }

    private fun insertOrUpdateUser()
    {
        var emailaddress=email.text.toString()
        var user=AppDatabase.getInstance(this).userDao().loadUserByEmail(emailaddress)
        if(user==null)
        {
            var userInfo=User()
            userInfo.name=name.text.toString()
            userInfo.mobile=phone.text.toString()
            userInfo.email=emailaddress
            userInfo.address=address.text.toString()
            userInfo.password=password.text.toString()
            userInfo.isSocialLogin=false
            userInfo.logOut=false

            AppDatabase.getInstance(this).userDao().insertPerson(userInfo)
            AppExecutors.getInstance().mainThread().execute(Runnable {
                Toast.makeText(this,"Successfully Registered",Toast.LENGTH_SHORT).show();
                finish()
            })

        }else
        {
            AppExecutors.getInstance().mainThread().execute(Runnable {
                Toast.makeText(this,"User Already Available",Toast.LENGTH_SHORT).show()

            })
        }

    }

    override fun onClick(v: View?) {
        if(v == signup)
        {
            AppExecutors.getInstance().diskIO().execute(Runnable {
                insertOrUpdateUser()

            })
        }
    }
}
