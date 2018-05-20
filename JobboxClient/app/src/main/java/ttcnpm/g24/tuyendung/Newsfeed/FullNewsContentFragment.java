package ttcnpm.g24.tuyendung.Newsfeed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ttcnpm.g24.tuyendung.R;

public class FullNewsContentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.news_feed_content_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        NewsfeedAdapter.SerializableNews serializableNews = (NewsfeedAdapter.SerializableNews)bundle.getSerializable("news");
        News news = new News(serializableNews.getAvatar(),
                serializableNews.getCompany_name(),
                serializableNews.getNews_title(),
                serializableNews.getNews_time(),
                serializableNews.getNews_positions(),
                serializableNews.getNews_content());
        ((ImageView)view.findViewById(R.id.content_avatar)).setImageBitmap(news.getAvatar());
        ((TextView)view.findViewById(R.id.content_company_name)).setText(news.getCompany_name());
        ((TextView)view.findViewById(R.id.content_news_title)).setText(news.getNews_title());
        ((TextView)view.findViewById(R.id.content_news_time)).setText(news.getNews_time());
        ((TextView)view.findViewById(R.id.content_news_positions)).setText(news.getPositions());
        ((TextView)view.findViewById(R.id.full_news_content)).setText(news.getNews_content());
    }
}
