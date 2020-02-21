package random.beasts.common.world.biome;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.fml.common.FMLLog;
import random.beasts.api.world.biome.underground.UndergroundBiome;
import random.beasts.common.BeastsMod;
import random.beasts.common.init.BeastsBlocks;

public class BiomeAbyss extends UndergroundBiome {

	public BiomeAbyss(Biome base) {
		super("The Abyss", 20, base);
	}
	
	@Override
	public void populate(World world, Random rand, BlockPos pos) {
		super.populate(world, rand, pos);
		ChunkPos chunk = new ChunkPos(pos);
		BlockPos chunkStart = new BlockPos(chunk.getXStart(), (pos.getY()>>5)*32, chunk.getZStart());
		BlockPos chunkEnd = new BlockPos(chunk.getXEnd(), (pos.getY()>>5)*32 + 32, chunk.getZEnd());

		int radiusX = 8;
		int radiusY = 16;
		int radiusZ = 8;
		BlockPos center = chunkStart.add(radiusX, radiusY, radiusZ);
		for(BlockPos posit : BlockPos.getAllInBox(chunkStart, chunkEnd)) {
			
			double a = Math.pow(posit.getX()-center.getX(), 2)/Math.pow(radiusX, 2) + 
					Math.pow(posit.getY()-center.getY(), 2)/Math.pow(radiusY, 2) + 
							Math.pow(posit.getZ()-center.getZ(), 2)/Math.pow(radiusZ, 2);
			if(a < 1) {
				//world.setBlockToAir(posit);
				if(world.getBlockState(posit).getBlock() == Blocks.STONE) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_STONE.getDefaultState());		
				}
				if(world.getBlockState(posit).getBlock() == Blocks.IRON_ORE) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_IRON_ORE.getDefaultState());		
				}
				if(world.getBlockState(posit).getBlock() == Blocks.GOLD_ORE) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_GOLD_ORE.getDefaultState());		
				}
				if(world.getBlockState(posit).getBlock() == Blocks.LAPIS_ORE) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_LAPIS_ORE.getDefaultState());		
				}
				if(world.getBlockState(posit).getBlock() == Blocks.REDSTONE_ORE) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_REDSTONE_ORE.getDefaultState());		
				}
				if(world.getBlockState(posit).getBlock() == Blocks.COAL_ORE) {
					world.setBlockState(posit, BeastsBlocks.ABYSSAL_COAL_ORE.getDefaultState());		
				}
			}
		}
	}

}
