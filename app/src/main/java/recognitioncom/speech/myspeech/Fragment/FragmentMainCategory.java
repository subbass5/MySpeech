package recognitioncom.speech.myspeech.Fragment;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import recognitioncom.speech.myspeech.R;
import recognitioncom.speech.myspeech.TTS.MyTTS;

import static android.app.Activity.RESULT_OK;

public class FragmentMainCategory extends Fragment implements View.OnClickListener{
    private final int REQ_CODE_SPEECH_INPUT = 1001;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String Category = "";
    String UrlCategory="";
    String TAG = "<FragmentMainCategory>";
    TextView tv_header;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    FragmentManager fragmentManager;

    MediaPlayer mPlayer;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_main_category,container,false);
        init(v);
        return v;
    }

    private void init(View v){

        fragmentManager = getActivity().getSupportFragmentManager();

        context = getContext();
        v.findViewById(R.id.btn_playsound).setOnClickListener(this);
        v.findViewById(R.id.btn_playch).setOnClickListener(this);
        tv_header = v.findViewById(R.id.tv_header_category);
        sharedPreferences = getActivity().getSharedPreferences(FragmentLogin.MYFER, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Category = sharedPreferences.getString(FragmentLogin.KEY_CATEGORY,"");
        UrlCategory = sharedPreferences.getString(FragmentLogin.KEY_URL_MAIN_CATEGORY,"");

        try {
//            Log.e(TAG,UrlCategory);
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(UrlCategory);
            mPlayer.prepare();

            // Start playing audio from http url
            mPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
//                Toast.makeText(context,"End",Toast.LENGTH_SHORT).show();
                try {
                    promptSpeechInput();
                }catch (Exception e){

                }

            }
        });

        tv_header.setText(Category);
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                promptSpeechInput();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "th-TH");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));


        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(context,
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void btnPlaySound(){

    }

    private void btnPlayChoice(){

    }
    /**
     * Receiving speech input
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    MyTTS.getInstance(context).setLocale(new Locale("th")).speak(" "+result.get(0));

                    if(result.get(0).equals("กลับสู่หน้าหลัก")){
                        fragmentManager.popBackStack();
                    }


                }
                break;
            }

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_playsound:
                btnPlaySound();
                break;
            case R.id.btn_playch:
                btnPlayChoice();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayer.stop();
    }
}
