package com.soulmagnet.winebar;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.soulmagnet.connectivity.DBConnect;
import com.soulmagnet.data.WineDao;

public class WineBarMainActivity extends Activity
{
	//DB QUERIES:
	public static final String TAG = "INFORMATION: ";
	public static final String DB_GET_LIST = DBConnect.DB_HOST + "/winebar/get_wine_list.php";
	public static final String DB_WINE_TYPE = DBConnect.DB_HOST + "/winebar/sort_by_type.php";
	
	
	String db_result = null;
	DBConnect db = new DBConnect();
	EditText inputSearch;
	ArrayAdapter<String> adapter;

	private ArrayList<WineDao> listOfWines = new ArrayList<WineDao>();
	
	private JSONObject json_data = new JSONObject();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wine_bar_main);
		
	
		//BUILDTHE VIEWLISTTHROUGH VIA WINE NAMES
		
		try
		{
			//Get json data from winebar database
			db_result = db.customHttpClient(null, DB_GET_LIST);
			
			//Parse JSON data
			try {
				JSONArray jARR = new JSONArray(db_result);
				
				for(int i=0;i<jARR.length();i++){
					WineDao wine = new WineDao();
					json_data = jARR.getJSONObject(i);
					wine.setW_id((json_data.getString("w_id"))) ;
					wine.setName((json_data.getString("name"))) ;
					wine.setDescription((json_data.getString("description"))) ;
					wine.setImage((json_data.getString("image"))) ;
					listOfWines.add(wine);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList<String> names = new ArrayList<String>();
			for (WineDao wine : listOfWines) {
				
				names.add(wine.getName());
			}
			ListView wineList = (ListView)findViewById(R.id.wine_list);
			inputSearch = (EditText)findViewById(R.id.ed_search);
			adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, names);
			wineList.setAdapter(adapter);
			
			wineList.setOnItemClickListener(new OnItemClickListener()
			{

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id)
				{
					String val_name = listOfWines.get(position).getName();
					String val_description = listOfWines.get(position).getDescription();
					String val_image =  listOfWines.get(position).getImage();
					String val_w_id = listOfWines.get(position).getW_id();
					
					
					Intent wineProfileIntent = new Intent(WineBarMainActivity.this, WineProfile.class);
					
					wineProfileIntent.putExtra("name", val_name);
					wineProfileIntent.putExtra("description", val_description);
					wineProfileIntent.putExtra("image", val_image);
					wineProfileIntent.putExtra("w_id", val_w_id);
					
					startActivity(wineProfileIntent);
					
				}
			});
			
			//TEXT WATCHER SEARCH BAR IMPLEMENTATION
			inputSearch.addTextChangedListener(new TextWatcher()
			{
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count)
				{
					WineBarMainActivity.this.adapter.getFilter().filter(s);
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after)
				{
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s)
				{
					// TODO Auto-generated method stub
					
				}
			});
			
		} catch (Exception e)
		{
			Toast.makeText(getApplicationContext(), "WineBar needs networking to serve you...", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		
		ImageButton b = (ImageButton)findViewById(R.id.sort_list);
		registerForContextMenu(b);
		
	
	}

	

 
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo)
	{
		
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Sort wines by color:");
		menu.add("red");
		menu.add("white");
		menu.add("rose");
	}



	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub
		 super.onContextItemSelected(item);
		 
		ArrayList<NameValuePair> val = new ArrayList<NameValuePair>(); 
		String wine_type = item.getTitle().toString();
		val.add(new BasicNameValuePair("val", wine_type));
		listOfWines.clear();
		
		try
		{
			//Get json data from winebar database
			db_result = db.customHttpClient(val, DB_WINE_TYPE);
			
			//Parse JSON data
			try {
				JSONArray jARR = new JSONArray(db_result);
				
				for(int i=0;i<jARR.length();i++){
					json_data = jARR.getJSONObject(i);
					WineDao wine = new WineDao();
					wine.setW_id((json_data.getString("w_id"))) ;
					wine.setType((json_data.getString("type"))) ;
					wine.setName((json_data.getString("name"))) ;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ListView wineList = (ListView)findViewById(R.id.wine_list);
			ArrayList<String> names = new ArrayList<String>();
			for (WineDao wine : listOfWines) {
				
				names.add(wine.getName());
			}
			wineList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, names));
		} catch (Exception e)
		{
			Toast.makeText(getApplicationContext(), "WineBar needs networking to serve you...", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		 
		 return true;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wine_bar_main, menu);
		return true;
	}

}
