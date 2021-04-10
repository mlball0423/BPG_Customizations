
package net.mcreator.bpgcustomizations.entity;

import net.minecraft.block.material.Material;

@BpgCustomizationsModElements.ModElement.Tag
public class DwarfHostileEntity extends BpgCustomizationsModElements.ModElement {

	public static EntityType entity = null;

	public DwarfHostileEntity(BpgCustomizationsModElements instance) {
		super(instance, 53);

		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());

		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.6f, 1.8f)).build("dwarf_hostile")
						.setRegistryName("dwarf_hostile");

		elements.entities.add(() -> entity);

		elements.items.add(() -> new SpawnEggItem(entity, -6681845, -13092808, new Item.Properties().group(BPGCustomizationsTabItemGroup.tab))
				.setRegistryName("dwarf_hostile_spawn_egg"));

	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
		boolean biomeCriteria = false;
		if (new ResourceLocation("bpg_customizations:gnome_forest_short_trees").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("bpg_customizations:gnome_mountains").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("bpg_customizations:gnome_mountains_trees").equals(event.getName()))
			biomeCriteria = true;
		if (!biomeCriteria)
			return;

		event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity, 20, 1, 4));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(this::setupAttributes);

		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				MonsterEntity::canMonsterSpawn);

	}

	private static class ModelRegisterHandler {

		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
				return new MobRenderer(renderManager, new DwarfModel(), 0.5f) {

					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("bpg_customizations:textures/dwarf_texture.png");
					}
				};
			});

		}
	}

	private void setupAttributes() {
		AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
		ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3);
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 50);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 0);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 6);

		ammma = ammma.createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1);

		GlobalEntityTypeAttributes.put(entity, ammma.create());
	}

	public static class CustomEntity extends MonsterEntity {

		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 5;
			setNoAI(false);

			this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_PICKAXE, (int) (1)));

		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();

			this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
			this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setCallsForHelp(this.getClass()));
			this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 0.8));
			this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
			this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, GnomeEntity.CustomEntity.class, false, false));
			this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, GnomeEntity.CustomEntity.class, false, false));
			this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(this, GnomeSlingerEntity.CustomEntity.class, false, false));
			this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(this, GnomeWarriorEntity.CustomEntity.class, false, false));
			this.targetSelector.addGoal(9, new NearestAttackableTargetGoal(this, GnomePassiveEntity.CustomEntity.class, false, false));

		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
		}

		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if (source.getImmediateSource() instanceof PotionEntity)
				return false;
			if (source == DamageSource.FALL)
				return false;
			if (source == DamageSource.DROWN)
				return false;
			return super.attackEntityFrom(source, amount);
		}

	}

	// Made with Blockbench 3.8.3
	// Exported for Minecraft version 1.15 - 1.16
	// Paste this class into your mod and generate all required imports

	public static class DwarfModel extends EntityModel<Entity> {
		private final ModelRenderer headbeardhairhat;
		private final ModelRenderer hair;
		private final ModelRenderer beard;
		private final ModelRenderer hat;
		private final ModelRenderer hattip_r1;
		private final ModelRenderer hatbase_r1;
		private final ModelRenderer rightleg;
		private final ModelRenderer leftleg;
		private final ModelRenderer rightarm;
		private final ModelRenderer leftarm;
		private final ModelRenderer bb_main;

		public DwarfModel() {
			textureWidth = 32;
			textureHeight = 32;

			headbeardhairhat = new ModelRenderer(this);
			headbeardhairhat.setRotationPoint(0.0F, 13.9F, -0.15F);
			headbeardhairhat.setTextureOffset(28, 24).addBox(-0.5F, -1.9F, -2.35F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			headbeardhairhat.setTextureOffset(20, 26).addBox(-1.5F, -2.9F, -1.35F, 3.0F, 3.0F, 3.0F, 0.0F, false);

			hair = new ModelRenderer(this);
			hair.setRotationPoint(0.0F, 7.1F, 0.15F);
			headbeardhairhat.addChild(hair);
			hair.setTextureOffset(26, 21).addBox(-1.75F, -10.0F, 1.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
			hair.setTextureOffset(26, 18).addBox(-0.25F, -10.0F, 1.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);

			beard = new ModelRenderer(this);
			beard.setRotationPoint(0.0F, 7.1F, 0.15F);
			headbeardhairhat.addChild(beard);
			beard.setTextureOffset(26, 15).addBox(-1.75F, -8.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			beard.setTextureOffset(26, 11).addBox(-1.75F, -10.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			beard.setTextureOffset(26, 5).addBox(0.75F, -8.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			beard.setTextureOffset(24, 8).addBox(-1.25F, -8.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			beard.setTextureOffset(26, 1).addBox(0.75F, -10.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

			hat = new ModelRenderer(this);
			hat.setRotationPoint(0.0F, 7.1F, 0.15F);
			headbeardhairhat.addChild(hat);

			hattip_r1 = new ModelRenderer(this);
			hattip_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
			hat.addChild(hattip_r1);
			setRotationAngle(hattip_r1, -0.1309F, 0.0F, 0.0F);
			hattip_r1.setTextureOffset(12, 25).addBox(-1.0F, -12.0F, -2.25F, 2.0F, 2.0F, 2.0F, 0.0F, false);

			hatbase_r1 = new ModelRenderer(this);
			hatbase_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
			hat.addChild(hatbase_r1);
			setRotationAngle(hatbase_r1, -0.0873F, 0.0F, 0.0F);
			hatbase_r1.setTextureOffset(0, 27).addBox(-1.5F, -11.0F, -2.25F, 3.0F, 2.0F, 3.0F, 0.0F, false);

			rightleg = new ModelRenderer(this);
			rightleg.setRotationPoint(1.1F, 19.05F, 0.0F);
			rightleg.setTextureOffset(0, 22).addBox(-0.5F, -0.05F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);
			rightleg.setTextureOffset(0, 19).addBox(-0.5F, 3.95F, -1.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);

			leftleg = new ModelRenderer(this);
			leftleg.setRotationPoint(-1.1F, 19.05F, 0.0F);
			leftleg.setTextureOffset(0, 22).addBox(-0.5F, -0.05F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);
			leftleg.setTextureOffset(0, 19).addBox(-0.5F, 3.95F, -1.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);

			rightarm = new ModelRenderer(this);
			rightarm.setRotationPoint(2.65F, 14.5F, 0.0F);
			rightarm.setTextureOffset(6, 21).addBox(-0.15F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);

			leftarm = new ModelRenderer(this);
			leftarm.setRotationPoint(-2.7F, 14.5F, 0.0F);
			leftarm.setTextureOffset(6, 21).addBox(-0.8F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);

			bb_main = new ModelRenderer(this);
			bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
			bb_main.setTextureOffset(0, 12).addBox(-2.5F, -10.0F, -1.0F, 5.0F, 5.0F, 2.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			headbeardhairhat.render(matrixStack, buffer, packedLight, packedOverlay);
			rightleg.render(matrixStack, buffer, packedLight, packedOverlay);
			leftleg.render(matrixStack, buffer, packedLight, packedOverlay);
			rightarm.render(matrixStack, buffer, packedLight, packedOverlay);
			leftarm.render(matrixStack, buffer, packedLight, packedOverlay);
			bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {

			this.rightleg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.rightarm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
			this.leftleg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
			this.headbeardhairhat.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.headbeardhairhat.rotateAngleX = f4 / (180F / (float) Math.PI);
		}
	}

}
