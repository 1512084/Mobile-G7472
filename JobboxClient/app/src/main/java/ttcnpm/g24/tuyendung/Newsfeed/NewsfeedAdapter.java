package ttcnpm.g24.tuyendung.Newsfeed;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import ttcnpm.g24.tuyendung.AccountInfo;
import ttcnpm.g24.tuyendung.R;

public class NewsfeedAdapter extends RecyclerView.Adapter<NewsfeedAdapter.NewsViewHolder> {
    private List<News> newsList;

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView company_name, news_title, news_time, news_positions, news_content;
        private LinearLayout item;
        public NewsViewHolder(View view) {
            super(view);
            avatar = view.findViewById(R.id.avatar);
            company_name = view.findViewById(R.id.company_name);
            news_title = view.findViewById(R.id.news_title);
            news_time = view.findViewById(R.id.news_time);
            news_positions = view.findViewById(R.id.news_positions);
            news_content = view.findViewById(R.id.news_content);
            item = view.findViewById(R.id.newslist_item);
        }
    }

    public NewsfeedAdapter(List<News> newsList){
            this.newsList = newsList;
        }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_feed_content, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        final News news = newsList.get(position);
        //TODO: setting avatar to ViewHolder
        //this is only default avatar
        if(news.getAvatar() == null){
            holder.avatar.setImageResource(R.drawable.box_green);
        }else{
            holder.avatar.setImageBitmap(news.getAvatar());
        }
        holder.company_name.setText(news.getCompany_name());
        holder.news_title.setText(news.getNews_title());
        holder.news_time.setText(news.getNews_time());
        holder.news_positions.setText(news.getPositions());
        holder.news_content.setText(news.getNews_content());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("news",new SerializableNews(news.getAvatar(),
                                                                news.getCompany_name(),
                                                                news.getNews_title(),
                                                                news.getNews_time(),
                                                                news.getPositions(),
                                                                news.getNews_content()));
                Fragment fragment = new FullNewsContentFragment();
                fragment.setArguments(args);
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                //Log.d("mytype",AccountInfo.accountInfo.type);
                if(AccountInfo.accountInfo.type == "S")
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.contain_student,fragment).addToBackStack(null).commit();
                else{
                    Log.d("SADf","Dfasdf");
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.contain_company,fragment).addToBackStack(null).commit();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class SerializableNews implements Serializable {
        private Bitmap avatar;
        private String company_name, news_title, news_time, news_positions, news_content;

        public SerializableNews(Bitmap avatar, String company_name, String news_title, String news_time, String news_positions, String news_content){
            this.avatar = avatar;
            this.company_name = company_name;
            this.news_title = news_title;
            this.news_time = news_time;
            this.news_positions = news_positions;
            this.news_content = news_content;
        }

        public Bitmap getAvatar() {
            return avatar;
        }

        public String getCompany_name() {
            return company_name;
        }

        public String getNews_title() {
            return news_title;
        }

        public String getNews_time() {
            return news_time;
        }

        public String getNews_positions() {
            return news_positions;
        }

        public String getNews_content() {
            return news_content;
        }
    }
}

