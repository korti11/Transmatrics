package at.korti.transmatrics.tileentity;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.util.helper.TextHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

/**
 * Created by Korti on 25.02.2016.
 */
public class TileEntityInventory extends TileEntity implements IInventory{

    private ItemStack[] inventory;
    private int stackLimit;
    private String name;

    protected TileEntityInventory(int inventorySize, int stackLimit, String name) {
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
            setInventorySlotContents(slot, stack);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
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
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(getPos(), -1, tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
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

        markDirty(); // save tile entity
        if (!worldObj.isRemote) {
            worldObj.markBlockForUpdate(this.getPos());
        }
        return stack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getStackInSlot(index);
        setInventorySlotContents(index, null);
        markDirty();
        if (!worldObj.isRemote) {
            worldObj.markBlockForUpdate(this.getPos());
        }
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory[index] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
        markDirty(); // save tile entity
        if (!worldObj.isRemote) {
            worldObj.markBlockForUpdate(this.getPos());
        }
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
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
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
    public IChatComponent getDisplayName() {
        return new ChatComponentText(getName());
    }
    //endregion
}
