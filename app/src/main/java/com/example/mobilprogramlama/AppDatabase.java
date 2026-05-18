package com.example.mobilprogramlama; // Kendi paket adının kaldığına emin ol

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Veritabanının hangi tablolardan oluştuğunu ve versiyonunu belirtiyoruz
@Database(entities = {Member.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // DAO'ya erişim sağlayacağımız zorunlu metot
    public abstract MemberDao memberDao();

    // Singleton (Tekil Nesne) kuralı: Hafızada sadece bir kopya yaşayacak
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "gym_database")
                            // DİKKAT: Sadece test ve MVP için Main Thread'de çalışmasına izin veriyoruz
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}