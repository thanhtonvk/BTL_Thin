package com.example.shoprobothutbui.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.shoprobothutbui.Fragments.GioHangFragment;
import com.example.shoprobothutbui.Fragments.TrangChuFragment;
import com.example.shoprobothutbui.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class ManHinhChinhActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);
        anhXa();
        if (savedInstanceState == null)

        {

            chipNavigationBar.setItemSelected(R.id.home, true);

            fragmentManager = getSupportFragmentManager();

            TrangChuFragment homeFragment = new TrangChuFragment();

            fragmentManager.beginTransaction()

                    .replace(R.id.fragment_container, homeFragment)

                    .commit();

        }

        setFrameLayout();
    }

    //ánh xạ
    private void anhXa() {

        chipNavigationBar = findViewById(R.id.bottom_nav);
    }

    //set view fragment
    private void setFrameLayout() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {

            @Override

            public void onItemSelected(int i) {

                Fragment fragment = null;

                switch (i)

                {

                    case R.id.home:

                        fragment = new TrangChuFragment();

                        break;

                    case R.id.giohang:

                        fragment = new GioHangFragment();

                        break;

                }

                if (fragment != null)

                {

                    fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction()

                            .replace(R.id.fragment_container, fragment)

                            .commit();

                }

            }

        });
    }
}