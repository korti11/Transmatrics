package at.korti.transmatrics.api.network;

/**
 * Created by Korti on 06.03.2016.
 */
public class OperationMessage implements IOperationMessage {

    private String message;
    private boolean successful;

    public OperationMessage(String message, boolean successful) {
        this.message = message;
        this.successful = successful;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean isSuccessful() {
        return successful;
    }
}
