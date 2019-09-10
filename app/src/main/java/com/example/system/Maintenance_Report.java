package com.example.system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Maintenance_Report extends AppCompatActivity {

    private static int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    Button Btn_Camera, Btn_Submit, Btn_Upload;
    ImageView Image_View;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private final int PICK_IMAGE_REQUEST = 1;
    private static int LOAD_IMAGE_RESULTS = 1;

Context context=this;
    TextView message,textView;
    Spinner description;
    Uri image_path;
    String imageUrl;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage Storage;
    Uri pickedImage;
    FirebaseUser user;
    Uri downloadUri;
    public static final String CHANNEL_ID="Maintenance_Report";

    //description

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance__report);
        progressDialog = new ProgressDialog(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
       /// FirebaseMessaging.getInstance().subscribeToTopic("NEWS");

        ActionBar actionBar = getSupportActionBar();
        // actionBar.setIcon(R.mipmap.report);
        actionBar.setTitle("Report Maintenance");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("Maintenance Reports");

        Storage = FirebaseStorage.getInstance();
        //storageReference = Storage.getReferenceFromUrl("gs://ujreportsystem.appspot.com/");

        Btn_Camera = findViewById(R.id.Btn_Camera);
        description = findViewById(R.id.Spinner_description);
        message = findViewById(R.id.Txt_Message_Maintenance_Report);
        Btn_Submit = findViewById(R.id.Btn_Submit_Maintenance_Report);
        Btn_Upload = findViewById(R.id.button4);
        Image_View = findViewById(R.id.Image_View);
        Permissions();

        Btn_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IsCameraAvailable()) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                } else {
                    Toast("Camera Is Not Available");
                }
            }
        });


        Btn_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, LOAD_IMAGE_RESULTS);

            }
        });
        Btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("Creating report.....");


                if (Image_View.getDrawable() == null)
                {
                    Toast("You must attach a picture");
                }
                else
                    {
                        try
                    {
                        Save();
                        String id = databaseReference.push().getKey();
                        Maintenance maintenance = new Maintenance(description.getSelectedItem().toString(), message.getText().toString(), "un-resolved", user.getEmail(),imageUrl.toString());
                        databaseReference.child(id).setValue(maintenance);
                        progressDialog.dismiss();
                        Intent intent= new Intent(context, Login_Activity.class);

                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                                PendingIntent.FLAG_ONE_SHOT);



                        final String SENDER_ID = "YOUR_SENDER_ID";
                        final int messageId = 0; // Increment for each
                        // [START fcm_send_upstream]
                        FirebaseMessaging fm = FirebaseMessaging.getInstance();
                        fm.send(new RemoteMessage.Builder(SENDER_ID + "@fcm.googleapis.com")
                                .setMessageId(Integer.toString(messageId))
                                .addData("my_message", "Hello World")
                                .addData("my_action","SAY_HELLO")
                                .build());

                        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    } catch (Exception ex)
                    {

                    }
                }

            }
        });
    }

    public void Buttons(View v) {
        switch (v.getId()) {
            case R.id.Btn_Submit_Maintenance_Report:


                if (image_path != null) {
                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("Uploading image.....");
                    progressDialog.show();

                    StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
                    ref.putFile(image_path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast("Uploaded ");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast("File Upload Fail");

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploading" + (int) progress + "%");

                        }
                    });

                }
//            if (message.getText().toString().trim()=="" )
//            {
//                message.setError(getString(R.string.input_required));
//                message.requestFocus();
//            }
//            else
//                {
//                    try
//                    {
//                        String id = databaseReference.push().getKey();
//                        Maintenance maintenance= new Maintenance(message.getText().toString(),description.getSelectedItem().toString());
//                        databaseReference.child(id).setValue(maintenance);
//
//                    }catch(Exception ex)
//                    {
//                        Log.d("Error",ex.getMessage().toString());
//                    }
//
//                }
//                break;

        }
    }

    public void Up_Load_Image(View v) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, LOAD_IMAGE_RESULTS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            pickedImage = data.getData();
            image_path = data.getData();
            Image_View.setImageURI(pickedImage);
        } else
            {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Image_View.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Back:
                Intent intent = new Intent(this, Home_Activity.class);
                intent.putExtra("key", 2);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_right_activity);
                //startActivity(new Intent(this,Reports.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left_activity, R.anim.slide_out_right_activity);
    }

    private void Permissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);

        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);

        }

    }

    public void Toast(String msg) {
        View view = getLayoutInflater().inflate(R.layout.toast_uj, (ViewGroup) findViewById(R.id.toast_uj));
        TextView text = (TextView) view.findViewById(R.id.textToast);
        text.setText(msg);
        Toast toast = new Toast(Maintenance_Report.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }

    private boolean IsCameraAvailable() {
        return (Maintenance_Report.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA));
    }

    public void Save()
    {
        progressDialog.setTitle("Please wait");
        progressDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String User_email = user.getEmail();

        Bitmap capture = Bitmap.createBitmap(Image_View.getWidth(), Image_View.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas captureCanvas = new Canvas(capture);
        Image_View.draw(captureCanvas);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        capture.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] data = outputStream.toByteArray();

        String path = "Maintenance Reports Pictures/" + UUID.randomUUID() + ".png";
        StorageReference storageReference = Storage.getReference(path);
        StorageMetadata metadata = new StorageMetadata.Builder().setCustomMetadata("caption", User_email + " : Maintenance Reports").build();

        UploadTask uploadTask = storageReference.putBytes(data, metadata);

        uploadTask.addOnCompleteListener(Maintenance_Report.this, new OnCompleteListener<UploadTask.TaskSnapshot>()
        {

            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
            {
                progressDialog.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Uploading content" + (int) progress + "%");
            }
        });

        Task<Uri> getDownloadUriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
        {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
            {

                if (!task.isSuccessful())
                {
                    throw task.getException();
                }
                return storageReference.getDownloadUrl();
            }
        });


        getDownloadUriTask.addOnCompleteListener(Maintenance_Report.this, new OnCompleteListener<Uri>()
        {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUri = task.getResult();
                    //textView.setText(downloadUri.toString());
                    imageUrl=downloadUri.toString();

                    try
                    {


                        SmsManager mm = SmsManager.getDefault();
                        mm.sendTextMessage("0712771026", null, "User Email : " + user.getEmail() + " Has report a maintenance on campus !. " + "Message states :" + message.getText().toString() + " " + "Link of the image reported :" +  imageUrl, null, null);
                        Toast toast = Toast.makeText(Maintenance_Report.this, "SMS has been sent to notify authorities", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        message.setText("");



                    } catch (Exception ex)
                    {

                        System.out.println("Error :"+ex.getMessage());
                    }

                }
            }
        });

    }
}
