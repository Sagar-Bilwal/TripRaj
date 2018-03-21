package com.raj.sagar.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class comment extends AppCompatActivity {
    private String  post_key = null;
    FirebaseListAdapter<PostComment> adapter;
    private DatabaseReference mDatabase;
    private TextView username_comment;
    private DatabaseReference mDatabaseComment;
    FirebaseAuth mAuth;
    FirebaseUser user;
    private ImageView imageView_comment;
    private EditText comment_edittext;
    private Button send_comment;
    String newcomment;
    ListView recyclerViewComment;
    DatabaseReference reference2;

    ArrayList<PostComment>  comments;
    TextView comment_username;
    TextView comment_body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        post_key = getIntent().getExtras().getString("post_key");
        Toast.makeText(getApplicationContext(), post_key, Toast.LENGTH_LONG).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerViewComment = (ListView) findViewById(R.id.comments_recycler);
        comment_body = findViewById(R.id.comment_body);
        comment_username = findViewById(R.id.comment_name);



        comment_edittext = (EditText) findViewById(R.id.comment_edittext);
        send_comment = (Button) findViewById(R.id.send_comment);
//        recyclerViewComment = findViewById(R.id.comments_recycler);
//        recyclerViewComment.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Story");
        mDatabaseComment = FirebaseDatabase.getInstance().getReference().child("Comment");
        reference2 = mDatabaseComment.child(post_key);
        username_comment = (TextView) findViewById(R.id.username_comment);
        imageView_comment = (ImageView) findViewById(R.id.imageView_comment);






        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String comment = (String) dataSnapshot.child("comment").getValue();
                String image = (String) dataSnapshot.child("image").getValue();
                String username = (String) dataSnapshot.child("username").getValue();
                username_comment.setText(username);
                Picasso.with(getApplicationContext()).load(image).into(imageView_comment);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        mDatabaseComment.child(post_key).addValueEventListener(new ValueEventListener() {
//                                                       @Override
//                                                       public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                                           for(DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()){
//
//                                                               ArrayList<DataSnapshot> arrayList = new ArrayList<>();
//                                                               arrayList.add(uniqueKeySnapshot);
//                                                               //Loop 1 to go through all the child nodes of users
//                                                               for(DataSnapshot booksSnapshot : uniqueKey.child("Books").getChildren()){
//                                                                   //loop 2 to go through all the child nodes of books node
//                                                                   String bookskey = booksSnapshot.getKey();
//                                                                   String booksValue = booksSnapshot.getValue();
//                                                               }
//                                                           }
//                                                       }
//
//                                                       @Override
//                                                       public void onCancelled(DatabaseError databaseError) {
//
//                                                       }
//                                                   }


//            2nd option
//        mDatabaseComment.child(post_key).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                HashMap<String,Object> data = dataSnapshot.getValue(new HashMap<String, Object>);
//
////                String comment = (String) dataSnapshot.getChildren();
////                String image = (String) dataSnapshot.child("image").getValue();
////                String username = (String) dataSnapshot.child("username").getValue();
////                username_comment.setText(username);
////                Picasso.with(getApplicationContext()).load(image).into(imageView_comment);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });





        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newcomment = comment_edittext.getText().toString().trim();
                Toast.makeText(getApplicationContext(),newcomment,Toast.LENGTH_LONG).show();
//                writeNewPost(user.getDisplayName(), newcomment);
                comment_edittext.setText("");
//                DatabaseReference reference = mDatabaseComment.child(post_key);
//                PostComment taskMap = new PostComment(user.getDisplayName(), newcomment);
//
//                reference.updateChildren(taskMap);

//                DatabaseReference reference = mDatabaseComment.child(post_key);
//                final Map<String, Object> dataMap = new HashMap<String, Object>();
//                PostComment postComment = new PostComment(user.getDisplayName(),newcomment);
//                dataMap.put()
//                reference.updateChildren();

//                    Working Code
                DatabaseReference reference = mDatabaseComment.child(post_key);
                Map<String,Object> taskMap = new HashMap<>();
                taskMap.put(user.getDisplayName(),newcomment);
                reference.updateChildren(taskMap);



            }
        });

//        displayComment();






    }

//    public void displayComment(){
//        adapter = new FirebaseListAdapter<PostComment>(comment.this, PostComment.class, R.layout.comment_item, mDatabaseComment.child(post_key)) {
//            @Override
//            protected void populateView(View v, PostComment model, int position) {
//                TextView comment_name, comment_body;
//                comment_name = v.findViewById(R.id.comment_name);
//                comment_body = v.findViewById(R.id.comment_body);
//
//                comment_name.setText(model.getUsername());
//                comment_body.setText(model.getComment());
//
//            }
//        };
//
//        recyclerViewComment.setAdapter(adapter);
//
//
//
//
//
//
//    }




    @Override
    protected void onStart() {
        super.onStart();
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                comments = new ArrayList<>();
//                PostComment postComment = dataSnapshot.getValue(PostComment.class);
//                comments.add(postComment);
////                comment_body.setText(postComment.getComment());
////                comment_username.setText(postComment.getUsername());
//
//
//
//
////                DataSnapshot commentSnapchot = dataSnapshot.child(post_key);
////                Iterable<DataSnapshot> commentChildren = commentSnapchot.getChildren();
////                ArrayList<PostComment>  comments = new ArrayList<>();
////                for (DataSnapshot comment:commentChildren){
////
////
////                    PostComment pc = comment.getValue(PostComment.class);
////                    comments.add(pc);
////                }
////                adapter= new CommentAdapter(getApplicationContext(),comments);
////                recyclerViewComment.setAdapter(adapter);
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(comment.this, "Failed to load post.",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        };
//        mDatabaseComment.addValueEventListener(valueEventListener);
//        adapter = new CommentAdapter(getApplicationContext(), comments);
//        recyclerViewComment.setAdapter(adapter);

//        FirebaseRecyclerAdapter<PostComment, CommentViewHolder> firebaseRecyclerAdapterComment = new FirebaseRecyclerAdapter<PostComment, CommentViewHolder>(
//                PostComment.class,
//                R.layout.comment_item,
//                CommentViewHolder.class,
//                reference2
//        ) {
//            @Override
//            protected void populateViewHolder(CommentViewHolder viewHolder, PostComment model, int position) {
//
//                viewHolder.setUsername(model.getUsername());
//                viewHolder.setComment(model.getComment());
//
//            }
//        };
//        recyclerViewComment.setAdapter(firebaseRecyclerAdapterComment);
//        firebaseRecyclerAdapterComment.notifyDataSetChanged();
    }

//    public static class CommentViewHolder extends RecyclerView.ViewHolder{
//        View mView;
//        private final TextView comment_name;
//        private final TextView comment_body;
//
//
//
//        public CommentViewHolder(View itemView) {
//            super(itemView);
//            mView = itemView;
//            comment_name = (TextView)mView.findViewById(R.id.textView1monument);
//            comment_body = (TextView)mView.findViewById(R.id.textView2monument);
//
//
//
//
//
//        }
//
//        //        public void send_Intent(){
////
////
////
//        public void setUsername(String username){
//
//            comment_name.setText(username);
//        }
//        public void setComment(String comment){
//
//
//            comment_body.setText(comment);
//        }
//
//    }


}
