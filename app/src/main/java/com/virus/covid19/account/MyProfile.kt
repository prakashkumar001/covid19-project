package com.virus.covid19.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.virus.covid19.R
import com.virus.covid19.database.AppDatabase
import com.virus.covid19.database.AppExecutors
import com.virus.covid19.database.entities.User
import kotlinx.android.synthetic.main.fragment_my_profile.view.*


class MyProfile : Fragment(), AdapterView.OnItemSelectedListener {
    var user:User?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_my_profile, container, false)

        AppExecutors.getInstance().diskIO().execute(Runnable {
            user=AppDatabase.getInstance(activity!!).userDao().getUser()

            if(user!=null){
                AppExecutors.getInstance().mainThread().execute(Runnable {
                    v.name.text=user?.name
                    v.phone.text=user?.mobile
                    v.email.text=user?.email
                    v.address.text=user?.address
                })
            }
        })
        // Spinner element
        val spinner = v.findViewById(R.id.spinner) as Spinner

        // Spinner click listener

        // Spinner click listener
        spinner.setOnItemSelectedListener(this)

        // Spinner Drop down elements

        // Spinner Drop down elements
        val categories: MutableList<String> = ArrayList()
        categories.add("Kanchipuram")
        categories.add("Trichy")
        categories.add("Chennai")
        categories.add("Thanjavur")
        categories.add("Kumbakonam")

        // Creating adapter for spinner

        // Creating adapter for spinner
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, categories)

        // Drop down layout style - list view with radio button

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // attaching data adapter to spinner

        // attaching data adapter to spinner
        spinner.adapter = dataAdapter
        return v
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}