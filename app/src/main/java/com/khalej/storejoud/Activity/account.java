package com.khalej.storejoud.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import de.hdodenhof.circleimageview.CircleImageView;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.khalej.storejoud.R;
import com.khalej.storejoud.model.Apiclient_home;
import com.khalej.storejoud.model.apiinterface_home;
import com.khalej.storejoud.model.contact_profile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class account extends AppCompatActivity {
    ImageView back;
    CircleImageView photo;
    LinearLayout genderUpdate,dateUpdate,emailUpdate,phoneUpdate,passUpdate;
    TextView name,gender,phone,phoneInside,pass,email,date;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private static final int MY_CAMERA_PERMISSION_CODE = 1;
    private static final int CAMERA_REQUEST_User = 1;
    String mediaPath;
    String imagePath;
    private  static final int IMAGEUser = 100;
    contact_profile profile;
    int x;
    private apiinterface_home apiinterface;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_account);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Tajawal-Bold.ttf", true);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        photo=findViewById(R.id.photo);
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(account.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(account.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        Intialize();
        gender.setText(sharedpref.getString("gender","male"));
        name.setText(sharedpref.getString("name","Store Joud"));
        phone.setText(sharedpref.getString("phone","+02000000000"));
        phoneInside.setText(sharedpref.getString("phone","+02000000000"));
        email.setText(sharedpref.getString("address","Store.Joud@gmail.com"));
        date.setText(sharedpref.getString("birthDate","20-10-2020"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        genderUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(account.this,GenderUpdate.class));
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(account.this,NameUpdate.class));
            }
        });
        phoneUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(account.this,PhoneUpdate.class));
            }
        });
        emailUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(account.this,EmailUpdate.class));
            }
        });
        passUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(account.this,PasswordUpdate.class));
            }
        });
        dateUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(account.this,BirthDateUpdate.class));
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    showPictureDialog();
                }
            }
        });
        Glide.with(account.this).load("https://storejoud.com/"+
               sharedpref.getString("imageProfile","")).error(R.drawable.logocolor).into(photo);

    }
    public void Intialize(){
        back=findViewById(R.id.back);
        genderUpdate=findViewById(R.id.genderUpdate);
        dateUpdate=findViewById(R.id.dateUpdate);
        emailUpdate=findViewById(R.id.emailUpdate);
        phoneUpdate=findViewById(R.id.phoneUpdate);
        passUpdate=findViewById(R.id.passUpdate);
        name=findViewById(R.id.Username);
        gender=findViewById(R.id.gender);
        phone=findViewById(R.id.phone);
        phoneInside=findViewById(R.id.phone_inside);
        pass=findViewById(R.id.password);
        email=findViewById(R.id.email);
        date=findViewById(R.id.birthdate);
    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        x=0;
        startActivityForResult(galleryIntent, IMAGEUser);

    }

    private void takePhotoFromCamera() {
        x=1;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",  /* suffix */
                    storageDir     /* directory */
            );
            Uri uri = FileProvider.getUriForFile(account.this, getPackageName(), image);
            imagePath=image.getAbsolutePath();//Store this path as globe variable

            Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST_User);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_User && resultCode == Activity.RESULT_OK)
        {

            mediaPath = imagePath;
            String name=random();

            mediaPath=resizeAndCompressImageBeforeSend(account.this,mediaPath  ,name);
            photo.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            fetchImage();
        }
        if(requestCode == IMAGEUser && resultCode == RESULT_OK && null != data)
        {
            Uri pathImag = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(pathImag, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);
            // Toast.makeText(Registration.this,mediaPath,Toast.LENGTH_LONG).show();
            String namee=random();

            mediaPath=resizeAndCompressImageBeforeSend(account.this,mediaPath,namee);

            photo.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            fetchImage();
             cursor.close();
        }
    }
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
    public static String resizeAndCompressImageBeforeSend(Context context, String filePath, String fileName){
        final int MAX_IMAGE_SIZE = 300 * 1024; // max final file size in kilobytes

        // First decode with inJustDecodeBounds=true to check dimensions of image
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath,options);

        // Calculate inSampleSize(First we are going to resize the image to 800x800 image, in order to not have a big but very low quality image.
        //resizing the image will already reduce the file size, but after resizing we will check the file size and start to compress image
        options.inSampleSize = calculateInSampleSize(options, 800, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig= Bitmap.Config.ARGB_8888;

        Bitmap bmpPic = BitmapFactory.decodeFile(filePath,options);


        int compressQuality = 100; // quality decreasing by 5 every loop.
        int streamLength;
        do{
            ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
            Log.d("compressBitmap", "Quality: " + compressQuality);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
            byte[] bmpPicByteArray = bmpStream.toByteArray();
            streamLength = bmpPicByteArray.length;
            compressQuality -= 5;
            Log.d("compressBitmap", "Size: " + streamLength/1024+" kb");
        }while (streamLength >= MAX_IMAGE_SIZE);

        try {
            //save the resized and compressed file to disk cache
            Log.d("compressBitmap","cacheDir: "+context.getCacheDir());
            FileOutputStream bmpFile = new FileOutputStream(context.getCacheDir()+fileName);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpFile);
            bmpFile.flush();
            bmpFile.close();
        } catch (Exception e) {
            Log.e("compressBitmap", "Error on saving file");
        }
        //return the path of resized and compressed file
        return  context.getCacheDir()+fileName;
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        String debugTag = "MemoryInformation";
        // Image nin islenmeden onceki genislik ve yuksekligi
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(debugTag,"image height: "+height+ "---image width: "+ width);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(debugTag,"inSampleSize: "+inSampleSize);
        return inSampleSize;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try{
            if (requestCode == MY_CAMERA_PERMISSION_CODE)
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    try {
                        Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e){}
                }
                else
                {
                    Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
                }
            }}catch (Exception e){}
    }

    public void fetchImage(){
        progressDialog = ProgressDialog.show(account.this, "جاري تغيير الصورة", "Please wait...", false, false);
        progressDialog.show();
        String image="";
        File file = null;
        try{
            file = new File(mediaPath);}
        catch (Exception e){
            Toast.makeText(account.this,"من فضلك قم بتحديد صوره شخصيه",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }
        MultipartBody.Part fileToUpload  = null;
        try {
            // Parsing any Media type file
            RequestBody requestBodyId = RequestBody.create(MediaType.parse("*/*"), file);
            fileToUpload = MultipartBody.Part.createFormData("logo", file.getName(), requestBodyId);
        }
        catch (Exception e){
            Toast.makeText(account.this,"من فضلك قم بتحديد صوره شخصيه",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        Call<contact_profile> call = apiinterface.getcontact_updateImage(fileToUpload,headers);
        call.enqueue(new Callback<contact_profile>() {
            @Override
            public void onResponse(Call<contact_profile> call, Response<contact_profile> response) {
                progressDialog.dismiss();
          profile=response.body();
          edt.putString("imageProfile",profile.getPayload().getLogo_url());
          edt.apply();
                Glide.with(account.this).load("https://storejoud.com/"+
                        sharedpref.getString("imageProfile","")).error(R.drawable.logocolor).into(photo);
            }

            @Override
            public void onFailure(Call<contact_profile> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
