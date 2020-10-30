package com.example.myapplication4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //super.setTitle("건강데이터분석");
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottomnavbar);
        menu=bottomNavigationView.getMenu();

        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        bottomNavigationView.setSelectedItemId(R.id.menu_capsual);  //초기 화면 메뉴 아이템 지정
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new MainFragment()).commit(); //초기 화면 fragment 지정



    } //onCreate(),,

    //


    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
                case R.id.menu_data:
                    menuItem.setIcon(R.drawable.data_after_icon);  //건강데이터수집 선택한 이미지
                    menu.findItem(R.id.menu_capsual).setIcon(R.drawable.main_icon);
                    menu.findItem(R.id.menu_mypage).setIcon(R.drawable.mypage_icon);
                    selectedFragment = new DataFragment();
                    break;
                case R.id.menu_capsual:
                    menuItem.setIcon(R.drawable.main_after_icon);  //티캡슐 선택한 이미지
                    menu.findItem(R.id.menu_data).setIcon(R.drawable.data_icon);
                    menu.findItem(R.id.menu_mypage).setIcon(R.drawable.mypage_icon);
                    selectedFragment = new MainFragment();
                    break;
                case R.id.menu_mypage:
                    menuItem.setIcon(R.drawable.mypage_after_icon);  //마이페이지 선택한 이미지
                    menu.findItem(R.id.menu_data).setIcon(R.drawable.data_icon);
                    menu.findItem(R.id.menu_capsual).setIcon(R.drawable.main_icon);
                    selectedFragment = new MypageFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,selectedFragment).commit();

            return true;
        } //ItemSelectedListener class,,
    }
}