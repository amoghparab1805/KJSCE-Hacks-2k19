package com.redhatpirates.kjsce_hacks_2k19;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class SignInOptions extends AppCompatActivity implements View.OnClickListener {
    private final int RC_SIGN_IN = 1;
    static GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    Button googleSignIn;
    Button pnoSignIn;
    FragmentManager fm;
    SharedPreferences spref;
    SharedPreferences.Editor editor;
    FrameLayout mainFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spref = getApplicationContext().getSharedPreferences("userName", MODE_PRIVATE);
        editor = spref.edit();

        setContentView(R.layout.activity_sign_in_options);
        mAuth = FirebaseAuth.getInstance();
        mainFrag=findViewById(R.id.mainFrag);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(SignInOptions.this, gso);
        googleSignIn = findViewById(R.id.googleSignIn);
        googleSignIn.setOnClickListener(this);
        pnoSignIn = findViewById(R.id.pnoSignIn);
        pnoSignIn.setOnClickListener(this);
        fm = getSupportFragmentManager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.googleSignIn:
                mainFrag.setVisibility(View.GONE);
                signIn();
                break;
            case R.id.pnoSignIn:
                mainFrag.setVisibility(View.VISIBLE);
                fm.beginTransaction().replace(R.id.mainFrag, new PnoFrag()).addToBackStack(null).commit();
                break;

            default:
                Toast.makeText(this, "Impropper selection", Toast.LENGTH_LONG).show();

        }
    }


    // Google SignIN Start
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("signIn result", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("signIn Result", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("sigIn result", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("sigIn result", "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInOptions.this, "SignIn Failed", Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

//change temp to family and stud
//        editor.putString("userName", user.getDisplayName());
//        editor.commit();
        Toast.makeText(SignInOptions.this, "Welcome", Toast.LENGTH_LONG).show();
        Intent gSignIn = new Intent(this, InformationForm.class);
        gSignIn.putExtra("type","email");
        startActivity(gSignIn);
        finish();
    }
    //Google SignIn End
}