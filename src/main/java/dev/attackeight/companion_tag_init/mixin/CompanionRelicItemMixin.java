package dev.attackeight.companion_tag_init.mixin;

import dev.attackeight.companion_tag_init.CompanionTagInit;
import iskallia.vault.config.CompanionRelicsConfig;
import iskallia.vault.core.random.RandomSource;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.item.CompanionRelicItem;
import iskallia.vault.item.core.DataInitializationItem;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(value = CompanionRelicItem.class, remap = false)
public class CompanionRelicItemMixin implements DataInitializationItem {

    @Override
    public void initialize(ItemStack stack, RandomSource randomSource) {
        if (stack.hasTag()) {
            ListTag list = stack.getTag().getList("modifiers", Tag.TAG_STRING);

            for (Tag child : list) {
                if (child instanceof StringTag stringTag) {
                    if (stringTag.getAsString().startsWith("@")) {
                        ResourceLocation poolId = new ResourceLocation(stringTag.getAsString().substring(1));
                        Optional<CompanionRelicsConfig.ResolvedEntry> relic = ModConfigs.COMPANION_RELICS.generate(poolId, 0, randomSource);

                        ListTag newList = new ListTag();

                        if (relic.isEmpty()) {
                            newList.add(StringTag.valueOf(poolId.toString()));
                            stack.getTag().put("modifiers", newList);
                            CompanionTagInit.LOGGER.error("Unknown companion relic modifier pool: {}", poolId);
                            return;
                        }

                        for (ResourceLocation modifier : relic.get().getModifiers()) {
                            newList.add(StringTag.valueOf(modifier.toString()));
                        }

                        stack.getTag().put("modifiers", newList);
                        stack.getTag().put("model", IntTag.valueOf(relic.get().getModel().get(randomSource)));
                    }
                }
            }
        }
    }
}
