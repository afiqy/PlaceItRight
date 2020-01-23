package my.edu.unikl.placeitright;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button signup, backButton;
    private EditText txtName,txtEmailSignUp,txtPasswordSignUp,txtPhone;
    private FirebaseAuth fbAuth;
    private ProgressBar progressBar;
    private DatabaseReference UserDatabase;
    int ErrorCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //create an instance of database ref
        UserDatabase = FirebaseDatabase.getInstance().getReference("users");

        //assign button
        signup = (Button) findViewById(R.id.buttonSignup);
        backButton = (Button) findViewById(R.id.buttonBackLogin);

        //assign variable to xml variable
        txtName = (EditText) findViewById(R.id.txtName);
        txtEmailSignUp  = (EditText) findViewById(R.id.txtEmail);
        txtPasswordSignUp = (EditText) findViewById(R.id.txtPasswordSignUp);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        progressBar = (ProgressBar) findViewById(R.id.SignUpProgressBar);

        progressBar.setVisibility(View.GONE);

        //initialize firebase auth
        fbAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(this);
        backButton.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fbAuth.getCurrentUser() != null){
            //handle the already login user
        }
    }

    private void registerUser(){
        final String email = txtEmailSignUp.getText().toString().trim();
        String passwordInput = txtPasswordSignUp.getText().toString().trim();
        final String fullname = txtName.getText().toString().trim();
        final String PhoneNo = txtPhone.getText().toString().trim();

        checkError(email,passwordInput,fullname,PhoneNo);
        if(ErrorCount==0) {
            fbAuth.createUserWithEmailAndPassword(email, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        User user = new User(fullname, email, PhoneNo);
                        UserDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "User successfully registered!", Toast.LENGTH_SHORT).show();
                                    txtEmailSignUp.setText("");
                                    txtPasswordSignUp.setText("");
                                    txtName.setText("");
                                    txtPhone.setText("");
                                    Toast.makeText(getApplicationContext(), "You may log in now.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error occured! Cannot register. Please try again later.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Error occured! Cannot register. Please try again later.", Toast.LENGTH_SHORT);
                    }

                }
            });
        }


    }

    private void checkError(String email, String pass,String Name, String PhoneNum){
        if(email.isEmpty()){
            txtEmailSignUp.setError("Email is required!");
            txtEmailSignUp.requestFocus();
            ErrorCount++;
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtEmailSignUp.setError("Please enter a valid email!");
            txtEmailSignUp.requestFocus();
            ErrorCount++;
            return;
        }
        if(pass.isEmpty()){
            txtPasswordSignUp.setError("Password is required!");
            txtPasswordSignUp.requestFocus();
            ErrorCount++;
            return;
        }
        if(pass.length()<6){
            txtPasswordSignUp.setError("Minimum length of password is 6!");
            txtPasswordSignUp.requestFocus();
            ErrorCount++;
            return;
        }
        if(Name.isEmpty()){
            txtName.setError("Name is required!");
            txtName.requestFocus();
            ErrorCount++;
            return;
        }
        if(PhoneNum.isEmpty()){
            txtPhone.setError("Phone Number is required!");
            txtPhone.requestFocus();
            ErrorCount++;
            return;
        }

        if(PhoneNum.length() < 10 || PhoneNum.length() > 11){
            txtPhone.setError("Enter a valid phone number!");
            txtPhone.requestFocus();
            ErrorCount++;
            return;
        }

    }

    @Override
    public void onClick(View v) {
        if(v == signup){
            registerUser();
        }

        if(v == backButton){
            Intent backLogin = new Intent(this, LoginActivity.class);
            startActivity(backLogin);
        }
    }
}
