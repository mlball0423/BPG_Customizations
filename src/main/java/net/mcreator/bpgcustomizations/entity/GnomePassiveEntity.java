
package net.mcreator.bpgcustomizations.entity;

import net.minecraft.block.material.Material;

@BpgCustomizationsModElements.ModElement.Tag
public class GnomePassiveEntity extends BpgCustomizationsModElements.ModElement {

	public static EntityType entity = null;

	public GnomePassiveEntity(BpgCustomizationsModElements instance) {
		super(instance, 55);

		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());

		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.6f, 1.8f)).build("gnome_passive")
						.setRegistryName("gnome_passive");

		elements.entities.add(() -> entity);

		elements.items.add(() -> new SpawnEggItem(entity, -4550185, -8789326, new Item.Properties().group(BPGCustomizationsTabItemGroup.tab))
				.setRegistryName("gnome_passive_spawn_egg"));

	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
		boolean biomeCriteria = false;
		if (new ResourceLocation("bpg_customizations:gnome_forest_short_trees").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("bpg_customizations:gnome_forest_tall_trees").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("bpg_customizations:gnome_plains").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("bpg_customizations:gnome_mountains_trees").equals(event.getName()))
			biomeCriteria = true;
		if (!biomeCriteria)
			return;

		event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity, 10, 2, 4));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(this::setupAttributes);

		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos,
						random) -> (world.getBlockState(pos.down()).getMaterial() == Material.ORGANIC && world.getLightSubtracted(pos, 0) > 8));

	}

	private static class ModelRegisterHandler {

		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
				return new MobRenderer(renderManager, new GnomeModel(), 0.5f) {

					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("bpg_customizations:textures/lilac_gnome.png");
					}
				};
			});

		}
	}

	private void setupAttributes() {
		AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
		ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3);
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 20);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 0);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 3);

		GlobalEntityTypeAttributes.put(entity, ammma.create());
	}

	public static class CustomEntity extends TameableEntity {

		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 0;
			setNoAI(false);

		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();

			this.goalSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
			this.goalSelector.addGoal(2, new OwnerHurtTargetGoal(this));
			this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false));
			this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1, (float) 10, (float) 2, false));
			this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1));
			this.goalSelector.addGoal(6,
					new TemptGoal(this, 1, Ingredient.fromItems(new ItemStack(Blocks.LILY_OF_THE_VALLEY, (int) (1)).getItem()), false));
			this.goalSelector.addGoal(7, new AvoidEntityGoal(this, PlayerEntity.class, (float) 6, 1, 1.2));
			this.goalSelector.addGoal(8, new AvoidEntityGoal(this, DwarfHostileEntity.CustomEntity.class, (float) 6, 1, 1.2));
			this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
			this.goalSelector.addGoal(10, new SwimGoal(this));

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
			if (source.getImmediateSource() instanceof ArrowEntity)
				return false;
			if (source == DamageSource.LIGHTNING_BOLT)
				return false;
			return super.attackEntityFrom(source, amount);
		}

		@Override
		public ActionResultType func_230254_b_(PlayerEntity sourceentity, Hand hand) {
			ItemStack itemstack = sourceentity.getHeldItem(hand);
			ActionResultType retval = ActionResultType.func_233537_a_(this.world.isRemote());

			Item item = itemstack.getItem();
			if (itemstack.getItem() instanceof SpawnEggItem) {
				retval = super.func_230254_b_(sourceentity, hand);
			} else if (this.world.isRemote()) {
				retval = (this.isTamed() && this.isOwner(sourceentity) || this.isBreedingItem(itemstack))
						? ActionResultType.func_233537_a_(this.world.isRemote())
						: ActionResultType.PASS;
			} else {
				if (this.isTamed()) {
					if (this.isOwner(sourceentity)) {
						if (item.isFood() && this.isBreedingItem(itemstack) && this.getHealth() < this.getMaxHealth()) {
							this.consumeItemFromStack(sourceentity, itemstack);
							this.heal((float) item.getFood().getHealing());
							retval = ActionResultType.func_233537_a_(this.world.isRemote());
						} else if (this.isBreedingItem(itemstack) && this.getHealth() < this.getMaxHealth()) {
							this.consumeItemFromStack(sourceentity, itemstack);
							this.heal(4);
							retval = ActionResultType.func_233537_a_(this.world.isRemote());
						} else {
							retval = super.func_230254_b_(sourceentity, hand);
						}
					}
				} else if (this.isBreedingItem(itemstack)) {
					this.consumeItemFromStack(sourceentity, itemstack);
					if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, sourceentity)) {
						this.setTamedBy(sourceentity);
						this.world.setEntityState(this, (byte) 7);
					} else {
						this.world.setEntityState(this, (byte) 6);
					}

					this.enablePersistence();
					retval = ActionResultType.func_233537_a_(this.world.isRemote());
				} else {
					retval = super.func_230254_b_(sourceentity, hand);
					if (retval == ActionResultType.SUCCESS || retval == ActionResultType.CONSUME)
						this.enablePersistence();
				}
			}

			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();
			Entity entity = this;
			return retval;
		}

		@Override
		public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageable) {
			CustomEntity retval = (CustomEntity) entity.create(serverWorld);
			retval.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(new BlockPos(retval.getPosition())), SpawnReason.BREEDING,
					(ILivingEntityData) null, (CompoundNBT) null);
			return retval;
		}

		@Override
		public boolean isBreedingItem(ItemStack stack) {
			if (stack == null)
				return false;

			if (new ItemStack(Blocks.LILY_OF_THE_VALLEY, (int) (1)).getItem() == stack.getItem())
				return true;

			return false;
		}

	}

	// Made with Blockbench 3.8.3
	// Exported for Minecraft version 1.15 - 1.16
	// Paste this class into your mod and generate all required imports

	public static class GnomeModel extends EntityModel<Entity> {
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

		public GnomeModel() {
			textureWidth = 32;
			textureHeight = 32;

			headbeardhairhat = new ModelRenderer(this);
			headbeardhairhat.setRotationPoint(0.0F, 17.0F, 0.0F);
			headbeardhairhat.setTextureOffset(0, 0).addBox(-0.5F, -2.0F, -2.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			headbeardhairhat.setTextureOffset(0, 0).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);

			hair = new ModelRenderer(this);
			hair.setRotationPoint(0.0F, 7.0F, 0.0F);
			headbeardhairhat.addChild(hair);
			hair.setTextureOffset(16, 2).addBox(-1.75F, -10.0F, 1.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
			hair.setTextureOffset(0, 18).addBox(-0.25F, -10.0F, 1.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);

			beard = new ModelRenderer(this);
			beard.setRotationPoint(0.0F, 7.0F, 0.0F);
			headbeardhairhat.addChild(beard);
			beard.setTextureOffset(16, 17).addBox(-1.75F, -8.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			beard.setTextureOffset(4, 14).addBox(-1.75F, -10.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			beard.setTextureOffset(16, 6).addBox(0.75F, -8.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
			beard.setTextureOffset(9, 0).addBox(-1.25F, -8.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
			beard.setTextureOffset(0, 12).addBox(0.75F, -10.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

			hat = new ModelRenderer(this);
			hat.setRotationPoint(0.0F, 7.0F, 0.0F);
			headbeardhairhat.addChild(hat);

			hattip_r1 = new ModelRenderer(this);
			hattip_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
			hat.addChild(hattip_r1);
			setRotationAngle(hattip_r1, -0.1309F, 0.0F, 0.0F);
			hattip_r1.setTextureOffset(18, 9).addBox(-0.5F, -13.0F, -1.75F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			hattip_r1.setTextureOffset(10, 4).addBox(-1.0F, -12.0F, -2.25F, 2.0F, 2.0F, 2.0F, 0.0F, false);

			hatbase_r1 = new ModelRenderer(this);
			hatbase_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
			hat.addChild(hatbase_r1);
			setRotationAngle(hatbase_r1, -0.0873F, 0.0F, 0.0F);
			hatbase_r1.setTextureOffset(9, 9).addBox(-1.5F, -11.0F, -2.25F, 3.0F, 2.0F, 3.0F, 0.0F, false);

			rightleg = new ModelRenderer(this);
			rightleg.setRotationPoint(1.5F, 21.0F, 0.0F);
			rightleg.setTextureOffset(6, 18).addBox(-0.9F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

			leftleg = new ModelRenderer(this);
			leftleg.setRotationPoint(-1.5F, 21.0F, 0.0F);
			leftleg.setTextureOffset(10, 19).addBox(-0.1F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

			rightarm = new ModelRenderer(this);
			rightarm.setRotationPoint(2.0F, 17.0F, 0.0F);
			rightarm.setTextureOffset(10, 14).addBox(0.0F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);

			leftarm = new ModelRenderer(this);
			leftarm.setRotationPoint(-2.0F, 17.0F, 0.0F);
			leftarm.setTextureOffset(14, 14).addBox(-1.0F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);

			bb_main = new ModelRenderer(this);
			bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
			bb_main.setTextureOffset(0, 6).addBox(-2.0F, -7.0F, -1.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);
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
