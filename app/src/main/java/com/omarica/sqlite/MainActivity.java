package com.omarica.sqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText nameEditText;
    EditText surnameEditText;
    EditText gradeEditText;
    Button viewButton;
    Button addButton;
    String name,surname,grade;
    boolean isInserted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        nameEditText = findViewById(R.id.nameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);
        gradeEditText = findViewById(R.id.gradeEditText);
        viewButton = findViewById(R.id.viewButton);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = nameEditText.getText().toString();
                surname = surnameEditText.getText().toString();
                grade = gradeEditText.getText().toString();
                if(!name.equals("") && !surname.equals("") && !grade.equals(""))
                {
                    isInserted = myDb.insertData(name,surname,grade);
                    if(isInserted)
                    {
                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Data NOT Inserted", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please complete the data.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();

                if(res.getCount() == 0){
                    showMessage("Error","No records..");
                    return;
                }
                StringBuffer buffer = new StringBuffer();

                while (res.moveToNext())
                {
                    buffer.append("ID : "+res.getString(0)+"\n");
                    buffer.append("Name : "+res.getString(1)+"\n");
                    buffer.append("Surname : "+res.getString(2)+"\n");
                    buffer.append("Grade : "+res.getString(3)+"\n\n");

                }

                showMessage("Data",buffer.toString());
            }

        });

    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
