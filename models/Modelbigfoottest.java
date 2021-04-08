// Made with Blockbench 3.8.3
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports

public static class Modelbigfoottest extends EntityModel<Entity> {
	private final ModelRenderer Body;
	private final ModelRenderer RightLeg;
	private final ModelRenderer RightLegLower;
	private final ModelRenderer RightFoot;
	private final ModelRenderer LeftLeg;
	private final ModelRenderer LeftLegLower;
	private final ModelRenderer LeftFoot;
	private final ModelRenderer RightArmTop;
	private final ModelRenderer RightForearm_r1;
	private final ModelRenderer RightTop_r1;
	private final ModelRenderer Head;
	private final ModelRenderer LeftArmTop;
	private final ModelRenderer LeftForearm_r1;
	private final ModelRenderer LeftTop_r1;

	public Modelbigfoottest() {
		textureWidth = 128;
		textureHeight = 128;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 0.0F, -0.125F);
		Body.setTextureOffset(0, 69).addBox(-6.0F, -7.0F, -3.875F, 12.0F, 14.0F, 6.0F, 0.0F, false);
		Body.setTextureOffset(0, 56).addBox(-5.0F, -6.0F, 2.125F, 10.0F, 12.0F, 1.0F, 0.0F, false);
		Body.setTextureOffset(0, 90).addBox(-5.0F, -8.0F, -3.875F, 10.0F, 1.0F, 6.0F, 0.0F, false);
		Body.setTextureOffset(33, 111).addBox(-5.0F, 7.0F, -3.875F, 10.0F, 1.0F, 6.0F, 0.0F, false);
		Body.setTextureOffset(0, 97).addBox(-3.0F, -9.0F, -2.875F, 6.0F, 1.0F, 4.0F, 0.0F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(3.0F, 8.0F, -1.0F);
		RightLeg.setTextureOffset(39, 46).addBox(-2.5F, -1.0F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, false);

		RightLegLower = new ModelRenderer(this);
		RightLegLower.setRotationPoint(3.0F, 15.5F, -1.0F);
		RightLegLower.setTextureOffset(60, 46).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, false);

		RightFoot = new ModelRenderer(this);
		RightFoot.setRotationPoint(3.0F, 23.5F, -1.5F);
		RightFoot.setTextureOffset(1, 27).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 1.0F, 9.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(-3.0F, 8.0F, -1.0F);
		LeftLeg.setTextureOffset(39, 46).addBox(-2.5F, -1.0F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, false);

		LeftLegLower = new ModelRenderer(this);
		LeftLegLower.setRotationPoint(-3.0F, 15.5F, -1.0F);
		LeftLegLower.setTextureOffset(59, 46).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, false);

		LeftFoot = new ModelRenderer(this);
		LeftFoot.setRotationPoint(-3.0F, 23.5F, -1.5F);
		LeftFoot.setTextureOffset(1, 28).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 1.0F, 9.0F, 0.0F, false);

		RightArmTop = new ModelRenderer(this);
		RightArmTop.setRotationPoint(6.6183F, -5.9004F, -1.2375F);
		RightArmTop.setTextureOffset(37, 79).addBox(-0.8433F, -1.0496F, -2.2625F, 5.0F, 2.0F, 5.0F, 0.0F, false);
		RightArmTop.setTextureOffset(1, 47).addBox(4.0567F, 12.3254F, -2.2125F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		RightForearm_r1 = new ModelRenderer(this);
		RightForearm_r1.setRotationPoint(5.2394F, 10.1861F, -0.2625F);
		RightArmTop.addChild(RightForearm_r1);
		setRotationAngle(RightForearm_r1, 0.0F, 0.0F, -0.3185F);
		RightForearm_r1.setTextureOffset(54, 87).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

		RightTop_r1 = new ModelRenderer(this);
		RightTop_r1.setRotationPoint(3.0472F, 3.5382F, 0.2375F);
		RightArmTop.addChild(RightTop_r1);
		setRotationAngle(RightTop_r1, 0.0F, 0.0F, -0.3185F);
		RightTop_r1.setTextureOffset(86, 105).addBox(-2.5F, -4.0F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 24.0F, 0.0F);
		Head.setTextureOffset(0, 102).addBox(-4.0F, -41.0F, -4.5F, 8.0F, 8.0F, 8.0F, 0.0F, true);

		LeftArmTop = new ModelRenderer(this);
		LeftArmTop.setRotationPoint(-6.6067F, -5.9004F, -1.2375F);
		setRotationAngle(LeftArmTop, 0.0F, 3.1416F, 0.0F);
		LeftArmTop.setTextureOffset(37, 72).addBox(-0.8433F, -1.0496F, -2.2625F, 5.0F, 2.0F, 5.0F, 0.0F, false);
		LeftArmTop.setTextureOffset(0, 0).addBox(4.0567F, 12.3254F, -2.2125F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		LeftForearm_r1 = new ModelRenderer(this);
		LeftForearm_r1.setRotationPoint(5.2394F, 10.1861F, -0.2625F);
		LeftArmTop.addChild(LeftForearm_r1);
		setRotationAngle(LeftForearm_r1, 0.0F, 0.0F, -0.3185F);
		LeftForearm_r1.setTextureOffset(37, 87).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

		LeftTop_r1 = new ModelRenderer(this);
		LeftTop_r1.setRotationPoint(3.0472F, 3.5382F, 0.2375F);
		LeftArmTop.addChild(LeftTop_r1);
		setRotationAngle(LeftTop_r1, 0.0F, 0.0F, -0.3185F);
		LeftTop_r1.setTextureOffset(65, 105).addBox(-2.5F, -4.0F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		Body.render(matrixStack, buffer, packedLight, packedOverlay);
		RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		RightLegLower.render(matrixStack, buffer, packedLight, packedOverlay);
		RightFoot.render(matrixStack, buffer, packedLight, packedOverlay);
		LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		LeftLegLower.render(matrixStack, buffer, packedLight, packedOverlay);
		LeftFoot.render(matrixStack, buffer, packedLight, packedOverlay);
		RightArmTop.render(matrixStack, buffer, packedLight, packedOverlay);
		Head.render(matrixStack, buffer, packedLight, packedOverlay);
		LeftArmTop.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.LeftLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		this.Head.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.Head.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.RightArmTop.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
		this.RightLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		this.LeftArmTop.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
	}
}