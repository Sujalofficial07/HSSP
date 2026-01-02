package com.sujal.hssp.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.sujal.hssp.items.ItemManager;
import com.sujal.hssp.utils.MapLoader;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class HSSPAdminCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("hssp")
            .requires(source -> source.hasPermissionLevel(2))
            
            // Subcommand: Give Item
            .then(CommandManager.literal("give")
                .then(CommandManager.argument("item_id", StringArgumentType.string())
                    .executes(context -> {
                        String id = StringArgumentType.getString(context, "item_id");
                        ItemStack stack = ItemManager.getItem(id);
                        if (stack != null) {
                            context.getSource().getPlayer().getInventory().insertStack(stack);
                            context.getSource().sendFeedback(() -> Text.literal("§aGave " + id), false);
                        } else {
                            context.getSource().sendError(Text.literal("§cItem not found: " + id));
                        }
                        return 1;
                    })
                )
            )

            // Subcommand: Load Map
            .then(CommandManager.literal("loadmap")
                .then(CommandManager.argument("path", StringArgumentType.greedyString())
                    .executes(context -> {
                        String path = StringArgumentType.getString(context, "path");
                        MapLoader.loadHubMap(context.getSource().getServer(), path);
                        return 1;
                    })
                )
            )
        );
    }
}
