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
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
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

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.rightleg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		this.rightarm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
		this.leftleg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		this.leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
		this.headbeardhairhat.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.headbeardhairhat.rotateAngleX = f4 / (180F / (float) Math.PI);
	}
}