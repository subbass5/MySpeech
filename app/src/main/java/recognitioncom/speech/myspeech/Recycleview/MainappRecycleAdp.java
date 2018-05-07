package recognitioncom.speech.myspeech.Recycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import recognitioncom.speech.myspeech.R;

public class MainappRecycleAdp extends RecyclerView.Adapter<MainappRecycleAdp.MyHolder> {
    Context context;
    List<String> categories;

    public MainappRecycleAdp(Context context){
        this.context = context;

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
            holder.btnAns.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, ""+categories.get(position), Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
    public class MyHolder extends RecyclerView.ViewHolder{
        Context context;
        Button btnSound,btnAns;
        TextView tv_category;
        public MyHolder(View v,Context context) {
            super(v);

            this.context = context;
            tv_category = v.findViewById(R.id.tv_categories);
            btnSound = v.findViewById(R.id.btnSound);
            btnAns = v.findViewById(R.id.btnAns);
        }
    }
}
