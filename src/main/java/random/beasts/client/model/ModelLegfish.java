package random.beasts.client.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import random.beasts.common.entity.passive.EntityLegfish;

public class ModelLegfish extends EntityModel<EntityLegfish> {
    public RendererModel body;
    public RendererModel fins;
    public RendererModel head;
    public RendererModel armLeft;
    public RendererModel armRight;
    public RendererModel legLeft;
    public RendererModel legRight;
    public RendererModel tail;

    @Override
    public void render(EntityLegfish entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (((LivingEntity) entity).isChild()) {
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
            GlStateManager.translatef(0.0F, 24.0F * f5, 0.0F);
        }
        if (entity.isSneaking()) GlStateManager.translatef(0.0F, 0.2F, 0.0F);
        this.body.render(f5);

    }

    @Override
    public void setRotationAngles(EntityLegfish entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        float speed = 1.5f, degree = 0.9f;
        this.armLeft.rotateAngleX = MathHelper.cos(limbSwing * speed) * degree * limbSwingAmount;
        this.armRight.rotateAngleX = MathHelper.cos(limbSwing * speed + (float) Math.PI) * degree * limbSwingAmount;
        this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * speed + (float) Math.PI) * degree * limbSwingAmount;
        this.legRight.rotateAngleX = MathHelper.cos(limbSwing * speed) * degree * limbSwingAmount;
        this.tail.rotateAngleY = MathHelper.cos(20 + limbSwing * speed) * degree * limbSwingAmount;
    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public static class Type1 extends ModelLegfish {
        public Type1() {
            this.textureWidth = 64;
            this.textureHeight = 32;
            this.legLeft = new RendererModel(this, 0, 0);
            this.legLeft.setRotationPoint(1.0F, 1.0F, 1.0F);
            this.legLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.fins = new RendererModel(this, 0, 3);
            this.fins.setRotationPoint(0.0F, 0.0F, -3.0F);
            this.fins.addBox(0.0F, -4.0F, 0.0F, 0, 6, 7, 0.0F);
            this.legRight = new RendererModel(this, 0, 0);
            this.legRight.setRotationPoint(-1.0F, 1.0F, 1.0F);
            this.legRight.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.tail = new RendererModel(this, 0, 16);
            this.tail.setRotationPoint(0.0F, 0.0F, 2.0F);
            this.tail.addBox(0.0F, -1.0F, 0.0F, 0, 2, 2, 0.0F);
            this.armLeft = new RendererModel(this, 11, 0);
            this.armLeft.setRotationPoint(1.0F, 1.0F, -1.5F);
            this.armLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.head = new RendererModel(this, 15, 4);
            this.head.setRotationPoint(0.0F, 0.0F, -3.0F);
            this.head.addBox(-0.5F, 0.0F, -3.0F, 1, 2, 3, 0.0F);
            this.body = new RendererModel(this, 0, 0);
            this.body.setRotationPoint(0.0F, 20.0F, 0.0F);
            this.body.addBox(-1.0F, -2.0F, -3.0F, 2, 4, 5, 0.0F);
            this.armRight = new RendererModel(this, 11, 0);
            this.armRight.setRotationPoint(-1.0F, 1.0F, -1.5F);
            this.armRight.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.body.addChild(this.legLeft);
            this.body.addChild(this.fins);
            this.body.addChild(this.legRight);
            this.body.addChild(this.tail);
            this.body.addChild(this.armLeft);
            this.body.addChild(this.head);
            this.body.addChild(this.armRight);
        }
    }

    public static class Type2 extends ModelLegfish {
        public Type2() {
            this.textureWidth = 64;
            this.textureHeight = 32;
            this.armRight = new RendererModel(this, 11, 0);
            this.armRight.setRotationPoint(-1.0F, 0.5F, -1.0F);
            this.armRight.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.legLeft = new RendererModel(this, 0, 0);
            this.legLeft.setRotationPoint(1.0F, 0.5F, 2.0F);
            this.legLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.body = new RendererModel(this, 0, 0);
            this.body.setRotationPoint(0.0F, 20.0F, 0.0F);
            this.body.addBox(-1.0F, -1.5F, -3.0F, 2, 3, 6, 0.0F);
            this.fins = new RendererModel(this, 0, 3);
            this.fins.setRotationPoint(0.0F, -1.5F, -3.0F);
            this.fins.addBox(0.0F, -3.0F, 0.0F, 0, 3, 6, 0.0F);
            this.armLeft = new RendererModel(this, 11, 0);
            this.armLeft.setRotationPoint(1.0F, 0.5F, -1.0F);
            this.armLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.legRight = new RendererModel(this, 0, 0);
            this.legRight.setRotationPoint(-1.0F, 0.5F, 2.0F);
            this.legRight.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.tail = new RendererModel(this, 0, 16);
            this.tail.setRotationPoint(0.0F, 0.0F, 3.0F);
            this.tail.addBox(0.0F, -1.5F, 0.0F, 0, 3, 4, 0.0F);
            this.body.addChild(this.armRight);
            this.body.addChild(this.legLeft);
            this.body.addChild(this.fins);
            this.body.addChild(this.armLeft);
            this.body.addChild(this.legRight);
            this.body.addChild(this.tail);
        }
    }

    public static class Type3 extends ModelLegfish {
        public Type3() {
            this.textureWidth = 64;
            this.textureHeight = 32;
            this.legLeft = new RendererModel(this, 0, 0);
            this.legLeft.setRotationPoint(1.0F, 0.5F, 1.5F);
            this.legLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.armLeft = new RendererModel(this, 11, 0);
            this.armLeft.setRotationPoint(1.0F, 0.5F, -0.5F);
            this.armLeft.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.body = new RendererModel(this, 0, 0);
            this.body.setRotationPoint(0.0F, 20.0F, 0.0F);
            this.body.addBox(-1.0F, -1.5F, -2.0F, 2, 3, 4, 0.0F);
            this.fins = new RendererModel(this, 0, 3);
            this.fins.setRotationPoint(0.0F, -1.5F, 0.5F);
            this.fins.addBox(0.0F, -4.0F, -2.5F, 0, 4, 5, 0.0F);
            this.armRight = new RendererModel(this, 11, 0);
            this.armRight.setRotationPoint(-1.0F, 0.5F, -0.5F);
            this.armRight.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.legRight = new RendererModel(this, 0, 0);
            this.legRight.setRotationPoint(-1.0F, 0.5F, 1.5F);
            this.legRight.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
            this.tail = new RendererModel(this, 0, 16);
            this.tail.setRotationPoint(0.0F, 0.0F, 2.0F);
            this.tail.addBox(0.0F, -1.5F, 0.0F, 0, 3, 4, 0.0F);
            this.body.addChild(this.legLeft);
            this.body.addChild(this.armLeft);
            this.body.addChild(this.fins);
            this.body.addChild(this.armRight);
            this.body.addChild(this.legRight);
            this.body.addChild(this.tail);
        }
    }
}
