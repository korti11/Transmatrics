package at.korti.transmatrics.api.network;

/**
 * Created by Korti on 06.03.2016.
 */
public interface IStatusMessage {

    /**
     * @return Get a message about what went wrong or just the operation was successful.
     */
    String getMessage();

    /**
     * @return Get the unlocalized message.
     */
    String getUnlocalizedMessage();

    /**
     * @return If the operation was successful.
     */
    boolean isSuccessful();

}
