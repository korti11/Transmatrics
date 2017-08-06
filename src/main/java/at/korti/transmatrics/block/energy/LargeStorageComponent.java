package at.korti.transmatrics.block.energy;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MultiMachineBlock;
import at.korti.transmatrics.tileentity.energy.TileEntityLargeStorageComponent;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 06.08.2017.
 */
public class LargeStorageComponent extends MultiMachineBlock {

    public LargeStorageComponent() {
        super(Material.IRON, TransmatricsBlock.LARGE_STORAGE_COMPONENT.getRegName(),
                TileEntityLargeStorageComponent.class);
    }

}
