package bike.rapido.passenger.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bike.rapido.passenger.AppBaseConstants;
import bike.rapido.passenger.R;
import bike.rapido.passenger.adapter.QuestionsListAdapter;
import bike.rapido.passenger.functions.RememberPreferences;
import bike.rapido.passenger.model.OptionsDataModel;
import bike.rapido.passenger.model.QuestionDataModel;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Map<String, String> languageMap;
    private Spinner spinner;
    private RecyclerView rvChatData;
    private RelativeLayout rlLoader;

    private QuestionsListAdapter questionsListAdapter;
    private List<QuestionDataModel> questionDataModelList = new ArrayList<QuestionDataModel>();
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        spinner = (Spinner) findViewById(R.id.spinner);
        rvChatData = (RecyclerView) findViewById(R.id.rvChatData);
        rlLoader = (RelativeLayout) findViewById(R.id.rlLoader);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        // Hashmap values
        languageMap = new HashMap<String, String>();
        languageMap.put("English", "en");
        languageMap.put("Telugu", "te");
        languageMap.put("Kannada", "kn");
        languageMap.put("Tamil", "ta");
        languageMap.put("Hindi", "hi");

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        for (Map.Entry<String, String> entry : languageMap.entrySet()) {
            categories.add(entry.getKey());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        // Set language based on shared preferences
        String DEFAULT_LANGUAGE = RememberPreferences.getString(AppBaseConstants.LANGUAGE_SELECTION,
                AppBaseConstants.DEFAULT_LANGUAGE);
        for (Map.Entry<String, String> entry : languageMap.entrySet()) {
            if (entry.getValue().equals(DEFAULT_LANGUAGE)) {
                spinner.setSelection(getIndex(spinner, entry.getKey()));
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        rvChatData.setLayoutManager(layoutManager);
        rvChatData.setHasFixedSize(true);
        questionsListAdapter = new QuestionsListAdapter(questionDataModelList, this);
        rvChatData.setAdapter(questionsListAdapter);

        // getting json data
        sendRequest();
    }

    public void sendRequest() {

        rlLoader.setVisibility(View.VISIBLE);

        JsonArrayRequest request = new JsonArrayRequest(AppBaseConstants.JSON_URL,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        // storing json data locally
                        RememberPreferences.putString(AppBaseConstants.CHAT_JSON_DATA, jsonArray.toString());

                        questionsListAdapter.notifyDataSetChanged();
                        rlLoader.setVisibility(View.GONE);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        rlLoader.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Unable to fetch data: " +
                                volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        RememberPreferences.putString(AppBaseConstants.LANGUAGE_SELECTION, languageMap.get(item));
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }

    public void setDataFromNotification(final String trigger) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String JSON_DATA = RememberPreferences.getString(AppBaseConstants.CHAT_JSON_DATA, null);

                try {
                    JSONArray jArray = new JSONArray(JSON_DATA);

                    for (int j = 1; j < jArray.length(); j++) {
                        JSONObject jsonObject = jArray.getJSONObject(j);

                        if (jsonObject.getString("id").equals(trigger)) {
                            QuestionDataModel questionDataModel = new QuestionDataModel();
                            questionDataModel.setQuestion(jsonObject.getString("question"));

                            JSONArray jsonArray1 = jsonObject.getJSONArray("options");

                            List<OptionsDataModel> optionsDataModelList = new ArrayList<OptionsDataModel>();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                OptionsDataModel optionsDataModel = new OptionsDataModel();
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                optionsDataModel.setMessage(jsonObject1.getString("string"));
                                optionsDataModel.setNext_question(jsonObject1.getString("next_question"));
                                optionsDataModel.setSelected(false);
                                optionsDataModelList.add(optionsDataModel);
                            }

                            questionDataModel.setOptionsDataModelList(optionsDataModelList);
                            questionDataModelList.add(questionDataModel);
                        }

                    }

                    questionsListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

}
