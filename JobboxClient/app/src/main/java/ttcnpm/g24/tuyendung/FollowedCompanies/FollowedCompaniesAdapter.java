package ttcnpm.g24.tuyendung.FollowedCompanies;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ttcnpm.g24.tuyendung.AccountInfo;
import ttcnpm.g24.tuyendung.MySingleton;
import ttcnpm.g24.tuyendung.R;

public class FollowedCompaniesAdapter extends RecyclerView.Adapter<FollowedCompaniesAdapter.FollowedCompanyViewHolder> {
    private List<FollowedCompany> followedCompanyList;
    private String unfollowUrl = "http://jws-app-mobile.a3c1.starter-us-west-1.openshiftapps.com/follow";

    private ProgressDialog progressDialog;

    FollowedCompaniesAdapter (List<FollowedCompany> followedCompanyList){
        this.followedCompanyList = followedCompanyList;
    }

    public class FollowedCompanyViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView company_name;
        private Button delete_company;

        public FollowedCompanyViewHolder(View view) {
            super(view);
            avatar = view.findViewById(R.id.followed_cpn_avatar);
            company_name = view.findViewById(R.id.followed_cpn_name);
            delete_company = view.findViewById(R.id.followed_cpn_delete);

        }
    }

    @NonNull
    @Override
    public FollowedCompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.followed_company_content,parent,false);
        return new FollowedCompanyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FollowedCompanyViewHolder holder, final int position) {
        final FollowedCompany followedCompany = followedCompanyList.get(position);
        final FollowedCompanyViewHolder followedCompanyViewHolder = (FollowedCompanyViewHolder)holder;
        followedCompanyViewHolder.avatar.setImageResource(R.drawable.box_green);
        followedCompanyViewHolder.company_name.setText(followedCompany.getCompany_name());
        //UNFOLLOW BUTTON HANDLING
        followedCompanyViewHolder.delete_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                confirmDialog(v,followedCompany,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return followedCompanyList.size();
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
    private void confirmDialog(final View v, final FollowedCompany followedCompany, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog = new ProgressDialog(v.getContext());
                        progressDialog.setMessage("Loading...");
                        progressDialog.setCancelable(false);

                        JSONObject jsonRequest = new JSONObject();
                        try {
                            jsonRequest.put("useridstudent", AccountInfo.accountInfo.username);
                            jsonRequest.put("useridcompany",followedCompany.getCompany_username().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("AAA",jsonRequest.toString());
                        showProgressDialog();
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                unfollowUrl,
                                jsonRequest,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        followedCompanyList.remove(position);

                                        notifyDataSetChanged();
                                        hideProgressDialog();
                                        Toast.makeText(v.getContext(),"Done",Toast.LENGTH_LONG).show();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        hideProgressDialog();
                                        Toast.makeText(v.getContext(),"Error Listener",Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                        MySingleton.getmInstance(v.getContext()).addToRequestque(jsonObjectRequest);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}
