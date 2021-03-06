package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelLegfish;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.passive.EntityLegfish;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class RenderLegfish extends MobRenderer<EntityLegfish, ModelLegfish> {

    private static final Map<Integer, ResourceLocation[]> TEXTURES = new HashMap<>();
    private final ModelLegfish[] types;

    public RenderLegfish(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelLegfish.Type1(), 0.1f);
        types = new ModelLegfish[]{getEntityModel(), new ModelLegfish.Type2(), new ModelLegfish.Type3()};
    }

    @Override
    public void doRender(EntityLegfish entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.entityModel = types[entity.getLegfishType()];
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityLegfish entity) {
        int type = entity.getLegfishType();
        ResourceLocation[] textures = TEXTURES.computeIfAbsent(type, k -> {
            ResourceLocation[] typeTextures = new ResourceLocation[EntityLegfish.VARIANTS.get(type)];
            for (int i = 0; i < typeTextures.length; i++)
                typeTextures[i] = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/legfish/type_" + (type + 1) + "_" + (i + 1) + ".png");
            return typeTextures;
        });
        return textures[entity.getVariant()];
    }
}
