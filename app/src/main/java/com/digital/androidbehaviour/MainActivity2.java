package com.digital.androidbehaviour;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity2 extends AppCompatActivity {

    String[] required_permission = new String[]{
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO
    };

    ImageView denyImage, givenImage, denyVideo, givenVideo, DenyAudio, givenAudio;
    Button buttonCheckAllPermission;

    String Tag = "permission";

    boolean is_storage_image_permitted = false;
    boolean is_storage_video_permitted = false;
    boolean is_storage_audio_permitted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        denyImage = (ImageView) findViewById(R.id.ImgDenyImage);
        givenImage = (ImageView) findViewById(R.id.givenImage);

        denyVideo = (ImageView) findViewById(R.id.denyVideo);
        givenVideo = (ImageView) findViewById(R.id.givenVideo);

        DenyAudio = (ImageView) findViewById(R.id.DenyAudio);
        givenAudio = (ImageView) findViewById(R.id.givenAudio);

        buttonCheckAllPermission = (Button) findViewById(R.id.buttonCheckAllPermission);

        buttonCheckAllPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allPermissionResultCheck()) {
                    requestPermissionStorageImage();

                } else {
                    Toast.makeText(MainActivity2.this, "All Storage Permission granted", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public boolean allPermissionResultCheck() {

        return is_storage_image_permitted && is_storage_video_permitted && is_storage_audio_permitted;
    }

    public void requestPermissionStorageImage() {
        if (ContextCompat.checkSelfPermission(MainActivity2.this, required_permission[0]) == PackageManager.PERMISSION_GRANTED)
            ;

        Log.d(Tag, required_permission[0] + "Granted");
        is_storage_image_permitted = true;
        denyImage.setVisibility(View.GONE);
        givenImage.setVisibility(View.VISIBLE);

        if (!allPermissionResultCheck()) {
            requestPermissionStorageVideo();
        } else {

            request_permission_launcher_storage_images.launch(required_permission[0]);
        }
    }

    private ActivityResultLauncher<String> request_permission_launcher_storage_images =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {

                if (isGranted) {
                    Log.d(Tag, required_permission[0] + "Granted");
                    is_storage_image_permitted = true;
                    denyImage.setVisibility(View.GONE);
                    givenImage.setVisibility(View.VISIBLE);
                } else {
                    Log.d(Tag, required_permission[0] + " NOt Granted");
                    is_storage_image_permitted = false;


                }
                if (!allPermissionResultCheck()) {
                    requestPermissionStorageVideo();
                }
            });

    public void requestPermissionStorageVideo() {
        if (ContextCompat.checkSelfPermission(MainActivity2.this, required_permission[1]) == PackageManager.PERMISSION_GRANTED)
            ;

        Log.d(Tag, required_permission[1] + "Granted");
        is_storage_video_permitted = true;
        denyVideo.setVisibility(View.GONE);
        givenImage.setVisibility(View.VISIBLE);

        if (!allPermissionResultCheck()) {
            requestPermissionStorageAudio();
        } else {

            request_permission_launcher_storage_images.launch(required_permission[0]);
        }
    }

}

