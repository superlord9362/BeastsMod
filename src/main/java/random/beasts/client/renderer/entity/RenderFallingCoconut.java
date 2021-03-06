package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.renderer.RenderCoconut;
import random.beasts.common.entity.item.EntityFallingCoconut;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderFallingCoconut extends EntityRenderer<EntityFallingCoconut> {

    public RenderFallingCoconut(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn);
    }

    @Override
    public void doRender(EntityFallingCoconut entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        RenderCoconut.render(x, y, z, this::bindTexture, this.renderOutlines, this.getTeamColor(entity));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityFallingCoconut entity) {
        return RenderCoconut.TEXTURE;
    }
}
