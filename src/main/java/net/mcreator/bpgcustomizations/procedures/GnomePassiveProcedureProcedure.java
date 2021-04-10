package net.mcreator.bpgcustomizations.procedures;

@BpgCustomizationsModElements.ModElement.Tag
public class GnomePassiveProcedureProcedure extends BpgCustomizationsModElements.ModElement {

	public GnomePassiveProcedureProcedure(BpgCustomizationsModElements instance) {
		super(instance, 57);

	}

	public static boolean executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				BpgCustomizationsMod.LOGGER.warn("Failed to load dependency entity for procedure GnomePassiveProcedure!");
			return false;
		}

		Entity entity = (Entity) dependencies.get("entity");

		return (!(((((entity instanceof MobEntity) ? ((MobEntity) entity).getAttackTarget() : null) instanceof LivingEntity)
				? ((LivingEntity) ((entity instanceof MobEntity) ? ((MobEntity) entity).getAttackTarget() : null))
						.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, (int) 3))
				: ItemStack.EMPTY).getItem() == new ItemStack(HyperiumArmorItem.helmet, (int) (1)).getItem()));

	}

}
