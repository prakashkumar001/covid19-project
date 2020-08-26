package com.virus.covid19.location

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import com.virus.covid19.R
import com.virus.covid19.utilities.DeviceUtility
import com.virus.covid19.utilities.TagGroup


class UserLocationDialog constructor(context: Context) :DialogFragment(),View.OnClickListener,TagGroup.OnTagClickListener {
    var tagGroup: TagGroup?=null
    var tagList:ArrayList<String> = ArrayList<String>()
    var selectedLocation:String?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      var  rootView = inflater.inflate(R.layout.user_location_dialog, container, false)
        tagGroup=rootView.findViewById(R.id.tagGroup)
        addTagsToGroup()
        tagGroup?.setTags(tagList)
        tagGroup?.setOnClickListener(this)
        tagGroup?.setOnTagClickListener(this)

        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.attributes?.windowAnimations = R.style.PopUpDialogAnimation
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL)
        dialog?.setCanceledOnTouchOutside(true)
        return rootView
    }

    private fun addTagsToGroup()
    {
        tagList.add("Chennai")
        tagList.add("Kanchipuram")
        tagList.add("Trichy")
        tagList.add("Thanjavur")
        tagList.add("Kumbakonam")

    }

    override fun onClick(v: View?) {

    }

    override fun onTagClick(tag: String?) {
        selectedLocation=tag

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var aertDialogParent = view?.findViewById<CoordinatorLayout>(R.id.appAlertDialogParent)


            // set the W/H of dialog
            var layoutParamParent = aertDialogParent?.layoutParams
            layoutParamParent?.width = 1000
            layoutParamParent?.height =1000
            aertDialogParent?.layoutParams = layoutParamParent


    }
}
