package com.example.diplomjava;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PinCode extends AppCompatActivity {

    Button number1, number2, number3, number4, number5, number6,
            number7, number8, number9, number0, backSpace;
    TextView txtMassage;
    ImageView oval1, oval2, oval3, oval4;
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
        oval1 = findViewById(R.id.oval1);
        oval2 = findViewById(R.id.oval2);
        oval3 = findViewById(R.id.oval3);
        oval4 = findViewById(R.id.oval4);

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

        backSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editPinCode.size() == 3) {
                    editPinCode.remove(2);
                    oval3.setBackgroundResource(R.drawable.shape_for_pin_code);
                } else if (editPinCode.size() == 2) {
                    editPinCode.remove(1);
                    oval2.setBackgroundResource(R.drawable.shape_for_pin_code);
                } else if (editPinCode.size() == 1) {
                    editPinCode.remove(0);
                    oval1.setBackgroundResource(R.drawable.shape_for_pin_code);
                }
            }
        });

    }

    public void checkOnly4(String someNumber) {
        if (editPinCode.isEmpty()) {
            editPinCode.add(someNumber);
            oval1.setBackgroundResource(R.drawable.shape_for_pin_code_uses);
        } else if (editPinCode.size() == 1) {
            editPinCode.add(someNumber);
            oval2.setBackgroundResource(R.drawable.shape_for_pin_code_uses);
        } else if (editPinCode.size() == 2) {
            editPinCode.add(someNumber);
            oval3.setBackgroundResource(R.drawable.shape_for_pin_code_uses);
        } else if (editPinCode.size() == 3) {
            editPinCode.add(someNumber);
            oval4.setBackgroundResource(R.drawable.shape_for_pin_code_uses);

            StringBuilder pin = new StringBuilder();
            for (String s : editPinCode) {
                pin.append(s);
            }
            if (App.getKeystore().checkPin(pin.toString())) {
                cleanEditPin();
                Intent intent = new Intent();
                intent.putExtra("PIN", true);
                setResult(RESULT_OK, intent);


                finish();
            } else {
                txtMassage.setText(R.string.txt_wrong_pin);
                cleanEditPin();
            }
        }
    }

    public void cleanEditPin() {
        editPinCode.clear();
        oval1.setBackgroundResource(R.drawable.shape_for_pin_code);
        oval2.setBackgroundResource(R.drawable.shape_for_pin_code);
        oval3.setBackgroundResource(R.drawable.shape_for_pin_code);
        oval4.setBackgroundResource(R.drawable.shape_for_pin_code);
    }
}
