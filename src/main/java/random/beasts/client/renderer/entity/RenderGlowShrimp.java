package random.beasts.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import random.beasts.api.main.BeastsReference;
import random.beasts.client.model.ModelGlowShrimp;
import random.beasts.common.entity.passive.EntityGlowShrimp;

@SideOnly(Side.CLIENT)
public class RenderGlowShrimp extends RenderLiving<EntityGlowShrimp> {
	
    private static final ResourceLocation SHRIMP = new ResourceLocation(BeastsReference.ID, "textures/entity/glow_shrimp.png");

    public RenderGlowShrimp(RenderManager rm) {
        super(rm, new ModelGlowShrimp(), 0.3f);
    }

    protected ResourceLocation getEntityTexture(EntityGlowShrimp entity) {
        return SHRIMP;
    }
}