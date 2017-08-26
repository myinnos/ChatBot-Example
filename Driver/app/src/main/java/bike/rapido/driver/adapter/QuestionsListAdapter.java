package bike.rapido.driver.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bike.rapido.driver.AppBaseConstants;
import bike.rapido.driver.R;
import bike.rapido.driver.functions.RememberPreferences;
import bike.rapido.driver.functions.Translator;
import bike.rapido.driver.holder.QuestionsViewHolder;
import bike.rapido.driver.model.QuestionDataModel;

/**
 * Created by myinnos on 24/08/17.
 */

public class QuestionsListAdapter extends RecyclerView.Adapter<QuestionsViewHolder> {

    private Activity activity;
    private List<QuestionDataModel> questionDataModelList;

    public QuestionsListAdapter(List<QuestionDataModel> questionDataModelList, Activity activity) {
        this.activity = activity;
        this.questionDataModelList = questionDataModelList;
    }

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_questions, parent, false);

        return new QuestionsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final QuestionsViewHolder holder, final int position) {

        if (RememberPreferences.getLocalLanguage().equals(AppBaseConstants.DEFAULT_LANGUAGE)) {
            holder.tvQuestion.setText(questionDataModelList.get(position).getQuestion());
        } else {
            Translator.Translate(questionDataModelList.get(position).getQuestion(), holder.tvQuestion);
        }

        LinearLayoutManager horizontalLinearLayout
                = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        holder.rvOptionsData.setLayoutManager(horizontalLinearLayout);
        holder.rvOptionsData.setAdapter(
                new OptionsListAdapter(questionDataModelList.get(position).getOptionsDataModelList(),
                        activity));
    }

    @Override
    public int getItemCount() {
        return questionDataModelList.size();
    }
}
