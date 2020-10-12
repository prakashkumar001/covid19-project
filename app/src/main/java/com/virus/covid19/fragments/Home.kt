package com.virus.covid19.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.virus.covid19.R
import com.virus.covid19.interfaces.CardClickListener
import com.virus.covid19.model.Category
import com.virus.covid19.viewholder.CategoryViewAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*

class Home : Fragment(), CardClickListener {
    var categoryList:ArrayList<Category> = ArrayList()
    var categoryRecyclerAdapter:CategoryViewAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v: View = inflater.inflate(R.layout.fragment_home, container, false)
        getListOfCategory()
        categoryRecyclerAdapter= CategoryViewAdapter(activity!!,categoryList,this)
        v.recyclerView.layoutManager = GridLayoutManager(activity,2)
        v.recyclerView.adapter=categoryRecyclerAdapter
        return v
    }

    fun getListOfCategory()
    {
        categoryList.add(Category("Grocery Shops","https://intermountainhealthcare.org/-/media/images/modules/blog/posts/2018/04/grocery-aisle.jpg?la=en&h=463&w=896&mw=896&hash=1EF7FADBA82984D0BF978FD397BA09CE9D62E3C0"))
        categoryList.add(Category("Saloon","https://images-na.ssl-images-amazon.com/images/I/71lewidx49L._SY355_.jpg"))
        categoryList.add(Category("Medicals","https://www.businessplanhindi.com/wp-content/uploads/2019/05/Medical-Store-business-in-hindi.jpg"))
        categoryList.add(Category("Hotels","https://images.squarespace-cdn.com/content/v1/5c5c3833840b161566b02a76/1573133725500-Y5PCN0V04I86HDAT8AT0/ke17ZwdGBToddI8pDm48kLkXF2pIyv_F2eUT9F60jBl7gQa3H78H3Y0txjaiv_0fDoOvxcdMmMKkDsyUqMSsMWxHk725yiiHCCLfrh8O1z4YTzHvnKhyp6Da-NYroOW3ZGjoBKy3azqku80C789l0iyqMbMesKd95J-X4EagrgU9L3Sa3U8cogeb0tjXbfawd0urKshkc5MgdBeJmALQKw/WBC_7095.jpg?format=2500w"))
        categoryList.add(Category("Physiotherapist","https://img.freepik.com/free-vector/doctor-character-background_1270-84.jpg?size=338&ext=jpg"))
        categoryList.add(Category("Vegetable Shops","https://media.istockphoto.com/photos/shopping-bag-full-of-fresh-vegetables-and-fruits-picture-id1128687123?k=6&m=1128687123&s=612x612&w=0&h=PGSt75Y0gXRgKAQBLy55zEP_kvkv4EJmf5xzF0Lzvz4="))


    }
    override fun onCardClick(shopName: String) {
        var shopListFragment=ShopListFragment()
        var bundle=Bundle()
        bundle.putString("Title",shopName)
        shopListFragment.setArguments(bundle)

       activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left,
           R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
        .add(R.id.container, shopListFragment, shopListFragment.tag).addToBackStack(shopListFragment.tag).commit()
    }

    override fun getCategoryName(category: String) {

    }
}