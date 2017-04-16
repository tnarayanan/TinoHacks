package com.example.tejas.tinohacks;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.StringTokenizer;


public class LoginActivity extends AppCompatActivity {


    public GoogleApiClient mGoogleApiClient;

    public FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public FirebaseAuth.AuthStateListener mAuthListener;

    public Context context;

    public FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static final int RC_SIGN_IN = 9001;

    public Boolean previousUser = false;

    private Button signOut;
    private com.google.android.gms.common.SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        context = this;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signOut = (Button) findViewById(R.id.signOut);
        signInButton = (com.google.android.gms.common.SignInButton) findViewById(R.id.signIn);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    Toast.makeText(getApplicationContext(), "Sign In Plsls", Toast.LENGTH_LONG).show();

                }
            }
        };

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
/*
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                email.setText(" ".toString());
                                name.setText(" ".toString());
                            }
                        });
            }
        });*/

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, 9001);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Toast.makeText(getApplicationContext(), "Successsign", Toast.LENGTH_LONG).show();
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                Toast.makeText(getApplicationContext(), "badSign", Toast.LENGTH_LONG).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Cred=Failure", Toast.LENGTH_LONG).show();
                        } else {
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Cred=Success", Toast.LENGTH_LONG).show();
                            //email.setText(user.getEmail().toString());
                            //name.setText(user.getUid());


                            //database.getReference("users").addValueEventListener(new ValueEventListener() {
                            mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                                @Override
                                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                    database.getReference("users").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for(DataSnapshot ChildDataSnapshot:dataSnapshot.getChildren()){
                                                StringTokenizer tokenizer = new StringTokenizer(ChildDataSnapshot.getValue().toString());

                                                if(tokenizer.nextElement().equals(user.getEmail().toString())){
                                                    previousUser=true;
                                                    Toast.makeText(getApplicationContext(), "prevUserTruuu", Toast.LENGTH_LONG).show();
                                                }
                                            }



                                            if(!previousUser){
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                                        final EditText edittext = new EditText(getApplicationContext());
//                                        builder.setTitle("Please enter a display name.");
//                                        builder.setView(edittext);
//                                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int whichButton) {
//                                                String username = edittext.getText().toString();
//                                                database.getReference("users").child(user.getUid()).setValue(user.getEmail() + " " + username);
//                                            }
//                                        });
//                                        builder.show();

                                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                builder.setTitle("Username:");

                                                // Set up the input
                                                final EditText input = new EditText(context);
                                                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                                input.setInputType(InputType.TYPE_CLASS_TEXT);
                                                builder.setView(input);

                                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        String username = input.getText().toString();
                                                        database.getReference("users").child(user.getUid()).setValue(user.getEmail() + " " + username);

                                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                        startActivity(i);

                                                    }
                                                });

                                                builder.show();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                            });




                        }
                        // ...
                    }
                });
    }
}
