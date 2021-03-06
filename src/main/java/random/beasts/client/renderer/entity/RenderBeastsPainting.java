package random.beasts.client.renderer.entity;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.item.EntityBeastsPainting;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class RenderBeastsPainting extends EntityRenderer<EntityBeastsPainting> {
    private static final Map<EntityBeastsPainting.BeastsPainting, ResourceLocation> TEXTURES = new HashMap<>();

    public RenderBeastsPainting(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    public void doRender(EntityBeastsPainting entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translated(x, y, z);
        GlStateManager.rotatef(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.enableRescaleNormal();
        this.bindEntityTexture(entity);
        float f = 0.0625F;
        GlStateManager.scalef(f, f, f);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.setupSolidRenderingTextureCombine(this.getTeamColor(entity));
        }
        this.renderBeastsPainting(entity, entity.art.sizeX, entity.art.sizeY);

        if (this.renderOutlines) {
            GlStateManager.tearDownSolidRenderingTextureCombine();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    protected ResourceLocation getEntityTexture(EntityBeastsPainting entity) {
        return TEXTURES.putIfAbsent(entity.art, new ResourceLocation(BeastsMod.MOD_ID, "textures/painting/" + entity.art.title.toLowerCase().replace(" ", "_") + ".png"));
    }

    private void renderBeastsPainting(EntityBeastsPainting painting, int width, int height) {
        float f = (float) (-width) / 2.0F;
        float f1 = (float) (-height) / 2.0F;

        for (int i = 0; i < width / 16; ++i) {
            for (int j = 0; j < height / 16; ++j) {
                float f15 = f + (float) ((i + 1) * 16);
                float f16 = f + (float) (i * 16);
                float f17 = f1 + (float) ((j + 1) * 16);
                float f18 = f1 + (float) (j * 16);
                this.setLightmap(painting, (f15 + f16) / 2.0F, (f17 + f18) / 2.0F);
                float f19 = (float) (width - i * 16) / width;
                float f20 = (float) (width - (i + 1) * 16) / width;
                float f21 = (float) (height - j * 16) / height;
                float f22 = (float) (height - (j + 1) * 16) / height;
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
                bufferbuilder.pos(f15, f18, -0.5D).tex(f20, f21).normal(0.0F, 0.0F, -1.0F).endVertex();
                bufferbuilder.pos(f16, f18, -0.5D).tex(f19, f21).normal(0.0F, 0.0F, -1.0F).endVertex();
                bufferbuilder.pos(f16, f17, -0.5D).tex(f19, f22).normal(0.0F, 0.0F, -1.0F).endVertex();
                bufferbuilder.pos(f15, f17, -0.5D).tex(f20, f22).normal(0.0F, 0.0F, -1.0F).endVertex();
                bufferbuilder.pos(f15, f17, 0.5D).tex(0.75D, 0.0D).normal(0.0F, 0.0F, 1.0F).endVertex();
                bufferbuilder.pos(f16, f17, 0.5D).tex(0.8125D, 0.0D).normal(0.0F, 0.0F, 1.0F).endVertex();
                bufferbuilder.pos(f16, f18, 0.5D).tex(0.8125D, 0.0625D).normal(0.0F, 0.0F, 1.0F).endVertex();
                bufferbuilder.pos(f15, f18, 0.5D).tex(0.75D, 0.0625D).normal(0.0F, 0.0F, 1.0F).endVertex();
                bufferbuilder.pos(f15, f17, -0.5D).tex(0.75D, 0.001953125D).normal(0.0F, 1.0F, 0.0F).endVertex();
                bufferbuilder.pos(f16, f17, -0.5D).tex(0.8125D, 0.001953125D).normal(0.0F, 1.0F, 0.0F).endVertex();
                bufferbuilder.pos(f16, f17, 0.5D).tex(0.8125D, 0.001953125D).normal(0.0F, 1.0F, 0.0F).endVertex();
                bufferbuilder.pos(f15, f17, 0.5D).tex(0.75D, 0.001953125D).normal(0.0F, 1.0F, 0.0F).endVertex();
                bufferbuilder.pos(f15, f18, 0.5D).tex(0.75D, 0.001953125D).normal(0.0F, -1.0F, 0.0F).endVertex();
                bufferbuilder.pos(f16, f18, 0.5D).tex(0.8125D, 0.001953125D).normal(0.0F, -1.0F, 0.0F).endVertex();
                bufferbuilder.pos(f16, f18, -0.5D).tex(0.8125D, 0.001953125D).normal(0.0F, -1.0F, 0.0F).endVertex();
                bufferbuilder.pos(f15, f18, -0.5D).tex(0.75D, 0.001953125D).normal(0.0F, -1.0F, 0.0F).endVertex();
                bufferbuilder.pos(f15, f17, 0.5D).tex(0.751953125D, 0.0D).normal(-1.0F, 0.0F, 0.0F).endVertex();
                bufferbuilder.pos(f15, f18, 0.5D).tex(0.751953125D, 0.0625D).normal(-1.0F, 0.0F, 0.0F).endVertex();
                bufferbuilder.pos(f15, f18, -0.5D).tex(0.751953125D, 0.0625D).normal(-1.0F, 0.0F, 0.0F).endVertex();
                bufferbuilder.pos(f15, f17, -0.5D).tex(0.751953125D, 0.0D).normal(-1.0F, 0.0F, 0.0F).endVertex();
                bufferbuilder.pos(f16, f17, -0.5D).tex(0.751953125D, 0.0D).normal(1.0F, 0.0F, 0.0F).endVertex();
                bufferbuilder.pos(f16, f18, -0.5D).tex(0.751953125D, 0.0625D).normal(1.0F, 0.0F, 0.0F).endVertex();
                bufferbuilder.pos(f16, f18, 0.5D).tex(0.751953125D, 0.0625D).normal(1.0F, 0.0F, 0.0F).endVertex();
                bufferbuilder.pos(f16, f17, 0.5D).tex(0.751953125D, 0.0D).normal(1.0F, 0.0F, 0.0F).endVertex();
                tessellator.draw();
            }
        }
    }

    private void setLightmap(EntityBeastsPainting BeastsPainting, float p_77008_2_, float p_77008_3_) {
        int i = MathHelper.floor(BeastsPainting.posX);
        int j = MathHelper.floor(BeastsPainting.posY + (p_77008_3_ / 16.0F));
        int k = MathHelper.floor(BeastsPainting.posZ);
        Direction enumfacing = BeastsPainting.getHorizontalFacing();

        if (enumfacing == Direction.NORTH) i = MathHelper.floor(BeastsPainting.posX + (p_77008_2_ / 16.0F));
        if (enumfacing == Direction.WEST) k = MathHelper.floor(BeastsPainting.posZ - (p_77008_2_ / 16.0F));
        if (enumfacing == Direction.SOUTH) i = MathHelper.floor(BeastsPainting.posX - (p_77008_2_ / 16.0F));
        if (enumfacing == Direction.EAST) k = MathHelper.floor(BeastsPainting.posZ + (p_77008_2_ / 16.0F));

        int l = this.renderManager.world.getCombinedLight(new BlockPos(i, j, k), 0);
        int i1 = l % 65536;
        int j1 = l / 65536;
        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float) i1, (float) j1);
        GlStateManager.color3f(1.0F, 1.0F, 1.0F);
    }
}
