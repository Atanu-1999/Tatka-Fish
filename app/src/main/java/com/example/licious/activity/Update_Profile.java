package com.example.licious.activity;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.MainActivity;
import com.example.licious.R;
import com.example.licious.api.ApiService;
import com.example.licious.response.ImageResponse;
import com.example.licious.response.Otp_verify_Response;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Update_Profile extends AppCompatActivity {

    ImageView back;
    TextView edit_user_dob, txt_edite, txt_add;
    private Calendar calendar;
    AlertDialog alertDialog;

    EditText tv_phone, edite_Fname, edite_Last_name, et_email;
    String f_name, l_name, email, Dob, gender, phoneNum;
    RadioGroup gender_radio_group;
    RadioButton radio_male, radio_female, radio_other;
    ImageView Iv_profile;
    private String mediaPath;
    File mediaFile;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
String mCurrentPhotoPath="";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        txt_edite = findViewById(R.id.txt_edite);
        edit_user_dob = findViewById(R.id.edit_user_dob);
        back = findViewById(R.id.back);
        tv_phone = findViewById(R.id.tv_phone);
        edite_Fname = findViewById(R.id.edite_Fname);
        edite_Last_name = findViewById(R.id.edite_Last_name);
        et_email = findViewById(R.id.et_email);
        txt_add = findViewById(R.id.txt_add);
        gender_radio_group = findViewById(R.id.gender_radio_group);
        radio_male = findViewById(R.id.radio_male);
        radio_female = findViewById(R.id.radio_female);
        radio_other = findViewById(R.id.radio_other);
        Iv_profile = findViewById(R.id.Iv_profile);

        SharedPreferences loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);

        phoneNum = loginPref.getString("phone", "");
        tv_phone.setText(phoneNum);
        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: " + uri);
                try {
                    // Creating file
                    mediaFile = null;
                    try {
                        mediaFile = createImageFile();
                    } catch (IOException ex) {
                        Log.d(TAG, "Error occurred while creating the file");
                    }
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    FileOutputStream fileOutputStream = new FileOutputStream(mediaFile);
                    // Copying
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    uploadImg();
                } catch (Exception e) {
                    Log.d(TAG, "onActivityResult: " + e.toString());
                }
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //camera
        Iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
                    chosePhoto();
                } else if (checkStoragePermissions()) {
                    chosePhoto();
                }
            }
        });
        gender_radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            // Handle the selected radio button
            switch (checkedId) {
                case R.id.radio_male:
                    gender = "Male";
                    break;
                case R.id.radio_female:
                    gender = "Female";
                    break;
                case R.id.radio_other:
                    gender = "Other";
                    break;
            }
        });
        calendar = Calendar.getInstance();
        edit_user_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Update_Profile.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                edit_user_dob.setText(dateFormat.format(calendar.getTime()));
                            }
                        },
                        year,
                        month,
                        dayOfMonth);
                datePickerDialog.show();
            }
        });
        txt_edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.photoupdate_layout, null);
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Update_Profile.this);
                alertbox.setView(layout);
                alertbox.setCancelable(false);
                alertDialog = alertbox.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });
        txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedProfile();
            }
        });
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
    private void updatedProfile() {
        f_name = edite_Fname.getText().toString();
        l_name = edite_Last_name.getText().toString();
        email = et_email.getText().toString();
        Dob = edit_user_dob.getText().toString();


        Call<Otp_verify_Response> otp_verify = ApiService.apiHolders().UpdateProfile("", f_name, l_name, email, phoneNum, Dob, gender);
        otp_verify.enqueue(new Callback<Otp_verify_Response>() {
            @Override
            public void onResponse(Call<Otp_verify_Response> call, Response<Otp_verify_Response> response) {
                if (response.body().getVerify() == true) {
//                    Toast.makeText(OTP_Verify.this, "Success"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(OTP_Verify.this, MainActivity.class);
//                    SharedPreferences.Editor editor = loginPref.edit();
//                    String deviceId = response.body().getData().get(0).getDeviceId();
//                    String phoneNumber = response.body().getData().get(0).getPhone();
//                    editor.putString("device_id",deviceId);
//                    editor.putString("phone",phoneNumber);
//                    editor.commit();
//                    startActivity(i);
                } else {
                    //  Toast.makeText(OTP_Verify.this, "Fail"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Otp_verify_Response> call, Throwable t) {
                //Toast.makeText(OTP_Verify.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(OTP_Verify.this, R.string.otp_verify, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //camera
    private void chosePhoto() {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Update_Profile.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView1 = inflater.inflate(R.layout.profile_image_layout, null);
        builder.setCancelable(false);
        builder.setView(dialogView1);

//        LinearLayout imgcamera = dialogView1.findViewById(R.id.imgcamera);
        LinearLayout imggellary = dialogView1.findViewById(R.id.imggellary);

        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
//        imgcamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkAndRequestPermission()){
//                    takePictureCamera();
//                    alertDialog.dismiss();
//                }
//            }
//        });
        imggellary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAndRequestPermission()) {
                    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
                        pickMedia.launch(new PickVisualMediaRequest.Builder()
                                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                                .build());
                    } else {
                        takePictureGallery();
                        alertDialog.dismiss();
                    }
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(true);
    }
/*    private void takePictureCamera() {
        Intent takePicture1 = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture1.resolveActivity(getPackageManager()) !=null){
            startActivityForResult(takePicture1,1);
        }
    }*/

    private void takePictureGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 2);
    }

    private boolean checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = ActivityCompat.checkSelfPermission(Update_Profile.this, Manifest.permission.CAMERA);
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(Update_Profile.this, new String[]{Manifest.permission.CAMERA}, 20);
                return false;
            }
        }
        return true;
    }

    private boolean checkStoragePermissions() {
        return
                ContextCompat.checkSelfPermission(Update_Profile.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(Update_Profile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(Update_Profile.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ///takePictureCamera();
            takePictureGallery();
        } else {
            Toast.makeText(Update_Profile.this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), thumbnail, "val", null);
                    Uri uri = Uri.parse(path);
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    Iv_profile.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    cursor.close();
                    Iv_profile.setImageURI(uri);
                  mediaFile=  new File(mediaPath);
                     uploadImg();
                } else {
                    Toast.makeText(this, "No image Capture to upload", Toast.LENGTH_SHORT).show();
                }
            case 2:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    Iv_profile.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    cursor.close();
                    Iv_profile.setImageURI(selectedImage);
                    mediaFile=  new File(mediaPath);
                      uploadImg();
                } else {
                    Toast.makeText(this, "No image select to upload", Toast.LENGTH_SHORT).show();
                }
        }
    }

    //call API for Image
    private void uploadImg() {
        SharedPreferences loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        String token = loginPref.getString("device_id", null);
        int id = loginPref.getInt("userId", 0);
        Log.e("MEDIA", "" + mediaPath);
        //File file =
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), mediaFile);
        MultipartBody.Part userImg = MultipartBody.Part.createFormData("image", mediaFile.getName(), requestBody);
        RequestBody userToken = RequestBody.create(MediaType.parse("multipart/form-data"),""+token);
        RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"),""+id);

        Call<ImageResponse> otp_verify = ApiService.apiHolders().profile_change(userId, userToken, userImg);
        otp_verify.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(Update_Profile.this, "Image upload successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Toast.makeText(Update_Profile.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}