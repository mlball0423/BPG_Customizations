
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
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.projectile.ArrowEntity;
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

import net.mcreator.bpgcustomizations.procedures.GnomePassiveProcedureProcedure;
import net.mcreator.bpgcustomizations.itemgroup.BPGCustomizationsTabItemGroup;
import net.mcreator.bpgcustomizations.BpgCustomizationsModElements;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

import com.google.common.collect.ImmutableMap;

@BpgCustomizationsModElements.ModElement.Tag
public class GnomeSlingerEntity extends BpgCustomizationsModElements.ModElement {
	public static EntityType entity = null;
	@ObjectHolder("bpg_customizations:entitybulletgnome_slinger")
	public static final EntityType arrow = null;
	public GnomeSlingerEntity(BpgCustomizationsModElements instance) {
		super(instance, 54);
		FMLJavaModLoadingContext.get().getModEventBus().register(new ModelRegisterHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.6f, 1.8f)).build("gnome_slinger")
						.setRegistryName("gnome_slinger");
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -1135243, -28668, new Item.Properties().group(BPGCustomizationsTabItemGroup.tab))
				.setRegistryName("gnome_slinger_spawn_egg"));
		elements.entities.add(() -> (EntityType.Builder.<ArrowCustomEntity>create(ArrowCustomEntity::new, EntityClassification.MISC)
				.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).setCustomClientFactory(ArrowCustomEntity::new)
				.size(0.5f, 0.5f)).build("entitybulletgnome_slinger").setRegistryName("entitybulletgnome_slinger"));
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
		if (!biomeCriteria)
			return;
		event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity, 15, 1, 2));
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
				return new MobRenderer(renderManager, new GnomeModel(), 0.5f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("bpg_customizations:textures/peach_gnome.png");
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
		ammma = ammma.createMutableAttribute(Attributes.MAX_HEALTH, 20);
		ammma = ammma.createMutableAttribute(Attributes.ARMOR, 12);
		ammma = ammma.createMutableAttribute(Attributes.ATTACK_DAMAGE, 3);
		GlobalEntityTypeAttributes.put(entity, ammma.create());
	}
	public static class CustomEntity extends MonsterEntity implements IRangedAttackMob {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 5;
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
			this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setCallsForHelp(this.getClass()));
			this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 0.8));
			this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
			this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, PlayerEntity.class, false, false) {
				@Override
				public boolean shouldExecute() {
					double x = CustomEntity.this.getPosX();
					double y = CustomEntity.this.getPosY();
					double z = CustomEntity.this.getPosZ();
					Entity entity = CustomEntity.this;
					return super.shouldExecute() && GnomePassiveProcedureProcedure.executeProcedure(ImmutableMap.of("entity", entity));
				}

				@Override
				public boolean shouldContinueExecuting() {
					double x = CustomEntity.this.getPosX();
					double y = CustomEntity.this.getPosY();
					double z = CustomEntity.this.getPosZ();
					Entity entity = CustomEntity.this;
					return super.shouldContinueExecuting() && GnomePassiveProcedureProcedure.executeProcedure(ImmutableMap.of("entity", entity));
				}
			});
			this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, DwarfHostileEntity.CustomEntity.class, false, false));
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
			if (source.getImmediateSource() instanceof ArrowEntity)
				return false;
			if (source == DamageSource.LIGHTNING_BOLT)
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
			return new ItemStack(Items.COD, (int) (1));
		}

		@Override
		protected ItemStack getArrowStack() {
			return new ItemStack(Items.COD, (int) (1));
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
