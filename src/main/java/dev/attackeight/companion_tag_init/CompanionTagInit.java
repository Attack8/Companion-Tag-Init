package dev.attackeight.companion_tag_init;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(CompanionTagInit.ID)
public class CompanionTagInit {

    public static final String ID = "companion_tag_init";

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public CompanionTagInit() {}

}
