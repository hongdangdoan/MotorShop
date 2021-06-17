package com.example.motorshop.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.motorshop.activity.R;
import com.example.motorshop.helper.Helper;

public class LoginActivity extends AppCompatActivity {

    EditText etUsn, etPwd;
    Button btnLogin;
    TextView tvGuest;

    private final Helper h = new Helper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        setControl();
        setEvent();
    }

    private void setControl() {
        etUsn = (EditText) findViewById(R.id.etUsn);
        etPwd = (EditText) findViewById(R.id.etPwd);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvGuest = (TextView) findViewById(R.id.tvGuest);
    }

    private void setEvent() {

        btnLogin.setOnClickListener(v -> {
            String usn = etUsn.getText().toString();
            String pwd = etPwd.getText().toString();
            if(isValidLogin(usn, pwd)) {
                pwd = h.getCryptoHash(pwd, "MD5");

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://172.168.86.127:8080/api/motorshop/customers/authen?usn=" + usn + "&pwd=" + pwd;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        response -> {
                            if (!h.isNull(response)) {
                                System.out.println(response);
                                System.out.println("go from customer");
                                Intent i = new Intent(getApplicationContext(), VerifyActivity.class);
                                i.putExtra("userType", "customer");
                                i.putExtra("id", response);
                                startActivity(i);
                            }
                        },
                        error -> {
                            Toast.makeText(getApplicationContext(), "You cannot access right now!", Toast.LENGTH_SHORT).show();
                        }
                );
                queue.add(stringRequest);

                url = "http://172.168.86.127:8080/api/motorshop/staffs/authen?usn=" + usn + "&pwd=" + pwd;
                stringRequest = new StringRequest(Request.Method.GET, url,
                        response -> {
                            if (!h.isNull(response)) {
                                Intent i = new Intent(getApplicationContext(), VerifyActivity.class);
                                i.putExtra("userType", "staff");
                                i.putExtra("id", response);
                                startActivity(i);
                            }
                        },
                        error -> {
                            Toast.makeText(getApplicationContext(), "You cannot access right now!", Toast.LENGTH_SHORT).show();
                        }
                );
                queue.add(stringRequest);
            }
        });

        tvGuest.setOnClickListener(v -> {
                startActivity(new Intent(getApplicationContext(), VerifyActivity.class)); //test
        });

    }

    public boolean isValidLogin(String usn, String pwd) {
        if (isNull(usn, pwd)) {
            Toast.makeText(getApplicationContext(), "Username & Password must not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!h.isNum(usn)) {
            Toast.makeText(getApplicationContext(), "Phone only contains numbers!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (usn.length() < 10 || usn.length() > 15) {
            Toast.makeText(getApplicationContext(), "Phone's length is in range of 10 & 15!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pwd.length() < 4) {
            Toast.makeText(getApplicationContext(), "Password's min length is 4!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isNull(String phone, String password) {
        return h.isNull(phone) || h.isNull(password);
    }

//    public void setFullScreen() {
//        this.getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        );
//    }
}