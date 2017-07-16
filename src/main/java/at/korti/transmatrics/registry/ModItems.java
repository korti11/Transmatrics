package at.korti.transmatrics.registry;

import at.korti.transmatrics.api.Constants;
import at.korti.transmatrics.api.Constants.Energy;
import at.korti.transmatrics.api.Constants.TransmatricsItem;
import at.korti.transmatrics.item.crafting.ItemCast;
import at.korti.transmatrics.item.crafting.ItemElectronics;
import at.korti.transmatrics.item.crafting.ItemGear;
import at.korti.transmatrics.item.crafting.ItemPlate;
import at.korti.transmatrics.item.energy.ItemCapacitor;
import at.korti.transmatrics.item.ore.ItemIngot;
import at.korti.transmatrics.item.ore.ItemPulverizedDust;
import at.korti.transmatrics.item.tool.ItemConnector;
import at.korti.transmatrics.item.tool.ItemHammer;
import at.korti.transmatrics.item.tool.ItemWrench;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Korti on 13.07.2017.
 */
@SuppressWarnings("WeakerAccess")
@GameRegistry.ObjectHolder(Constants.Mod.MODID)
public class ModItems {

    //region Tools
    public static ItemWrench WRENCH = new ItemWrench();

    public static ItemConnector CONNECTOR = new ItemConnector();

    public static ItemHammer HAMMER = new ItemHammer();
    //endregion

    //region Items
    public static ItemPulverizedDust PULVERIZED_DUST = new ItemPulverizedDust();

    public static ItemIngot INGOT = new ItemIngot();

    public static ItemGear GEAR = new ItemGear();

    public static ItemCast CAST = new ItemCast();

    public static ItemElectronics ELECTRONICS = new ItemElectronics();

    public static ItemPlate PLATE = new ItemPlate();
    //endregion

    //region Capacitor
    public static ItemCapacitor LEAD_CAPACITOR = new ItemCapacitor(
            TransmatricsItem.LEAD_CAPACITOR.getRegName(), Energy.LEAD_CAPACITOR_CAPACITY
    );

    public static ItemCapacitor INVAR_CAPACITOR = new ItemCapacitor(
            TransmatricsItem.INVAR_CAPACITOR.getRegName(), Energy.INVAR_CAPACITOR_CAPACITY
    );

    public static ItemCapacitor ELECTRUM_CAPACITOR = new ItemCapacitor(
            TransmatricsItem.ELECTRUM_CAPACITOR.getRegName(), Energy.ELECTRUM_CAPACITOR_CAPACITY
    );
    //endregion

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        public static final Set<Item> ITEMS = new HashSet<>();

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            final Item[] items = {
                    WRENCH,
                    CONNECTOR,
                    HAMMER,
                    PULVERIZED_DUST,
                    INGOT,
                    GEAR,
                    CAST,
                    ELECTRONICS,
                    PLATE,
                    LEAD_CAPACITOR,
                    INVAR_CAPACITOR,
                    ELECTRUM_CAPACITOR
            };

            final IForgeRegistry<Item> registry = event.getRegistry();

            for(final Item item : items) {
                registry.register(item);
                ITEMS.add(item);
            }

            OreDicts.registerOreDictItems();
        }
    }
}
