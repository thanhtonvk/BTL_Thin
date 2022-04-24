package com.example.shoprobothutbui.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shoprobothutbui.API.ApiService;
import com.example.shoprobothutbui.Activity.DanhSachSanPhamActivity;
import com.example.shoprobothutbui.Adapters.HangLoaiAdapter;
import com.example.shoprobothutbui.Adapters.SanPhamAdapter;
import com.example.shoprobothutbui.Models.Hang;
import com.example.shoprobothutbui.Models.SanPham;
import com.example.shoprobothutbui.R;
import com.example.shoprobothutbui.Utils.Common;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrangChuFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trang_chu, container, false);
    }

    TwoWayView lv_hang;
    GridView lv_sanpham;
    HangLoaiAdapter hangAdapter;
    HangLoaiAdapter loaiAdapter;
    List<String> hangList = new ArrayList<>();
    List<Hang> hangs = new ArrayList<>();
    SanPhamAdapter sanPhamAdapter;
    List<SanPham> sanPhamList = new ArrayList<>();
    EditText edtTimKiem;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        loadDS();
        edtTimKiem = view.findViewById(R.id.edtTimKiem);
        edtTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String timKiem = edtTimKiem.getText().toString();
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    Common.sanPhamList = new ArrayList<>();
                    for (SanPham sanPham : sanPhamList
                    ) {
                        if (sanPham.getTenSP().toLowerCase().contains(timKiem.toLowerCase()) ||
                                sanPham.getTenHang().toLowerCase().contains(timKiem.toLowerCase()) ||
                                sanPham.getTenLoai().toLowerCase().contains(timKiem.toLowerCase())) {
                            Common.sanPhamList.add(sanPham);
                        }
                    }
                    startActivity(new Intent(getContext(), DanhSachSanPhamActivity.class));
                }
                return false;
            }
        });
    }

    //ánh xạ
    private void anhXa(View view) {
        lv_hang = view.findViewById(R.id.lv_hang);
        lv_sanpham = view.findViewById(R.id.lv_sanpham);
        hangAdapter = new HangLoaiAdapter(getContext(), hangList);
        lv_hang.setAdapter(hangAdapter);
        sanPhamAdapter = new SanPhamAdapter(getContext(), sanPhamList);
        lv_sanpham.setAdapter(sanPhamAdapter);
    }

    //load DS
    private void loadDS() {
        lv_hang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Hang hang = hangs.get(i);
                ApiService.api.getSanPhamTheoHang(hang.getMaHang()).enqueue(new Callback<List<SanPham>>() {
                    @Override
                    public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                        if (response.body() != null) {
                            Common.sanPhamList = response.body();
                            startActivity(new Intent(getContext(), DanhSachSanPhamActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SanPham>> call, Throwable t) {

                    }
                });
            }
        });
        ApiService.api.getHangs().enqueue(new Callback<List<Hang>>() {
            @Override
            public void onResponse(Call<List<Hang>> call, Response<List<Hang>> response) {
                if (response.body() != null) {

                    for (Hang hang : response.body()
                    ) {
                        hangs.add(hang);
                        hangList.add(hang.getHinhAnh());
                        hangAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Hang>> call, Throwable t) {

            }
        });

        ApiService.api.getSanPhams().enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if (response.body() != null) {
                    for (SanPham sanPham : response.body()
                    ) {
                        sanPhamList.add(sanPham);
                        sanPhamAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });
    }
}