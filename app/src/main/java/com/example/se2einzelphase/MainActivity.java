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
        TextView answerField = findViewById(R.id.answerField);
        Button submitButton = findViewById(R.id.submitButton);
        Button calculateButton = findViewById(R.id.calculateButton);

        submitButton.setOnClickListener(new View.OnClickListener() {    //add onClickListener to submit button
            @Override
            public void onClick(View view) {
                String matrikelnr = inputMatrikelnummer.getText().toString();   //get input from textfield
                TCPClient client = new TCPClient(matrikelnr);   //create new client and give matrikelnummer as parameter

                Thread t = new Thread(client);
                t.start();  //start thread
                try {
                    t.join();   //wait until thread gets terminated, this means the answer from server should be saved in 'client.serverAnswer'
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                answerField.setText(client.serverAnswer);  //set textfield to display the answer of the server
            }
        });

        calculateButton.setOnClickListener((new View.OnClickListener() {    //add onClickListener to calculate button
            @Override
            public void onClick(View view) {
                String matrikelnr = inputMatrikelnummer.getText().toString();   //get matrikelnummer from input field
                int crossSum = calculateCrossSum(matrikelnr);   //calculate cross sum of input

                String binaryResult = Integer.toBinaryString(crossSum); //convert crossSum to binary

                answerField.setText(binaryResult);  //set textfield to display the calculation result
            }
        }));
    }

    private int calculateCrossSum(String matrikelnr) {
        int result = 0;
        for(int i = 0; i < matrikelnr.length(); i++){   //iterate over matrikelnummer
            int digit = Character.getNumericValue(matrikelnr.charAt(i));    //get numeric value of digits
            result += digit;    //increment result by the digits
        }
        return result;
    }
}