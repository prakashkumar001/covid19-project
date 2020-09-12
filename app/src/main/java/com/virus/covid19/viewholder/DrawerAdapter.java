package com.virus.covid19.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.virus.covid19.R;
import com.virus.covid19.account.Login;
import com.virus.covid19.database.AppDatabase;
import com.virus.covid19.database.AppExecutors;
import com.virus.covid19.database.entities.User;
import com.virus.covid19.fragments.Home;
import com.virus.covid19.model.DrawerItem;

import java.util.ArrayList;
import java.util.List;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {
    Activity context;
    Typeface fonts,bold;
    ArrayList<DrawerItem> drawerList;
   public DrawerAdapter(Activity context, ArrayList<DrawerItem> drawerList) {
       this.context=context;
       this.drawerList=drawerList;
   }
   @Override  
   public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     View view;
     view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item, parent, false);
     return new DrawerViewHolder(view);  
   }  
   @Override  
   public void onBindViewHolder(DrawerViewHolder holder, final int position) {
        //fonts = Typeface.createFromAsset(context.getAssets(), "fonts/futura.ttf");
       // bold= Typeface.createFromAsset(context.getAssets(), "fonts/futura.ttf");

      // holder.title.setTypeface(fonts);
       holder.title.setText(drawerList.get(position).getTitle());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(position==3)
               {
                   AppExecutors.getInstance().diskIO().execute(new Runnable() {
                       @Override
                       public void run() {

                           List<User> user= AppDatabase.getInstance(context).userDao().loadAllUser();
                           if(user.size()>0)
                           {
                               for(int i=0;i<user.size();i++)
                               {
                                   User user1=user.get(i);
                                   user1.setLogOut(true);
                                   AppDatabase.getInstance(context).userDao().updatePerson(user1);
                               }
                               Intent intent=new Intent(context, Login.class);
                               context.startActivity(intent);
                               ActivityCompat.finishAffinity(context);
                           }

                       }
                   });
               }
           }
       });

   }  
   @Override  
   public int getItemCount() {  
     return drawerList.size();
   }  
   class DrawerViewHolder extends RecyclerView.ViewHolder {  
     TextView title;
     ImageView icon;
     public DrawerViewHolder(View itemView) {  
       super(itemView);  
       title = (TextView) itemView.findViewById(R.id.title);  
       icon = (ImageView) itemView.findViewById(R.id.icon);  
     }  
   }  
 }  