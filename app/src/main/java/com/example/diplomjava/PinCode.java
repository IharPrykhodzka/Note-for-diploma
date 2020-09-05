package com.example.diplomjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PinCode extends AppCompatActivity {

    Button number1, number2, number3, number4, number5, number6,
            number7, number8, number9, number0, backSpace;
    TextView txtMassage;
    ArrayList<String> editPinCode = new ArrayList<String>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        number0 = findViewById(R.id.btnZero);
        number1 = findViewById(R.id.btnOne);
        number2 = findViewById(R.id.btnTwo);
        number3 = findViewById(R.id.btnThree);
        number4 = findViewById(R.id.btnFour);
        number5 = findViewById(R.id.btnFive);
        number6 = findViewById(R.id.btnSix);
        number7 = findViewById(R.id.btnSeven);
        number8 = findViewById(R.id.btnEight);
        number9 = findViewById(R.id.btnNine);
        backSpace = findViewById(R.id.btnBackspace);
        txtMassage = findViewById(R.id.txtMassage);

        checkPinCode();

    }

    public void checkPinCode() {

        number0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOnly4("0");
            }
        });

        number1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOnly4("1");
            }
        });

        number2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOnly4("2");
            }
        });

        number3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOnly4("3");
            }
        });

        number4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOnly4("4");
            }
        });

        number5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOnly4("5");
            }
        });

        number6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOnly4("6");
            }
        });

        number7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOnly4("7");
            }
        });

        number8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOnly4("8");
            }
        });

        number9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOnly4("9");
            }
        });

    }

    public void checkOnly4(String someNumber) {
        if (editPinCode.get(0) == null) {
            editPinCode.add(someNumber);
        }else if (editPinCode.get(1) == null) {
            editPinCode.add(someNumber);
        }else if (editPinCode.get(2) == null) {
            editPinCode.add(someNumber);
        }else if (editPinCode.get(3) == null) {
            editPinCode.add(someNumber);
        }else {

            StringBuilder pin = new StringBuilder();
            for (String s : editPinCode) {
                pin.append(s);
            }
            if (App.getKeystore().checkPin(pin.toString())) {
                finish();
            }else {
                txtMassage.setText(R.string.txt_wrong_pin);
                editPinCode.clear();
            }

        }
    }
}
