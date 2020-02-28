package random.beasts.common.world.gen.structure;

import net.minecraft.block.BlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import random.beasts.api.world.gen.structure.BeastsStructure;

import java.util.Random;

public class WorldGenStructure<T extends IFeatureConfig> extends BeastsStructure<T> {
    private static PlacementSettings settings = (new PlacementSettings()).setIgnoreEntities(false).setIgnoreStructureBlock(false).setMirror(Mirror.NONE);
    private ResourceLocation structureName;

    public WorldGenStructure(String name) {
        super(() -> {
        });
        this.structureName = new ResourceLocation(BeastsMod.MOD_ID, name);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        MinecraftServer mcServer = world.getMinecraftServer();
        if (mcServer != null) {
            TemplateManager manager = mcServer.getWorld(0).getStructureTemplateManager();
            Template template = manager.get(mcServer, structureName);
            if (template != null) {
                BlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
                ChunkPos chunk = new ChunkPos(pos);
                settings = settings.setRotation(Rotation.values()[rand.nextInt(4)]).setBoundingBox(new StructureBoundingBox(new int[]{(chunk.x * 16) + 1, 0, (chunk.z * 16) + 1, (chunk.x * 16) + 15, 255, (chunk.x * 16) + 1}));
                template.addBlocksToWorldChunk(world, pos, settings);
                return true;
            }
        }
        return false;
    }
}
