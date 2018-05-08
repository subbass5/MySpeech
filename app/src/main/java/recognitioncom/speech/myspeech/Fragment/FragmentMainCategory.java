package recognitioncom.speech.myspeech.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import recognitioncom.speech.myspeech.R;

public class FragmentMainCategory extends Fragment implements View.OnClickListener{

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String Category = "";
    TextView tv_header;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_main_category,container,false);
        init(v);
        return v;
    }

    private void init(View v){

        v.findViewById(R.id.btn_playsound).setOnClickListener(this);
        v.findViewById(R.id.btn_playch).setOnClickListener(this);
        tv_header = v.findViewById(R.id.tv_header_category);
        sharedPreferences = getActivity().getSharedPreferences(FragmentLogin.MYFER, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Category = sharedPreferences.getString(FragmentLogin.KEY_CATEGORY,"");
        tv_header.setText(Category);


    }

    private void btnPlaySound(){

    }

    private void btnPlayChoice(){

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
}
