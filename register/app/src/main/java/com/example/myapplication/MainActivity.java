package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.res.Resources;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;


public class MainActivity extends AppCompatActivity {
    EditText name;  //用户名
    Spinner sex;  //性别
    EditText stunum;  //学号
    EditText pass;  //密码
    Spinner department;  //学院
    Button mbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=(EditText) findViewById(R.id.name);  //获取用户名
        pass=(EditText) findViewById(R.id.pass);  //获取密码
//        sex=(EditText) findViewById(R.id.sex);
        stunum=(EditText) findViewById(R.id.stunum);
  //      department=(EditText) findViewById(R.id.department);
        mbutton = findViewById(R.id.buttonback);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Resources res =getResources();
        String[] sexlist=res.getStringArray(R.array.sex0);//将province中内容添加到数组city中
        String[] departlist=res.getStringArray(R.array.depart0);
        sex = (Spinner) findViewById(R.id.sex1);//获取到spacer1
        department= (Spinner) findViewById(R.id.depart1);

        final ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sexlist);//创建Arrayadapter适配器
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,departlist);
        sex.setAdapter(adapter0);
        department.setAdapter(adapter1);
        sex.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String str=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        department.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String str=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }

    //登录验证代码
    public void  Check(View v) {

        String mname = name.getText().toString().trim();
        String mpass = pass.getText().toString().trim();
        String mnumber=stunum.getText().toString().trim();
//        String mdepartment = department.getText().toString().trim();
//        String msex = sex.getText().toString().trim();
        Toast.makeText(this, "恭喜，通过", Toast.LENGTH_SHORT).show();
    }
}
