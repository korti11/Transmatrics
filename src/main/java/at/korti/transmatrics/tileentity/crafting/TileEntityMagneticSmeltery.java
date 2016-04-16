package at.korti.transmatrics.tileentity.crafting;

import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.TransmatricsTileEntity;
import at.korti.transmatrics.registry.crafting.MagneticSmelteryCraftingRegistry;
import at.korti.transmatrics.tileentity.TileEntityFluidCraftingMachine;

/**
 * Created by Korti on 31.03.2016.
 */
public class TileEntityMagneticSmeltery extends TileEntityFluidCraftingMachine {

    public TileEntityMagneticSmeltery() {
        super(Energy.MAGNETIC_SMELTERY_CAPACITY, Energy.MAGNETIC_SMELTERY_RECEIVE, Energy.MAGNETIC_SMELTERY_ENERGY_USE,
                TransmatricsTileEntity.MAGNETIC_SMELTERY.getRegName(), MagneticSmelteryCraftingRegistry.getInstance());
    }

    @Override
    protected boolean isFluidInput() {
        return false;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 6:
                return tanks[0].getFluidAmount();
            case 7:
                return tanks[0].getCapacity();
        }
        return super.getField(id);
    }

    @Override
    public void setField(int id, int value) {
        super.setField(id, value);
        switch (id) {
            case 6:
                if(tanks[0].getFluid() != null) {
                    tanks[0].getFluid().amount = value;
                }
                break;
            case 7:
                tanks[0].setCapacity(value);
                break;
        }
    }

    @Override
    public int getFieldCount() {
        return 8;
    }
}
