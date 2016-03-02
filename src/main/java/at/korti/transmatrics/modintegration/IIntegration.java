package at.korti.transmatrics.modintegration;

/**
 * Created by Korti on 02.03.2016.
 */
public interface IIntegration {

    void preInit();

    void init();

    void postInit();

    void clientInit();

}
