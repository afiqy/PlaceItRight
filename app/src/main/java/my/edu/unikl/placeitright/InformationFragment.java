package my.edu.unikl.placeitright;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class InformationActivity extends AppCompatActivity {
    private Button CancelToProfile,ProductRecall,Privacy,Disclaimers,License;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        CancelToProfile = findViewById(R.id.btnCancel);
        CancelToProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });


    }

    public void openProfileActivity(){
        startActivity(new Intent(this,ProfileFragment.class));
    }
}
