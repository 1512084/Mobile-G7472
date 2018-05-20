package ttcnpm.g24.tuyendung.Searching;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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


//NewsfeedAdapter
public class SearchingAdapter extends RecyclerView.Adapter<SearchingAdapter.NewsViewHolder> {
    private List<SearchingSupport> newsList;

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView fullname, detail, id,  news_content;
        private LinearLayout item;
        public NewsViewHolder(View view) {
            super(view);
            avatar = view.findViewById(R.id.avatar);
            fullname = view.findViewById(R.id.company_name);
            detail = view.findViewById(R.id.news_title);
            id = view.findViewById(R.id.news_time);
            news_content = view.findViewById(R.id.news_content);
            item = view.findViewById(R.id.newslist_item);
        }
    }

    SearchingAdapter(List<SearchingSupport> newsList){
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_content, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        final SearchingSupport news = newsList.get(position);
        //TODO: setting avatar to ViewHolder
        //this is only default avatar
        if(news.getAvatar() == null){
            holder.avatar.setImageResource(R.drawable.box_green);
        }else{
            holder.avatar.setImageBitmap(news.getAvatar());
        }
        holder.fullname.setText(news.getCompany_name());
        holder.detail.setText(news.getDetail());
        holder.news_content.setText(news.getNews_content());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("news",new SerializableNews(
                        news.getAvatar(),
                        news.getCompany_name(),
                        news.getDetail(),
                        news.getNews_content()));
                Fragment fragment = new FullSearchingContent();
                fragment.setArguments(args);
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                if(AccountInfo.accountInfo.type == "S")
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.contain_student,fragment).addToBackStack(null).commit();
                else{
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
        private String fullname, detail, id,  news_content;

        public SerializableNews(Bitmap avatar, String company_name, String detail,   String news_content){
            this.avatar = avatar;
            this.fullname = company_name;
            this.detail = detail;
            this.news_content = news_content;
        }

        public Bitmap getAvatar() {
            return avatar;
        }

        public String getFullname() {
            return fullname;
        }

        public String getDetail() {
            return detail;
        }

        public String getId() {
            return id;
        }

        public String getNews_content() {
            return news_content;
        }
    }
}

