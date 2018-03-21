package com.raj.sagar.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MomumentList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ArrayList<String> titlesmonument;
    private ArrayList<String> descriptionsmonument;
    private DatabaseReference mDatabase;
    private static final int CAMERA_REQUEST = 1888;
    private static final int RESULT_LOAD_IMAGE = 343;
    CardView cardView;
    FloatingActionButton floatingActionButton;
    String post_key;
    static boolean clicked = false;
    private boolean mProcessLike = false;
    Handler handler;

    private ArrayList<Integer> urlsToImagemonument;
    MonumentAdapter adaptermonument;
    CardView cardViewmonument;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;


    RecyclerView recyclerViewmonument;
    FirebaseUser user;
    private static Context c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        c= getApplicationContext();



        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        floatingActionButton=findViewById(R.id.fab1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "FAB clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MomumentList.this, Form.class);

                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    Intent i = new Intent(MomumentList.this, Login.class);
                    startActivity(i);
                }
            }
        };

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView email = (TextView)hView.findViewById(R.id.email);
        TextView displayName = (TextView)hView.findViewById(R.id.display_name);
        ImageView dp = (ImageView)hView.findViewById(R.id.display_picture);
        email.setText(user.getDisplayName());
        displayName.setText(user.getEmail());
        Picasso.with(getApplicationContext()).load(user.getPhotoUrl()).transform(new RoundedTransformation(50, 4)).into(dp);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Story");




        recyclerViewmonument = findViewById(R.id.recylerViewmonument);

//        int[] androidColors = getResources().getIntArray(R.array.androidcolors);
//        titlesmonument = new ArrayList<String>();
//        descriptionsmonument = new ArrayList<String>();
//
//        urlsToImagemonument = new ArrayList<Integer>();
//        cardViewmonument = findViewById(R.id.cardViewmonument);
//        adaptermonument = new MonumentAdapter(androidColors, titlesmonument, descriptionsmonument,urlsToImagemonument, getApplicationContext());


        recyclerViewmonument.setLayoutManager(new LinearLayoutManager(this));


//        recyclerViewmonument.setAdapter(adaptermonument);
        FirebaseRecyclerAdapter<Story,StoryViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Story, StoryViewHolder>(
                Story.class,
                R.layout.monument_item,
                StoryViewHolder.class,
                mDatabase
        ) {


            @Override
            protected void populateViewHolder(StoryViewHolder viewHolder, Story model, int position) {
                post_key = getRef(position).getKey();
                viewHolder.setUsername(model.getUsername().equals(user.getDisplayName())?"You":model.getUsername());
                viewHolder.setComment(model.getComment());
                viewHolder.setLocation(model.getCity());
                viewHolder.setImage(getApplicationContext(), model.getImage());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "CLICKED!!", Toast.LENGTH_LONG).show();
                    }
                });




            }

        };

        recyclerViewmonument.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();
        recyclerViewmonument.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerViewmonument, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(MomumentList.this, comment.class);
                i.putExtra("post_key", post_key);
                startActivity(i);




                }



            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);





    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder{
        View mView;
        private final TextView user_name;
        private final TextView user_comment;
        private final TextView loc;
        private final ImageView user_image;



        public StoryViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            user_name = (TextView)mView.findViewById(R.id.textView1monument);
            loc = (TextView)mView.findViewById(R.id.loc);
            user_comment = (TextView)mView.findViewById(R.id.textView2monument);
            user_image = (ImageView)mView.findViewById(R.id.imageviewmonument);







        }

//        public void send_Intent(){
//
//
//
        public void setUsername(String username){


            user_name.setText(username);
        }
        public void setComment(String comment){


            user_comment.setText(comment);
        }
        public void setLocation(String location){


            loc.setText(location);
        }
        public void setImage(Context ctx, String image){


            Picasso.with(ctx).load(image).into(user_image);
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera)
        {
            Toast.makeText(getApplicationContext(), "Import clicked", Toast.LENGTH_LONG).show();
            // Handle the camera action
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } else if (id == R.id.location) {

            Intent inte = new Intent(MomumentList.this, GetLocation.class);
            startActivity(inte);



        }else if (id==R.id.nav_web){

            String urlString="https://ui.careless32.hasura-app.io";
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            try {
                getApplicationContext().startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                // Chrome browser presumably not installed so allow user to choose instead
                intent.setPackage(null);
                getApplicationContext().startActivity(intent);
            }



//            Intent i = new Intent(MomumentList.this, Website.class);
//            startActivity(i);
        }
        else if(id==R.id.signOut){

            mAuth.signOut();
            Intent signout = new Intent(MomumentList.this, Login.class);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data != null) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");

            Intent form = new Intent(this, Form.class);

            form.putExtra("BitmapImage", mphoto);
            startActivity(form);
        }
    }
}
