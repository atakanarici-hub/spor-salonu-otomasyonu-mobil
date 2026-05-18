package com.example.mobilprogramlama; // Kendi paket adın olmalı

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Arayüzdeki elemanları Java'ya bağlıyoruz
        EditText editUsername = findViewById(R.id.editUsername);
        EditText editPassword = findViewById(R.id.editPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        // Butona tıklama olayı
        btnLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();

            // Hardcoded doğrulama
            if (username.equals("user") && password.equals("123456")) {

                // 1. Intent (Niyet) oluştur: LoginActivity'den MainActivity'ye gitmek istiyorum
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                // 2. Ekranı başlat
                startActivity(intent);

                // 3. Login ekranını hafızadan sil (Geri tuşuna basınca tekrar login'e dönmemesi için)
                finish();

            } else {
                // Hatalı girişte Toast (Kısa süreli uyarı mesajı) göster
                Toast.makeText(LoginActivity.this, "Hatalı kullanıcı adı veya şifre!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}