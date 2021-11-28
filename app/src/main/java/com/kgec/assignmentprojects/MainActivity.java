package com.kgec.assignmentprojects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String url="https://sheets.googleapis.com/v4/spreadsheets/1ghrzN4xvIVu9tLDah5sflb_WsC7BUWxxzT5o59DL420/values/Sheet1?key=AIzaSyAjjKlHaxvd4Rwn6UXvJmUA6Kql1tA5vy0";
    private RecyclerView recyclerView;
    public List<Data> list;
    DataAdapter dataAdapter;
    Button btn,add_students;

    String students_id,students_name,students_marks;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            add_students=findViewById(R.id.add_students);



        list=new ArrayList<>();
         dataAdapter=new DataAdapter(this,list);

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dataAdapter);
        btn=findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(0).setStatus("Status");
                for (int i=1;i<list.size();i++){
                    int mark=Integer.parseInt(list.get(i).getMarks());
                    if (mark>40){
                        list.get(i).setStatus("PASS");
                    }
                    else{
                        list.get(i).setStatus("FAIL");
                    }
                }
                dataAdapter.notifyDataSetChanged();

                sendDataToSheetApi();
            }
        });


        add_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(new Intent(getApplicationContext(),StduentsActivity.class),100);
            }
        });





        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response.toString());
                            JSONArray jsonArray=jsonObject.getJSONArray("values");

                            for (int i=0;i<jsonArray.length();i++){

                                JSONArray object=jsonArray.getJSONArray(i);
                               // int state_name= (int) object.get(Integer.parseInt("Students Id"));
//                                String no_recovered=object.getString(Integer.parseInt("Students Name"));
//                                String no_deaths=object.getString(Integer.parseInt("Marks"));

                                String id= String.valueOf(object.get(0));
                                String name= String.valueOf(object.get(1));
                                String marks= String.valueOf(object.get(2));
                                Log.w("onResponse", name );
                                Log.w("onResponse2", id );
                                Log.w("onResponse", marks );
                                Log.w("length", String.valueOf(object.length()));
                                if (object.length()>3)
                                    list.add(new Data(id,name,marks,String.valueOf(object.get(3))));
                                else
                                    list.add(new Data(id,name,marks,null));


                            }
                            dataAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // textView.setText("That didn't work!");
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK && data!=null){
            students_id=data.getExtras().get("id").toString();
            students_name=data.getExtras().get("name").toString();
            students_marks=data.getExtras().get("marks").toString();

            addDataToAPI();

            Data data2=new Data(students_id,students_name,students_marks,null);
            list.add(data2);
            dataAdapter.notifyDataSetChanged();

        }
    }

    private void addDataToAPI(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String uurl="https://sheets.googleapis.com/v4/spreadsheets/1ghrzN4xvIVu9tLDah5sflb_WsC7BUWxxzT5o59DL420/values/Sheet1:append?valueInputOption=RAW&key=AIzaSyAjjKlHaxvd4Rwn6UXvJmUA6Kql1tA5vy0";
        JSONObject jsonObject=getAddBody();

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, uurl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("res2",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer ya29.a0ARrdaM-YsL3Io3-Am7V7T52YWOs77ZQiOrrXAQeVmI-BC5Anecz5qS1L7O_Ydj4iP1mdO9MKzMvbnQck84XjLFnRL7hOQ7pYppCuo9jbld0qVeCkyfRabsinZMHUGY6yuLKwA5heMGWofEmrAgtkfgUcrZCB");
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");

                return params;
            }
        };

        queue.add(request);


    }


    private void sendDataToSheetApi() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject jsonObject=getRequestBody();
        String uurl="https://sheets.googleapis.com/v4/spreadsheets/1ghrzN4xvIVu9tLDah5sflb_WsC7BUWxxzT5o59DL420/values/Sheet1?valueInputOption=RAW&key=AIzaSyAjjKlHaxvd4Rwn6UXvJmUA6Kql1tA5vy0";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.PUT, uurl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("res",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer ya29.a0ARrdaM-YsL3Io3-Am7V7T52YWOs77ZQiOrrXAQeVmI-BC5Anecz5qS1L7O_Ydj4iP1mdO9MKzMvbnQck84XjLFnRL7hOQ7pYppCuo9jbld0qVeCkyfRabsinZMHUGY6yuLKwA5heMGWofEmrAgtkfgUcrZCB");
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");

                return params;
            }
        };

        queue.add(request);

    }

    private JSONObject getRequestBody(){
        JSONObject jsonObject=new JSONObject();
       try {
           jsonObject.put("majorDimension","ROWS");
           jsonObject.put("range","Sheet1");
           JSONArray jsonArray1=new JSONArray();
           for (int i=0;i<list.size();i++){
               JSONArray jsonArray=new JSONArray();
               jsonArray.put(list.get(i).getId());
               jsonArray.put(list.get(i).getStudentName());
               jsonArray.put(list.get(i).getMarks());
               jsonArray.put(list.get(i).getStatus());
               jsonArray1.put(jsonArray);
           }
           jsonObject.put("values",jsonArray1);
       }
       catch (Exception e){

       }
       return jsonObject;
    }

    private JSONObject getAddBody(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("majorDimension","ROWS");
            jsonObject.put("range","Sheet1");
            JSONArray jsonArray1=new JSONArray();
//            for (int i=0;i<list.size();i++){
//                JSONArray jsonArray=new JSONArray();
//                jsonArray.put(list.get(i).getId());
//                jsonArray.put(list.get(i).getStudentName());
//                jsonArray.put(list.get(i).getMarks());
//                jsonArray.put(list.get(i).getStatus());
//                jsonArray1.put(jsonArray);
//            }

            JSONArray jsonArray=new JSONArray();
            jsonArray.put(students_id);
            jsonArray.put(students_name);
            jsonArray.put(students_marks);
            jsonArray1.put(jsonArray);
            jsonObject.put("values",jsonArray1);
        }
        catch (Exception e){

        }
        return jsonObject;
    }

}

