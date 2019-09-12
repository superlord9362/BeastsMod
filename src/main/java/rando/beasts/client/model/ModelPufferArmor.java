package rando.beasts.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPufferArmor extends ModelBiped {
    public ModelRenderer right;
    public ModelRenderer strap;
    public ModelRenderer spikes;
    public ModelRenderer spikes_1;
    public ModelRenderer right_1;
    public ModelRenderer footRight;
    public ModelRenderer spikes_2;
    public ModelRenderer spikes_3;
    public ModelRenderer strap_1;
    public ModelRenderer spikes_4;
    public ModelRenderer spikes_5;
    public ModelRenderer head;
    public ModelRenderer spikes_6;
    public ModelRenderer spikes_7;
    public ModelRenderer spikes_8;
    public ModelRenderer spikes_9;
    public ModelRenderer strapRight;
    public ModelRenderer strapLeft;
    public ModelRenderer spikes_10;
    public ModelRenderer chestplate;
    public ModelRenderer strapLeft_1;
    public ModelRenderer strapRight_1;
    public ModelRenderer spikes_11;
    public ModelRenderer spikes_12;
    public ModelRenderer spikes_13;
    public ModelRenderer spikes_14;
    public ModelRenderer left;
    public ModelRenderer strap_2;
    public ModelRenderer spikes_15;
    public ModelRenderer spikes_16;
    public ModelRenderer left_1;
    public ModelRenderer footLeft;
    public ModelRenderer spikes_17;
    public ModelRenderer spikes_18;
    public ModelRenderer strap_3;
    public ModelRenderer spikes_19;
    public ModelRenderer spikes_20;

    public ModelPufferArmor() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.strapRight_1 = new ModelRenderer(this, 68, 10);
        this.strapRight_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strapRight_1.addBox(-3.0F, 0.0F, -2.5F, 1, 6, 5, 0.0F);
        this.strap_1 = new ModelRenderer(this, 79, 31);
        this.strap_1.setRotationPoint(-1.0F, 1.0F, 0.0F);
        this.strap_1.addBox(-2.0F, 3.0F, -2.5F, 4, 2, 5, 0.0F);
        this.spikes_7 = new ModelRenderer(this, 112, -1);
        this.spikes_7.setRotationPoint(-5.0F, -7.0F, -5.0F);
        this.spikes_7.addBox(0.0F, -2.0F, -1.0F, 0, 4, 1, 0.0F);
        this.setRotateAngle(spikes_7, 0.0F, 0.8726646259971648F, 0.0F);
        this.spikes_6 = new ModelRenderer(this, 69, 2);
        this.spikes_6.setRotationPoint(0.0F, -5.0F, -5.0F);
        this.spikes_6.addBox(-5.0F, 0.0F, 0.0F, 10, 1, 0, 0.0F);
        this.setRotateAngle(spikes_6, -0.8726646259971648F, 0.0F, 0.0F);
        this.spikes_12 = new ModelRenderer(this, 69, 3);
        this.spikes_12.setRotationPoint(4.5F, 9.0F, -3.5F);
        this.spikes_12.addBox(0.0F, -3.0F, -1.0F, 0, 7, 1, 0.0F);
        this.setRotateAngle(spikes_12, 0.0F, -0.8726646259971648F, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(bipedRightArm, 0.0F, 0.0F, 0.10000000149011613F);
        this.left = new ModelRenderer(this, 97, 15);
        this.left.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.left.addBox(1.0F, 1.0F, -3.0F, 3, 6, 6, 0.0F);
        this.chestplate = new ModelRenderer(this, 91, 41);
        this.chestplate.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.chestplate.addBox(-4.5F, 6.0F, -3.5F, 9, 7, 7, 0.0F);
        this.spikes_10 = new ModelRenderer(this, 72, 8);
        this.spikes_10.setRotationPoint(0.0F, -6.5F, -5.0F);
        this.spikes_10.addBox(-1.5F, -1.0F, 0.0F, 3, 1, 0, 0.0F);
        this.setRotateAngle(spikes_10, 0.8726646259971648F, 0.0F, 0.0F);
        this.spikes_9 = new ModelRenderer(this, 69, 0);
        this.spikes_9.setRotationPoint(0.0F, -9.0F, -5.0F);
        this.spikes_9.addBox(-5.0F, -1.0F, 0.0F, 10, 1, 0, 0.0F);
        this.setRotateAngle(spikes_9, 0.8726646259971648F, 0.0F, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 16, 16);
        this.bipedRightLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedRightLeg.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.spikes = new ModelRenderer(this, 68, 4);
        this.spikes.setRotationPoint(-4.0F, 4.0F, 3.0F);
        this.spikes.addBox(-1.0F, -3.0F, 0.0F, 1, 6, 0, 0.0F);
        this.setRotateAngle(spikes, 0.0F, 0.8726646259971648F, 0.0F);
        this.spikes_18 = new ModelRenderer(this, 114, -1);
        this.spikes_18.setRotationPoint(-2.0F, 5.0F, -3.0F);
        this.spikes_18.addBox(0.0F, -3.0F, -1.0F, 0, 6, 1, 0.0F);
        this.setRotateAngle(spikes_18, 0.0F, 0.8726646259971648F, 0.0F);
        this.footRight = new ModelRenderer(this, 68, 41);
        this.footRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.footRight.addBox(-2.5F, 8.1F, -2.5F, 5, 4, 5, 0.0F);
        this.setRotateAngle(footRight, 0.0F, 0.03490658503988659F, 0.0F);
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.spikes_11 = new ModelRenderer(this, 69, 3);
        this.spikes_11.setRotationPoint(-4.5F, 9.0F, -3.5F);
        this.spikes_11.addBox(0.0F, -3.0F, -1.0F, 0, 7, 1, 0.0F);
        this.setRotateAngle(spikes_11, 0.0F, 0.8726646259971648F, 0.0F);
        this.spikes_8 = new ModelRenderer(this, 112, -1);
        this.spikes_8.setRotationPoint(5.0F, -7.0F, -5.0F);
        this.spikes_8.addBox(0.0F, -2.0F, -1.0F, 0, 4, 1, 0.0F);
        this.setRotateAngle(spikes_8, 0.0F, -0.8726646259971648F, 0.0F);
        this.strapLeft = new ModelRenderer(this, 86, 3);
        this.strapLeft.mirror = true;
        this.strapLeft.setRotationPoint(4.5F, -5.0F, 0.0F);
        this.strapLeft.addBox(0.0F, 0.0F, -0.5F, 0, 3, 1, 0.0F);
        this.spikes_15 = new ModelRenderer(this, 68, 4);
        this.spikes_15.setRotationPoint(4.0F, 4.0F, 3.0F);
        this.spikes_15.addBox(0.0F, -3.0F, 0.0F, 1, 6, 0, 0.0F);
        this.setRotateAngle(spikes_15, 0.0F, -0.8726646259971648F, 0.0F);
        this.spikes_2 = new ModelRenderer(this, 114, -1);
        this.spikes_2.setRotationPoint(2.5F, 5.0F, -3.0F);
        this.spikes_2.addBox(0.0F, -3.0F, -1.0F, 0, 6, 1, 0.0F);
        this.setRotateAngle(spikes_2, 0.0F, -0.8726646259971648F, 0.0F);
        this.strap_2 = new ModelRenderer(this, 80, 20);
        this.strap_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strap_2.addBox(-1.5F, 3.0F, -2.5F, 3, 2, 5, 0.0F);
        this.spikes_3 = new ModelRenderer(this, 114, -1);
        this.spikes_3.setRotationPoint(-2.5F, 5.0F, -3.0F);
        this.spikes_3.addBox(0.0F, -3.0F, -1.0F, 0, 6, 1, 0.0F);
        this.setRotateAngle(spikes_3, 0.0F, 0.8726646259971648F, 0.0F);
        this.strapRight = new ModelRenderer(this, 86, 3);
        this.strapRight.setRotationPoint(-4.5F, -5.0F, 0.0F);
        this.strapRight.addBox(0.0F, 0.0F, -0.5F, 0, 3, 1, 0.0F);
        this.right = new ModelRenderer(this, 97, 15);
        this.right.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.right.addBox(-4.0F, 1.0F, -3.0F, 3, 6, 6, 0.0F);
        this.spikes_5 = new ModelRenderer(this, 116, -1);
        this.spikes_5.setRotationPoint(-2.5F, 10.1F, -2.5F);
        this.spikes_5.addBox(0.0F, -2.0F, -1.0F, 0, 4, 1, 0.0F);
        this.setRotateAngle(spikes_5, 0.0F, 0.8726646259971648F, 0.0F);
        this.left_1 = new ModelRenderer(this, 98, 29);
        this.left_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.left_1.addBox(-2.0F, 2.0F, -3.0F, 5, 6, 3, 0.0F);
        this.setRotateAngle(left_1, 0.0F, -0.08726646259971647F, 0.0F);
        this.spikes_14 = new ModelRenderer(this, 69, 3);
        this.spikes_14.setRotationPoint(-4.5F, 9.0F, 3.5F);
        this.spikes_14.addBox(0.0F, -3.0F, 0.0F, 0, 7, 1, 0.0F);
        this.setRotateAngle(spikes_14, 0.0F, -0.8726646259971648F, 0.0F);
        this.spikes_19 = new ModelRenderer(this, 116, -1);
        this.spikes_19.setRotationPoint(2.5F, 10.1F, 2.5F);
        this.spikes_19.addBox(0.0F, -2.0F, 0.0F, 0, 4, 1, 0.0F);
        this.setRotateAngle(spikes_19, 0.0F, 0.8726646259971648F, 0.0F);
        this.spikes_4 = new ModelRenderer(this, 116, -1);
        this.spikes_4.setRotationPoint(-2.5F, 10.1F, 2.5F);
        this.spikes_4.addBox(0.0F, -2.0F, 0.0F, 0, 4, 1, 0.0F);
        this.setRotateAngle(spikes_4, 0.0F, -0.8726646259971648F, 0.0F);
        this.spikes_20 = new ModelRenderer(this, 116, -1);
        this.spikes_20.setRotationPoint(2.5F, 10.1F, -2.5F);
        this.spikes_20.addBox(0.0F, -2.0F, -1.0F, 0, 4, 1, 0.0F);
        this.setRotateAngle(spikes_20, 0.0F, -0.8726646259971648F, 0.0F);
        this.spikes_1 = new ModelRenderer(this, 69, 4);
        this.spikes_1.setRotationPoint(-4.0F, 4.0F, -3.0F);
        this.spikes_1.addBox(-1.0F, -3.0F, 0.0F, 1, 6, 0, 0.0F);
        this.setRotateAngle(spikes_1, 0.0F, -0.8726646259971648F, 0.0F);
        this.strapLeft_1 = new ModelRenderer(this, 68, 10);
        this.strapLeft_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strapLeft_1.addBox(2.0F, 0.0F, -2.5F, 1, 6, 5, 0.0F);
        this.spikes_17 = new ModelRenderer(this, 114, -1);
        this.spikes_17.setRotationPoint(3.0F, 4.0F, -3.0F);
        this.spikes_17.addBox(0.0F, -2.0F, -1.0F, 0, 6, 1, 0.0F);
        this.setRotateAngle(spikes_17, 0.0F, -0.8726646259971648F, 0.0F);
        this.footLeft = new ModelRenderer(this, 68, 41);
        this.footLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.footLeft.addBox(-2.5F, 8.1F, -2.5F, 5, 4, 5, 0.0F);
        this.setRotateAngle(footLeft, 0.0F, -0.03490658503988659F, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.setRotationPoint(-1.899999976158142F, 12.0F, 0.10000000149011612F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.strap = new ModelRenderer(this, 80, 20);
        this.strap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strap.addBox(-1.5F, 3.0F, -2.5F, 3, 2, 5, 0.0F);
        this.spikes_16 = new ModelRenderer(this, 69, 4);
        this.spikes_16.setRotationPoint(4.0F, 4.0F, -3.0F);
        this.spikes_16.addBox(0.0F, -3.0F, 0.0F, 1, 6, 0, 0.0F);
        this.setRotateAngle(spikes_16, 0.0F, 0.8726646259971648F, 0.0F);
        this.spikes_13 = new ModelRenderer(this, 69, 3);
        this.spikes_13.setRotationPoint(4.5F, 9.0F, 3.5F);
        this.spikes_13.addBox(0.0F, -3.0F, 0.0F, 0, 7, 1, 0.0F);
        this.setRotateAngle(spikes_13, 0.0F, 0.8726646259971648F, 0.0F);
        this.strap_3 = new ModelRenderer(this, 79, 31);
        this.strap_3.setRotationPoint(0.5F, 1.0F, 0.0F);
        this.strap_3.addBox(-2.0F, 3.0F, -2.5F, 4, 2, 5, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 32, 48);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(bipedLeftArm, 0.0F, 0.0F, -0.10000000149011613F);
        this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
        this.bipedLeftLeg.setRotationPoint(1.899999976158142F, 12.0F, 0.10000000149011612F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.right_1 = new ModelRenderer(this, 98, 29);
        this.right_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.right_1.addBox(-2.5F, 2.0F, -3.0F, 5, 6, 3, 0.0F);
        this.setRotateAngle(right_1, 0.0F, 0.08726646259971647F, 0.0F);
        this.head = new ModelRenderer(this, 80, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-5.0F, -9.0F, -5.0F, 10, 4, 10, 0.0F);
        this.chestplate.addChild(this.strapRight_1);
        this.right_1.addChild(this.strap_1);
        this.head.addChild(this.spikes_7);
        this.head.addChild(this.spikes_6);
        this.chestplate.addChild(this.spikes_12);
        this.bipedLeftArm.addChild(this.left);
        this.bipedRightLeg.addChild(this.chestplate);
        this.head.addChild(this.spikes_10);
        this.head.addChild(this.spikes_9);
        this.right.addChild(this.spikes);
        this.left_1.addChild(this.spikes_18);
        this.bipedRightLeg.addChild(this.footRight);
        this.chestplate.addChild(this.spikes_11);
        this.head.addChild(this.spikes_8);
        this.head.addChild(this.strapLeft);
        this.left.addChild(this.spikes_15);
        this.right_1.addChild(this.spikes_2);
        this.left.addChild(this.strap_2);
        this.right_1.addChild(this.spikes_3);
        this.head.addChild(this.strapRight);
        this.bipedRightLeg.addChild(this.right);
        this.footRight.addChild(this.spikes_5);
        this.bipedLeftLeg.addChild(this.left_1);
        this.chestplate.addChild(this.spikes_14);
        this.footLeft.addChild(this.spikes_19);
        this.footRight.addChild(this.spikes_4);
        this.footLeft.addChild(this.spikes_20);
        this.right.addChild(this.spikes_1);
        this.chestplate.addChild(this.strapLeft_1);
        this.left_1.addChild(this.spikes_17);
        this.bipedLeftLeg.addChild(this.footLeft);
        this.right.addChild(this.strap);
        this.left.addChild(this.spikes_16);
        this.chestplate.addChild(this.spikes_13);
        this.left_1.addChild(this.strap_3);
        this.bipedRightLeg.addChild(this.right_1);
        this.bipedHead.addChild(this.head);
    }

    /*@Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.bipedRightLeg.render(f5);
        this.bipedRightLeg.render(f5);
        this.bipedHead.render(f5);
        this.bipedRightLeg.render(f5);
        this.bipedLeftArm.render(f5);
        this.bipedLeftLeg.render(f5);
    }*/

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}