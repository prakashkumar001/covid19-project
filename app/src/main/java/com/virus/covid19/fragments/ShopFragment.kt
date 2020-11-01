package com.virus.covid19.fragments

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.virus.covid19.R
import com.virus.covid19.application.GlobalClass
import com.virus.covid19.database.AppDatabase
import com.virus.covid19.database.AppExecutors
import com.virus.covid19.database.entities.OrderHistory
import com.virus.covid19.database.entities.Product
import com.virus.covid19.home.HomeActivity
import com.virus.covid19.interfaces.addCartListener
import com.virus.covid19.textview.CustomTextView
import com.virus.covid19.utilities.ProductAvailable
import com.virus.covid19.viewholder.ShopListItemAdapter
import kotlinx.android.synthetic.main.dialog_physio_saloon_alert.view.*
import kotlinx.android.synthetic.main.fragment_shop.view.*


class ShopFragment : Fragment(),addCartListener {
    var productList:List<Product>?=ArrayList()
    var productListAdapter: ShopListItemAdapter?=null
    var shopName:String?=null
    var category:String?=null
    private val sharedPrefFile = "Login"
    var sharedPreferences: SharedPreferences?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_shop, container, false)
        shopName=arguments?.getString("Title")
        category=arguments?.getString("Category")
        sharedPreferences = activity?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        AppExecutors.getInstance().diskIO().execute(Runnable {
            var shop = AppDatabase.getInstance(activity).shopsDao().getShopByName(shopName!!)
            AppExecutors.getInstance().mainThread().execute(Runnable {
                /*Glide.with(activity!!).asBitmap().load(shop?.shopImageUrl)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            val drawable: Drawable =
                                BitmapDrawable(context!!.resources, resource)
                            v.bg.backgroundDrawable=drawable
                            v.shopItemList.alpha=0.8f

                        }


                    })*/
                Glide.with(activity!!).asBitmap().load(shop?.shopImageUrl).fitCenter().into(v.bg)
                v.shopItemList.alpha=0.8f
            })
        })

        v.shopItemList.layoutManager= LinearLayoutManager(activity)
        v.shopItemList.addItemDecoration(
            DividerItemDecoration(
                v.shopItemList.context,
                DividerItemDecoration.VERTICAL
            )
        )
        getShopList(v)

        v.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                productListAdapter?.filter?.filter(newText)
                return false
            }

        })
        return v
    }

    private fun getShopList(v:View)
    {
        AppExecutors.getInstance().diskIO().execute(Runnable {
            productList = AppDatabase.getInstance(activity!!).productDao().loadAllByProduct(category!!,shopName!!)
            AppExecutors.getInstance().mainThread().execute(Runnable {
                productListAdapter=ShopListItemAdapter(ArrayList<Product>(productList!!),this)
                v.shopItemList.adapter=productListAdapter
                productListAdapter?.notifyDataSetChanged()

            })
        })
    }

    override fun addToCart(product: Product) {
        var ProductAvailable=containsProduct(GlobalClass.cartList,product.id)
        if(ProductAvailable?.isProductAvailable!!)
        {
            var isSameShopProductAvailable=iscontainsDifferentShopProduct(GlobalClass.cartList,product)

            if(isSameShopProductAvailable?.isProductAvailable!!){
                Toast.makeText(activity,"Product Already added , Please add quantity in Cart Page",Toast.LENGTH_SHORT).show()
            }else
            {
                clearCartDialog()

            }
        }else
        {
            if(GlobalClass.cartList.size>0){
                var isSameShopProductAvailable=iscontainsDifferentShopProduct(GlobalClass.cartList,product)

                if(isSameShopProductAvailable?.isProductAvailable!!){
                    GlobalClass.cartList.add(product)
                    val set = AnimatorInflater.loadAnimator(
                        activity,
                        R.animator.flip
                    ) as AnimatorSet
                    set.setTarget((activity as HomeActivity).cartcount)
                    (activity as HomeActivity).cartcount?.setText(GlobalClass.cartList.size.toString())
                    set.start()

                }else
                {
                    clearCartDialog()
                }
            }else
            {
                GlobalClass.cartList.add(product)
                val set = AnimatorInflater.loadAnimator(
                    activity,
                    R.animator.flip
                ) as AnimatorSet
                set.setTarget((activity as HomeActivity).cartcount)
                (activity as HomeActivity).cartcount?.setText(GlobalClass.cartList.size.toString())
                set.start()
            }


        }

    }
    fun containsProduct(
        list: List<Product>,
        productid: Int
    ): ProductAvailable? {
        for (item in list) {
            if (item.id.equals(productid)) {
                val index = list.indexOf(item)
                return ProductAvailable(true, index)
            }
        }
        return ProductAvailable(false, -1)
    }

    fun iscontainsDifferentShopProduct(
        list: List<Product>,
        product: Product
    ): ProductAvailable? {
        for (item in list) {
            if (item.product_shop.equals(product.product_shop)) {
                val index = list.indexOf(item)
                return ProductAvailable(true, index)
            }
        }
        return ProductAvailable(false, -1)
    }

    override fun showSaloonOrPhysioDialog(shopName:String?,shopId:String?){
       var dialog: Dialog =  Dialog(activity!!);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_physio_saloon_alert);

        val dialogButton: CustomTextView = dialog.findViewById<View>(R.id.close) as CustomTextView
        dialogButton.setOnClickListener(View.OnClickListener {
            AppExecutors.getInstance().diskIO().execute(Runnable {
                val userId = sharedPreferences?.getInt("userId",0)
                var orderHistory: OrderHistory = OrderHistory(shopId,userId,shopName,"",GlobalClass.cartList.size.toString(),"Booked")
                AppDatabase.getInstance(activity).orderHistoryDao().insertOrder(orderHistory)
                dialog.dismiss()
                GlobalClass.cartList.clear();
                var intent= Intent(activity,HomeActivity::class.java)
                startActivity(intent)
                ActivityCompat.finishAffinity(activity!!)
            })

        })

        dialog.show()
    }

     fun clearCartDialog(){
        var dialog: Dialog =  Dialog(activity!!);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.cleart_cart_dialog);

        val dialogButton: CustomTextView = dialog.findViewById<View>(R.id.ok) as CustomTextView
         val cancelButton: CustomTextView = dialog.findViewById<View>(R.id.close) as CustomTextView
        dialogButton.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            GlobalClass.cartList.clear();
            var intent= Intent(activity,HomeActivity::class.java)
            startActivity(intent)
            ActivityCompat.finishAffinity(activity!!)
        })
         cancelButton.setOnClickListener(View.OnClickListener {
             dialog.dismiss()
         })

        dialog.show()
    }
}