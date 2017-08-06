package at.korti.transmatrics.block.energy;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.MultiMachineBlock;
import at.korti.transmatrics.tileentity.energy.TileEntityMediumStorageComponent;
import net.minecraft.block.material.Material;

/**
 * Created by Korti on 06.08.2017.
 */
public class MediumStorageComponent extends MultiMachineBlock {

    public MediumStorageComponent() {
        super(Material.IRON, TransmatricsBlock.MEDIUM_STORAGE_COMPONENT.getRegName(),
                TileEntityMediumStorageComponent.class);
    }

}
