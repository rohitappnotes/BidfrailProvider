package com.bidfrail.provider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.bidfrail.provider.databinding.ActivityTestBinding;
import com.bidfrail.provider.ui.afterloginregister.fragment.leads.view.LeadsFragment;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TestActivity extends AppCompatActivity {

    private ActivityTestBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityTestBinding.inflate(getLayoutInflater());
        View view = mViewBinding.getRoot();
        setContentView(view);

        System.out.println("===FROM CREATE===");
        handleIntent(getIntent());


        mViewBinding.blinkLayout.startShimmerAnimation();
    }

    private double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        System.out.println("===FROM NEW INTENT===");
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();

            if (bundle != null) {

                String navigateToScreen = bundle.getString(AppConstants.Screen.Extras.EXTRA_NAVIGATE_TO);
                String title= bundle.getString(AppConstants.Screen.Extras.EXTRA_TITLE);
                String message= bundle.getString(AppConstants.Screen.Extras.EXTRA_MESSAGE);
                String orderType= bundle.getString(AppConstants.Screen.Extras.EXTRA_ORDER_TYPE);
                String orderId= bundle.getString(AppConstants.Screen.Extras.EXTRA_ORDER_ID);
                String dateRequiredForScreen= bundle.getString(AppConstants.Screen.Extras.EXTRA_DATA_REQUIRED);

                System.out.println("====TEST NAVIGATE_TOE===="+navigateToScreen);
                System.out.println("====TEST TITLE===="+title);
                System.out.println("====TEST MESSAGE===="+message);
                System.out.println("====TEST ORDER_TYPE===="+orderType);
                System.out.println("====TEST ORDER_ID===="+orderId);
                System.out.println("====TEST DATA_REQUIRED===="+dateRequiredForScreen);
            }
        }
    }


    /*private void registerUser(){

        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        String confirmPass=confirmPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if(!confirmPass.equals(password)) {
            Toast.makeText(this,"Your password do not match",Toast.LENGTH_SHORT).show();
            editTextPassword.setText("");
            confirmPassword.setText("");
            return;
        }


        //if the email and password are not empty
        //displaying a progress dialog


        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String userID = user.getUid();
                            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                            myRef.child("users").child(userID).child("email").setValue(email);
                            //display some message here
                            Toast.makeText(RegisterActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                            finish();
                            //startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                        }else{
                            //display some message here
                            Toast.makeText(RegisterActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }*/



    /*<android.support.design.widget.TextInputLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginLeft="15dp"
    android:layout_marginRight="15dp">

            <EditText
    android:id="@+id/editTextEmail"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:drawableLeft="@drawable/ic_perm_identity_black_24dp"
    android:drawablePadding="5dp"
    android:hint="Enter email"
    android:inputType="textEmailAddress"
    android:paddingLeft="10dp"
    android:paddingRight="10dp"
    android:paddingTop="10dp" />
        </android.support.design.widget.TextInputLayout>


        <android.support.design.widget.TextInputLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="15dp"
            android:layout_marginRight="15dp"
            android:layout_marginTop="10dp">

            <EditText
                android:id="@+id/editTextPassword"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:drawableLeft="@drawable/ic_lock_outline_black_24dp"
                android:drawablePadding="5dp"
                android:hint="Enter password"
                android:inputType="textPassword"
                android:paddingLeft="10dp"
                android:paddingRight="10dp"
                android:paddingTop="10dp" />
        </android.support.design.widget.TextInputLayout>



   <TextView
            android:id="@+id/textViewSignUp"
            android:layout_width="match_parent"
            android:layout_height="40dp"
            android:layout_marginBottom="50dp"
            android:layout_marginLeft="15dp"
            android:layout_marginRight="15dp"
            android:layout_marginTop="25dp"
            android:gravity="center"
            android:text="Not have an account? Signup Here"
            android:textAlignment="center"
            android:textColor="@color/game_tag"
            android:textSize="16sp"
            android:visibility="visible" />


            <TextView
            android:id="@+id/textViewLogin"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="15dp"
            android:text=" have an account? Signin Here"
            android:textColor="@color/colorPrimary"
            android:textAlignment="center"
            />

        */
}