package random.beasts.client.renderer.entity;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import random.beasts.client.init.BeastsSounds;
import random.beasts.client.model.ModelAnglerQueen;
import random.beasts.client.renderer.entity.layers.LayerAnglerQueenGlow;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.monster.EntityAnglerQueen;

@OnlyIn(Dist.CLIENT)
public class RenderAnglerQueen extends MobRenderer<EntityAnglerQueen, ModelAnglerQueen> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/angler_queen/angler_queen.png");
    private static final ResourceLocation TEXTURE2 = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/angler_queen/angler_queen_2.png");
    private static final ResourceLocation QUEEN_BEAM_TEXTURE = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/angler_queen/angler_queen_beam.png");

    public RenderAnglerQueen(EntityRendererManager rm) {
        super(rm, new ModelAnglerQueen(), 1.0F);
        this.addLayer(new LayerAnglerQueenGlow(this));
    }

    public boolean shouldRender(EntityAnglerQueen livingEntity, ICamera camera, double camX, double camY, double camZ) {
        if (super.shouldRender(livingEntity, camera, camX, camY, camZ)) {
            return true;
        }
        else {
            if (livingEntity.isUsingBeam()) {
                double rot = livingEntity.renderYawOffset * 0.01745329238474369D + (Math.PI / 2D);
                double xMod = Math.cos(rot) * (double) (livingEntity.getWidth() + 1f);
                double zMod = Math.sin(rot) * (double) (livingEntity.getWidth() + 1f);
                double yMod = livingEntity.getHeight() + 1f;
                Vec3d vec3d1 = this.getPosition(livingEntity, xMod, yMod, zMod, 1.0F);

                Vec3d laserAngle = Vec3d.fromPitchYaw(livingEntity.getLaserPitch(), livingEntity.getLaserYaw());
                double range = 30d;
                Vec3d hitVec = vec3d1.add(laserAngle.scale(range));

                RayTraceResult trace = livingEntity.world.rayTraceBlocks(new RayTraceContext(vec3d1, hitVec, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, livingEntity));
                if (trace.getType() != RayTraceResult.Type.MISS)
                    hitVec = trace.getHitVec();

                Vec3d vec3d = hitVec;

                return camera.isBoundingBoxInFrustum(new AxisAlignedBB(vec3d1.x, vec3d1.y, vec3d1.z, vec3d.x, vec3d.y, vec3d.z).grow(0.5d));
            }
            return false;
        }
    }

    private Vec3d getPosition(LivingEntity entityLivingBaseIn, double xMod, double yMod, double zMod, float partialTicks) {
        double d0 = xMod + entityLivingBaseIn.lastTickPosX + (entityLivingBaseIn.posX - entityLivingBaseIn.lastTickPosX) * (double) partialTicks;
        double d1 = yMod + entityLivingBaseIn.lastTickPosY + (entityLivingBaseIn.posY - entityLivingBaseIn.lastTickPosY) * (double) partialTicks;
        double d2 = zMod + entityLivingBaseIn.lastTickPosZ + (entityLivingBaseIn.posZ - entityLivingBaseIn.lastTickPosZ) * (double) partialTicks;
        return new Vec3d(d0, d1, d2);
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityAnglerQueen entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        double rot = this.interpolateValue(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks) * 0.01745329238474369D + (Math.PI / 2D);
        double xMod = Math.cos(rot) * (double) (entity.getWidth() + 1f);
        double zMod = Math.sin(rot) * (double) (entity.getWidth() + 1f);
        double yMod = entity.getHeight() + 0.8f;
        Vec3d lurePos = this.getPosition(entity, xMod, yMod, zMod, partialTicks);
        if (entity.isChargingBeam()) {
            if (entity.getRNG().nextBoolean() && entity.getRNG().nextBoolean()) {
                double alfa = entity.getRNG().nextDouble() * 2 * Math.PI;
                double beta = entity.getRNG().nextDouble() * 2 * Math.PI;
                double gamma = entity.getRNG().nextDouble() * 2 * Math.PI;
                double distance = 0.6d;
                double x2 = distance * Math.cos(alfa);
                double z2 = distance * Math.cos(beta);
                double y2 = distance * Math.cos(gamma);

                entity.world.addParticle(ParticleTypes.END_ROD, lurePos.x + x2, lurePos.y + y2, lurePos.z + z2, -x2 / 15d, -y2 / 15d, -z2 / 15d);
            }
        }
        if (entity.isUsingBeam()) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            this.bindTexture(QUEEN_BEAM_TEXTURE);
            GlStateManager.texParameter(3553, 10242, 10497);
            GlStateManager.texParameter(3553, 10243, 10497);
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            float f1 = 240.0F;
            GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240.0F, 240.0F);
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            float f2 = (float) entity.world.getGameTime() + partialTicks;
            float f3 = f2 * 0.5F % 1.0F;
            GlStateManager.pushMatrix();

            GlStateManager.translated(x + xMod, y + yMod, z + zMod);

            Vec3d laserAngle = Vec3d.fromPitchYaw(this.interpolateRotation(entity.getPrevLaserPitch(), entity.getLaserPitch(), partialTicks),
                    this.interpolateRotation(entity.getPrevLaserYaw(), entity.getLaserYaw(), partialTicks));
            double range = 30d;
            Vec3d hitVec = lurePos.add(laserAngle.scale(range));

            RayTraceResult trace = entity.world.rayTraceBlocks(new RayTraceContext(lurePos, hitVec, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity));
            if (trace.getType() != RayTraceResult.Type.MISS) {
                hitVec = trace.getHitVec();
                for (int i = 0; i < 4; ++i) {
                    entity.world.addParticle(ParticleTypes.SMOKE, hitVec.x, hitVec.y, hitVec.z, 0, 0, 0);
                }
                if (entity.ticksExisted % 15 == 0)
                    entity.world.playSound(hitVec.x, hitVec.y, hitVec.z, BeastsSounds.ANGLER_QUEEN_BEAM, SoundCategory.HOSTILE, 1f, 1f, false);
            }

            Vec3d vec3d = hitVec;

            Vec3d vec3d2 = vec3d.subtract(lurePos);
            double d0 = vec3d2.length() + 1.0D;
            vec3d2 = vec3d2.normalize();
            float f5 = (float) Math.acos(vec3d2.y);
            float f6 = (float) Math.atan2(vec3d2.z, vec3d2.x);
            GlStateManager.rotatef((((float) Math.PI / 2F) + -f6) * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotatef(f5 * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
            int i = 1;
            double d1 = (double) f2 * 0.05D * -1.5D;
            bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            int j = 255;
            int k = 255;
            int l = 255;
            double d2 = 0.2D;
            double d3 = 0.282D;
            double d4 = 0.0D + Math.cos(d1 + 2.356194490192345D) * d3;
            double d5 = 0.0D + Math.sin(d1 + 2.356194490192345D) * d3;
            double d6 = 0.0D + Math.cos(d1 + (Math.PI / 4D)) * d3;
            double d7 = 0.0D + Math.sin(d1 + (Math.PI / 4D)) * d3;
            double d8 = 0.0D + Math.cos(d1 + 3.9269908169872414D) * d3;
            double d9 = 0.0D + Math.sin(d1 + 3.9269908169872414D) * d3;
            double d10 = 0.0D + Math.cos(d1 + 5.497787143782138D) * d3;
            double d11 = 0.0D + Math.sin(d1 + 5.497787143782138D) * d3;
            double d12 = 0.0D + Math.cos(d1 + Math.PI) * d2;
            double d13 = 0.0D + Math.sin(d1 + Math.PI) * d2;
            double d14 = 0.0D + Math.cos(d1 + 0.0D) * d2;
            double d15 = 0.0D + Math.sin(d1 + 0.0D) * d2;
            double d16 = 0.0D + Math.cos(d1 + (Math.PI / 2D)) * 0.2D;
            double d17 = 0.0D + Math.sin(d1 + (Math.PI / 2D)) * 0.2D;
            double d18 = 0.0D + Math.cos(d1 + (Math.PI * 3D / 2D)) * d2;
            double d19 = 0.0D + Math.sin(d1 + (Math.PI * 3D / 2D)) * d2;
            double d20 = 0.0D;
            double d21 = 0.4999D;
            double d22 = -1.0F + f3;
            double d23 = d0 * 2.5D + d22;
            bufferbuilder.pos(d12, d0, d13).tex(d21, d23).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d12, d20, d13).tex(d21, d22).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d14, d20, d15).tex(d20, d22).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d14, d0, d15).tex(d20, d23).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d16, d0, d17).tex(d21, d23).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d16, d20, d17).tex(d21, d22).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d18, d20, d19).tex(d20, d22).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d18, d0, d19).tex(d20, d23).color(j, k, l, 255).endVertex();
            double d24 = 0.0D;

            if (entity.ticksExisted % 2 == 0) {
                d24 = 0.5D;
            }

            bufferbuilder.pos(d4, d0, d5).tex(0.5D, d24 + 0.5D).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d6, d0, d7).tex(1.0D, d24 + 0.5D).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d10, d0, d11).tex(1.0D, d24).color(j, k, l, 255).endVertex();
            bufferbuilder.pos(d8, d0, d9).tex(0.5D, d24).color(j, k, l, 255).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
        }
    }

    protected float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
        float f;

        f = yawOffset - prevYawOffset;
        while (f < -180.0F) {
            f += 360.0F;
        }

        while (f >= 180.0F) {
            f -= 360.0F;
        }

        return prevYawOffset + partialTicks * f;
    }
    
    private double interpolateValue(double start, double end, double pct)
    {
        return start + (end - start) * pct;
    }

    protected ResourceLocation getEntityTexture(EntityAnglerQueen entity) {
        return TEXTURE;
    }
}