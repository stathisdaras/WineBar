package com.soulmagnet.winebar;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.soulmagnet.connectivity.DBConnect;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WineProfile extends Activity
{
	
	
	public static final String DB_GET_VENDORS = DBConnect.DB_HOST + "/winebar/get_vendors.php";
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		//INITIALIZE COMPONENTS
		ArrayAdapter<String> adapter;
		
		// SET VIEW FROM XML
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wine_profile);
		
		//PASS NECESSARY DATA FROM MAIN ACTIVITY
		Intent getDataIntent = getIntent();
		String nameSelected = getDataIntent.getStringExtra("name");
		String descriptionSelected = getDataIntent.getStringExtra("description");
		String imageSelected = getDataIntent.getStringExtra("image");
		String w_idSelected = getDataIntent.getStringExtra("w_id");
		
		//FILL UI COMPONENTS USING DATA PASSED
		TextView tvName = (TextView)findViewById(R.id.tv_name);
		TextView tvDescription = (TextView)findViewById(R.id.tv_description);
		
		tvName.setText(nameSelected);
		tvDescription.setText(descriptionSelected);
		//Toast.makeText(getApplicationContext(), imageSelected, Toast.LENGTH_LONG).show();
		
		//LOAD IMAGE FROM URL
		ImageView image = (ImageView)findViewById(R.id.image);
		try
		{
			Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageSelected).getContent());
			image.setImageBitmap(bitmap);
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		//CREATE LISTVIEW WITH VENDORS
		
		//GET VENDORS USING WINE NAME FOR THE QUERY
		DBConnect db_vendor = new DBConnect();
		ArrayList<NameValuePair> w_id = new ArrayList<NameValuePair>(); 
		w_id.add(new BasicNameValuePair("w_id", w_idSelected));
		ArrayList<String> vendorNames = new ArrayList<String>();
		final ArrayList<String> geoPointX = new ArrayList<String>();
		final ArrayList<String> geoPointY = new ArrayList<String>();
		String db_result = db_vendor.customHttpClient(w_id, DB_GET_VENDORS);
		
		//Parse JSON data
		try {
			JSONArray jARR = new JSONArray(db_result);
			JSONObject json_data;
			
			
			for(int i=0;i<jARR.length();i++){
				json_data = jARR.getJSONObject(i);
				vendorNames.add((json_data.getString("name"))) ;
				geoPointX.add((json_data.getString("geo_x"))) ;
				geoPointY.add((json_data.getString("geo_y"))) ;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ListView vendorList = (ListView)findViewById(R.id.lv_vendors);
		
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, vendorNames);
		vendorList.setAdapter(adapter);
		
		vendorList.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				
				String val_x = geoPointX.get(position);
				String val_y = geoPointY.get(position);
				
				/*Intent locationIntent = new Intent(WineProfile.this, VendorLocation.class);
				
				locationIntent.putExtra("x", val_x);
				locationIntent.putExtra("y", val_y);
			
				startActivity(locationIntent);*/
				
			}
		});
		
		
	}

	
	
}
