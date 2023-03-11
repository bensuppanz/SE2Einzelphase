package com.example.se2einzelphase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText inputMatrikelnummer = findViewById(R.id.inputMatrikelnummer);
        TextView serverAnswer = findViewById(R.id.serverAnswer);
        Button submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matrikelnr = inputMatrikelnummer.getText().toString();
                TCPClient client = new TCPClient(matrikelnr);

                Thread t = new Thread(client);
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                serverAnswer.setText(client.serverAnswer);
            }
        });
    }
}