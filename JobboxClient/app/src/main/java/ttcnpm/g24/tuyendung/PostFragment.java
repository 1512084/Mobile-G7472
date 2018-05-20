package ttcnpm.g24.tuyendung;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PostFragment extends Fragment {
    private String json_url="http://jws-app-mobile.a3c1.starter-us-west-1.openshiftapps.com/PostServer";

    private EditText txtTitle;
    private EditText txtContent;
    private EditText txtTags;
    private EditText txtPosition;
    private Button btnPost;

    private View contextView;
    // Progress dialog
    private ProgressDialog pDialog;

    private JSONObject requestData = new JSONObject();

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.post_fragment,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contextView=view;
        pDialog = new ProgressDialog(view.getContext());
        pDialog.setTitle("Posting...");
        pDialog.setMessage("Just give me a second.");
        getActivity().setTitle("Post");


        addController();
        addEvent();
    }

    private void addController() {
        btnPost=(Button) contextView.findViewById(R.id.btnPost);
        txtTitle=(EditText) contextView.findViewById(R.id.txtTitle);
        txtContent=(EditText) contextView.findViewById(R.id.txtContent);
        txtTags=(EditText) contextView.findViewById(R.id.txtTags);
        txtPosition=(EditText) contextView.findViewById(R.id.txtPosition);
    }


    public void createJSON() {
        try {
            requestData.put("title", txtTitle.getText().toString());
            requestData.put("content", txtContent.getText().toString());
            requestData.put("company",AccountInfo.accountInfo.username);
            JSONArray tags = new JSONArray();
            tags.put(txtTags.getText().toString());
            requestData.put("tags",tags.toString());

            JSONArray position = new JSONArray();
            position.put(txtPosition.getText().toString());
            requestData.put("positions",position.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.d("createJSON",requestData.toString());


        /*JSONArray jsonArray = new JSONArray();

        jsonArray.put(requestData);

        JSONObject postObj = new JSONObject();
        */
//        try {
//            requestData.put("Post", obj);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        //String jsonStr = postObj.toString();
        //System.out.println("jsonString: "+jsonStr);
    };

    private void addEvent() {

       btnPost.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showpDialog();

                if (txtTitle.getText().toString().equals("")||txtContent.getText().toString().equals("")||txtPosition.getText().toString().equals("")||txtTags.getText().toString().equals(""))
                {}else {
                    createJSON();

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url, requestData,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {

                                        hidepDialog();
                                        Toast.makeText(contextView.getContext(), response.getString("STATUS"), Toast.LENGTH_SHORT).show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        hidepDialog();
                                        Toast.makeText(contextView.getContext(), "Something went wrong json", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hidepDialog();
                            Toast.makeText(contextView.getContext(), "Something went wrong on response", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    });


                    MySingleton.getmInstance(contextView.getContext()).addToRequestque(jsonObjectRequest);
                }
           }
       });
    }

}
