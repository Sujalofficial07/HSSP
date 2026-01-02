package com.sujal.hssp;

import com.sujal.hssp.commands.HSSPAdminCommand;
import com.sujal.hssp.items.ItemManager;
import com.sujal.hssp.events.PlayerStatsHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HSSP implements ModInitializer {
    public static final String MOD_ID = "hssp";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing HSSP Phase 1...");

        // Register Items
        ItemManager.registerItems();

        // Register Commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            HSSPAdminCommand.register(dispatcher);
        });

        // Register Event Handlers (Damage calculation, stat updates)
        PlayerStatsHandler.register();
    }
}
