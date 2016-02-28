package at.korti.transmatrics.block.generator;

import at.korti.transmatrics.api.Constants.TransmatricsBlock;
import at.korti.transmatrics.block.ActiveMachineBlock;
import at.korti.transmatrics.block.MachineBlock;
import at.korti.transmatrics.tileentity.TileEntityFluidGenerator;
import at.korti.transmatrics.tileentity.TileEntityLavaGenerator;
import at.korti.transmatrics.util.helper.ItemStackHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Created by Korti on 27.02.2016.
 */
public class LavaGenerator extends ActiveMachineBlock{

    public LavaGenerator() {
        super(Material.iron, TransmatricsBlock.LAVA_GENERATOR.getRegName(), TileEntityLavaGenerator.class);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (isActive(worldIn, pos)) {
            double d0 = (double) pos.getX() + 0.5;
            double d1 = (double) pos.getY() + rand.nextDouble() * 6 / 16;
            double d2 = (double) pos.getZ() + 0.5;

            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1 + 1, d2, 0, 0, 0);
        }
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
                                playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, ItemStackHelper.consumeStack(currentStack));
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
                                            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, ItemStackHelper.consumeStack(currentStack));
                                        }
                                    }else {
                                        playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, ItemStackHelper.consumeStack(currentStack));
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
