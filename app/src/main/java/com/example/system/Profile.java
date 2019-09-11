package com.example.system;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Profile extends AppCompatActivity
{

    Button capture,upload,update;
    EditText New_Password, Confirm_Password, pass;
    private static int LOAD_IMAGE_RESULTS=1;
    private static final int SELECTED_PIC = 1;
    ImageView image;
    Context context=this;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        capture=(Button)findViewById(R.id.btnCapture);
        upload=(Button)findViewById(R.id.btnUpload);
        update=(Button)findViewById(R.id.btn_Update_Profile);
        progressDialog = new ProgressDialog(this);
        New_Password= findViewById(R.id.txtUser_New_Password);
        Confirm_Password= findViewById(R.id.txtUser_Confirm_Password);
        mAuth = FirebaseAuth.getInstance();
        image=findViewById(R.id.profile_image) ;
        capture = findViewById(R.id.btnCapture);


        ActionBar actionBar = getSupportActionBar();
        // actionBar.setIcon(R.mipmap.report);
        actionBar.setTitle("My Profile");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        capture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (IsCameraAvailable())
                {
                    Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,0);
                }else
                {
                    Toast( "Camera Is Not Available");
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Update_Profile();

            }
        });
    }//end of on create


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.back1,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.Back:
                startActivity(new Intent(this,Home_Activity.class));
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_right_activity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Toast (String msg)
    {
        View view=getLayoutInflater().inflate(R.layout.toast_uj,(ViewGroup) findViewById(R.id.toast_uj));
        TextView text=(TextView)view.findViewById(R.id.textToast);
        text.setText(msg);
        Toast toast = new Toast(Profile.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,0);
        toast.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==LOAD_IMAGE_RESULTS&& resultCode==RESULT_OK&&data!=null)
        {
            Uri pickedImage =data.getData();
            String [] filepath={MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage,filepath,null,null,null);
            cursor.moveToFirst();
            String imagePath= cursor.getString(cursor.getColumnIndex(filepath[0]));
            //image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            //cursor.close();
            //Uri pickedImage =data.getData();
            // String [] filepath={MediaStore.Images.Media.DATA};
            image.setImageURI(pickedImage);
        }
        else
        {
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }
    }
    public void ImageButton(View v)
    {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,LOAD_IMAGE_RESULTS);
    }
    private boolean  IsCameraAvailable()
    {
        return (Profile.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA));
    }

    public void showToast(String msg)
    {
            Toast.makeText(Profile.this, msg, Toast.LENGTH_SHORT).show();
        }

    private void Update_Profile()
    {
        String password = New_Password.getText().toString().trim();
        String password2 = Confirm_Password.getText().toString().trim();


        if (password.isEmpty())
        {
            New_Password.setError(getString(R.string.input_error_password));
            New_Password.requestFocus();
            return;
        }

        if (password.length() < 6)
        {
            New_Password.setError(getString(R.string.input_error_password_length));
            New_Password.requestFocus();
            return;
        }

        if (!password.equalsIgnoreCase(password2))
        {
            New_Password.setError(getString(R.string.input_error_password_match));
            New_Password.requestFocus();
            Confirm_Password.setError(getString(R.string.input_error_password_match));
            Confirm_Password.requestFocus();
        }

        if (Helper_Class.connectionAvailable(context))
        {
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Resetting Password.....");
            progressDialog.show();
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user!=null)
        {
            user.updatePassword(Confirm_Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>()
            {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        Toast("Your password has been re-rest");
                        mAuth.signOut();
                        finish();
                        Intent intent =new Intent(context,Login_Activity.class);
                        startActivity(intent);
                    }
                    else
                        {
                            progressDialog.dismiss();
                            Toast("Your password could not be re-rest");

                        }
                }
            });
        }

    }
}//end of profile

















