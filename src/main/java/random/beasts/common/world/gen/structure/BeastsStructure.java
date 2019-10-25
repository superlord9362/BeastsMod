package random.beasts.common.world.gen.structure;

import net.minecraft.world.gen.feature.WorldGenerator;
import random.beasts.common.init.BeastsStructures;

public abstract class BeastsStructure extends WorldGenerator {

    public final BeastsStructures.StructurePiecesRegisterer registerer;

    BeastsStructure(BeastsStructures.StructurePiecesRegisterer registerer) {
        this.registerer = registerer;
        BeastsStructures.LIST.add(this);
    }
}