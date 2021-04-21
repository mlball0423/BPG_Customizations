package net.mcreator.bpgcustomizations.procedures;

import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.bpgcustomizations.BpgCustomizationsModElements;
import net.mcreator.bpgcustomizations.BpgCustomizationsMod;

import java.util.Map;

@BpgCustomizationsModElements.ModElement.Tag
public class InfinitiumSwordRightClickedInAirProcedure extends BpgCustomizationsModElements.ModElement {
	public InfinitiumSwordRightClickedInAirProcedure(BpgCustomizationsModElements instance) {
		super(instance, 66);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BpgCustomizationsMod.LOGGER.warn("Failed to load dependency entity for procedure InfinitiumSwordRightClickedInAir!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.REGENERATION, (int) 12000, (int) 2, (false), (false)));
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.RESISTANCE, (int) 12000, (int) 2, (false), (false)));
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, (int) 12000, (int) 2, (false), (false)));
	}
}
