package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import random.beasts.client.model.ModelPufferFishDog;
import random.beasts.client.renderer.entity.layers.LayerCollar;
import random.beasts.client.renderer.entity.layers.LayerGlasses;
import random.beasts.common.BeastsMod;
import random.beasts.common.entity.passive.EntityPufferfishDog;

import javax.annotation.Nullable;

public class RenderPufferfishDog extends MobRenderer<EntityPufferfishDog, ModelPufferFishDog> {

    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/pufferfish_dog/normal.png");
    private static final ResourceLocation TEXTURE_INFLATED = new ResourceLocation(BeastsMod.MOD_ID, "textures/entity/pufferfish_dog/inflated.png");

    public RenderPufferfishDog(EntityRendererManager manager) {
        super(manager, new ModelPufferFishDog(), 0.3f);
        this.addLayer(new LayerGlasses(this));
        this.addLayer(new LayerCollar(this));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityPufferfishDog entity) {
        return entity.isInflated() ? TEXTURE_INFLATED : TEXTURE_NORMAL;
    }
}

