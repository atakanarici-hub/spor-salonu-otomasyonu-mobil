package com.example.mobilprogramlama; // Kendi paket adının kaldığına emin ol

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Bu anotasyon, sınıfın veritabanında 'members' adında bir tablo olacağını belirtir
@Entity(tableName = "members")
public class Member {

    // ID'yi otomatik artan Primary Key yapıyoruz
    @PrimaryKey(autoGenerate = true)
    public int id;

    // Sütun isimlerini belirliyoruz
    @ColumnInfo(name = "ad_soyad")
    public String adSoyad;

    @ColumnInfo(name = "telefon")
    public String telefon;

    @ColumnInfo(name = "uyelik_tipi")
    public String uyelikTipi;

    // Not: Tarih işlemleri Android'de tip dönüşümü (TypeConverter) gerektirdiği için
    // MVP aşamasında şimdilik dışarıda bırakıyoruz, temel CRUD'a odaklanacağız.
}