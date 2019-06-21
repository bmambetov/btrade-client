package Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import Config.BaseURL;
import Model.Home_Icon_model;
import btrade.tcc.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Unknown.
 */

public class Home_Icon_Adapter extends RecyclerView.Adapter<Home_Icon_Adapter.MyViewHolder> {

    private List<Home_Icon_model> modelList;
    private Context context;
    String language;
    SharedPreferences preferences;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.service_text);
            image = view.findViewById(R.id.service_image);


        }
    }

    public Home_Icon_Adapter(List<Home_Icon_model> modelList) {
        this.modelList = modelList;
    }

    @Override
    public Home_Icon_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_headre_catogaries, parent, false);

        context = parent.getContext();

        return new Home_Icon_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Home_Icon_Adapter.MyViewHolder holder, int position) {
        Home_Icon_model mList = modelList.get(position);
        Glide.with(context)
                .load(BaseURL.IMG_CATEGORY_URL + mList.getImage())
                .placeholder(R.drawable.icon)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.image);
        preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
        language = preferences.getString("language", "");
        holder.title.setText(mList.getTitle());
        Log.e("TAG", "english: " + mList.getTitle());
//        if (language.contains("english")) {
//        } else {
//            Log.e("TAG", "arab: " + mList.getArb_title());
//            holder.title.setText(mList.getArb_title());
//
//        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}

