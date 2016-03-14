package com.example.webservicerestclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
    String pais;
    String ciudad;
    String url;
    String resultado;

    EditText txt_pais;
    EditText txt_ciudad;
    Button btn_enviar;
    TextView txt_resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_enviar = (Button)findViewById(R.id.btn_enviar);
        txt_pais = (EditText)findViewById(R.id.txt_pais);
        txt_ciudad = (EditText)findViewById(R.id.txt_ciudad);
        txt_resultado = (TextView)findViewById(R.id.txt_respuesta);

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pais = txt_pais.getText().toString();
                ciudad = txt_ciudad.getText().toString();
                pais = pais.trim();
                ciudad = ciudad.trim();
                //http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=bd82977b86bf27fb59a04b61b657fb6f
                url = "http://api.openweathermap.org/data/2.5/weather?q=" +
                			pais + "," + ciudad + "&appid=bd82977b86bf27fb59a04b61b657fb6f";
                new ReadJson().execute();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ReadJson extends AsyncTask<Object, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Object[] params) {
            HttpClient httpcliente = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.setHeader("content-type", "application/json");
            try{
            	//post.setEntity(url);
            	HttpResponse resp = httpcliente.execute(post);
            	resultado = EntityUtils.toString(resp.getEntity());
            }catch(Exception e){
            	Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
            	 return false;
            }
            return true;
        }

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			String rpta= "";
			if(result == false){
				Toast.makeText(getApplicationContext(),"Error!", Toast.LENGTH_SHORT).show();
			}
			//txt_resultado.setText(resultado);
			
			try {
				JSONObject jsonObject = new JSONObject(resultado);
				//JSONObject jsonObject2 = new JSONObject(jsonObject.getString("coord"));
				JSONObject jsonObject2 = jsonObject.getJSONObject("coord");
				
				rpta += "Longitud: " + jsonObject2.getDouble("lon") + "\n";
				rpta += "Latitud : " + jsonObject2.getDouble("lat") + "\n";
				//Log.i("Neyo1","Fuck!");
				JSONArray jsonArray = new JSONArray(jsonObject.getString("weather"));
				//Log.i("Neyo2","Fuck!");
				JSONObject json_weather = jsonArray.getJSONObject(0);
				
				rpta += "Nubes: " + json_weather.getString("description") + "\n";
				
				JSONObject json_main = jsonObject.getJSONObject("main");
				
				rpta += "Temp: " + json_main.getDouble("temp") + "\n";
				rpta += "Humedad : " + json_main.getDouble("humidity") + "\n";
				
				txt_resultado.setText(rpta);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Log.i("Neyo","Fuck!" + e);
				//Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
			}
			
		}
        
    }

}
