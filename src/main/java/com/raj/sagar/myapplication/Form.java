package com.raj.sagar.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class Form extends AppCompatActivity {
    FirebaseAuth mAuth;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private Uri imageUri;
    int RESULT_LOAD_IMAGE = 100;
    Bitmap bitmaps;
    ImageView img;
    private ProgressDialog mProgress;
    private EditText comment_body;
    private String city;
    FirebaseUser user;
    ///Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        user = mAuth.getCurrentUser();
        comment_body = (EditText) findViewById(R.id.comment_body);
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Story");

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        img = (ImageView)findViewById(R.id.img);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                city = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + city, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<String> categories = new ArrayList<String>();
        categories.add("Jaipur");
        categories.add("Thar desert");
        categories.add("Udaipur");
        Intent intent = getIntent();
        Bitmap camera_img_bitmap = (Bitmap) intent
                .getParcelableExtra("BitmapImage");
        if (camera_img_bitmap != null) {
            img.setImageBitmap(camera_img_bitmap);
        }




        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, categories);
        spinner.setAdapter(dataAdapter);


        Button get_image = (Button) findViewById(R.id.get_image);
        get_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE && null!=data) {
            imageUri = data.getData();

            ImageView imageView=findViewById(R.id.img);
            imageView.setImageURI(imageUri);


        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send:
                Toast.makeText(getApplicationContext(), "Sending data", Toast.LENGTH_LONG).show();
                post();



                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void post(){
        mProgress.setMessage("Posting...");
        final String comment = comment_body.getText().toString().trim();
        final String username = user.getDisplayName();
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(city) && imageUri!=null ){



            mProgress.show();
            StorageReference filepath = mStorage.child("Story_Images").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    final DatabaseReference newPost = mDatabase.push();
                    newPost.child("city").setValue(city);
                    newPost.child("comment").setValue(comment);
                    newPost.child("username").setValue(username);
                    newPost.child("image").setValue(downloadUrl.toString());

                    mProgress.dismiss();
                    startActivity(new Intent(Form.this, MomumentList.class));
                }
            });
        }



    }
}
