package com.kgec.assignmentprojects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class StduentsActivity extends AppCompatActivity {

    private EditText id,name,marks;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stduents);
        id=findViewById(R.id.students_id_text);
        name=findViewById(R.id.students_name_text);
        marks=findViewById(R.id.students_mARKS_text);
        btn=findViewById(R.id.add_btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id1=id.getText().toString();
                String name1=name.getText().toString();
                String marks1=marks.getText().toString();

                UploadData(id1,name1,marks1);
            }
        });


    }

    private void UploadData(String id1, String name1, String marks1) {

        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("id",id1);
        intent.putExtra("name",name1);
        intent.putExtra("marks",marks1);
        setResult(RESULT_OK,intent);
        finish();

    }


}