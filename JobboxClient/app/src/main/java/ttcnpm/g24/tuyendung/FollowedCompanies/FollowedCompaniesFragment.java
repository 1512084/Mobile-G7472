package ttcnpm.g24.tuyendung.FollowedCompanies;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import ttcnpm.g24.tuyendung.AccountInfo;
import ttcnpm.g24.tuyendung.MySingleton;
import ttcnpm.g24.tuyendung.R;

public class FollowedCompaniesFragment extends Fragment {
    private List<FollowedCompany> followedCompanyList = new ArrayList<>();
    final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
    FollowedCompaniesAdapter followedCompaniesAdapter = new FollowedCompaniesAdapter(followedCompanyList);
    private String followedCompaniesUrl = "http://jws-app-mobile.a3c1.starter-us-west-1.openshiftapps.com/returnlistfollowing";
    private View view;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.followed_companies_fragment,container,false);
        final RecyclerView rvFollowedCompaniesList = view.findViewById(R.id.followed_cpn_list);

        rvFollowedCompaniesList.setLayoutManager(layoutManager);

        rvFollowedCompaniesList.setAdapter(followedCompaniesAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        getActivity().setTitle("Followed Companies");
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        getFollowedCompaniesList();
    }

    private void getFollowedCompaniesList(){
        showProgressDialog();

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("useridstudent", AccountInfo.accountInfo.username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(jsonObject);
        //Log.d("JSONArray",jsonArray.toString());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                followedCompaniesUrl,
                jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject followedCompanyJson = response.getJSONObject(i);
                                FollowedCompany followedCompany = new FollowedCompany((ImageView) view.findViewById(R.id.avatar),
                                        followedCompanyJson.getString("namecompany"),
                                        followedCompanyJson.getString("useridcompany"));
                                followedCompanyList.add(followedCompany);
                                followedCompaniesAdapter.notifyItemInserted(i);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        Toast.makeText(view.getContext(),"Error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        MySingleton.getmInstance(view.getContext()).addToRequestque(jsonArrayRequest);

        hideProgressDialog();
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
}
