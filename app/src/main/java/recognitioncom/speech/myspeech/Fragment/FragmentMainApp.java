package recognitioncom.speech.myspeech.Fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import recognitioncom.speech.myspeech.Pojo.CategoriesRes;
import recognitioncom.speech.myspeech.R;
import recognitioncom.speech.myspeech.Recycleview.MainappRecycleAdp;
import recognitioncom.speech.myspeech.Retrofit.CallbackCategoriesListener;
import recognitioncom.speech.myspeech.Retrofit.NetworkConnectionManager;
import recognitioncom.speech.myspeech.TTS.MyTTS;

import static android.app.Activity.RESULT_OK;

public class FragmentMainApp extends Fragment {

    private final int REQ_CODE_SPEECH_INPUT = 1001;
    Context context;
    RecyclerView recyclerView;
    MainappRecycleAdp adp;
    ProgressDialog progressDialog;
    String TAG = "<FragmentMainApp>";
    List<String> categories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layoutmainapp,container,false);
            initInstance(v);
        return v;
    }


    private void initInstance(View v){
//        MyTTS.getInstance(getContext()).setLocale(new Locale("th"))
//                .speak("พดเพ้ได้ได");
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide(); // hide tools bar

            context = getContext();
            recyclerView = v.findViewById(R.id.mainApprecycle);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

            adp = new MainappRecycleAdp(context);

            categories = new ArrayList<>();

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(getString(R.string.progressLoading));
            progressDialog.show();


            new NetworkConnectionManager().callCategories(listener);

    }

    CallbackCategoriesListener listener = new CallbackCategoriesListener() {
        @Override
        public void onResponse(List<CategoriesRes> res) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            for (int i  = 0; i<res.size();i++){
                categories.add(res.get(i).getCategoryName());
            }

            adp.UpdateData(categories);
            recyclerView.setAdapter(adp);
            try {
                String url = "http://192.168.1.4:81/sound/A.wav"; // your URL here
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare(); // might take long! (for buffering, etc)
                mediaPlayer.start();
                new CountDownTimer(mediaPlayer.getDuration(),1000){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(context, "Finish!", Toast.LENGTH_SHORT).show();
                        promptSpeechInput();
                    }
                }.start();

                Toast.makeText(context, ""+ mediaPlayer.getDuration(), Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }



        }

        @Override
        public void onBodyError(ResponseBody responseBodyError) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            Toast.makeText(context, "responseBodyError"+responseBodyError.source(), Toast.LENGTH_SHORT).show();
            Log.d(TAG,""+responseBodyError.source());

        }

        @Override
        public void onBodyErrorIsNull() {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Throwable t) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

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
                    Toast.makeText(context, result.get(0), Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }
    }



}
