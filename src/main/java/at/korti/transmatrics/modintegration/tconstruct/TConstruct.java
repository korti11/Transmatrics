package at.korti.transmatrics.modintegration.tconstruct;

import at.korti.transmatrics.modintegration.IIntegration;
import at.korti.transmatrics.modintegration.tconstruct.helper.CraftingCrossOverHelper;

/**
 * Created by Korti on 11.05.2016.
 */
public class TConstruct implements IIntegration {
    @Override
    public void preInit() {

    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {
        CraftingCrossOverHelper.loadSmelteryCrossOver();
    }

    @Override
    public void clientInit() {

    }
}
