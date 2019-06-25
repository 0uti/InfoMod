package de.outinetworks.infomod.config;

import net.minecraftforge.common.ForgeConfigSpec;

//@Config.LangKey("infomod.config.title")
public class InfoModConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);









    public static class General {
        public final ForgeConfigSpec.BooleanValue showArmorDurability;
        public final ForgeConfigSpec.BooleanValue showHotBarDurability;
        public final ForgeConfigSpec.BooleanValue showAnimalInfo;


        General(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            showArmorDurability = builder
                    .comment("Show armor durability.")
                    .translation("infomod.config.armordurability")
                    .define("showArmorDurability", true);

            showHotBarDurability = builder
                    .comment("Show hotbar items durability.")
                    .translation("infomod.config.hotbardurability")
                    .define("showHotBarDurability", true);

            showAnimalInfo = builder
                    .comment("Show animal info overlay.")
                    .translation("infomod.config.animalinfo")
                    .define("showAnimalInfo", true);

            builder.pop();
        }



    }





    public static final ForgeConfigSpec spec = BUILDER.build();

}
