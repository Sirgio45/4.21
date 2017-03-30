package com.ebookfrenzy.a421;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    EditText downloadedEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadedEditText = (EditText)findViewById(R.id.downloadedEditText);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FileService.TRANSACTION_DONE);

        registerReceiver(downloadReceiver, intentFilter);
    }

    public void startFileService(View view)
    {
        Intent intent = new Intent(this, FileService.class);

        intent.putExtra("url","https://www.newthinktank.com/wordpress/lotr.txt");

        this.startService(intent);
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("FileService", "Service Received");

            showFileContents();
        }
    };

    public void showFileContents()
    {
        StringBuilder sb;

        try
        {
            FileInputStream fis = this.openFileInput("myFile");

            InputStreamReader isr = new InputStreamReader(fis,"UTF-8");

            BufferedReader bufferedReader = new BufferedReader(isr);

            sb = new StringBuilder();

            String line;

            while((line = bufferedReader.readLine()) !=null)
            {
                sb.append(line).append("\n");
            }

            downloadedEditText.setText(sb.toString());

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
