package twilightforest.biomes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.potions.TFPotions;
import twilightforest.world.feature.TFGenPenguins;
import twilightforest.world.TFWorld;

import java.util.Random;

/**
 * @author Ben
 */
public class TFBiomeGlacier extends TFBiomeBase {

	public TFBiomeGlacier(BiomeProperties props) {
		super(props);

		getTFBiomeDecorator().setTreesPerChunk(1);
		getTFBiomeDecorator().setGrassPerChunk(0);
		getTFBiomeDecorator().hasCanopy = false;

		spawnableCreatureList.clear();
		spawnableCreatureList.add(new SpawnListEntry(twilightforest.entity.passive.EntityTFPenguin.class, 10, 4, 4));
	}

	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random random) {
		if (random.nextInt(3) == 0) {
			return new WorldGenTaiga1();
		} else {
			return new WorldGenTaiga2(true);
		}
	}

	@Override
	public boolean getEnableSnow() {
		return true;
	}

	/**
	 * Required for actual snow?
	 */
	@Override
	public boolean canRain() {
		return false;
	}

	@Override
	public void decorate(World world, Random random, BlockPos pos) {
		super.decorate(world, random, pos);
		TFGenPenguins penguins = new TFGenPenguins();

		if (random.nextInt(4) == 0) {
			int j = pos.getX() + random.nextInt(16) + 8;
			int y = TFWorld.SEALEVEL;
			int k = pos.getZ() + random.nextInt(16) + 8;
			penguins.generate(world, random, new BlockPos(j, y, k));
		}
	}

	@Override
	protected ResourceLocation[] getRequiredAdvancements() {
		return new ResourceLocation[]{ new ResourceLocation(TwilightForestMod.ID, "progress_yeti") };
	}

	@Override
	public void enforceProgession(EntityPlayer player, World world) {
		if (!world.isRemote && player.ticksExisted % 60 == 0) {
			player.addPotionEffect(new PotionEffect(TFPotions.frosty, 100, 3));
		}
		// hint monster?
		if (world.rand.nextInt(4) == 0) {
			TFFeature.ICE_TOWER.trySpawnHintMonster(world, player);
		}
	}
}
