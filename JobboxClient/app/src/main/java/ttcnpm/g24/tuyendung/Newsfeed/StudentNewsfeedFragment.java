package ttcnpm.g24.tuyendung.Newsfeed;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ttcnpm.g24.tuyendung.AccountInfo;
import ttcnpm.g24.tuyendung.MySingleton;
import ttcnpm.g24.tuyendung.R;
public class StudentNewsfeedFragment extends Fragment {
    private List<News> newsList = new ArrayList<>();
    private List<News> allNewsList = new ArrayList<>();
    private RecyclerView rvNewsList;

    private NewsfeedAdapter adapter = new NewsfeedAdapter(newsList);
    private LinearLayoutManager layoutManager;
    private Parcelable state;

    private int prevTotal = 0, visibleThreshold = 0;
    private boolean loading = true;
    private int firstVisibleItemPosition, visibleItemCount, totalItemCount;
    private int newsPerLoad = 5;
    private int currentNewsInList = 0;
    private boolean flag = false;
    //TODO change localhost to something
    private String url = "http://jws-app-mobile.a3c1.starter-us-west-1.openshiftapps.com/ReturnPostForStudent";
    private View view;

    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.student_news_feed_fragment,container,false);
        //Log.d("Fff",AccountInfo.accountInfo.type);
        rvNewsList = view.findViewById(R.id.std_news_list);
        layoutManager = new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL,false);
        rvNewsList.setAdapter(adapter);
        rvNewsList.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        if (state != null){
            rvNewsList.getLayoutManager().onRestoreInstanceState(state);
        }
        else {
            getNewsList();
            Log.d("SIZEoflist", String.valueOf(allNewsList.size()));


        }




        rvNewsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = rvNewsList.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                //Log.d("variable",String.valueOf(layoutManager.findLastVisibleItemPosition()) + " " + String.valueOf(totalItemCount));

                if (loading) {
                    if (totalItemCount > prevTotal) {
                        loading = false;
                        prevTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - layoutManager.findLastVisibleItemPosition() ==1)){
                    //end has been reached
                    //load more News here
                    if (moreNews()) {
                        loadNews();
                        Log.d("scroll","scroll");
                    }
                    loading = true;
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getActivity().setTitle("Followed Companies' Posts");

        FloatingActionButton btn_refresh = view.findViewById(R.id.std_news_refresh);
        btn_refresh.setAlpha(0.35f);
        this.view = view;


        //add request here

        //Toast.makeText(view.getContext(),"size " + String.valueOf(newsList.size()) + " / " +  String.valueOf(allNewsList.size()), Toast.LENGTH_SHORT).show();

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allNewsList.clear();
                newsList.clear();
                adapter.notifyDataSetChanged();
                currentNewsInList = 0;
                prevTotal = 0;
                loading = true;
                getNewsList();
                //while(!flag);
                //loadNews();
            }
        });
    }

    @Override
    public void onPause() {
        state = rvNewsList.getLayoutManager().onSaveInstanceState();
        super.onPause();
    }

    private void getNewsList() {
        showProgressDialog();

        //create requestJson
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", AccountInfo.accountInfo.username);
        } catch (JSONException e){
            e.printStackTrace();
        }
        jsonArray.put(jsonObject);
        //Log.d("AAA",jsonArray.toString());
        //add Request to RequestQueue
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                url,
                jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d("response",String.valueOf(response.length()));
                        try {
                            for (int i=0; i < response.length(); i++) {
                                JSONObject newsJson = response.getJSONObject(i);
                                //TODO: GET COMPANY NAME, NOT USERNAME
                                String companyName = newsJson.getString("namecompany");
                                //TODO: news title here
                                String newsTitle = newsJson.getString("title");
                                String newsTime = newsJson.getString("time");

                                String positions = "Position(s): ";
                                try {
                                    JSONArray positionList = newsJson.getJSONArray("listPosition");
                                    for (int j = 0; j < positionList.length() - 1; j++) {
                                        positions += positionList.getJSONObject(j).getString("position") + ", ";
                                    }
                                    positions += positionList.getJSONObject(positionList.length() - 1).getString("position");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String content = newsJson.getString("content");
                                String hexStringAvatr = newsJson.getString("avatar");
                                if(hexStringAvatr.equals("")) {

                                    News news = new News(null, companyName, newsTitle, newsTime, positions, content);
                                    allNewsList.add(news);
                                }else{
                                    //Log.d("myavatar",newsJson.getString("namecompany") + ": " + "has avatar");
                                    byte[] b = new byte[hexStringAvatr.length() / 2];
                                    for (int j = 0; j < b.length; j++) {
                                        int index = j * 2;
                                        int v = Integer.parseInt(hexStringAvatr.substring(index, index + 2), 16);
                                        b[j] = (byte) v;
                                    }

                                    Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                                    if(bitmap==null){
                                        Log.d("NULLbitmap","null");
                                    }
                                    News news = new News(bitmap, companyName, newsTitle, newsTime, positions, content);
                                    allNewsList.add(news);
                                }

                                //Log.d("size",String.valueOf(allNewsList.size()));
                                //loadNews();
                            }

                            if (allNewsList.size()>= newsPerLoad) {
                                //.makeText(view.getContext(),"Loaded " + String.valueOf(allNewsList.size()), Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < newsPerLoad; i++) {
                                    //Log.d("ListSize: ", String.valueOf(allNewsList.size()) + " " + String.valueOf(newsList.size()));
                                    //Log.d("currentNewList: ",String.valueOf(currentNewsInList));
                                    newsList.add(allNewsList.get(newsList.size()));
                                    adapter.notifyDataSetChanged();
                                }
                                currentNewsInList += newsPerLoad;
                            } else {
                                int d = allNewsList.size();
                                //Toast.makeText(view.getContext(),"Loaded " + String.valueOf(allNewsList.size()), Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < d; i++) {
                                    newsList.add(allNewsList.get(newsList.size()));
                                    adapter.notifyDataSetChanged();
                                    currentNewsInList ++;
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        Toast.makeText(view.getContext(),"Error on response", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        MySingleton.getmInstance(view.getContext()).addToRequestque(jsonArrayRequest);

        hideProgressDialog();
    }

    private void loadNews(
    ) {
        if (allNewsList.size() - currentNewsInList >= newsPerLoad) {
            //.makeText(view.getContext(),"Loaded " + String.valueOf(allNewsList.size()), Toast.LENGTH_SHORT).show();
            for (int i = 0; i < newsPerLoad; i++) {
                //Log.d("ListSize: ", String.valueOf(allNewsList.size()) + " " + String.valueOf(newsList.size()));
                //Log.d("currentNewList: ",String.valueOf(currentNewsInList));
                newsList.add(allNewsList.get(newsList.size()));
                adapter.notifyDataSetChanged();
            }
            currentNewsInList += newsPerLoad;
        } else {
            int d = allNewsList.size() - currentNewsInList;
            //Toast.makeText(view.getContext(),"Loaded " + String.valueOf(allNewsList.size()), Toast.LENGTH_SHORT).show();
            for (int i = 0; i < d; i++) {
                newsList.add(allNewsList.get(newsList.size()));
                adapter.notifyDataSetChanged();
                currentNewsInList ++;
            }
        }
    }

//    private void getNewsList2() {
//        try {
//            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
//            try {
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject newsJson = jsonArray.getJSONObject(i);
//                    //TODO: GET COMPANY NAME, NOT USERNAME
//                    String companyName = newsJson.getString("namecompany");
//                    //TODO: get news title here
//                    String newsTitle = newsJson.getString("title");
//                    String newsTime = newsJson.getString("time");
//                    String positions = "Position(s): ";
//                    try {
//                        JSONArray positionList = newsJson.getJSONArray("listPosition");
//                        for (int j = 0; j < positionList.length() - 1; j++) {
//                            positions += positionList.getJSONObject(j).getString("position") + ", ";
//                        }
//                        positions += positionList.getJSONObject(positionList.length() - 1).getString("position");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String content = newsJson.getString("content");
//
//                    News news = new News((ImageView) view.findViewById(R.id.avatar), companyName, newsTitle, newsTime, positions, content);
//                    allNewsList.add(news);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    private boolean moreNews() {
        return allNewsList.size() > currentNewsInList;
    }

    private void showProgressDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }
    private void hideProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private String loadJSONFromAsset(){
        String json;
        try {
            InputStream is  = getActivity().getAssets().open("returnpostforstudent.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}