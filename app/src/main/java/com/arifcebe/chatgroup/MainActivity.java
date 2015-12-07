package com.arifcebe.chatgroup;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.arifcebe.chatgroup.model.User;
import com.arifcebe.chatgroup.model.UserName;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String FIREBASE_URL = "https://androidgroupchat.firebaseio.com/";
    private static final String TAG = "main";

    private UserAdapter adapter;
    private List<User> listUser = new ArrayList<>();

    private ListView lvUser;

    private EditText edUser,edName,edYear;
    private Button btnSimpan;

    private Firebase myFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set firebase
        Firebase.setAndroidContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edUser = (EditText) findViewById(R.id.username);
        edName = (EditText) findViewById(R.id.name);
        edYear = (EditText) findViewById(R.id.birthDay);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);

        adapter = new UserAdapter(this,listUser);
        lvUser = (ListView) findViewById(R.id.lv_user);
        lvUser.setAdapter(adapter);


        myFirebase = new Firebase(FIREBASE_URL);
        Log.d(TAG, "my firebase " + myFirebase.child("name").getKey().toString());

      /*  myFirebase.child("name").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "child name : " + dataSnapshot.getValue());
                Log.d(TAG, "child name : " + dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User post = postSnapshot.getValue(User.class);
                    System.out.println(post.getFullName() + " - " + post.getBirthYear());
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/

        //myFirebase.child("name").setValue("Do you have data? You'll love Firebase.");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getDataFromFirebase();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edUser.getText().toString();
                String name = edName.getText().toString();
                int year = Integer.valueOf(edYear.getText().toString());

                Firebase vibyRef = myFirebase.child("users").child(user);
                User viby = new User(year,name);

                // simpan data ke firebase
                vibyRef.setValue(viby, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if(firebaseError != null){
                            Log.d(TAG,"data not saved");
                        }else{
                            getDataFromFirebase();
                            Log.d(TAG,"data successfully save "+firebase.child("user").getKey().toString());

                        }
                    }
                });
            }
        });


    }


    private void getDataFromFirebase(){
        myFirebase.child("users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("get user","data user "+dataSnapshot.getKey().length());
                        if(dataSnapshot != null) {
                            Log.d("get user", "data list user " + dataSnapshot.getValue().toString());

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                /*User post = postSnapshot.getValue(User.class);
                                System.out.println(post.getFullName() + " - " + post.getBirthYear()
                                        + " - " + post.getUsername());*/
                                UserName un = new UserName(postSnapshot.getKey());
                                Log.d("user","get username "+un.getUsername());

                                myFirebase.child("users").child(un.getUsername())
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                System.out.println(dataSnapshot.child("fullName").getValue().toString());
                                                System.out.println(dataSnapshot.child("birthYear").getValue().toString());
                                                User us = new User(Integer.valueOf(dataSnapshot.child("birthYear").getValue().toString()),
                                                        dataSnapshot.child("fullName").getValue().toString());

                                                listUser.add(us);
                                            }

                                            @Override
                                            public void onCancelled(FirebaseError firebaseError) {

                                            }
                                        });

                            }
                            adapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
