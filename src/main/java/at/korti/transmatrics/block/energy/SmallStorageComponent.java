package at.korti.transmatrics.block.energy;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MultiMachineBlock;
import at.korti.transmatrics.tileentity.energy.TileEntitySmallStorageComponent;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 05.08.2017.
 */
public class SmallStorageComponent extends MultiMachineBlock {

    public SmallStorageComponent() {
        super(Material.IRON, TransmatricsBlock.SMALL_STORAGE_COMPONENT.getRegName(),
                TileEntitySmallStorageComponent.class);
    }
}
