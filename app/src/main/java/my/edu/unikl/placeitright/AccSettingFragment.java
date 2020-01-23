package my.edu.unikl.placeitright;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingFragment extends Fragment {
    private Button CancelToProfile,store,country,language,logout,chgpass;
    FirebaseAuth mAuth;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result =  inflater.inflate(R.layout.fragment_settings, null);
        CancelToProfile = result.findViewById(R.id.btnCancel2);
        CancelToProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        store = result.findViewById(R.id.btnStore);
        country = result.findViewById(R.id.btnCountry);
        language = result.findViewById(R.id.btnLanguage);
        logout = result.findViewById(R.id.btnLogOut);
        chgpass = result.findViewById(R.id.btnChgPass);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            logout.setVisibility(View.VISIBLE);
            chgpass.setVisibility(View.VISIBLE);
            chgpass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAuth.getCurrentUser() != null)
                        mAuth.signOut();
                    startActivity(new Intent(getActivity(), WelcomeActivity.class));

                    Toast.makeText(getActivity().getApplicationContext(), "You have successfully logged out!", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            chgpass.setVisibility(View.GONE);
            logout.setText("Log In");
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
        }
        return  result;
    }
    public void openProfileActivity(){
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment()).commit();
    }

}
