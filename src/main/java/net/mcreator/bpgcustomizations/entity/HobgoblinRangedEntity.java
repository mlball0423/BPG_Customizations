
package net.mcreator.bpgcustomizations.entity;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.block.Blocks;

import net.mcreator.bpgcustomizations.itemgroup.BPGCustomizationsTabItemGroup;
import net.mcreator.bpgcustomizations.BpgCustomizationsModElements;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@BpgCustomizationsModElements.ModElement.Tag
public class HobgoblinRangedEntity extends BpgCustomizationsModElements.ModElement {
	public static EntityType entity = null;
	@ObjectHolder("bpg_customizations:entitybullethobgoblin_ranged")
	public static final EntityType arrow = null;
	public HobgoblinRangedEntity(BpgCustomizationsModElements instance) {
		super(instance, 59);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).immuneToFire().size(0.6f, 1.8f))
						.build("hobgoblin_ranged").setRegistryName("hobgoblin_ranged");
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -8688318, -3355444, new Item.Properties().group(BPGCustomizationsTabItemGroup.tab))
				.setRegistryName("hobgoblin_ranged_spawn_egg"));
		elements.entities.add(() -> (EntityType.Builder.<ArrowCustomEntity>create(ArrowCustomEntity::new, EntityClassification.MISC)
				.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).setCustomClientFactory(ArrowCustomEntity::new)
				.size(0.5f, 0.5f)).build("entitybullethobgoblin_ranged").setRegistryName("entitybullethobgoblin_ranged"));
	}

	@SubscribeEvent
	public void addFeatureToBiomes(BiomeLoadingEvent event) {
		boolean biomeCriteria = false;
		if (new ResourceLocation("mountains").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("forest").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("taiga").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("swamp").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("snowy_tundra").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("snowy_mountains").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("wooded_hills").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("taiga_hills").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("mountain_edge").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("birch_forest").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("birch_forest_hills").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("dark_forest").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("snowy_taiga").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("snowy_taiga_hills").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("giant_tree_taiga").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("giant_tree_taiga_hills").equals(event.getName()))
			biomeCriteria = true;
		if (new ResourceLocation("wooded_mountains").equals(event.getName()))
			biomeCriteria = true;
		if (!biomeCriteria)
			return;
		event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity, 5, 1, 4));
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
				return new MobRenderer(renderManager, new GoblinModel(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("bpg_customizations:textures/goblin_texture_yellow.png");
					}
				};
			});
			RenderingRegistry.registerEntityRenderingHandler(arrow,
					renderManager -> new SpriteRenderer(renderManager, Minecraft.getInstance().getItemRenderer()));
		}
	}
	private void setupAttributes() {
		AttributeModifierMap.MutableAttribute ammma = MobEntity.func_233666_p_();
		ammma = ammma.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3);
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 25);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 4);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 6);
		GlobalEntityTypeAttributes.put(entity, ammma.create());
	}
	public static class CustomEntity extends MonsterEntity implements IRangedAttackMob {
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
			this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
			this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
			this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 0.8));
			this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
			this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, PlayerEntity.class, false, false));
			this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25, 20, 10) {
				@Override
				public boolean shouldContinueExecuting() {
					return this.shouldExecute();
				}
			});
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
			if (source == DamageSource.DROWN)
				return false;
			return super.attackEntityFrom(source, amount);
		}

		public void attackEntityWithRangedAttack(LivingEntity target, float flval) {
			ArrowCustomEntity entityarrow = new ArrowCustomEntity(arrow, this, this.world);
			double d0 = target.getPosY() + (double) target.getEyeHeight() - 1.1;
			double d1 = target.getPosX() - this.getPosX();
			double d3 = target.getPosZ() - this.getPosZ();
			entityarrow.shoot(d1, d0 - entityarrow.getPosY() + (double) MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F, d3, 1.6F, 12.0F);
			world.addEntity(entityarrow);
		}
	}

	@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
	private static class ArrowCustomEntity extends AbstractArrowEntity implements IRendersAsItem {
		public ArrowCustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			super(arrow, world);
		}

		public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, World world) {
			super(type, world);
		}

		public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, double x, double y, double z, World world) {
			super(type, x, y, z, world);
		}

		public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, LivingEntity entity, World world) {
			super(type, entity, world);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack getItem() {
			return new ItemStack(Blocks.COBBLESTONE, (int) (1));
		}

		@Override
		protected ItemStack getArrowStack() {
			return new ItemStack(Blocks.COBBLESTONE, (int) (1));
		}
	}

	// Made with Blockbench 3.8.3
	// Exported for Minecraft version 1.15 - 1.16
	// Paste this class into your mod and generate all required imports
	public static class GoblinModel extends EntityModel<Entity> {
		private final ModelRenderer leftleg;
		private final ModelRenderer rightleg;
		private final ModelRenderer Body;
		private final ModelRenderer rightarm;
		private final ModelRenderer leftarm;
		private final ModelRenderer head;
		private final ModelRenderer jaw;
		private final ModelRenderer mandible;
		private final ModelRenderer teeth;
		private final ModelRenderer Skull;
		private final ModelRenderer brow_r1;
		private final ModelRenderer fleshyfeatures;
		private final ModelRenderer nose_r1;
		private final ModelRenderer ears;
		private final ModelRenderer rightear;
		private final ModelRenderer leftear;
		private final ModelRenderer tail;
		private final ModelRenderer thicktail;
		private final ModelRenderer midtail_r1;
		private final ModelRenderer closetail_r1;
		private final ModelRenderer thintail;
		private final ModelRenderer base_r1;
		private final ModelRenderer end_r1;
		private final ModelRenderer main_r1;
		public GoblinModel() {
			textureWidth = 64;
			textureHeight = 64;
			leftleg = new ModelRenderer(this);
			leftleg.setRotationPoint(-2.6667F, 15.0F, 0.6667F);
			leftleg.setTextureOffset(28, 40).addBox(-0.8333F, 3.0F, -0.6667F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			leftleg.setTextureOffset(0, 31).addBox(-2.3333F, 0.0F, -1.6667F, 4.0F, 4.0F, 4.0F, 0.0F, false);
			leftleg.setTextureOffset(36, 11).addBox(-0.8333F, 8.0F, -2.6667F, 2.0F, 1.0F, 4.0F, 0.0F, false);
			rightleg = new ModelRenderer(this);
			rightleg.setRotationPoint(2.6667F, 15.0F, 0.6667F);
			rightleg.setTextureOffset(0, 39).addBox(-1.1667F, 3.0F, -0.6667F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			rightleg.setTextureOffset(24, 27).addBox(-1.6667F, 0.0F, -1.6667F, 4.0F, 4.0F, 4.0F, 0.0F, false);
			rightleg.setTextureOffset(28, 35).addBox(-1.1667F, 8.0F, -2.6667F, 2.0F, 1.0F, 4.0F, 0.0F, false);
			Body = new ModelRenderer(this);
			Body.setRotationPoint(0.0F, 24.0F, 0.0F);
			Body.setTextureOffset(0, 22).addBox(-5.0F, -19.0F, -1.0F, 10.0F, 5.0F, 4.0F, 0.0F, false);
			Body.setTextureOffset(23, 0).addBox(-3.0F, -14.0F, 0.0F, 6.0F, 3.0F, 3.0F, 0.0F, false);
			Body.setTextureOffset(40, 54).addBox(4.0F, -11.0F, -1.0F, -8.0F, 2.0F, 4.0F, 0.0F, false);
			Body.setTextureOffset(0, 0).addBox(-1.0F, -12.0F, -1.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
			rightarm = new ModelRenderer(this);
			rightarm.setRotationPoint(-5.0F, 7.0F, 1.0F);
			rightarm.setTextureOffset(38, 38).addBox(-3.0F, 2.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			rightarm.setTextureOffset(12, 35).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
			leftarm = new ModelRenderer(this);
			leftarm.setRotationPoint(5.0F, 7.0F, 1.0F);
			leftarm.setTextureOffset(0, 13).addBox(1.0F, 2.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
			leftarm.setTextureOffset(24, 13).addBox(0.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, 5.0F, -1.0F);
			jaw = new ModelRenderer(this);
			jaw.setRotationPoint(0.0F, 19.0F, 1.0F);
			head.addChild(jaw);
			mandible = new ModelRenderer(this);
			mandible.setRotationPoint(0.0F, 0.0F, 0.0F);
			jaw.addChild(mandible);
			mandible.setTextureOffset(0, 13).addBox(-4.0F, -20.0F, -6.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
			mandible.setTextureOffset(24, 24).addBox(-3.0F, -21.0F, -6.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
			teeth = new ModelRenderer(this);
			teeth.setRotationPoint(0.0F, 0.0F, 0.0F);
			jaw.addChild(teeth);
			teeth.setTextureOffset(24, 13).addBox(-4.0F, -22.0F, -6.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			teeth.setTextureOffset(0, 22).addBox(3.0F, -22.0F, -6.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			Skull = new ModelRenderer(this);
			Skull.setRotationPoint(0.0F, 19.0F, 1.0F);
			head.addChild(Skull);
			Skull.setTextureOffset(0, 0).addBox(-4.0F, -26.0F, -5.0F, 8.0F, 6.0F, 7.0F, 0.0F, false);
			brow_r1 = new ModelRenderer(this);
			brow_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
			Skull.addChild(brow_r1);
			setRotationAngle(brow_r1, 0.3491F, 0.0F, 0.0F);
			brow_r1.setTextureOffset(24, 22).addBox(-4.0F, -25.0F, 3.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
			fleshyfeatures = new ModelRenderer(this);
			fleshyfeatures.setRotationPoint(0.0F, 0.0F, 0.0F);
			Skull.addChild(fleshyfeatures);
			nose_r1 = new ModelRenderer(this);
			nose_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
			fleshyfeatures.addChild(nose_r1);
			setRotationAngle(nose_r1, 0.2618F, 0.0F, 0.0F);
			nose_r1.setTextureOffset(30, 6).addBox(-1.0F, -23.0F, -3.0F, 2.0F, 1.0F, 4.0F, 0.0F, false);
			ears = new ModelRenderer(this);
			ears.setRotationPoint(0.0F, 0.0F, 0.0F);
			Skull.addChild(ears);
			rightear = new ModelRenderer(this);
			rightear.setRotationPoint(0.0F, 0.0F, 0.0F);
			ears.addChild(rightear);
			rightear.setTextureOffset(36, 29).addBox(4.0F, -24.0F, -2.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
			rightear.setTextureOffset(17, 31).addBox(4.0F, -23.0F, -2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
			leftear = new ModelRenderer(this);
			leftear.setRotationPoint(0.0F, 0.0F, 0.0F);
			ears.addChild(leftear);
			leftear.setTextureOffset(0, 4).addBox(-6.0F, -23.0F, -2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
			leftear.setTextureOffset(30, 11).addBox(-7.0F, -24.0F, -2.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
			tail = new ModelRenderer(this);
			tail.setRotationPoint(0.0F, 24.0F, 0.0F);
			thicktail = new ModelRenderer(this);
			thicktail.setRotationPoint(0.5F, -3.0F, -6.0F);
			tail.addChild(thicktail);
			setRotationAngle(thicktail, -0.4363F, 0.0F, 0.0F);
			midtail_r1 = new ModelRenderer(this);
			midtail_r1.setRotationPoint(0.0F, 0.178F, 0.2802F);
			thicktail.addChild(midtail_r1);
			setRotationAngle(midtail_r1, -0.6109F, 0.0F, 0.0F);
			midtail_r1.setTextureOffset(36, 24).addBox(-1.5F, -12.35F, 0.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
			closetail_r1 = new ModelRenderer(this);
			closetail_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
			thicktail.addChild(closetail_r1);
			setRotationAngle(closetail_r1, -0.3927F, 0.0F, 0.0F);
			closetail_r1.setTextureOffset(37, 32).addBox(-1.5F, -12.0F, 0.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
			thintail = new ModelRenderer(this);
			thintail.setRotationPoint(0.0F, 0.0F, 0.0F);
			tail.addChild(thintail);
			base_r1 = new ModelRenderer(this);
			base_r1.setRotationPoint(0.5F, -1.3532F, -0.5487F);
			thintail.addChild(base_r1);
			setRotationAngle(base_r1, -0.48F, 0.0F, 0.0F);
			base_r1.setTextureOffset(12, 31).addBox(-1.0F, -5.45F, 6.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
			end_r1 = new ModelRenderer(this);
			end_r1.setRotationPoint(2.5F, -3.243F, 1.823F);
			thintail.addChild(end_r1);
			setRotationAngle(end_r1, -0.8727F, 0.0F, 0.0F);
			end_r1.setTextureOffset(1, 47).addBox(-3.0F, -5.0F, 2.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
			main_r1 = new ModelRenderer(this);
			main_r1.setRotationPoint(2.5F, -4.2452F, -0.3927F);
			thintail.addChild(main_r1);
			setRotationAngle(main_r1, -1.1345F, 0.0F, 0.0F);
			main_r1.setTextureOffset(24, 35).addBox(-3.0F, -7.0F, 1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			leftleg.render(matrixStack, buffer, packedLight, packedOverlay);
			rightleg.render(matrixStack, buffer, packedLight, packedOverlay);
			Body.render(matrixStack, buffer, packedLight, packedOverlay);
			rightarm.render(matrixStack, buffer, packedLight, packedOverlay);
			leftarm.render(matrixStack, buffer, packedLight, packedOverlay);
			head.render(matrixStack, buffer, packedLight, packedOverlay);
			tail.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.rightarm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
			this.leftleg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.rightleg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
		}
	}
}
