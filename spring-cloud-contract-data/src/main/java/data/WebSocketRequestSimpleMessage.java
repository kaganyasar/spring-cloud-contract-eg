package data;

public class WebSocketRequestSimpleMessage {
    private String message;
    private Person messagedByPerson;

    public WebSocketRequestSimpleMessage() {
    }

    public WebSocketRequestSimpleMessage(String message, Person messagedByPerson) {
        this.message = message;
        this.messagedByPerson = messagedByPerson;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Person getMessagedByPerson() {
        return messagedByPerson;
    }

    public void setMessagedByPerson(Person messagedByPerson) {
        this.messagedByPerson = messagedByPerson;
    }
}
