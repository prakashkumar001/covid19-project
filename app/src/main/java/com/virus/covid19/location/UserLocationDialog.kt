package com.virus.covid19.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import com.google.android.gms.location.*
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.virus.covid19.R
import com.virus.covid19.database.AppDatabase
import com.virus.covid19.database.AppExecutors
import com.virus.covid19.database.entities.User
import com.virus.covid19.home.HomeActivity
import com.virus.covid19.utilities.DeviceUtility
import kotlinx.android.synthetic.main.user_location_dialog.*
import org.jetbrains.anko.wrapContent
import java.util.*
import kotlin.collections.ArrayList


class UserLocationDialog : DialogFragment,View.OnClickListener {
    var mLocationRequest:LocationRequest?=null
    var mLocationCallback:LocationCallback?=null
    var location:String?=null
    private val UPDATE_INTERVAL = 10 * 1000 /* 10 secs */.toLong()
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */

    constructor(context: Context) : super() {
        this.tagList = ArrayList<String>()
    }
    var user:User?=null
    var tagGroup: ChipGroup?=null
    var tagList:ArrayList<String>
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    var myLoc:AppCompatImageView?=null
    var hello_user:AppCompatTextView?=null
    var next:AppCompatTextView?=null
    private val REQUEST_CODE = 200

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      var  rootView = inflater.inflate(R.layout.user_location_dialog, container, false)
        tagGroup=rootView.findViewById(R.id.tagGroup)
        myLoc=rootView.findViewById(R.id.myloc)
        hello_user=rootView.findViewById(R.id.hello_user)
        next=rootView.findViewById(R.id.next)
AppExecutors.getInstance().diskIO().execute(Runnable {
    user=AppDatabase.getInstance(activity).userDao().getUser()
    AppExecutors.getInstance().mainThread().execute(Runnable {
        hello_user?.text="Hello "+user?.name

    })
})
        myLoc?.setOnClickListener(this)
        next?.setOnClickListener(this)
        addTagsToGroup()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!);
        startLocationUpdates()
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.attributes?.windowAnimations = R.style.PopUpDialogAnimation
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL)
        dialog?.setCanceledOnTouchOutside(true)


        return rootView
    }

    private fun addTagsToGroup()
    {

        tagList.add("Kanchipuram")
        tagList.add("Trichy")
        tagList.add("Chennai")
        tagList.add("Thanjavur")
        tagList.add("Kumbakonam")



        for(i in 0 until tagList.size){
            val chip = Chip(context)
            chip.text = tagList.get(i)
            chip.isClickable = true
            chip.isCheckable = true
            chip.setChipBackgroundColor(AppCompatResources.getColorStateList(activity!!, R.color.chip_state_selector))
            chip.isCloseIconEnabled = false
            chip.isCheckedIconEnabled=false
            tagGroup?.addView(chip)
        }

    }

    override fun onClick(v: View?) {
        if(v == myloc)
        {
            //fetchLastlocation()
            startLocationUpdates()
        }else if(v == next)
        {
           AppExecutors.getInstance().diskIO().execute(Runnable {
               user?.location=location
               AppDatabase.getInstance(activity).userDao().updatePerson(user)
            })

             val intent = Intent(activity, HomeActivity::class.java)
                // start your next activity
                startActivity(intent)
                activity?.finish()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var aertDialogParent = view?.findViewById<CoordinatorLayout>(R.id.appAlertDialogParent)

        if (!resources.getBoolean(R.bool.isTablet)) {
            var layoutParamParent = aertDialogParent?.layoutParams
            layoutParamParent?.width = DeviceUtility.getDeviceWidth(activity).minus(resources.getDimension(R.dimen.activity_horizontal_margin) * 2).toInt()
            layoutParamParent?.height = wrapContent
            aertDialogParent?.layoutParams = layoutParamParent
        }else
        {
            // set the W/H of dialog
            var layoutParamParent = aertDialogParent?.layoutParams
            layoutParamParent?.width = resources.getDimensionPixelSize(R.dimen.user_location_dialog_width).toInt()
            layoutParamParent?.height =wrapContent
            aertDialogParent?.layoutParams = layoutParamParent
        }

        tagGroup?.setOnCheckedChangeListener(ChipGroup.OnCheckedChangeListener { chipGroup, i ->
            val chip: Chip = chipGroup.findViewById(i)
            if (chip != null) {
                location=chip.text.toString()
            }
        })


    }

    private fun fetchLastlocation()
    {
        if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE);
            return;
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

               when (requestCode) {
             REQUEST_CODE ->

                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                }
        }
    }

    fun getCompleteAddress(latitude:Double,longitude:Double)
    {
        var geocoder =  Geocoder(activity, Locale.getDefault());

        var addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        var address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        var city = addresses.get(0).getLocality();
        var state = addresses.get(0).getAdminArea();
        var country = addresses.get(0).getCountryName();
        var postalCode = addresses.get(0).getPostalCode();
        var knownName = addresses.get(0).getFeatureName()

        Log.e("Location","Location"+city)
        //Toast.makeText(activity!!,"Your Current Location is : "+city,Toast.LENGTH_SHORT).show()
        for(i in 0 until tagList.size)
        {
            if(tagList.get(i).equals(city,true))
            {
                tagGroup?.get(i)?.performClick()
            }
        }
    }

    // Trigger new location updates at interval
    protected fun startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = LocationRequest()
        mLocationRequest?.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest?.setInterval(UPDATE_INTERVAL)
        mLocationRequest?.setFastestInterval(FASTEST_INTERVAL)

        // Create LocationSettingsRequest object using location request
        val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        val locationSettingsRequest: LocationSettingsRequest = builder.build()

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        val settingsClient: SettingsClient = LocationServices.getSettingsClient(activity!!)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                onLocationChanged(locationResult.getLastLocation())
            }
        }
        fusedLocationProviderClient?.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    fun onLocationChanged(location: Location) {
        // New location has now been determined
        if(location!=null){
            getCompleteAddress(location.latitude,location.longitude)
            removeLocationUpdates()
        }
    }

    override fun onStop() {
        super.onStop()
        if (fusedLocationProviderClient != null && mLocationCallback!=null) {
            fusedLocationProviderClient?.removeLocationUpdates(mLocationCallback)
        }
    }

    override fun onPause() {
        super.onPause()
        if (fusedLocationProviderClient != null && mLocationCallback!=null) {
            fusedLocationProviderClient?.removeLocationUpdates(mLocationCallback)
        }
    }

    fun removeLocationUpdates()
    {
        if (fusedLocationProviderClient != null && mLocationCallback!=null) {
            fusedLocationProviderClient?.removeLocationUpdates(mLocationCallback)
        }
    }
}
