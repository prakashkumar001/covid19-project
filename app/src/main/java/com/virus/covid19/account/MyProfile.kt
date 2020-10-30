package com.virus.covid19.account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.virus.covid19.R
import com.virus.covid19.application.GlobalClass
import com.virus.covid19.database.AppDatabase
import com.virus.covid19.database.AppExecutors
import com.virus.covid19.database.entities.User
import com.virus.covid19.ui.Splash
import kotlinx.android.synthetic.main.fragment_my_profile.*
import kotlinx.android.synthetic.main.fragment_my_profile.view.*


class MyProfile : Fragment(), AdapterView.OnItemSelectedListener {
    var user:User?=null
    var spinnerLocation:String?=null
    val categories: MutableList<String> = ArrayList()
    private val sharedPrefFile = "Login"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_my_profile, container, false)

        val sharedPreferences: SharedPreferences = activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        // Spinner element
        val spinner = v.findViewById(R.id.spinner) as Spinner
        categories.add("Kanchipuram")
        categories.add("Trichy")
        categories.add("Chennai")
        categories.add("Thanjavur")
        categories.add("Kumbakonam")

        // Spinner click listener
        spinner.setOnItemSelectedListener(this)

        // Creating adapter for spinner
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, categories)


        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        // attaching data adapter to spinner
        spinner.adapter = dataAdapter

        AppExecutors.getInstance().diskIO().execute(Runnable {
            val userId = sharedPreferences.getInt("userId",0)
            user=AppDatabase.getInstance(activity!!).userDao().getUser(userId)

            if(user!=null){
                AppExecutors.getInstance().mainThread().execute(Runnable {
                    v.name.setText(user?.name)
                    v.phone.setText(user?.mobile)
                    v.email.setText(user?.email)
                    v.completeaddress.setText(user?.address)
                    for(i in 0 until categories.size){
                        if(categories[i].equals(user?.location,ignoreCase = true)){
                            spinner.setSelection(i)
                        }
                    }
                })
            }
        })






        v.update.setOnClickListener(View.OnClickListener {

            if(phone.text.toString().length>=10 && completeaddress.text.toString().length>10){
                if(user!=null){
                    AppExecutors.getInstance().diskIO().execute(Runnable {
                        user?.name=name.text.toString()
                        user?.location=spinnerLocation
                        user?.address=completeaddress.text.toString()
                        user?.mobile=phone.text.toString()

                        AppDatabase.getInstance(activity).userDao().updatePerson(user)
                        GlobalClass.cartList.clear()

                        var intent= Intent(activity,Splash::class.java)
                        startActivity(intent)
                        ActivityCompat.finishAffinity(activity!!)
                    })
                }

            }else if(phone.text!!.length<10)
            {
                phone.error="Phone number should 10 numbers"
            }else if(completeaddress.text!!.length<10)
            {
                completeaddress.error="Address cannot be empty , atleast 10 characters mandatory"

            }


        })

        return v
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        spinnerLocation=parent?.getItemAtPosition(position).toString()
    }
}