package dev.attackeight.vh_addon_template;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(VhAddonTemplate.ID)
public class VhAddonTemplate {

    public static final String ID = "just_enough_vh";

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public VhAddonTemplate() {}

}
