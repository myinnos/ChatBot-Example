package bike.rapido.passenger.model;

import java.util.List;

/**
 * Created by myinnos on 24/08/17.
 */

public class QuestionDataModel {

    String id;
    String question;
    List<OptionsDataModel> optionsDataModelList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<OptionsDataModel> getOptionsDataModelList() {
        return optionsDataModelList;
    }

    public void setOptionsDataModelList(List<OptionsDataModel> optionsDataModelList) {
        this.optionsDataModelList = optionsDataModelList;
    }
}
