package net.mcreator.bpgcustomizations.procedures;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.bpgcustomizations.item.ParadoxiumHarvesterItem;
import net.mcreator.bpgcustomizations.item.ParadoxiumCrystalItem;
import net.mcreator.bpgcustomizations.block.ParadoxiumOreBlock;
import net.mcreator.bpgcustomizations.BpgCustomizationsModElements;
import net.mcreator.bpgcustomizations.BpgCustomizationsMod;

import java.util.Map;

@BpgCustomizationsModElements.ModElement.Tag
public class ParadoxiumHarvesterBlockDestroyedWithToolProcedure extends BpgCustomizationsModElements.ModElement {
	public ParadoxiumHarvesterBlockDestroyedWithToolProcedure(BpgCustomizationsModElements instance) {
		super(instance, 51);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BpgCustomizationsMod.LOGGER.warn("Failed to load dependency entity for procedure ParadoxiumHarvesterBlockDestroyedWithTool!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				BpgCustomizationsMod.LOGGER.warn("Failed to load dependency x for procedure ParadoxiumHarvesterBlockDestroyedWithTool!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				BpgCustomizationsMod.LOGGER.warn("Failed to load dependency y for procedure ParadoxiumHarvesterBlockDestroyedWithTool!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				BpgCustomizationsMod.LOGGER.warn("Failed to load dependency z for procedure ParadoxiumHarvesterBlockDestroyedWithTool!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				BpgCustomizationsMod.LOGGER.warn("Failed to load dependency world for procedure ParadoxiumHarvesterBlockDestroyedWithTool!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		if (((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(ParadoxiumHarvesterItem.block, (int) (1)).getItem())
				&& ((world.getBlockState(new BlockPos((int) x, (int) y, (int) z))).getBlock() == ParadoxiumOreBlock.block.getDefaultState()
						.getBlock()))) {
			world.destroyBlock(new BlockPos((int) x, (int) y, (int) z), false);
			if (world instanceof World && !world.isRemote()) {
				ItemEntity entityToSpawn = new ItemEntity((World) world, x, y, z, new ItemStack(ParadoxiumCrystalItem.block, (int) (1)));
				entityToSpawn.setPickupDelay((int) 10);
				world.addEntity(entityToSpawn);
			}
			if (world instanceof World && !world.isRemote()) {
				ItemEntity entityToSpawn = new ItemEntity((World) world, x, y, z, new ItemStack(ParadoxiumCrystalItem.block, (int) (1)));
				entityToSpawn.setPickupDelay((int) 10);
				world.addEntity(entityToSpawn);
			}
		}
	}
}
