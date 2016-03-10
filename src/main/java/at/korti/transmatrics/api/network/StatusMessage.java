package at.korti.transmatrics.api.network;

import at.korti.transmatrics.util.helper.TextHelper;

/**
 * Created by Korti on 06.03.2016.
 */
public class StatusMessage implements IStatusMessage {

    private String unlocMessage;
    private Object[] messageArgs;
    private boolean successful;

    public StatusMessage(boolean successful, String unlocMessage, Object... messageArgs) {
        this.unlocMessage = unlocMessage;
        this.messageArgs = messageArgs;
        this.successful = successful;
    }

    @Override
    public String getMessage() {
        return TextHelper.localize(unlocMessage, messageArgs);
    }

    @Override
    public String getUnlocalizedMessage() {
        return unlocMessage;
    }

    @Override
    public boolean isSuccessful() {
        return successful;
    }
}
