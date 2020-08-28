package com.virus.covid19.location

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.virus.covid19.R


class UserLocationDialog constructor(context: Context) :DialogFragment(),View.OnClickListener {
    var tagGroup: ChipGroup?=null
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



        for(i in 0 until tagList.size){
            val chip = Chip(context)
            chip.text = tagList.get(i)
            chip.isClickable = true
            chip.isCheckable = true
            chip.setChipBackgroundColor(AppCompatResources.getColorStateList(activity!!, R.color.chip_state_selector))
            chip.isCloseIconEnabled = false
            tagGroup?.addView(chip)
        }

    }

    override fun onClick(v: View?) {

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

    // method to generate color state list programmatically
    fun generateColorStateList(
        enabledColor:Int = Color.parseColor("#00BFFF"), // Capri
        disabledColor:Int = Color.parseColor("#A2A2D0"), // Blue bell
        checkedColor:Int = Color.parseColor("#7BB661"), // Bud green
        uncheckedColor:Int = Color.parseColor("#A3C1AD"), // Cambridge blue
        activeColor:Int = Color.parseColor("#1034A6"), // Egyptian blue
        inactiveColor:Int = Color.parseColor("#614051"), // Eggplant
        pressedColor:Int = Color.parseColor("#FFD300"), // Cyber yellow
        focusedColor:Int = Color.parseColor("#00FFFF") // Aqua
    ):ColorStateList{
        val states = arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_active),
            intArrayOf(-android.R.attr.state_active),
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(android.R.attr.state_focused)
        )
        val colors = intArrayOf(
            enabledColor, // enabled
            disabledColor, // disabled
            checkedColor, // checked
            uncheckedColor, // unchecked
            activeColor, // active
            inactiveColor, // inactive
            pressedColor, // pressed
            focusedColor // focused
        )
        return ColorStateList(states, colors)
    }
}
