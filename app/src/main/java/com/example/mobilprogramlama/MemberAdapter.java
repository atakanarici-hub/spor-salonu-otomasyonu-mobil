package com.example.mobilprogramlama; // Paket adını kontrol et

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private List<Member> memberList;
    private OnMemberClickListener listener;

    // Tıklama olayları için arayüz (Interface)
    public interface OnMemberClickListener {
        void onDeleteClick(Member member);
        void onItemClick(Member member); // <--- Bunu ekle
    }

    public MemberAdapter(List<Member> memberList, OnMemberClickListener listener) {
        this.memberList = memberList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new MemberViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        Member member = memberList.get(position);
        holder.txtAd.setText(member.adSoyad);
        holder.txtDetay.setText(member.telefon + " | " + member.uyelikTipi);

        holder.btnSil.setOnClickListener(v -> listener.onDeleteClick(member));
        // Satırın tamamına tıklama olayı
        holder.itemView.setOnClickListener(v -> listener.onItemClick(member));
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public void updateList(List<Member> newList) {
        this.memberList = newList;
        notifyDataSetChanged();
    }

    static class MemberViewHolder extends RecyclerView.ViewHolder {
        TextView txtAd, txtDetay;
        ImageButton btnSil;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAd = itemView.findViewById(R.id.txtItemAd);
            txtDetay = itemView.findViewById(R.id.txtItemDetay);
            btnSil = itemView.findViewById(R.id.btnItemSil);
        }
    }
}