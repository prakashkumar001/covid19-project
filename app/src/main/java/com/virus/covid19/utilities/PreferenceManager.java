package com.virus.covid19.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
	 SharedPreferences preferences;
	private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    Context context;
	public PreferenceManager(Context context) {
		this.context=context;
	}



	public  void setUserId(String userId) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("userId", userId).commit();
	}

	public  String getUserId() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("userId", "");
	}

	public void setUsername(String username) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("username", username).commit();
	}

	public String getUsername() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("username", "");
	}

	public void setUseremail(String useremail) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("user_email", useremail).commit();
	}

	public String getUseremail() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("user_email", "");
	}

	public void setUserpassword(String userpassword) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("user_password", userpassword).commit();
	}

	public String getUserpassword() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("user_password", "");
	}

	public void setUserAppid(String userid) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("user_id", userid).commit();
	}

	public String getUserAppid() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("user_id", "");
	}

	public void setAppusername(String Appusername) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("appusername", Appusername).commit();
	}

	public String getAppusername() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("appusername", "");
	}

	public void setAppcount(String Appcount) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("appcount", Appcount).commit();
	}

	public String getAppcount() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("appcount", "");
	}

	public void setLogintype(String Logintype) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("logintype", Logintype).commit();
	}

	public String getLogintype() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("logintype", "");
	}

	//FOR NEARBY REVIEW PAGE

	public void setLatitude(String Latitude) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("latitude", Latitude).commit();
	}

	public String getLatitude() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("latitude", "");
	}

	public void setLongitude(String Longitude) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("longitude", Longitude).commit();
	}

	public String getLongitude() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("longitude", "");
	}

	public void setMid(String Mid) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Mid", Mid).commit();
	}

	public String getMid() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Mid", "");
	}

	public void setPrd_name(String Prd_name) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Prd_name", Prd_name).commit();
	}

	public String getPrd_name() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Prd_name", "");
	}

	/////////
	public void setPrd_image(String Prd_image) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Prd_image", Prd_image).commit();
	}

	public String getPrd_image() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Prd_image", "");
	}

	public void setPrd_status(String Prd_status) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Prd_status", Prd_status).commit();
	}

	public String getPrd_status() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Prd_status", "");
	}

	public void setPrd_distance(String Prd_distance) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Prd_distance", Prd_distance).commit();
	}

	public String getPrd_distance() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Prd_distance", "");
	}

	public void setPrd_types(String Prd_types) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Prd_types", Prd_types).commit();
	}

	public String getPrd_types() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Prd_types", "");
	}

	public void setprd_merchant(String prd_merchant) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("prd_merchant", prd_merchant).commit();
	}

	public String getprd_merchant() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("prd_merchant", "");
	}

	public void setProductid(String Productid) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("productid", Productid).commit();
	}

	public String getProductid() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("productid", "");
	}

	public void setFrom(String From) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("From", From).commit();
	}

	public String getFrom() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("From", "");
	}
//FOR REVIEW PAGE

	public void setReview_nearby(String Review_nearby) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Review_nearby", Review_nearby).commit();
	}

	public String getReview_nearby() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Review_nearby", "");
	}

	//FOR FACEBOOK IMAGE

	public void setstr_fb_image(String str_fb_image) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("str_fb_image", str_fb_image).commit();
	}

	public String getstr_fb_image() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("str_fb_image", "");
	}

	public void setGoogle_placeid(String Google_placeid) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Google_placeid", Google_placeid).commit();
	}

	public String getGoogle_placeid() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Google_placeid", "");
	}

	public void setMerchant_address(String Merchant_address) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Merchant_address", Merchant_address).commit();
	}

	public String getMerchant_address() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Merchant_address", "");
	}

	public void setMerchant_phone_number(String Merchant_phone_number) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Merchant_phone_number", Merchant_phone_number).commit();
	}

	public String getMerchant_phone_number() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Merchant_phone_number", "");
	}

	//FOR INSTAGRAM
	public void setInsta_image(String Insta_image) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Insta_image", Insta_image).commit();
	}

	public String getInsta_image() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Insta_image", "");
	}
	//FOR CATEGORY

	public void setCategory_id(String Category_id) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Category_id", Category_id).commit();
	}

	public String getCategory_id() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Category_id", "");
	}

	//FOR LOGIN_CREATED_DATE
	public void setCreated_date(String Created_date) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Created_date", Created_date).commit();
	}

	public String getCreated_date() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Created_date", "");
	}

	//
	public void setMiles(String Miles) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Miles", Miles).commit();
	}

	public String getMiles() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Miles", "");
	}

	public void setDistance_id(String Distance_id) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Distance_id", Distance_id).commit();
	}

	public String getDistance_id() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Distance_id", "");
	}


	public void setPush_msg(String Push_msg) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Push_msg", Push_msg).commit();
	}

	public String getPush_msg() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getString("Push_msg", "");
	}

	public void setBadge(int setBadge) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putInt("Badge", setBadge).commit();
	}

	public int getBadge() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getInt("Badge", 0);
	}


	public void setWebsite(String Website) {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().putString("Website", Website).commit();
	}




	public void deleteUserDetails() {

		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);

		preferences.edit().remove("userDetails").commit();;
	}
	public void setFirstTimeLaunch(boolean isFirstTime) {
		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);

		preferences.edit().putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime).commit();
		
	}

	public boolean isFirstTimeLaunch() {
		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
	}


	public  void clearAll()
	{
		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		preferences.edit().remove("userDetails").commit();
	}

}

