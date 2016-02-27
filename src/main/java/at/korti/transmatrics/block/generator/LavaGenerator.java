package at.korti.transmatrics.block.generator;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.ModBlockContainer;
import at.korti.transmatrics.tileentity.TileEntityFluidGenerator;
import at.korti.transmatrics.tileentity.TileEntityLavaGenerator;
import at.korti.transmatrics.util.helper.InventoryHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

/**
 * Created by Korti on 27.02.2016.
 */
public class LavaGenerator extends ModBlockContainer {

    public LavaGenerator() {
        super(Material.iron, TransmatricsBlock.LAVA_GENERATOR.getRegName(), TileEntityLavaGenerator.class);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ)) {
            return true;
        }

        ItemStack currentStack = playerIn.getHeldItem();
        if (currentStack != null) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof TileEntityFluidGenerator) {
                TileEntityFluidGenerator fluidGenerator = (TileEntityFluidGenerator) tile;
                if (FluidContainerRegistry.isContainer(currentStack)) {
                    FluidStack fluidStack = FluidContainerRegistry.getFluidForFilledItem(currentStack);
                    if (fluidStack != null && fluidGenerator.canFill(side, fluidStack.getFluid())) {
                        int amount = fluidGenerator.fill(side, fluidStack, true);
                        if (amount != 0 && !playerIn.capabilities.isCreativeMode) {
                            if (currentStack.stackSize > 1) {
                                if (!playerIn.inventory.addItemStackToInventory(FluidContainerRegistry.drainFluidContainer(currentStack))) {
                                    playerIn.dropPlayerItemWithRandomChoice(FluidContainerRegistry.drainFluidContainer(currentStack), false);
                                }
                                playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, InventoryHelper.consumeStack(currentStack));
                            }else {
                                playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, FluidContainerRegistry.drainFluidContainer(currentStack));
                            }
                        }

                        return true;
                    } else {
                        FluidStack available = fluidGenerator.getTankInfo(side)[0].fluid;

                        if (available != null) {
                            ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, currentStack);
                            fluidStack = FluidContainerRegistry.getFluidForFilledItem(filled);

                            if (fluidStack != null) {
                                if (!playerIn.capabilities.isCreativeMode) {
                                    if (currentStack.stackSize > 1) {
                                        if (!playerIn.inventory.addItemStackToInventory(filled)) {
                                            return false;
                                        } else {
                                            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, InventoryHelper.consumeStack(currentStack));
                                        }
                                    }else {
                                        playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, InventoryHelper.consumeStack(currentStack));
                                        playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, filled);
                                    }
                                }
                                fluidGenerator.drain(side, fluidStack.amount, true);
                                return true;
                            }
                        }
                    }
                } else if (currentStack.getItem() instanceof IFluidContainerItem) {
                    if (currentStack.stackSize != 1) {
                        return false;
                    }

                    if (!worldIn.isRemote) {
                        IFluidContainerItem containerItem = (IFluidContainerItem) currentStack.getItem();
                        FluidStack fluidStack = containerItem.getFluid(currentStack);
                        FluidStack generatorFluidStack = fluidGenerator.getTankInfo(side)[0].fluid;
                        boolean drain = fluidStack == null || fluidStack.amount == 0;
                        boolean fill = generatorFluidStack == null || generatorFluidStack.amount == 0;
                        if (drain || !playerIn.isSneaking()) {
                            fluidStack = fluidGenerator.drain(side, 1000, false);
                            int amountToFill = containerItem.fill(currentStack, fluidStack, true);
                            fluidGenerator.drain(side, amountToFill, true);
                        } else if (fill || playerIn.isSneaking()) {
                            if (fluidStack.amount > 0) {
                                int amount = fluidGenerator.fill(side, fluidStack, false);
                                fluidGenerator.fill(side, containerItem.drain(currentStack, amount, true), true);
                            }
                        }
                    }

                    return true;
                }
            }
        }
        return false;
    }
}
