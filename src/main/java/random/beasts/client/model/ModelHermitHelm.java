package random.beasts.client.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class ModelHermitHelm extends BipedModel<LivingEntity> {
    public RendererModel shell1;
    public RendererModel shell2;
    public RendererModel shell3;

    public ModelHermitHelm() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.shell3 = new RendererModel(this, 70, 104);
        this.shell3.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.shell3.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(shell3, 0.0F, 0.0F, -0.2617993877991494F);
        this.bipedLeftArm = new RendererModel(this, 32, 48);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.shell1 = new RendererModel(this, 70, 64);
        this.shell1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shell1.addBox(-5.0F, -8.5F, -5.0F, 10, 7, 10, 0.0F);
        this.bipedHead = new RendererModel(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.bipedLeftLeg = new RendererModel(this, 16, 48);
        this.bipedLeftLeg.setRotationPoint(1.899999976158142F, 12.0F, 0.0F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedRightLeg = new RendererModel(this, 0, 16);
        this.bipedRightLeg.setRotationPoint(-1.899999976158142F, 12.0F, 0.0F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.shell2 = new RendererModel(this, 70, 86);
        this.shell2.setRotationPoint(2.0F, -8.0F, 0.0F);
        this.shell2.addBox(-4.0F, -3.0F, -4.0F, 8, 6, 8, 0.0F);
        this.setRotateAngle(shell2, 0.0F, 0.0F, 0.5235987755982988F);
        this.bipedRightArm = new RendererModel(this, 40, 16);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedBody = new RendererModel(this, 16, 16);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.shell2.addChild(this.shell3);
        this.bipedHead.addChild(this.shell1);
        this.shell1.addChild(this.shell2);
    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
