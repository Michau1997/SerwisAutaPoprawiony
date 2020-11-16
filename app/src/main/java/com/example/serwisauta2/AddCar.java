package com.example.serwisauta2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class AddCar extends AppCompatActivity {

    private Toolbar acToolbar;
    private ProgressDialog loadingBar;
    private Button AddCarButton;
    private FirebaseAuth mAuth;
    private EditText Name, Mileage, Vin, Inspection, Policy, OilMileage, OilDate,
            FiltersMileage, FiltersDate, BrakesMileage, BrakesDate, TiresMileage,
            TiresDate, EngineTimingMileage, EngineTimingDate, SparkPlugsCablesMileage,
            SparkPlugsCablesDate;
    private Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        acToolbar = (Toolbar) findViewById(R.id.add_car_toolbar);
        setSupportActionBar(acToolbar);
        getSupportActionBar().setTitle("Dodawanie nowego auta");

        mAuth = FirebaseAuth.getInstance();
        AddCarButton = (Button) findViewById(R.id.buttonAddCar);
        loadingBar = new ProgressDialog(this);

        Name = (EditText) findViewById(R.id.car_name);
        Mileage = (EditText) findViewById(R.id.car_mileage);
        Vin = (EditText) findViewById(R.id.car_vin);
        Inspection = (EditText) findViewById(R.id.car_inspection);
        Policy = (EditText) findViewById(R.id.car_policy);
        OilMileage = (EditText) findViewById(R.id.car_oil_mileage);
        OilDate = (EditText) findViewById(R.id.car_oil_date);
        FiltersMileage = (EditText) findViewById(R.id.car_filters_mileage);
        FiltersDate = (EditText) findViewById(R.id.car_filters_date);
        BrakesMileage = (EditText) findViewById(R.id.car_brakes_mileage);
        BrakesDate = (EditText) findViewById(R.id.car_brakes_date);
        TiresMileage = (EditText) findViewById(R.id.car_tires_mileage);
        TiresDate = (EditText) findViewById(R.id.car_tires_date);
        EngineTimingMileage = (EditText) findViewById(R.id.car_engine_timing_mileage);
        EngineTimingDate = (EditText) findViewById(R.id.car_engine_timing_date);
        SparkPlugsCablesMileage = (EditText) findViewById(R.id.car_spark_plugs_cables_mileage);
        SparkPlugsCablesDate = (EditText) findViewById(R.id.car_spark_plugs_cables_date);


        AddCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String mileage = Mileage.getText().toString();
                String vin = Vin.getText().toString();
                String inspection = Inspection.getText().toString();
                String policy = Policy.getText().toString();
                String oilmileage = OilMileage.getText().toString();
                String oildate = OilDate.getText().toString();
                String filtersmileage = FiltersMileage.getText().toString();
                String filtersdate = FiltersDate.getText().toString();
                String brakesmileage = BrakesMileage.getText().toString();
                String brakesdate = BrakesDate.getText().toString();
                String tiresmileage = TiresMileage.getText().toString();
                String tiresdate = TiresDate.getText().toString();
                String enginetimingmileage = EngineTimingMileage.getText().toString();
                String enginetimingdate = EngineTimingDate.getText().toString();
                String sparkplugscablesmileage = SparkPlugsCablesMileage.getText().toString();
                String sparkplugscabledate = SparkPlugsCablesDate.getText().toString();

                if(name.isEmpty() || mileage.isEmpty() || vin.isEmpty() || inspection.isEmpty() || policy.isEmpty() ||
                oilmileage.isEmpty() || oildate.isEmpty() || filtersmileage.isEmpty() || filtersdate.isEmpty() ||
                brakesmileage.isEmpty() || brakesdate.isEmpty() || tiresmileage.isEmpty() || tiresdate.isEmpty() ||
                enginetimingmileage.isEmpty() || enginetimingdate.isEmpty() || sparkplugscablesmileage.isEmpty() ||
                sparkplugscabledate.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Upewnij się ze wypełniłeś wszystkie pola...",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Pomyślnie dodano auto.", Toast.LENGTH_SHORT).show();
                    carData();
                    startActivity(new Intent(AddCar.this, MainActivity.class));

                }


            }
        });






        //WYBOR DATY
        EditText car_inspection = (EditText) findViewById(R.id.car_inspection);
        EditText car_policy = (EditText) findViewById(R.id.car_policy);
        EditText car_oil_date = (EditText) findViewById(R.id.car_oil_date);
        EditText car_filters_date = (EditText) findViewById(R.id.car_filters_date);
        EditText car_brakes_date = (EditText) findViewById(R.id.car_brakes_date);
        EditText car_tires_date = (EditText) findViewById(R.id.car_tires_date);
        EditText car_engine_timing_date = (EditText) findViewById(R.id.car_engine_timing_date);
        EditText car_spark_plugs_cables_date = (EditText) findViewById(R.id.car_spark_plugs_cables_date);

        chooseDate(car_inspection);
        chooseDate(car_policy);
        chooseDate(car_oil_date);
        chooseDate(car_filters_date);
        chooseDate(car_brakes_date);
        chooseDate(car_tires_date);
        chooseDate(car_engine_timing_date);
        chooseDate(car_spark_plugs_cables_date);






    }
    public void carData(){
        String name = Name.getText().toString();
        String mileage = Mileage.getText().toString();
        String vin = Vin.getText().toString();
        String inspection = Inspection.getText().toString();
        String policy = Policy.getText().toString();
        String oilmileage = OilMileage.getText().toString();
        String oildate = OilDate.getText().toString();
        String filtersmileage = FiltersMileage.getText().toString();
        String filtersdate = FiltersDate.getText().toString();
        String brakesmileage = BrakesMileage.getText().toString();
        String brakesdate = BrakesDate.getText().toString();
        String tiresmileage = TiresMileage.getText().toString();
        String tiresdate = TiresDate.getText().toString();
        String enginetimingmileage = EngineTimingMileage.getText().toString();
        String enginetimingdate = EngineTimingDate.getText().toString();
        String sparkplugscablesmileage = SparkPlugsCablesMileage.getText().toString();
        String sparkplugscabledate = SparkPlugsCablesDate.getText().toString();


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Car").child(mAuth.getUid());
        final DatabaseReference newKey=databaseReference.push();
        String a=newKey.getKey();

        DatabaseReference databaseReference2 = firebaseDatabase.getReference("Car").child(mAuth.getUid()).child(a);

        car = new Car(name, mileage, vin, inspection, policy, oilmileage, oildate, filtersmileage, filtersdate,
                brakesmileage, brakesdate, tiresmileage, tiresdate, enginetimingmileage, enginetimingdate,
                sparkplugscablesmileage, sparkplugscabledate);
        databaseReference2.setValue(car);
    }


    private void chooseDate(EditText e)
    {
        e.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    DateDialog dialog = new DateDialog(view);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }

        });
    }



}

