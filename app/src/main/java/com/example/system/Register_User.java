package com.example.system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Locale;

public class Register_User extends AppCompatActivity {


    EditText Txt_Student_Number, Txt_Reg_Password, Txt_Reg_Re_Enter_Password, Txt_useremail,Txt_Phone;
    Button Register, Capture;
    TextToSpeech t1;
    Context context = this;
    ImageView Pro_Pic;
    private ProgressDialog progressDialog;
    private static int LOAD_IMAGE_RESULTS = 1;
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    StorageReference storageReference, imageFilePath, mStorageRef;
    Uri pickedImage;
    FirebaseUser new_user;
    FirebaseAuth firebaseAuth;
    String imagePath;
    private Uri picture_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__user);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("user");
        imageFilePath = FirebaseStorage.getInstance().getReference();


        ActionBar actionBar = getSupportActionBar();
        // actionBar.setIcon(R.mipmap.report);
        actionBar.setTitle("Register User");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Txt_Student_Number = findViewById(R.id.Txt_student_number);
        Txt_Reg_Password = findViewById(R.id.Txt_student_password);
        Txt_useremail = findViewById(R.id.Txt_useremail);
        Txt_Reg_Re_Enter_Password = findViewById(R.id.Txt_student_password_re_enter);
        Pro_Pic = findViewById(R.id.profile_image);
        Txt_Phone = findViewById(R.id.txt_Phone_Nr);

        t1 = new TextToSpeech(getBaseContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });

//        Capture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                if (IsCameraAvailable())
//                {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, 0);
//                } else
//                    {
//                    Toast("Camera Is Not Available");
//                }
//            }
//        });
    }

    public void Buttons(View v) {
        switch (v.getId()) {
            case R.id.Btn_Register:
                try {
                    if (Helper_Class.connectionAvailable(this)) {
                        registerUser(pickedImage);
                    } else
                        {
                        Toast("!! No internet connection !!");
                    }
                } catch (Exception e) {
                    Toast.makeText(Register_User.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }//end of try catch

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left_activity, R.anim.slide_out_right_activity);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_back:
                Intent intent = new Intent(Register_User.this, Login_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_right_activity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            pickedImage = data.getData();
            picture_path = data.getData();
            String[] filepath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage, filepath, null, null, null);
            cursor.moveToFirst();
            imagePath = cursor.getString(cursor.getColumnIndex(filepath[0]));
            //image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            //cursor.close();
            //Uri pickedImage =data.getData();
            // String [] filepath={MediaStore.Images.Media.DATA};
            Pro_Pic.setImageURI(pickedImage);
        } else {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Pro_Pic.setImageBitmap(bitmap);
        }
    }

    public void Up_Load_Image(View v) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, LOAD_IMAGE_RESULTS);
    }

    public void Toast(String msg) {
        View view = getLayoutInflater().inflate(R.layout.toast_uj, (ViewGroup) findViewById(R.id.toast_uj));
        TextView text = (TextView) view.findViewById(R.id.textToast);
        text.setText(msg);
        Toast toast = new Toast(Register_User.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }

    private boolean IsCameraAvailable() {
        return (Register_User.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA));
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }

    private void registerUser(Uri pickedImage)
    {
        final String Student_Number = Txt_Student_Number.getText().toString().trim();
        final String email = Txt_useremail.getText().toString().trim();
        String password = Txt_Reg_Password.getText().toString().trim();
        String password2 = Txt_Reg_Re_Enter_Password.getText().toString().trim();
        final String phone=Txt_Phone.getText().toString().trim();

        // final String phone = editTextPhone.getText().toString().trim();

        if (Student_Number.isEmpty()) {
            Txt_Student_Number.setError(getString(R.string.input_error_name));
            Txt_Student_Number.requestFocus();
            return;
        }

        if (Student_Number.length() != 9) {
            Txt_Student_Number.setError(getString(R.string.input_error_student_number));
            Txt_Student_Number.requestFocus();
            return;
        }

        if (!password.equalsIgnoreCase(password2)) {
            Txt_Reg_Re_Enter_Password.setError(getString(R.string.input_error_password_match));
            Txt_Reg_Password.requestFocus();
            Txt_Reg_Re_Enter_Password.setError(getString(R.string.input_error_password_match));
            Txt_Reg_Password.requestFocus();

        }

        if (email.isEmpty()) {
            Txt_useremail.setError(getString(R.string.input_error_email));
            Txt_useremail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Txt_useremail.setError(getString(R.string.input_error_email_invalid));
            Txt_useremail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            Txt_Reg_Password.setError(getString(R.string.input_error_password));
            Txt_Reg_Password.requestFocus();
            return;
        }

        if (password.length() < 6) {
            Txt_Reg_Password.setError(getString(R.string.input_error_password_length));
            Txt_Reg_Password.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            Txt_Phone.setError(getString(R.string.input_error_phone));
            Txt_Phone.requestFocus();
            return;
        }

        if (phone.length() != 10)
        {
            Txt_Phone.setError(getString(R.string.input_error_phone_invalid));
            Txt_Phone.requestFocus();
            return;
        }

         //progressDialog.show();

        if (Helper_Class.connectionAvailable(context))
        {
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Registering Users.....");
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        String id = myRef.push().getKey();
                        User user = new User(Student_Number, email, "0712771026","default");
                        myRef.child(id).setValue(user);

                        //progressDialog.cancel();
                        Txt_Student_Number.setText("");
                        Txt_useremail.setText("");
                        Txt_Reg_Password.setText("");
                        Txt_Reg_Re_Enter_Password.setText("");
                        Txt_Phone.setText("");
                        progressDialog.dismiss();


                        FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();
                        user2.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast toast = Toast.makeText(Register_User.this, "User Registered Please Check Your Email", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            }
                        });

                        Intent i = new Intent(context,Login_Activity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_right_activity);

                        //uploadPicture();

                    }
                }
            });
        }
        else
        {
            Toast("!! No internet connection !!");
        }

    }

    private void uploadPicture()
    {

        if (picture_path != null)
        {

            StorageReference riversRef = imageFilePath.child("images/users_profile_pictures/sello.jpg");

            riversRef.putFile(picture_path)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception)
                        {

                        }
                    });
        }
    }

}
