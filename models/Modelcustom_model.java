// Made with Blockbench 3.8.3
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports

public static class Modelcustom_model extends EntityModel<Entity> {
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

	public Modelcustom_model() {
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
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
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

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.rightarm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
		this.leftleg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.rightleg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		this.leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
	}
}