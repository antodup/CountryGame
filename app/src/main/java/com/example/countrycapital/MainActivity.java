package com.example.countrycapital;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lamudi.phonefield.PhoneEditText;
import com.lamudi.phonefield.PhoneInputLayout;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    EditText inputName;
    Button b;
    static Dialog d;
    int year = Calendar.getInstance().get(Calendar.YEAR);
    PhoneEditText phoneEditText;
    Button button;

    Button button_maps;
    static Dialog mapspop;
    MapView mMapView;

    Button button_validate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = (Button) findViewById(R.id.button_date);
        b.setText("" + year);
        b.setOnClickListener(this);

        phoneEditText = (PhoneEditText) findViewById(R.id.edit_text);
        button = (Button) findViewById(R.id.button_validate);

        // you can set the hint as follows
        phoneEditText.setHint(R.string.phone_hint);

        // you can set the default country as follows
        phoneEditText.setDefaultCountry("FR");

        button_maps = (Button) findViewById(R.id.button_pin);
        button_maps.setOnClickListener(this);

        button_validate = (Button) findViewById(R.id.button_validate);
        button_validate.setOnClickListener(this);

        inputName = (EditText) findViewById(R.id.input_name);
    }

    public void showYearDialog() {

        d = new Dialog(MainActivity.this);
        d.setTitle("Year Picker");
        d.setContentView(R.layout.yeardialog);
        Button set = (Button) d.findViewById(R.id.button1);
        Button cancel = (Button) d.findViewById(R.id.button2);
        TextView year_text = (TextView) d.findViewById(R.id.year_text);
        year_text.setText("" + year);
        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

        nopicker.setMaxValue(year + 50);
        nopicker.setMinValue(year - 50);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(year);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.setText(String.valueOf(nopicker.getValue()));
                d.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();

    }

    public void showMapsDialog() {
        mapspop = new Dialog(MainActivity.this);
        mapspop.setTitle("Google Maps");
        mapspop.setContentView(R.layout.mapsdialog);
        mMapView = (MapView) mapspop.findViewById(R.id.map_view);
        MapsInitializer.initialize(MainActivity.this);
        mMapView.onCreate(mapspop.onSaveInstanceState());
        mMapView.onResume();
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {

                LatLng sydney = new LatLng(-33.852, 151.211);
                googleMap.addMarker(new MarkerOptions().position(sydney)
                        .title("Marker in Sydney"));
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(sydney.latitude, sydney.longitude, 1);
                    Log.e(TAG, addresses.get(0).getAddressLine(0));
                    button_maps.setText(addresses.get(0).getAddressLine(0));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getLocalizedMessage());
                    button_maps.setText("Could not find nearest address");
                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        });
        mapspop.show();
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_date:
                showYearDialog();
                break;
            case R.id.button_validate:
                boolean valid = true;

                if (phoneEditText.isValid()) {
                    phoneEditText.setError(null);
                } else {
                    phoneEditText.setError(getString(R.string.invalid_phone_number));
                    valid = false;
                }

                if (valid) {
                    Toast.makeText(MainActivity.this, R.string.valid_phone_number, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent (MainActivity.this, GameActivity.class);

                    Log.i("name_input", inputName.getText().toString());

                    intent.putExtra("NAME_PLAYER", inputName.getText().toString());
                    MainActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, R.string.invalid_phone_number, Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.button_pin:
                showMapsDialog();
                break;

        }
    }


}
