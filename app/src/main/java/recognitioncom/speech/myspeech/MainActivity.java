package recognitioncom.speech.myspeech;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

import recognitioncom.speech.myspeech.Fragment.FragmentLogin;
import recognitioncom.speech.myspeech.TTS.MyTTS;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    FragmentLogin loginFrg;
    public static final String BASE_URL = "https://mumei-system.ml/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginFrg = new FragmentLogin();
        fragmentTran(loginFrg,null);
     


    }

    public void fragmentTran(Fragment fragment,Bundle bundle){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction frgTran = fragmentManager.beginTransaction();
        frgTran.replace(R.id.contentApp, fragment).addToBackStack(null).commit();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();

        int co = fragmentManager.getBackStackEntryCount();
        Toast.makeText(this, ""+co, Toast.LENGTH_SHORT).show();
        if(co > 1 ){
            fragmentManager.popBackStack();
        }

    }

}
