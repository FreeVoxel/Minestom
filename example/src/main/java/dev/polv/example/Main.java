package dev.polv.example;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;

public class Main {
    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();

        Instance instance = MinecraftServer.getInstanceManager().createInstanceContainer();

        instance.setBlock(new BlockVec(0, 99, 0), Block.STONE);

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            event.setSpawningInstance(instance);
            event.getPlayer().teleport(new Pos(0, 100, 0));
        });

        System.out.println("Starting server...");
        server.start("0.0.0.0", 25565);
    }
}