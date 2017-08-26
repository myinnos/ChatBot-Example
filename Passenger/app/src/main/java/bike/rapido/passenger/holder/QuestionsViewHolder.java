package bike.rapido.passenger.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import bike.rapido.passenger.R;

/**
 * Created by myinnos on 24/08/17.
 */

public class QuestionsViewHolder extends RecyclerView.ViewHolder {

    public TextView tvQuestion;
    public RecyclerView rvOptionsData;

    public QuestionsViewHolder(View itemView) {
        super(itemView);
        tvQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
        rvOptionsData = (RecyclerView) itemView.findViewById(R.id.rvOptionsData);
    }
}

