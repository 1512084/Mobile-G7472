package ttcnpm.g24.tuyendung.Searching;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import ttcnpm.g24.tuyendung.AccountInfo;
import ttcnpm.g24.tuyendung.MySingleton;
import ttcnpm.g24.tuyendung.Newsfeed.CompanyNewsFeedFragment;
import ttcnpm.g24.tuyendung.R;


//NewsfeedAdapter
public class SearchingAdapter extends RecyclerView.Adapter<SearchingAdapter.NewsViewHolder> {
    private List<SearchingSupport> newsList;
    private TextView txtV;
    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView fullname, detail;
        private LinearLayout item;
        private TextView txtFollow;

        public NewsViewHolder(View view) {
            super(view);
            avatar = view.findViewById(R.id.avatar);
            fullname = view.findViewById(R.id.company_name);
            detail = view.findViewById(R.id.news_title);
            item = view.findViewById(R.id.newslist_item);
            txtFollow= view.findViewById(R.id.txtFollow);
            txtV=txtFollow;
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
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        final SearchingSupport news = newsList.get(position);
        //TODO: setting avatar to ViewHolder
        //this is only default avatar
        if(news.getAvatar() == null){
            holder.avatar.setImageResource(R.drawable.box_green);
        }else{
            holder.avatar.setImageBitmap(news.getAvatar());
        }
        holder.fullname.setText(news.getFullname());
        holder.detail.setText(news.getDetail());

        holder.txtFollow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                txtV=holder.txtFollow;
                makeAReq(news.getId(),v);

                return false;
            }
        });

        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("news",new SerializableNews(
                        news.getId(),
                        news.getType()
                ));

                if (news.getType()=="company"){
                    Fragment fragment = new CompanySearching();
                    fragment.setArguments(args);
                    AppCompatActivity activity = (AppCompatActivity)v.getContext();
                    if(AccountInfo.accountInfo.type == "S")
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.contain_student,fragment).addToBackStack(null).commit();
                    else{
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.contain_company,fragment).addToBackStack(null).commit();
                    }
                }
            }
        });
        Log.d("isFollow",AccountInfo.accountInfo.type.equals("C")+" "+news.getIsFollow());
        if (news.getIsFollow().equals("")||(AccountInfo.accountInfo.type.equals("C"))){
            holder.txtFollow.setVisibility(View.INVISIBLE);
        } else
        if (news.getIsFollow().equals("yes")){
            holder.txtFollow.setVisibility(View.VISIBLE);
            holder.txtFollow.setText("Followed");
        }
        else if (news.getIsFollow().equals("no")){
            holder.txtFollow.setVisibility(View.VISIBLE);
            holder.txtFollow.setText("Follow");
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }
    private void makeAReq(String id,final View v){
        String url="http://jws-app-mobile.a3c1.starter-us-west-1.openshiftapps.com/FollowServer";
        // final String finalUsername = username;
        //final String finalPassword = password;
        final JSONObject json = new JSONObject();
        try {
            json.put("useridstudent", AccountInfo.accountInfo.username);
            json.put("useridcompany",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(v.getContext(),"Success",Toast.LENGTH_SHORT).show();
                if (txtV.getText().equals("Follow")){
                    txtV.setText("Followed");

                }
                else txtV.setText("Follow");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        });
        MySingleton.getmInstance(v.getContext()).addToRequestque(request);

    }
    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class SerializableNews implements Serializable {
        private String cpnID;
        private String type;

        public SerializableNews(String cpnID,String type){
            this.cpnID = cpnID;
            this.type=type;
        }

        public String getCpnID() {
            return cpnID;
        }

        public void setCpnID(String cpnID) {
            this.cpnID = cpnID;
        }
        public  String getType(){
            return type;
        }
    }
}

