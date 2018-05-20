package ttcnpm.g24.tuyendung;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdapterJSON {
    public void parseJson() {

        // Response from API call
        String response = "[{\"id\":\"1\",\"title\":\"12 May to 30 Jun\"},\n" +
                "{\"id\":\"2\",\"title\":\"3 Jun to 20 Jun\"}]";

        try {
            JSONArray jsonArray = new JSONArray(response);

            // Get all jsonObject from jsonArray
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id = null, title = null;

                // Id
                if (jsonObject.has("id") && !jsonObject.isNull("id")) {
                    id = jsonObject.getString("id");
                }

                // Title
                if (jsonObject.has("title") && !jsonObject.isNull("title")) {
                    title = jsonObject.getString("title");
                }

                Log.d("SUCCESS", "JSON Object: " + "\nId: " + id
                        + "\nTitle: " + title);
            }
        } catch (JSONException e) {
            Log.e("FAILED", "Json parsing error: " + e.getMessage());
        }
    }
}
