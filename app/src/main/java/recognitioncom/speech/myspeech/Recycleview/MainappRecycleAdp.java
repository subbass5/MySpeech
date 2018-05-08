package recognitioncom.speech.myspeech.Recycleview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import recognitioncom.speech.myspeech.Fragment.FragmentLogin;
import recognitioncom.speech.myspeech.Fragment.FragmentMainCategory;
import recognitioncom.speech.myspeech.R;

public class MainappRecycleAdp extends RecyclerView.Adapter<MainappRecycleAdp.MyHolder> {
    Context context;
    List<String> categories;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;

    public MainappRecycleAdp(Context context){
        this.context = context;
        sharedPreferences = ((AppCompatActivity) context).getSharedPreferences(FragmentLogin.MYFER,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void UpdateData(List<String> categories) {

        this.categories = categories;

    }


    @NonNull
    @Override
    public MainappRecycleAdp.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mainapps,parent,false);
        return new MyHolder(v,context);
    }

    @Override
    public void onBindViewHolder(@NonNull MainappRecycleAdp.MyHolder holder, final int position) {
            holder.tv_category.setText(categories.get(position));
            holder.setOnClickRecycleView(new RecycleViewOnClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick, MotionEvent motionEvent) {
//                    Toast.makeText(context, "Click "+categories.get(position), Toast.LENGTH_SHORT).show();
                    editor.putString(FragmentLogin.KEY_CATEGORY,categories.get(position));
                    editor.commit();

                    FragmentMainCategory mainCategory = new FragmentMainCategory();
                    fragmentTran(mainCategory,null);
                }

                @Override
                public void onLongClick(View view, int position, boolean isLongClick, MotionEvent motionEvent) {
//                    Toast.makeText(context, "Long Click "+categories.get(position), Toast.LENGTH_SHORT).show();
                }
            });

//            holder.btnAns.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, ""+categories.get(position), Toast.LENGTH_SHORT).show();
//                }
//            });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void fragmentTran(Fragment fragment, Bundle bundle){

        FragmentManager fragmentManager =((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction frgTran = fragmentManager.beginTransaction();
        frgTran.replace(R.id.contentApp, fragment).addToBackStack(null).commit();
    }


    public class MyHolder extends RecyclerView.ViewHolder  implements View.OnClickListener
            , View.OnLongClickListener, View.OnTouchListener{
        Context context;
        Button btnSound,btnAns;
        TextView tv_category;
        RecycleViewOnClickListener listener;

        public MyHolder(View v,Context context) {
            super(v);

            this.context = context;
            tv_category = v.findViewById(R.id.tv_categories);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }
        public void setOnClickRecycleView(RecycleViewOnClickListener listener){
            this.listener =  listener;

        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition(), false, null);

        }

        @Override
        public boolean onLongClick(View v) {
            listener.onLongClick(v, getAdapterPosition(), false, null);

            return false;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            listener.onClick(v, getAdapterPosition(), false, event);

            return false;
        }
    }


}
