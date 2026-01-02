package com.sujal.hssp.utils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.WorldSavePath;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class MapLoader {

    /**
     * Attempts to import a map from the specified path into the current save's dimension folder.
     * WARNING: This overrides the current DIM-1 (Nether) or similar if not careful. 
     * For Phase 1, we will copy it to a "hssp_hub" subfolder and manage teleportation logic later, 
     * or simply overwrite the Overworld region if it's a fresh save.
     */
    public static void loadHubMap(MinecraftServer server, String sourcePath) {
        Path savePath = server.getSavePath(WorldSavePath.ROOT);
        File sourceDir = new File(sourcePath);
        File targetDir = savePath.resolve("region").toFile(); // Target Overworld

        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            server.sendMessage(Text.literal("§cError: Path does not exist: " + sourcePath));
            return;
        }

        server.sendMessage(Text.literal("§eImporting SkyBlock Hub from: " + sourcePath));

        // Note: Real-time map swapping is dangerous. 
        // This method suggests the player to restart or copies region files.
        // For stability, we copy region files.
        try {
            FileUtils.copyDirectory(sourceDir, targetDir);
            server.sendMessage(Text.literal("§aMap imported successfully! Please restart the world."));
        } catch (IOException e) {
            e.printStackTrace();
            server.sendMessage(Text.literal("§cFailed to import map. Check logs."));
        }
    }
}
