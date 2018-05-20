package ttcnpm.g24.tuyendung.Searching;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import ttcnpm.g24.tuyendung.AccountInfo;
import ttcnpm.g24.tuyendung.MySingleton;
import ttcnpm.g24.tuyendung.R;

public class SearchingFragment extends Fragment {
    ///
    private String url="";
    String searchBy="Name";
    private RadioButton radioButton;
    private Spinner spinnerSelected;
    private EditText txtSelected;
    private RadioButton radioButtonStd;
    //private TextView txtFollow;



    private List<SearchingSupport> allNewsList = new ArrayList<>();
    private RecyclerView rvNewsList;

    private List<SearchingSupport> newsList = new ArrayList<>();
    private SearchingAdapter adapter = new SearchingAdapter(newsList);

    private LinearLayoutManager layoutManager;
    private Parcelable state;

    private int prevTotal = 0, visibleThreshold = 5;
    private boolean loading = true;
    private int firstVisibleItemPosition, visibleItemCount, totalItemCount;
    private int newsPerLoad = 5;
    private int currentNewsInList = 0;

    //TODO change localhost to something

    private View view;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //company_news_feed_fragment
        view = inflater.inflate(R.layout.search_enterprise_fragment,container,false);
        rvNewsList = view.findViewById(R.id.cpn_news_list);
        //txtFollow= view.findViewById(R.id.txtFollow);
        /////////

        spinnerSelected=(Spinner) view.findViewById(R.id.spinnerSearch);
        ArrayAdapter<CharSequence> adapterArr = ArrayAdapter.createFromResource(view.getContext(),
                R.array.kind_search, android.R.layout.simple_spinner_item);
        adapterArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelected.setAdapter(adapterArr);

        txtSelected=(EditText) view.findViewById(R.id.txtSearch);
        radioButton= (RadioButton) view.findViewById(R.id.pickcpn);
        radioButtonStd= (RadioButton) view.findViewById(R.id.pickstd);
        //////////
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
            //loadNews();
        }


        rvNewsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = rvNewsList.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > prevTotal) {
                        loading = false;
                        prevTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - layoutManager.findLastVisibleItemPosition() ==1)){
                    //end has been reached
                    //load more News here
                    if (moreNews()) loadNews();
                    loading = true;
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Searching");

        this.view = view;

        addEvents();

    }

    @Override
    public void onPause() {
        state = rvNewsList.getLayoutManager().onSaveInstanceState();
        super.onPause();
    }



    private void addEvents() {
        txtSelected.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                allNewsList.clear();
                newsList.clear();
                adapter.notifyDataSetChanged();
                currentNewsInList = 0;
                prevTotal = 0;
                loading = true;
                getNewsList();
                loadNews();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }
        );
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBy="Name";
                updateSpinner();

                allNewsList.clear();
                newsList.clear();
                adapter.notifyDataSetChanged();
                currentNewsInList = 0;
                prevTotal = 0;
                loading = true;
                getNewsList();
                loadNews();
            }
        });
        radioButtonStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBy="Major";
                updateSpinner();
                allNewsList.clear();
                newsList.clear();
                adapter.notifyDataSetChanged();
                currentNewsInList = 0;
                prevTotal = 0;
                loading = true;
                getNewsList();
                loadNews();
            }
        });
        updateSpinner();

    }
    public void updateSpinner(){

        final ArrayList<String> itemSpinner=new ArrayList<String>();
        if (radioButton.isChecked()){
            itemSpinner.add("Name");
            itemSpinner.add("Address");
            itemSpinner.add("Kind of business");
        }else{
            itemSpinner.add("Major");
            itemSpinner.add("University");
            itemSpinner.add("Address");
        }

        final ArrayAdapter arrayAdapter=new ArrayAdapter(view.getContext(),android.R.layout.simple_spinner_item,itemSpinner);
        spinnerSelected.setAdapter(arrayAdapter);

        spinnerSelected.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchBy= itemSpinner.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /////////////

    private void getNewsList() {
        showProgressDialog();

        //create requestJson
        JSONArray jsonArray = new JSONArray();
        JSONObject requestData = new JSONObject();


        if (radioButton.isChecked()){
            url= "http://jws-app-mobile.a3c1.starter-us-west-1.openshiftapps.com/companysearch";
            try {
                requestData.put("id", AccountInfo.accountInfo.username);
                if (txtSelected.getText().toString().equals("")){
                    requestData.put("fullname", "%");
                    requestData.put("address","%");
                    requestData.put("kindofbusiness","%");
                }
                else {
                    switch (searchBy) {
                        case "Name":
                            requestData.put("fullname", txtSelected.getText().toString());
                            requestData.put("address", "%");
                            requestData.put("kindofbusiness", "%");

                            break;
                        case "Address":
                            requestData.put("fullname", "%");
                            requestData.put("address", txtSelected.getText().toString());
                            requestData.put("kindofbusiness", "%");
                            break;
                        case "Kind of business":
                            requestData.put("fullname", "%");
                            requestData.put("address", "%");
                            requestData.put("kindofbusiness", txtSelected.getText().toString());
                            break;
                    }
                    if (txtSelected.getText().toString().equals("")) {
                        requestData.put("fullname", "%");
                        requestData.put("address", "%");
                        requestData.put("kindofbusiness", "%");
                    }
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Log.d("createJSONcom",requestData.toString());
        } else {
            url= "http://jws-app-mobile.a3c1.starter-us-west-1.openshiftapps.com/studentsearch";
            try {
                requestData.put("id", AccountInfo.accountInfo.username);
                if (txtSelected.getText().toString().equals("")){
                    requestData.put("major", "%");
                    requestData.put("address","%");
                    requestData.put("university","%");
                }
                else {

                    switch (searchBy) {
                        case "Major":
                            requestData.put("major", txtSelected.getText().toString());
                            requestData.put("address", "%");
                            requestData.put("university", "%");
                            break;
                        case "University":
                            requestData.put("university", txtSelected.getText().toString());
                            requestData.put("major", "%");
                            requestData.put("address", "%");
                            break;
                        case "Address":
                            requestData.put("address", txtSelected.getText().toString());
                            requestData.put("major", "%");
                            requestData.put("university", "%");
                            break;
                    }
                }



            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Log.d("createJSONstd",requestData.toString());
        }


        jsonArray.put(requestData);
        //add Request to RequestQueue
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                url,
                jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i=0; i < response.length(); i++) {
                                JSONObject newsJson = response.getJSONObject(i);
                                //TODO: GET COMPANY NAME, NOT USERNAME
                                String fullname = newsJson.getString("fullname");
                                //TODO: news title here
                                String id = newsJson.getString("id");
                                String add= newsJson.getString("address");
                                String isFollow="";

                                String decription;
                                String type;
                                if (radioButton.isChecked()){
                                    String kind=newsJson.getString("kindofbusiness");
                                    decription = "Field: " + kind + "\n" + "Adr: " + add;
                                    isFollow=newsJson.getString("follow");
                                    type="company";
                                }
                                else {
                                    String maj = newsJson.getString("major");
                                    String uni= newsJson.getString("university");
                                    decription= "University: "+ uni+"\n"+"Major: "+maj;

                                    type="student";
                                }




                                //String hexStringAvatr = newsJson.getString("avatar");
                                String hexStringAvatr = "";
                                if(hexStringAvatr.equals("")) {

                                    SearchingSupport news = new SearchingSupport(null, fullname, decription, id,type,isFollow);
                                    allNewsList.add(news);
                                }else{
                                    //Log.d("myavatar",newsJson.getString("fullname") + ": " + "has avatar");
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
//                                    ImageView imageView = (ImageView) view.findViewById(R.id.avatar);
//                                    imageView.setImageBitmap(bitmap);

                                    SearchingSupport news = new SearchingSupport(bitmap, fullname, decription, id,type,isFollow);
                                    allNewsList.add(news);
                                }
                            }

                            if (allNewsList.size()>= newsPerLoad) {
                                //Toast.makeText(view.getContext(),"Loaded " + String.valueOf(allNewsList.size()), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(view.getContext(),"Loaded " + String.valueOf(newsPerLoad), Toast.LENGTH_SHORT).show();
            for (int i = 0; i < newsPerLoad; i++) {
                newsList.add(allNewsList.get(newsList.size()));
                adapter.notifyDataSetChanged();
            }
            currentNewsInList += newsPerLoad;
        } else {
            int d = allNewsList.size() - currentNewsInList;
            Toast.makeText(view.getContext(),"Loaded " + String.valueOf(d), Toast.LENGTH_SHORT).show();
            for (int i = 0; i < d; i++) {
                newsList.add(allNewsList.get(newsList.size()));
                adapter.notifyDataSetChanged();
                currentNewsInList ++;
            }
        }
    }

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
        String json=null;
        try {
            InputStream is  = getActivity().getAssets().open("returnpostforcompany.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
