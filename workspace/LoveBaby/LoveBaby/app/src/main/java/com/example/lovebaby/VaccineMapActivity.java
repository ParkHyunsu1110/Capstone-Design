package com.example.lovebaby;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lovebaby.Fragment.VaccineFragment;
import com.example.lovebaby.Model.VaccineMapModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class VaccineMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private ArrayList<VaccineMapModel> arrayList = new ArrayList<>();
    private ArrayList<Marker> markerList = new ArrayList();
    public static final int LOAD_SUCCESS = 101;
    private Spinner city_spinner,facname_spinner;
    public String selected;
    private String str_url;
    private double selected_lat;
    private double selected_lng;
    private String info_name;
    private String info_tel;
    private String info_location;
    private ArrayList<String> facility_name = new ArrayList<>();
    private String putfromdialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_map);

        city_spinner = (Spinner)findViewById(R.id.vaccine_map_spinner);
        facname_spinner = (Spinner)findViewById(R.id.vaccine_map_spinner2);

        str_url = "https://openapi.gg.go.kr/TbChildnatnPrvntncltnmdnstM?key=adfd8b2e53804414b96c0a2d8bb536fd&Type=json";
        String[] city_name = {"선택하세요","가평군","고양시","과천시","광명시","광주시","구리시","군포시","김포시","남양주시","동두천시","부천시","성남시","수원시","시흥시","안산시","안성시","안양시","양주시"
                ,"양평군","여주시","연천군","오산시","용인시","의왕시","의정부시","이천시","파주시","평택시","포천시","하남시","화성시"};


        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        HttpConnector thread = new HttpConnector();
        thread.start();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,city_name);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,facility_name);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city_spinner.setAdapter(adapter);
        city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = city_name[position];
                if(!selected.equals("선택하세요")) {
                    str_url += "&SIGUN_NM=" + selected;
                    thread.interrupt();
                    HttpConnector thread2 = new HttpConnector();
                    thread2.start();
                    facname_spinner.setAdapter(adapter2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        facname_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for(VaccineMapModel v: arrayList){
                    if(v.FACLT_NM.equals(facility_name.get(position))){
                        LatLng latLng = new LatLng(Double.parseDouble(v.REFINE_WGS84_LAT),Double.parseDouble(v.REFINE_WGS84_LOGT));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                        Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng).title(v.FACLT_NM).snippet(v.REFINE_ROADNM_ADDR+"\n"+v.TELNO));
                        marker.showInfoWindow();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(37.557667, 126.926546);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            googleMap.setMyLocationEnabled(true);
        } else{
            checkLocationPermissionWithRationale();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("위치정보")
                        .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용해 주세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(VaccineMapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }


    public void drawMarker(ArrayList<VaccineMapModel> arrayList){
        googleMap.clear();
        facility_name.clear();
        for(int i=0;i<arrayList.size();i++){
            VaccineMapModel item = arrayList.get(i);
            selected_lat = Double.parseDouble(item.REFINE_WGS84_LAT);
            selected_lng = Double.parseDouble(item.REFINE_WGS84_LOGT);
            info_location = item.REFINE_ROADNM_ADDR;
            info_name = item.FACLT_NM;
            info_tel = item.TELNO;
            MarkerOptions markerOptions = new MarkerOptions();

            Marker marker = googleMap.addMarker(markerOptions.position(
                    new LatLng(selected_lat,selected_lng)).title(info_name).snippet(info_location+"\n"+info_tel));
            markerList.add(marker);
            facility_name.add(info_name);
            setInfoWindow();
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(@NonNull @NotNull Marker marker) {
                    Intent inIntent = getIntent();
                    putfromdialog = inIntent.getStringExtra("getName");
                    if(putfromdialog.equals("y")) {
                        Intent outIntent = new Intent(getApplicationContext(),VaccineFragment.class);
                        outIntent.putExtra("facname", info_name);
                        setResult(RESULT_OK, outIntent);
                        finish();
                    }
                }
            });
        }
    }

    private final MyHandler myHandler = new MyHandler(this);

    public class MyHandler extends Handler {
        private WeakReference<VaccineMapActivity> weakReference;
        public MyHandler(VaccineMapActivity vaccineMapActivity) {
            weakReference = new WeakReference<VaccineMapActivity>(vaccineMapActivity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            VaccineMapActivity vaccineMapActivity = weakReference.get();
            if(vaccineMapActivity != null){
                switch (msg.what){
                    case LOAD_SUCCESS:
                        drawMarker(arrayList);
                        break;
                }
            }
        }
    }


    public class HttpConnector extends Thread{
        @Override
        public void run() {
            try {
                URL url = new URL(str_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    int resCode = conn.getResponseCode();
                    if (resCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = null;
                        while (true) {
                            line = reader.readLine();
                            if (line == null) {
                                break;
                            }
                            arrayList = JsonParser.jsonParser(line);
                        }
                        reader.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception e) {

            }
            Message message = myHandler.obtainMessage(LOAD_SUCCESS);
            myHandler.sendMessage(message);
        }
    }
    public void setInfoWindow(){
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            public View getInfoWindow(@NonNull @NotNull Marker marker) {
                return null;
            }

            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            public View getInfoContents(@NonNull @NotNull Marker marker) {
                LinearLayout info = new LinearLayout(VaccineMapActivity.this);
                info.setOrientation(LinearLayout.VERTICAL);
                TextView title = new TextView(VaccineMapActivity.this);
                title.setText(marker.getTitle());
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                TextView snippet = new TextView(VaccineMapActivity.this);
                snippet.setText(marker.getSnippet());
                snippet.setGravity(Gravity.CENTER);
                info.addView(title);
                info.addView(snippet);
                return info;
            }
        });
    }
}