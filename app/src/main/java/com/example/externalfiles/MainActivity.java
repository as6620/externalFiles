package com.example.externalfiles;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    TextView tV;
    EditText eT;
    private final String FILENAME = "exttest.txt";
    private final int REQUEST_CODE_PERMISSION = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tV = (TextView) findViewById(R.id.tV);
        eT = (EditText) findViewById(R.id.eT);

        String textFromFile = getFileText();
        if(isExternalStorageAvailable() && checkPermission()) {
            displayTv.setText(textFromFile);
        } else {
            requestPermission();
        }
    }

    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
    }

    public void goSave(View view) {

    }


    public String getTextFile() {

    }


    public void goReset(View view) {

    }


    public void goExit(View view) {
        goSave(view);
        finish();
    }



    /**
     * Inflates the options menu.
     *
     * @param menu The options menu.
     * @return true if the menu is created successfully.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Handles selection of menu items.
     *
     * @param item The selected menu item.
     * @return true if the menu item was handled successfully.
     */
    @Override
    public boolean onOptionsItemSelected(@Nullable MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menuCred) {
            Intent si = new Intent(this, activity_credits.class);
            startActivity(si);
        }
        return true;
    }
}