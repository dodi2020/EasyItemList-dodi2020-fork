package me.justahuman.easy_item_list;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;

public class EasyItemList implements ClientModInitializer {
    public static final String MOD_ID = "easy_item_list";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitializeClient() {
        // JEI and EMI handle their own recipe loading through their plugin systems
        // No additional initialization needed
    }
}