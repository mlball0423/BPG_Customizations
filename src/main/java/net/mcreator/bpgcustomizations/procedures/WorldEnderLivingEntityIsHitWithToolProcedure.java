package net.mcreator.bpgcustomizations.procedures;

import net.minecraft.entity.Entity;

import net.mcreator.bpgcustomizations.BpgCustomizationsModElements;
import net.mcreator.bpgcustomizations.BpgCustomizationsMod;

import java.util.Map;

@BpgCustomizationsModElements.ModElement.Tag
public class WorldEnderLivingEntityIsHitWithToolProcedure extends BpgCustomizationsModElements.ModElement {
	public WorldEnderLivingEntityIsHitWithToolProcedure(BpgCustomizationsModElements instance) {
		super(instance, 70);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BpgCustomizationsMod.LOGGER.warn("Failed to load dependency entity for procedure WorldEnderLivingEntityIsHitWithTool!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		{
			Entity _ent = entity;
			if (!_ent.world.isRemote && _ent.world.getServer() != null) {
				_ent.world.getServer().getCommandManager().handleCommand(_ent.getCommandSource().withFeedbackDisabled().withPermissionLevel(4),
						"kill");
			}
		}
	}
}
