//package ttcnpm.g24.tuyendung.Searching;
//
//
//import android.media.Image;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import ttcnpm.g24.tuyendung.R;
//
////FullNewsContentFragment
//public class FullSearchingContent extends Fragment {
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        //return super.onCreateView(inflater, container, savedInstanceState);
//        //news_feed_content_fragment
//        View view = inflater.inflate(R.layout.search_content_fragment,container,false);
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        //super.onViewCreated(view, savedInstanceState);
//        Bundle bundle = getArguments();
//        SearchingAdapter.SerializableNews serializableNews = (SearchingAdapter.SerializableNews)bundle.getSerializable("news");
//        SearchingSupport news = new SearchingSupport(serializableNews.getAvatar(),
//                serializableNews.getFullname(),
//                serializableNews.getDetail(),
//                serializableNews.getId());
//
//        ((ImageView)view.findViewById(R.id.content_avatar)).setImageResource(R.drawable.box_green);
//        ((TextView)view.findViewById(R.id.content_company_name)).setText(news.getFullname());
//        ((TextView)view.findViewById(R.id.content_news_title)).setText(news.getDetail());
//    }
//}
