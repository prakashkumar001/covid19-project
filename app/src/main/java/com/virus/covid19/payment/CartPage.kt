package com.virus.covid19.payment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.virus.covid19.R
import com.virus.covid19.application.GlobalClass
import com.virus.covid19.viewholder.CartViewAdapter
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.fragment_shop.view.*
import org.json.JSONObject
import java.lang.Exception

class CartPage :AppCompatActivity(), PaymentResultListener {

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        Checkout.preload(applicationContext)

        cart_view.layoutManager= LinearLayoutManager(this)
        cart_view.adapter=CartViewAdapter(this,GlobalClass.cartList)
        proceed.setOnClickListener(View.OnClickListener {
           startPayment()
        })
    }

    private fun startPayment() {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Razorpay Corp")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("currency","INR")
            options.put("amount","100")

            val prefill = JSONObject()
            prefill.put("email","test@razorpay.com")
            prefill.put("contact","9876543210")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentError(errorCode: Int, response: String?) {
        try{
            Toast.makeText(this,"Payment failed $errorCode \n $response", Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Log.e("Exception in onPaymentSuccess","Exception in onPaymentSuccess", e)
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        try{
            Toast.makeText(this,"Payment Successful $razorpayPaymentId", Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Log.e("Exception in onPaymentSuccess","Exception in onPaymentSuccess", e)
        }
    }
}