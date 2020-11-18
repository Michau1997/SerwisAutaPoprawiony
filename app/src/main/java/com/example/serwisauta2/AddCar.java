package com.example.serwisauta2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Document;

import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Integer.parseInt;

public class AddCar extends AppCompatActivity {

    //VARIABLES
    private static final int PICK_IMAGE = 500;
    CircleImageView CarImage;
    Toolbar acToolbar;
    ProgressDialog loadingBar;
    Button AddCarButton;
    FirebaseAuth mAuth;
    EditText Name, Mileage, Vin, Inspection, Policy, OilMileage, OilDate,
            FiltersMileage, FiltersDate, BrakesMileage, BrakesDate, TiresMileage,
            TiresDate, EngineTimingMileage, EngineTimingDate, SparkPlugsCablesMileage,
            SparkPlugsCablesDate;
    Uri imagePath;
    private StorageReference storageReference;
    String name, mileage, vin, inspection, policy, oilmileage, oildate, filtersmileage, filtersdate,
            brakesmileage, brakesdate, tiresmileage, tiresdate, enginetimingmileage, enginetimingdate, sparkplugscablesmileage, sparkplugscablesdate, carid, id;
    String profileImageUrl;
    FirebaseAuth firebaseAuth;
    private Car car;
    final static int Gallery_Pick = 1;

    //set image fun
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                CarImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        acToolbar = (Toolbar) findViewById(R.id.add_car_toolbar);
        setSupportActionBar(acToolbar);
        getSupportActionBar().setTitle("Dodawanie nowego auta");

        //ANDROID COMPONENT

        firebaseAuth = FirebaseAuth.getInstance();

        mAuth = FirebaseAuth.getInstance();
        AddCarButton = (Button) findViewById(R.id.buttonAddCar);
        loadingBar = new ProgressDialog(this);


        CarImage = (CircleImageView) findViewById(R.id.car_picture);
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

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        //Add to database function
        AddCarButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                name=Name.getText().toString();
                mileage=Mileage.getText().toString();
                vin=Vin.getText().toString();
                inspection=Inspection.getText().toString();
                policy=Policy.getText().toString();
                oilmileage=OilMileage.getText().toString();
                oildate=OilDate.getText().toString();
                filtersmileage=FiltersMileage.getText().toString();
                filtersdate=FiltersDate.getText().toString();
                brakesmileage=BrakesMileage.getText().toString();
                brakesdate=BrakesDate.getText().toString();
                tiresmileage=TiresMileage.getText().toString();
                tiresdate=TiresDate.getText().toString();
                enginetimingmileage=EngineTimingMileage.getText().toString();
                enginetimingdate=EngineTimingDate.getText().toString();
                sparkplugscablesmileage=SparkPlugsCablesMileage.getText().toString();
                sparkplugscablesdate=SparkPlugsCablesDate.getText().toString();

                if (CarImage.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.addphoto).getConstantState())
                {
                    Toast.makeText(getApplicationContext(), "Upewnij się ze dodales zdjecie...",Toast.LENGTH_SHORT).show();
                }

                else if(name.isEmpty() || mileage.isEmpty() || vin.isEmpty() || inspection.isEmpty() || policy.isEmpty() ||
                        oilmileage.isEmpty() || oildate.isEmpty() || filtersmileage.isEmpty() || filtersdate.isEmpty() ||
                        brakesmileage.isEmpty() || brakesdate.isEmpty() || tiresmileage.isEmpty() || tiresdate.isEmpty() ||
                        enginetimingmileage.isEmpty() || enginetimingdate.isEmpty() || sparkplugscablesmileage.isEmpty() ||
                        sparkplugscablesdate.isEmpty())
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




        //setCarImage

        CarImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"),PICK_IMAGE);
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

    //VARIABLE


    //FUNCTION
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


    private void carData()
    {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref=firebaseDatabase.getReference("Car").child(mAuth.getUid()) ;
        final DatabaseReference newPost=ref.push();

        carid=newPost.getKey();
        final StorageReference imageReference = storageReference.child("Car").child(mAuth.getUid()).child(carid); // Path: // Car//carid
        final UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        profileImageUrl=task.getResult().toString();
                        id=carid;
                        car = new Car(name, mileage, vin, inspection, policy, oilmileage, oildate, filtersmileage, filtersdate,
                                brakesmileage, brakesdate, tiresmileage, tiresdate, enginetimingmileage, enginetimingdate,
                                sparkplugscablesmileage, sparkplugscablesdate, profileImageUrl, id);
                        newPost.setValue(car);
                    }
                });
            }
        });

    }
}