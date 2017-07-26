package at.korti.transmatrics.event;

import at.korti.transmatrics.api.crafting.ICraftingRegistry;
import at.korti.transmatrics.api.crafting.ICraftingRegistry.ICraftingEntry;
import cofh.redstoneflux.api.IEnergyHandler;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Created by Korti on 19.04.2016.
 */
public class MachineCraftingEvent {

    public static class Pre<I, O> extends Event {
        private final ICraftingEntry<I,O> entry;
        private final ICraftingRegistry<I> registry;
        private final IInventory inventory;
        private final IEnergyHandler energyHandler;

        public Pre(ICraftingEntry<I, O> entry, ICraftingRegistry<I> registry, IInventory inventory, IEnergyHandler energyHandler) {
            this.entry = entry;
            this.registry = registry;
            this.inventory = inventory;
            this.energyHandler = energyHandler;
        }

        public ICraftingEntry<I, O> getEntry() {
            return entry;
        }

        public ICraftingRegistry<I> getRegistry() {
            return registry;
        }

        public IInventory getInventory() {
            return inventory;
        }

        public IEnergyHandler getEnergyHandler() {
            return energyHandler;
        }
    }

    public static class Post<I,O> extends Event{
        private final ICraftingEntry<I,O> entry;
        private final ICraftingRegistry<I> registry;
        private final IInventory inventory;
        private final IEnergyHandler energyHandler;

        public Post(ICraftingEntry<I, O> entry, ICraftingRegistry<I> registry, IInventory inventory, IEnergyHandler energyHandler) {
            this.entry = entry;
            this.registry = registry;
            this.inventory = inventory;
            this.energyHandler = energyHandler;
        }

        public ICraftingEntry<I, O> getEntry() {
            return entry;
        }

        public ICraftingRegistry<I> getRegistry() {
            return registry;
        }

        public IInventory getInventory() {
            return inventory;
        }

        public IEnergyHandler getEnergyHandler() {
            return energyHandler;
        }
    }

}
