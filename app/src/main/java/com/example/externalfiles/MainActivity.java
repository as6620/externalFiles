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
import android.widget.Toast;

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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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

    /**
     * Checks whether external storage is mounted and available.
     *
     * @return true if external storage is mounted.
     */
    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * Checks if the app has permission to write to external storage.
     *
     * @return true if permission is granted.
     */
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Requests permission to write to external storage.
     */
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
    }

    /**
     * Callback after requesting permissions.
     *
     * @param requestCode  Request code passed in requestPermissions().
     * @param permissions  The requested permissions.
     * @param grantResults The grant results for the corresponding permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission to access external storage granted", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Permission to access external storage NOT granted", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Saves the input text to a file in external storage.
     *
     * @param view The button view that triggered this method.
     */
    public void goSave(View view) {
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file, true);
            writer.write(eT.getText().toString());
            writer.close();
            tV.setText(getTextFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Reads the contents of the file from external storage.
     *
     * @return The content of the file as a String.
     */
    public String getTextFile() {
        String text = "";
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();
            FileReader reader = new FileReader(file);
            BufferedReader bR = new BufferedReader(reader);
            StringBuilder sB = new StringBuilder();
            String line = bR.readLine();
            while (line != null) {
                sB.append(line+'\n');
                line = bR.readLine();
            }
            bR.close();
            reader.close();
            text = sB.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text;
    }


    /**
     * Clears the contents of the file and the input/output views.
     *
     * @param view The button view that triggered this method.
     */
    public void goReset(View view) {
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write("");
            writer.close();
            eT.setText("");
            tV.setText("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the current text and exits the activity.
     *
     * @param view The button view that triggered this method.
     */
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