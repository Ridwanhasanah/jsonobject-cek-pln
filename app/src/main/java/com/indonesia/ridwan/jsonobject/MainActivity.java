package com.indonesia.ridwan.jsonobject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONStringer;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

/*
    private String nama;
    private String tagihan;
*/

    private EditText edt_id,edt_bln,edt_thn;
    private TextView show_cek,txt_status,nama2,tagihan2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id();
    }
    public void id(){
        nama2 = (TextView) findViewById(R.id.nama2);
        tagihan2 =(TextView) findViewById(R.id.nominal2);
        edt_id = (EditText)findViewById(R.id.id);
        edt_bln = (EditText)findViewById(R.id.bln);
        edt_thn = (EditText)findViewById(R.id.thn);
        button = (Button)findViewById(R.id.btn_cek);
        button.setOnClickListener(this);
    }
    private void getDataPasien(String idp,String tahun,String bulan){
        String url = "http://ibacor.com/api/tagihan-pln?idp="+idp+"&thn="+tahun+"&bln="+bulan;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Data", response.toString());

                try{
                    JSONObject object = new JSONObject(response.toString());

                    //json object status
                    //json object data
                    JSONObject data = object.getJSONObject("data"); //string nama dari json object
                    String nama = data.getString("nama");
                    String tagihan = data.getString("tagihan");
                    //penutup json object data

                    nama2.setText(nama);
                    tagihan2.setText(tagihan);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Data",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    @Override
    public void onClick(View v) {
        if(v == button){
            String id = edt_id.getText().toString();
            String bln = edt_bln.getText().toString();
            String thn = edt_thn.getText().toString();

            try{
                getDataPasien(id,thn,bln);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Error Conection", Toast.LENGTH_LONG).show();
            }
        }
    }
}
