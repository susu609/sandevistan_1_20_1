package net.susu609.sandevistan;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.susu609.sandevistan.effect.SandevistanEffect;
import net.susu609.sandevistan.item.SandevistanItem;

@Mod(SandevistanMod.MOD_ID) public final class SandevistanMod {

    public static final String MOD_ID = "sandevistan";

    private static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MOD_ID);
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<MobEffect> SANDEVISTAN_EFFECT =
            EFFECTS.register("sandevistan", SandevistanEffect::new);
    public static final RegistryObject<Item> SANDEVISTAN =
            ITEMS.register("sandevistan", () -> new SandevistanItem(
                    new Item.Properties().stacksTo(1)));

    public SandevistanMod(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        EFFECTS.register(bus);
        ITEMS.register(bus);
        bus.addListener(this::addToCreativeTab);
    }

    private void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(SANDEVISTAN.get());
        }
    }
}
