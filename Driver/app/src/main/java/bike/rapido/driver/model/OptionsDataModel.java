package bike.rapido.driver.model;

/**
 * Created by myinnos on 24/08/17.
 */

public class OptionsDataModel {

    String message;
    String next_question;
    Boolean isSelected;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNext_question() {
        return next_question;
    }

    public void setNext_question(String next_question) {
        this.next_question = next_question;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
