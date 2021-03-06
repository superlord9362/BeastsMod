package random.beasts.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import random.beasts.common.entity.passive.EntityButterflyFish;

public class ModelButterflyFish extends EntityModel<EntityButterflyFish> {
    public RendererModel Body;
    public RendererModel Tailbase;
    public RendererModel Dorsalfinright;
    public RendererModel Dorsalfinleft;
    public RendererModel Pectoralfinleft;
    public RendererModel Pectoralfinright;
    public RendererModel Antennae;
    public RendererModel Pelvicfinright;
    public RendererModel Pelvicfinleft;
    public RendererModel Tailfin;

    public ModelButterflyFish() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Pectoralfinleft = new RendererModel(this, 35, -2);
        this.Pectoralfinleft.mirror = true;
        this.Pectoralfinleft.setRotationPoint(1.0F, 1.5F, -1.5F);
        this.Pectoralfinleft.addBox(0.0F, 0.0F, -1.5F, 0, 5, 3, 0.0F);
        this.Pelvicfinright = new RendererModel(this, 29, 4);
        this.Pelvicfinright.setRotationPoint(-0.5F, 1.5F, 2.0F);
        this.Pelvicfinright.addBox(0.0F, 0.0F, -1.0F, 0, 2, 3, 0.0F);
        this.Tailfin = new RendererModel(this, 24, -3);
        this.Tailfin.setRotationPoint(0.0F, -0.5F, 3.5F);
        this.Tailfin.addBox(0.0F, -2.0F, -0.5F, 0, 4, 4, 0.0F);
        this.Pectoralfinright = new RendererModel(this, 42, -2);
        this.Pectoralfinright.setRotationPoint(-1.0F, 1.5F, -1.5F);
        this.Pectoralfinright.addBox(0.0F, 0.0F, -1.5F, 0, 5, 3, 0.0F);
        this.Pelvicfinleft = new RendererModel(this, 24, 4);
        this.Pelvicfinleft.mirror = true;
        this.Pelvicfinleft.setRotationPoint(0.5F, 1.5F, 2.0F);
        this.Pelvicfinleft.addBox(0.0F, 0.0F, -1.0F, 0, 2, 3, 0.0F);
        this.Body = new RendererModel(this, 0, 0);
        this.Body.setRotationPoint(0.0F, 22.5F, -1.5F);
        this.Body.addBox(-1.5F, -1.5F, -4.0F, 3, 3, 8, 0.0F);
        this.Dorsalfinleft = new RendererModel(this, 0, 5);
        this.Dorsalfinleft.mirror = true;
        this.Dorsalfinleft.setRotationPoint(1.0F, -1.5F, 0.0F);
        this.Dorsalfinleft.addBox(0.0F, -9.0F, -3.5F, 0, 9, 7, 0.0F);
        this.Tailbase = new RendererModel(this, 15, 0);
        this.Tailbase.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Tailbase.addBox(-0.5F, -1.0F, 0.0F, 1, 2, 3, 0.0F);
        this.Dorsalfinright = new RendererModel(this, 16, 5);
        this.Dorsalfinright.setRotationPoint(-1.0F, -1.5F, 0.0F);
        this.Dorsalfinright.addBox(0.0F, -9.0F, -3.5F, 0, 9, 7, 0.0F);
        this.Antennae = new RendererModel(this, 0, 0);
        this.Antennae.setRotationPoint(0.0F, -1.5F, -4.0F);
        this.Antennae.addBox(-0.5F, -3.0F, -2.0F, 1, 3, 2, 0.0F);
        this.Body.addChild(this.Pectoralfinleft);
        this.Body.addChild(this.Pelvicfinright);
        this.Tailbase.addChild(this.Tailfin);
        this.Body.addChild(this.Pectoralfinright);
        this.Body.addChild(this.Pelvicfinleft);
        this.Body.addChild(this.Dorsalfinleft);
        this.Body.addChild(this.Tailbase);
        this.Body.addChild(this.Dorsalfinright);
        this.Body.addChild(this.Antennae);
    }

    @Override
    public void render(EntityButterflyFish entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.Body.render(f5);
    }

    float speed = 1.0f;
    float degree = 1.0f;

    @Override
    public void setRotationAngles(EntityButterflyFish entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entityIn, f, f1, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        this.Pelvicfinright.rotateAngleZ = MathHelper.cos((f * speed * 1.0F) + (float) Math.PI) * (degree * 1.0F) * f1 * 0.5F + 1.0F;
        this.Pelvicfinleft.rotateAngleZ = MathHelper.cos((f * speed * 1.0F) + (float) Math.PI) * (degree * -1.0F) * f1 * 0.5F + -1.0F;
        this.Pectoralfinleft.rotateAngleZ = MathHelper.cos(3.0F + (f * speed * 1.0F) + (float) Math.PI) * (degree * -1.0F) * f1 * 0.5F + -1.0F;
        this.Pectoralfinright.rotateAngleZ = MathHelper.cos(3.0F + (f * speed * 1.0F) + (float) Math.PI) * (degree * 1.0F) * f1 * 0.5F + 1.0F;
        this.Dorsalfinright.rotateAngleZ = MathHelper.cos((f * speed * 1.0F) + (float) Math.PI) * (degree * 3.0F) * f1 * 0.5F + -1.0F;
        this.Dorsalfinleft.rotateAngleZ = MathHelper.cos((f * speed * 1.0F) + (float) Math.PI) * (degree * -3.0F) * f1 * 0.5F + 1.0F;
        this.Tailbase.rotateAngleY = MathHelper.cos((f * speed * 1.0F) + (float) Math.PI) * (degree * 0.5F) * f1 * 0.5F;
        this.Tailfin.rotateAngleY = MathHelper.cos((f * speed * 1.0F) + (float) Math.PI) * (degree * 1.0F) * f1 * 0.5F;

    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
