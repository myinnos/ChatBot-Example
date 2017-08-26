package bike.rapido.driver.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bike.rapido.driver.AppBaseConstants;
import bike.rapido.driver.R;
import bike.rapido.driver.functions.RememberPreferences;
import bike.rapido.driver.functions.Translator;
import bike.rapido.driver.functions.TriggerNotification;
import bike.rapido.driver.holder.OptionsViewHolder;
import bike.rapido.driver.model.OptionsDataModel;

/**
 * Created by myinnos on 24/08/17.
 */

public class OptionsListAdapter extends RecyclerView.Adapter<OptionsViewHolder> {

    private Activity activity;
    private List<OptionsDataModel> optionsDataModelList = new ArrayList<OptionsDataModel>();

    public OptionsListAdapter(List<OptionsDataModel> optionsDataModelList, Activity activity) {
        this.activity = activity;
        this.optionsDataModelList = optionsDataModelList;
    }

    @Override
    public OptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_options, parent, false);

        return new OptionsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OptionsViewHolder holder, final int position) {

        if (optionsDataModelList.get(position).getSelected()) {
            holder.tvOption.setTextColor(activity.getResources().getColor(R.color.black));
        } else {
            holder.tvOption.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        }

        if (RememberPreferences.getLocalLanguage().equals(AppBaseConstants.DEFAULT_LANGUAGE)) {
            holder.tvOption.setText(optionsDataModelList.get(position).getMessage());
        } else {
            Translator.Translate(optionsDataModelList.get(position).getMessage(), holder.tvOption);
        }

        holder.cvOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(optionsDataModelList.get(position).getNext_question() != null) {
                    // triggering another user with notification
                    TriggerNotification.notify(activity, optionsDataModelList.get(position).getNext_question());
                }

                /*try {
                    OneSignal.postNotification(new JSONObject("{'contents': {'en':'Test Message'}, " +
                            "'included_segments': ['" + "All" + "']}"), new OneSignal.PostNotificationResponseHandler() {
                        @Override
                        public void onSuccess(JSONObject response) {

                            Log.d("TEXT", response.toString());
                        }

                        @Override
                        public void onFailure(JSONObject response) {
                            Log.d("TEXT", response.toString());
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                // setting selection value true
                optionsDataModelList.get(position).setSelected(true);

                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionsDataModelList.size();
    }
}
