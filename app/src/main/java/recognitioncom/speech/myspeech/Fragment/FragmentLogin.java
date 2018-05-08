package recognitioncom.speech.myspeech.Fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


import okhttp3.ResponseBody;
import recognitioncom.speech.myspeech.Pojo.LoginRes;
import recognitioncom.speech.myspeech.R;
import recognitioncom.speech.myspeech.Retrofit.NetworkConnectionManager;
import recognitioncom.speech.myspeech.Retrofit.CallbackLoginListener;
import recognitioncom.speech.myspeech.TTS.MyTTS;

import static android.app.Activity.RESULT_OK;

public class FragmentLogin extends Fragment implements View.OnClickListener{

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private EditText et_user,et_pwd;



    public static final String MYFER = "myFer";
    public static final String KEY_ID = "idUser";
    public static final String KEY_NAME ="nameUser";
    public static final String KEY_EMAIL = "emailUser";
    public static final String KEY_CATEGORY = "category";
    private String usr = "";
    private String pwd = "";
    private String TAG = "FragmentLogin";
    private CheckBox checkBox;
    private ProgressDialog progress;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_page,container,false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide(); // hide tools bar

//        MyTTS.getInstance(getContext()).setLocale(new Locale("th"))
//                .speak("ยินดีต้อนรับสู่เกมทายเสียง");
//        new CountDownTimer(3000,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            @Override
//            public void onFinish() {
//                MyTTS.getInstance(getContext()).setLocale(new Locale("th"))
//                        .speak("ดึงหน้าจอลงเพื่อสั่งงาน");
//            }
//        }.start();

        init(v);

        return v;
    }

    private void init(View v){

        //init instance
        context = getContext();
        v.findViewById(R.id.btn_login).setOnClickListener(this);
        v.findViewById(R.id.tv_register).setOnClickListener(this);

        checkBox = v.findViewById(R.id.chkRemeber);

        et_user = v.findViewById(R.id.et_username);
        et_pwd  = v.findViewById(R.id.et_password);

        et_user.setText("test");
        et_pwd.setText("0850400151");

        sharedPreferences = getActivity().getSharedPreferences(MYFER,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();



    }

    private void login(){




        usr = et_user.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();

        if(usr.length() >3 && usr.length() >3 ){

            progress = new ProgressDialog(context);
            progress.setMessage(getString(R.string.progressLoading));
            progress.show();

            new NetworkConnectionManager().callLogin(listener,usr,pwd);
        }




    }

    private void  register(){

        FragmentRegister fragmentMainApp = new FragmentRegister();
        fragmentTran(fragmentMainApp,null);

//        Toast.makeText(context, "Register", Toast.LENGTH_SHORT).show();

    }

    public void fragmentTran(Fragment fragment,Bundle bundle){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction frgTran = fragmentManager.beginTransaction();
        frgTran.replace(R.id.contentApp, fragment).addToBackStack(null).commit();
    }

    CallbackLoginListener listener = new CallbackLoginListener() {
        @Override
        public void onResponse(LoginRes loginRes) {
            Log.e(TAG,""+loginRes.getName());


            if(progress.isShowing()){
                progress.dismiss();

            }


            editor.putString(KEY_ID,loginRes.getID());
            editor.putString(KEY_NAME,loginRes.getName());
            editor.putString(KEY_EMAIL,loginRes.getEmail());
            editor.commit();

            FragmentMainApp fragmentMainApp = new FragmentMainApp();
            fragmentTran(fragmentMainApp,null);


        }

        @Override
        public void onBodyError(ResponseBody responseBodyError) {
            Log.e(TAG,"responseBodyError ");
            if(progress.isShowing()){
                progress.dismiss();
                Toast.makeText(context, "เข้าสู่ระบบไม่สำเร็จ", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        public void onBodyErrorIsNull() {
            Log.e(TAG,"onBodyErrorIsNull");
            if(progress.isShowing()){
                progress.dismiss();
                Toast.makeText(context, "เข้าสู่ระบบไม่สำเร็จ", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        public void onFailure(Throwable t) {
            Log.e(TAG,""+t.getMessage());
            if(progress.isShowing()){
                progress.dismiss();
                Toast.makeText(context, "เข้าสู่ระบบไม่สำเร็จ", Toast.LENGTH_SHORT).show();

            }


        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:  //event onClick button login
                login();
                break;
            case R.id.tv_register: //event onClick textview register
                register();
                break;
        }
    }


}
