package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.example.weatherapp.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private CurrentWeatherFragment currentWeatherFragment;
    private ForecastWeatherFragment forecastWeatherFragment;
    private WeatherPagerAdapter weatherPagerAdapter;

    private static final int LOCATION_REQUIRED_CODE = 111;
    private FusedLocationProviderClient providerClient;
    private Geocoder geocoder;
    private List<Address> addressList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main);
        currentWeatherFragment=new CurrentWeatherFragment();
        forecastWeatherFragment=new ForecastWeatherFragment();
        weatherPagerAdapter=new WeatherPagerAdapter(getSupportFragmentManager());
        providerClient= LocationServices.getFusedLocationProviderClient(this);
        binding.viewPager.setAdapter(weatherPagerAdapter);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));


        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Current"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Forecast"));
        binding.tabLayout.setTabTextColors(R.color.color1,Color.WHITE);
        binding.tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(checkLocationPermission()){
            getDeviceLastLocation();
        }
    }

    //..............................

    private boolean checkLocationPermission(){
        String [] permissionn={Manifest.permission.ACCESS_FINE_LOCATION};
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,permissionn,LOCATION_REQUIRED_CODE);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==LOCATION_REQUIRED_CODE){
            Toast.makeText(this, "Problem....1", Toast.LENGTH_SHORT).show();
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){

                getDeviceLastLocation();
                Toast.makeText(this, "Problem...2", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(this, "Pleae Allow permission", Toast.LENGTH_SHORT).show();
                //explain for request permission ...
            }
        }
    }

    private void getDeviceLastLocation() {
        if(checkLocationPermission()){
            providerClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location==null) {
                        Toast.makeText(MainActivity.this, "Not found Location", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double latitude=location.getLatitude();
                    double longitude=location.getLongitude();
                    currentWeatherFragment.updateLoction(latitude,longitude);
                    forecastWeatherFragment.udpateLocation2(latitude,longitude);


                }
            });
        }

    }



    public class WeatherPagerAdapter extends FragmentPagerAdapter {
        public WeatherPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return currentWeatherFragment;

                case 1:
                    return forecastWeatherFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
