package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.energy.IDischargeable;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;

/**
 * Created by Korti on 25.02.2016.
 */
public abstract class TileEntityInventory extends TileEntityEnergyNode implements IInventory{

    private ItemStack[] inventory;
    private int stackLimit;
    private String name;
    private int capacitorSlot = -1;

    protected TileEntityInventory(int capacity, int maxReceive, int inventorySize, int stackLimit, boolean capacitorSlot, String name) {
        super(capacity, maxReceive, 0);
        if (capacitorSlot) {
            this.capacitorSlot = inventorySize++;
        }
        this.inventory = new ItemStack[inventorySize];
        this.stackLimit = stackLimit;
        this.name = name;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        clear();
        NBTTagList itemList = compound.getTagList(Constants.NBT.INVENTORY, 10);
        for (int i = 0; i < itemList.tagCount(); i++) {
            NBTTagCompound item = itemList.getCompoundTagAt(i);
            ItemStack stack = ItemStack.loadItemStackFromNBT(item);
            int slot = item.getShort(Constants.NBT.SLOT);
            inventory[slot] = stack;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getStackInSlot(i);
            if (stack != null) {
                NBTTagCompound item = new NBTTagCompound();
                stack.writeToNBT(item);
                item.setShort(Constants.NBT.SLOT, (short) i);
                itemList.appendTag(item);
            }
        }
        compound.setTag(Constants.NBT.INVENTORY, itemList);
        return compound;
    }

    @Override
    public void update() {
        super.update();
        if (!worldObj.isRemote && capacitorSlot != -1) {
            ItemStack stack = getStackInSlot(capacitorSlot);
            if (stack != null && stack.getItem() instanceof IDischargeable) {
                IDischargeable item = (IDischargeable) stack.getItem();
                int energy = item.discharge(stack, Constants.Energy.DISCHARGE_RATE, true);
                energy = energyStorage.receiveEnergy(energy, false);
                item.discharge(stack, energy, false);
                syncClient();
            }
        }
    }

    public boolean hasCapacitorSlot() {
        return capacitorSlot != -1;
    }

    public int getCapacitorSlot() {
        return capacitorSlot;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new SPacketUpdateTileEntity(getPos(), -1, tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

    public void dropItems() {
        InventoryHelper.dropInventoryItems(worldObj, pos, this);
    }

    //region IInventory
    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = getStackInSlot(index);
        if (stack != null) {
            if (stack.stackSize <= count) {
                setInventorySlotContents(index, null);
            } else {
                stack = stack.splitStack(count);
            }
        }

        syncClient();
        return stack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getStackInSlot(index);
        setInventorySlotContents(index, null);
        syncClient();
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory[index] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
        syncClient();
    }

    @Override
    public int getInventoryStackLimit() {
        return stackLimit;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistanceSq(this.getPos()) <= 64;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public void clear() {
        this.inventory = new ItemStack[getSizeInventory()];
    }
    //endregion

    //region IWorldNameable
    @Override
    public String getName() {
        return TextHelper.localize("tile.Transmatrics." + name + ".name");
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(getName());
    }
    //endregion
}
