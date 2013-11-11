package com.soulmagnet.connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.text.style.SuperscriptSpan;
import android.widget.Toast;

public class DBConnect  {

	//connection's attributes
	public static final String DB_HOST  = "http://87.203.105.194:80";
	private String url;
	
	//constructing a dbConnection
	public DBConnect(String url){this.url = url;}

	
	//The connection has the following methods
	
	public DBConnect()
	{
	}


	public  String customHttpClient(ArrayList<NameValuePair> nameValuePairs, String url) {
		
		InputStream is = null;
		String result = null;
		// HTTP Post
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			
			
			if(nameValuePairs == null){
				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
			}
			else{
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Convert Response to String
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
//					final String lol = editpid.getText().toString();

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	
}
