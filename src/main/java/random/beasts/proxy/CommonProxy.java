package random.beasts.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import random.beasts.api.configuration.BeastsConfig;
import random.beasts.api.main.BeastsReference;
import random.beasts.api.main.BeastsUtils;
import random.beasts.client.init.BeastsCreativeTabs;
import random.beasts.common.block.BlockPalmTreeLeaves;
import random.beasts.common.block.BlockShell;
import random.beasts.common.block.BlockShellPiece;
import random.beasts.common.init.*;
import random.beasts.common.network.BeastsGuiHandler;
import random.beasts.common.network.BeastsPacketHandler;
import random.beasts.common.world.biome.RealisticBiomeDriedReef;
import rtg.api.RTGAPI;
import rtg.util.ModCompat;

public class CommonProxy {

    public EntityPlayer getPlayer(MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            return ctx.getServerHandler().player;
        }
        return null;
    }

    public boolean isTrimolaAttacking() {
        return false;
    }

    public void preInit() {
        BeastsUtils.setRegistryTab(BeastsCreativeTabs.MAIN);
        BeastsStructures.init();
        BeastsTriggers.init();
        BeastsTileEntities.init();
        BeastsLootTables.init();
        BeastsGuiHandler.init();
        BeastsWorldGenerators.init();
    }

    public void init() {
        BeastsConfig.init();
        if (Loader.isModLoaded("rtg")) {
            EnumHelper.addEnum(ModCompat.Mods.class, BeastsReference.ID, new Class[0]);
            RTGAPI.RTG_BIOMES.addBiomes(new RealisticBiomeDriedReef());
        }
        registerOreDict();
        registerSpawns();
        BeastsPacketHandler.initPackets();
    }

    public void registerEventRenders() {
    }

    public ModelBiped getArmorModel(Item armorItem, EntityEquipmentSlot armorSlot) {
        return null;
    }

    public String getArmorTexture(Item armorItem, EntityEquipmentSlot armorSlot) {
        return null;
    }

    public void setGraphicsLevel(BlockPalmTreeLeaves block, boolean enabled) {
    }

    private void registerOreDict() {
        //this could be done better
        OreDictionary.registerOre("logWood", BeastsBlocks.PALM_LOG);
        OreDictionary.registerOre("plankWood", BeastsBlocks.PALM_PLANKS);
        OreDictionary.registerOre("slabWood", BeastsBlocks.PALM_SLAB.half);
        OreDictionary.registerOre("stairWood", BeastsBlocks.PALM_STAIRS);
        OreDictionary.registerOre("fenceWood", BeastsBlocks.PALM_FENCE);
        OreDictionary.registerOre("fenceGateWood", BeastsBlocks.PALM_GATE);
        OreDictionary.registerOre("doorWood", BeastsBlocks.PALM_DOOR);
        OreDictionary.registerOre("logJelly", BeastsBlocks.JELLY_WOOD);
        OreDictionary.registerOre("plankJelly", BeastsBlocks.JELLY_WOOD_PLANKS);
        OreDictionary.registerOre("slabJelly", BeastsBlocks.JELLY_WOOD_SLAB.half);
        OreDictionary.registerOre("stairJelly", BeastsBlocks.JELLY_WOOD_STAIRS);
        OreDictionary.registerOre("fenceJelly", BeastsBlocks.JELLY_WOOD_FENCE);
        OreDictionary.registerOre("fenceGateJelly", BeastsBlocks.JELLY_WOOD_GATE);
        OreDictionary.registerOre("doorJelly", BeastsBlocks.JELLY_WOOD_DOOR);
        for (BlockShell shell : BeastsBlocks.SHELL_BLOCKS) OreDictionary.registerOre("blockShell", shell);
        for (BlockShellPiece shell : BeastsBlocks.SHELL_PIECES) OreDictionary.registerOre("pieceShell", shell);
    }

    private void registerSpawns() {
        BeastsEntities.SPAWNS.forEach((type, spawns) -> {
            for (BeastsEntities.SpawnEntry spawn : spawns) {
                Biome.SpawnListEntry entry = new Biome.SpawnListEntry(type.getEntityClass(), spawn.weight, spawn.min, spawn.max);
                for (Biome biome : spawn.biomes) {
                    biome.getSpawnableList(type.getClassification()).add(entry);
                }
            }
        });
    }
}
