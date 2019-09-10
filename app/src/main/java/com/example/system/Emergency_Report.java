package com.example.system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
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
import android.widget.VideoView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

public class Emergency_Report extends AppCompatActivity {

    ImageView imageView, play, record, btn_stop_record, btn_play, btn_stop, btn_record;
    Button take_picture, btn_submit;
    EditText editText1;
    String pathToFile;
    String imageUrl, url;
    EditText message;
    private static int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private Uri videoUri;
    private static int VIDEO_REQUEST = 101;
    private VideoView VideoView;
    String pathSave = "";
    MediaRecorder mediaRecorder;
    Uri downloadUri;
    MediaPlayer mediaPlayer;
    DatabaseReference databaseReference;
    FirebaseStorage Storage;
    private ProgressDialog progressDialog;
    FirebaseUser user;
    public final static int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private static final String TAG = "Emergency_Report";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment video_play = new Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("edttext", "From Activity");
        video_play.setArguments(bundle);
        databaseReference = FirebaseDatabase.getInstance().getReference("Emergency Reports");
        progressDialog = new ProgressDialog(this);
        user = FirebaseAuth.getInstance().getCurrentUser();

        setContentView(R.layout.activity_report_emergency);
        message = findViewById(R.id.edt_txt_ems_message);
        take_picture = findViewById(R.id.Btn_Take_Pic);
        imageView = findViewById(R.id.Image_View);
        VideoView = findViewById(R.id.Play_Video);
        btn_record = findViewById(R.id.btn_record);
        btn_stop_record = findViewById(R.id.btn_stop_record);
        btn_play = findViewById(R.id.btn_play);
        btn_stop = findViewById(R.id.btn_stop);
        btn_submit = findViewById(R.id.btn_ems_report);

        play = findViewById(R.id.play_video_button);
        record = findViewById(R.id.record_video_button);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.uj_logo_);
        actionBar.setTitle("Report Emergency");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        Permissions();
        Storage = FirebaseStorage.getInstance();

//        FirebaseMessaging.getInstance().subscribeToTopic("Emergency")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = "subscribed";
//                        if (!task.isSuccessful()) {
//                            msg = "subscribe failed";
//                        }
//                        Log.d(TAG, msg);
//                        Toast.makeText(Emergency_Report.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
        if (checkPermission(Manifest.permission.SEND_SMS)) {

        } else {
            ActivityCompat.requestPermissions(Emergency_Report.this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    videoUri.toString();
                    VideoView.setVideoURI(videoUri);
                    VideoView.start();
                } catch (Exception ex) {
                    Toast toast = Toast.makeText(Emergency_Report.this, "Please record a video !", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("Creating report.....");

                try {

                    Upload_Video(videoUri);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                   // SmsManager mm = SmsManager.getDefault();
                   // mm.sendTextMessage("0712771026", null, "User Email : " + user.getEmail().toString() + " Has report a emergency on campus !. " + "Message states :" + message.getText().toString() + " " + "Link of the video reported :" + "link..........", null, null);
//                        String[] to = new String[]{"selloedwin70@gmail.com"};
//                        String subject = ("Emergency on campus!");
//                        Intent email = new Intent(Intent.ACTION_SEND);
//                        email.putExtra(email.EXTRA_EMAIL, to);
//                        email.putExtra(email.EXTRA_TEXT, "Link to video uploaded ");
//                        email.putExtra(email.EXTRA_SUBJECT, subject);
//                        email.putExtra(email.EXTRA_TEXT, "");
//                        email.setType("message/rfc822");
//                        startActivity(email.createChooser(email, "Send Email"));


                    Toast toast = Toast.makeText(Emergency_Report.this, "SMS has been sent to notify authorities", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    message.setText("");

                    // mm.sendTextMessage("0712771026", null, "Student:" + user.getEmail().toString() + " Has report a emergency on campus !", null, null);
//
////                        StringTokenizer st=new StringTokenizer(phoneNo,",");
////                        while (st.hasMoreElements())
////                        {
////                            String tempMobileNumber = (String)st.nextElement();
////                            if(tempMobileNumber.length()>0 && message.trim().length()>0) {
////                                sendSMS(tempMobileNumber, message);
////                            }
////                            else {
////                                Toast.makeText(getBaseContext(),
////                                        "Please enter both phone number and message.",
////                                        Toast.LENGTH_SHORT).show();
////                            }
////                        }


                    // Toast("!! Report saved successfully and admin notified about emergency !!");
                    //startActivity(new Intent(Add_learner3.this,Reports.class));

                    //end of else
                } catch (Exception ex) {
                    Toast toast = Toast.makeText(Emergency_Report.this, "Please record a video !", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }

            }//end of method
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, VIDEO_REQUEST);
                }


            }
        });
        VideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //Uri  videoUri=  Uri.parse(getIntent().getExtras().getString("videoUri"));
                    videoUri.toString();
                    VideoView.setVideoURI(videoUri);
                    VideoView.start();
                } catch (Exception ex) {
                    Toast toast = Toast.makeText(Emergency_Report.this, "Please record a video !", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

            }
        });
        btn_record.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                try {

                } catch (Exception ex) {
                    ex.printStackTrace();

                }
                Permissions();

                pathSave = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record.3qp";
                setupMeiaRecorder();
                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ;
                }
                btn_play.setEnabled(false);
                btn_stop.setEnabled(false);
                Toast toast = Toast.makeText(Emergency_Report.this, "Recording..........", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        btn_stop_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                btn_stop_record.setEnabled(false);
                btn_play.setEnabled(true);
                btn_record.setEnabled(true);
                btn_stop.setEnabled(false);
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_stop.setEnabled(true);
                btn_stop_record.setEnabled(false);
                btn_record.setEnabled(false);

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(pathSave);
                    mediaPlayer.prepare();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                mediaPlayer.start();
                Toast toast = Toast.makeText(Emergency_Report.this, "Playing..........", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_stop_record.setEnabled(false);
                btn_record.setEnabled(true);
                btn_stop.setEnabled(false);
                btn_play.setEnabled(true);
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    setupMeiaRecorder();
                }

            }
        });
    }


    //when sending to multiple ppl SMS
    private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        //sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    private void setupMeiaRecorder() {
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setOutputFile(pathSave);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void captureVideo(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, VIDEO_REQUEST);
        }
    }

    public void playVideo(View view) {

        if (videoUri.toString().equalsIgnoreCase("")) {
            Toast("!! Please record a video !!");
        } else {
            Intent intentvideo = new Intent(this, VideoPlay.class);
            intentvideo.putExtra("videoUri", videoUri.toString());
            startActivity(intentvideo);
        }


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left_activity, R.anim.slide_out_right_activity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            try {
                if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK) {
                    videoUri = data.getData();
                    final Uri SelectedVideo = data.getData();
                    Upload_Video(SelectedVideo);
                }
            } catch (Exception ex) {
                System.out.println("Error :" + ex.getMessage());
            }


        } else {
            try {
                if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK) {
                    videoUri = data.getData();
                    final Uri SelectedVideo = data.getData();
                    Upload_Video(SelectedVideo);
                }
            } catch (Exception ex) {
                System.out.println("Error :" + ex.getMessage());
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_back:
                Intent intent = new Intent(this, Home_Activity.class);
                //intent.putExtra("key",2);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_right_activity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean IsCameraAvailable() {
        return (Emergency_Report.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA));
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUEST_CAMERA);

        }


    }

    public void Toast(String msg) {
        View view = getLayoutInflater().inflate(R.layout.toast_uj, (ViewGroup) findViewById(R.id.toast_uj));
        TextView text = (TextView) view.findViewById(R.id.textToast);
        text.setText(msg);
        Toast toast = new Toast(Emergency_Report.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }

    private boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return check == PackageManager.PERMISSION_GRANTED;
    }


    public void Upload_Video(Uri SelectedVideo) {
        progressDialog.setTitle("Please wait");
       // progressDialog.setMessage("Uploading content");
        progressDialog.show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String path = "Emergency Reports videos/" + UUID.randomUUID() + ".jpeg";
        StorageReference storageReference = Storage.getReference(path);
        StorageMetadata metadata = new StorageMetadata.Builder().setCustomMetadata("Reported By", user.getEmail().toString() + " : Emergency Reports").build();

        UploadTask uploadTask = storageReference.putFile(SelectedVideo, metadata);
        uploadTask.addOnCompleteListener(Emergency_Report.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                progressDialog.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
            {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Uploading content :" + (int) progress + "%");
            }
        });


        Task<Uri> getDownloadUriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return storageReference.getDownloadUrl();
            }
        });

        getDownloadUriTask.addOnCompleteListener(Emergency_Report.this, new OnCompleteListener<Uri>()
        {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if (!task.isSuccessful()) {
                    System.out.println("error :" + task.getResult().toString());
                } else {

                    Uri hello = task.getResult();
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c);
                    downloadUri = task.getResult();

                    pathToFile = hello.toString();
                    String id = databaseReference.push().getKey();
                    Emergency emergency = new Emergency(message.getText().toString(), user.getEmail().toString(), formattedDate, "un-resolved", hello.toString());
                    databaseReference.child(id).setValue(emergency);


                    try
                    {
                        String[] to = new String[]{"selloedwin70@gmail.com"};
                        String subject = ("Emergency on campus!");
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(email.EXTRA_EMAIL, to);
                        email.putExtra(email.EXTRA_SUBJECT, subject);
                        email.putExtra(email.EXTRA_TEXT,"Link to video uploaded :"+ hello.toString());
                        email.setType("message/rfc822");
                        startActivity(email.createChooser(email, "Send Email"));

                        SmsManager mm = SmsManager.getDefault();
                        mm.sendTextMessage("0712771026", null, "User Email : " + user.getEmail().toString() + " Has report a emergency on campus !. " + " " + "Link of the video reported :" +  hello.toString()+".", null, null);
                        Toast toast = Toast.makeText(Emergency_Report.this, "SMS has been sent to notify authorities", Toast.LENGTH_SHORT);
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

        //progressDialog.dismiss();
    }

    public String ClipUrl(String url) {
        String url_clip = this.url;

        return url_clip;

    }


}
