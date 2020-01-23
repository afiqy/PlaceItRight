package my.edu.unikl.placeitright;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingActivity extends Fragment {
    private Button CancelToProfile,store,country,language,logout;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result =  inflater.inflate(R.layout.fragment_settings, null);
        CancelToProfile = result.findViewById(R.id.btnCancel2);
        CancelToProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

        store = result.findViewById(R.id.btnStore);
        country = result.findViewById(R.id.btnCountry);
        language = result.findViewById(R.id.btnLanguage);
        logout = result.findViewById(R.id.btnLogOut);

        return  result;
    }
    public void openProfileActivity(){
        Fragment fragment = new ProfileFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
