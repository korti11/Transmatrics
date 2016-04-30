package at.korti.transmatrics.block;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import java.util.List;

/**
 * Created by Korti on 18.03.2016.
 */
public class OreBlock extends ModBlock {

    public static final PropertyEnum<OreType> TYPE = PropertyEnum.create("type", OreType.class);

    public OreBlock() {
        super(Material.ROCK, TransmatricsBlock.ORE_BLOCK.getRegName());

        this.setHarvestLevels();
        this.setHardness(5f);
    }

    private void setHarvestLevels() {
        for (OreType oreType : OreType.values()) {
            super.setHarvestLevel("pickaxe", oreType.harvestLevel, getDefaultState().withProperty(TYPE, oreType));
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).meta;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, getFromMeta(meta));
    }

    private OreType getFromMeta(int meta) {
        if (meta < 0 || meta > OreType.values().length) {
            meta = 0;
        }

        return OreType.values()[meta];
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (OreType oreType : OreType.values()) {
            list.add(new ItemStack(itemIn, 1, oreType.meta));
        }
    }

    public enum OreType implements IStringSerializable {
        copper(1),
        tin(1),
        silver(2),
        lead(2);

        public final int meta;
        public final int harvestLevel;

        OreType(int harvestLevel) {
            meta = ordinal();
            this.harvestLevel = harvestLevel;
        }

        @Override
        public String getName() {
            return this.toString();
        }
    }
}
