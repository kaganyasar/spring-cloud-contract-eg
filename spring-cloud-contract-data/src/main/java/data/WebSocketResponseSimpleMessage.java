package data;

public class WebSocketResponseSimpleMessage {
    private String message;
    private Person messagedToPerson;
    private Person messagedByPerson;

    public WebSocketResponseSimpleMessage() {
    }

    public WebSocketResponseSimpleMessage(String message, Person messagedToPerson, Person messagedByPerson) {
        this.message = message;
        this.messagedToPerson = messagedToPerson;
        this.messagedByPerson = messagedByPerson;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Person getMessagedToPerson() {
        return messagedToPerson;
    }

    public void setMessagedToPerson(Person messagedToPerson) {
        this.messagedToPerson = messagedToPerson;
    }

    public Person getMessagedByPerson() {
        return messagedByPerson;
    }

    public void setMessagedByPerson(Person messagedByPerson) {
        this.messagedByPerson = messagedByPerson;
    }
}
