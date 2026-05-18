package com.example.mobilprogramlama; // Kendi paket adının kaldığına emin ol

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

// Bu anotasyon, bu arayüzün veritabanı işlemleri yapacağını belirtir
@Dao
public interface MemberDao {

    // Ekleme işlemi (Arka planda INSERT INTO... kodunu kendisi yazar)
    @Insert
    void insert(Member member);

    // Güncelleme işlemi
    @Update
    void update(Member member);

    // Silme işlemi
    @Delete
    void delete(Member member);

    // Tüm üyeleri listeleme işlemi
    @Query("SELECT * FROM members")
    List<Member> getAllMembers();

    // Arama işlemi (Masaüstündeki arama vizyonumuzu mobile de taşıyoruz)
    @Query("SELECT * FROM members WHERE ad_soyad LIKE '%' || :searchQuery || '%'")
    List<Member> searchMembers(String searchQuery);
}