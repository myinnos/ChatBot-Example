package bike.rapido.passenger.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bike.rapido.passenger.AppBaseConstants;
import bike.rapido.passenger.R;
import bike.rapido.passenger.functions.RememberPreferences;
import bike.rapido.passenger.functions.Translator;
import bike.rapido.passenger.functions.TriggerNotification;
import bike.rapido.passenger.holder.OptionsViewHolder;
import bike.rapido.passenger.model.OptionsDataModel;

/**
 * Created by myinnos on 24/08/17.
 */

public class OptionsListAdapter extends RecyclerView.Adapter<OptionsViewHolder> {

    private Activity activity;
    private List<OptionsDataModel> optionsDataModelList;

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

                // triggering another user with notification
                TriggerNotification.notify(activity, optionsDataModelList.get(position).getNext_question());

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
