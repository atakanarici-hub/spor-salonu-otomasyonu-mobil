package com.example.mobilprogramlama;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Sınıf seviyesindeki değişkenler (Artık tüm metodlar erişebilir)
    private AppDatabase db;
    private MemberDao memberDao;
    private RecyclerView recyclerView;
    private MemberAdapter adapter;

    // Arayüz elemanları
    private EditText editAdSoyad, editTelefon;
    private Spinner spinnerUyelikTipi;
    private Button btnEkle;

    // Güncelleme takibi için
    private Member seciliUye = null;
    private boolean guncellemeModu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Veritabanı Bağlantısı
        db = AppDatabase.getInstance(this);
        memberDao = db.memberDao();

        // 2. Arayüz Elemanlarını Bağlama
        editAdSoyad = findViewById(R.id.editAdSoyad);
        editTelefon = findViewById(R.id.editTelefon);
        spinnerUyelikTipi = findViewById(R.id.spinnerUyelikTipi);
        btnEkle = findViewById(R.id.btnEkle);
        recyclerView = findViewById(R.id.recyclerViewUyeler);

        // 3. Spinner Doldurma
        String[] uyelikTipleri = {"Aylık", "3 Aylık", "6 Aylık", "Yıllık"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, uyelikTipleri);
        spinnerUyelikTipi.setAdapter(spinnerAdapter);

        // 4. RecyclerView Hazırlama
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData();

        // 5. Buton Tıklama (Ekle veya Güncelle)
        btnEkle.setOnClickListener(v -> {
            String adSoyad = editAdSoyad.getText().toString().trim();
            String telefon = editTelefon.getText().toString().trim();
            String uyelikTipi = spinnerUyelikTipi.getSelectedItem().toString();

            if (adSoyad.isEmpty() || telefon.isEmpty()) {
                Toast.makeText(MainActivity.this, "Lütfen alanları doldurun!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (guncellemeModu && seciliUye != null) {
                // GÜNCELLEME MANTIĞI
                seciliUye.adSoyad = adSoyad;
                seciliUye.telefon = telefon;
                seciliUye.uyelikTipi = uyelikTipi;

                memberDao.update(seciliUye);
                Toast.makeText(this, "Üye güncellendi!", Toast.LENGTH_SHORT).show();

                // Modu sıfırla
                guncellemeModu = false;
                seciliUye = null;
                btnEkle.setText("Yeni Üye Ekle");
            } else {
                // YENİ EKLEME MANTIĞI
                Member yeniUye = new Member();
                yeniUye.adSoyad = adSoyad;
                yeniUye.telefon = telefon;
                yeniUye.uyelikTipi = uyelikTipi;

                memberDao.insert(yeniUye);
                Toast.makeText(this, "Üye eklendi!", Toast.LENGTH_SHORT).show();
            }

            // Ortak İşlemler: Formu temizle ve listeyi yenile
            editAdSoyad.setText("");
            editTelefon.setText("");
            spinnerUyelikTipi.setSelection(0);
            loadData();
        });
    }

    private void loadData() {
        List<Member> uyeler = memberDao.getAllMembers();

        adapter = new MemberAdapter(uyeler, new MemberAdapter.OnMemberClickListener() {
            @Override
            public void onDeleteClick(Member member) {
                memberDao.delete(member);
                Toast.makeText(MainActivity.this, "Üye Silindi", Toast.LENGTH_SHORT).show();
                loadData();
            }

            @Override
            public void onItemClick(Member member) {
                // Bilgileri forma taşı
                editAdSoyad.setText(member.adSoyad);
                editTelefon.setText(member.telefon);

                // Spinner'da doğru tipi seç
                ArrayAdapter<String> currentAdapter = (ArrayAdapter<String>) spinnerUyelikTipi.getAdapter();
                int pos = currentAdapter.getPosition(member.uyelikTipi);
                spinnerUyelikTipi.setSelection(pos);

                // Güncelleme moduna gir
                seciliUye = member;
                guncellemeModu = true;
                btnEkle.setText("Üyeyi Güncelle");
            }
        });

        recyclerView.setAdapter(adapter);
    }
}