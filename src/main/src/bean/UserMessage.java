package bean;

public class UserMessage {

    private int id;
    private String Message;

    public UserMessage(int id, String message) {
        this.id = id;
        Message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
