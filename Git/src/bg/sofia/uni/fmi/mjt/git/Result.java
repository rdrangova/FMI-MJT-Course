package bg.sofia.uni.fmi.mjt.git;

public class Result {


    private boolean isSuccessful;
    private String message;

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }
}
