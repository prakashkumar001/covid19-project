package com.virus.covid19.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.virus.covid19.R
import com.virus.covid19.home.HomeActivity
import com.virus.covid19.utilities.DeviceUtility
import kotlinx.android.synthetic.main.user_location_dialog.*
import java.util.*
import kotlin.collections.ArrayList
import org.jetbrains.anko.wrapContent


class UserLocationDialog : DialogFragment,View.OnClickListener {
    constructor(context: Context, username: String) : super() {
        this.tagList = ArrayList<String>()
        this.username=username
    }
    var username:String?=null
    var tagGroup: ChipGroup?=null
    var tagList:ArrayList<String>
    var selectedLocation:String?=null
    var currentLocation: Location? = null
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

        hello_user?.text="Hello "+username
        myLoc?.setOnClickListener(this)
        next?.setOnClickListener(this)
        addTagsToGroup()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!);
        fetchLastlocation();
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
            fetchLastlocation()
        }else if(v == next)
        {
             val intent = Intent(activity, HomeActivity::class.java)
                // start your next activity
                startActivity(intent)
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



    }

    private fun fetchLastlocation()
    {
        if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE);
            return;
        }
         var task= fusedLocationProviderClient?.getLastLocation();
       task?.addOnSuccessListener(OnSuccessListener{
           if(it!=null)
           {
               getCompleteAddress(it.latitude,it.longitude)
               Log.e(it.latitude.toString(),"==="+it.longitude.toString())
           }

       })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

               when (requestCode) {
             REQUEST_CODE ->

                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastlocation();
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
        Toast.makeText(activity!!,"Your Current Location is : "+city,Toast.LENGTH_SHORT).show()
        for(i in 0 until tagList.size)
        {
            if(tagList.get(i).equals(city,true))
            {
                tagGroup?.get(i)?.performClick()
            }
        }
    }

}
