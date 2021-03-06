package random.beasts.common.init;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import random.beasts.api.advancements.BeastsTrigger;
import random.beasts.api.main.BeastsRegistries;

public class BeastsTriggers {

    //public static final BeastsTrigger DISCOVER_PUFFERFISH_DOG = new BeastsTrigger("pufferfish_discover");
    public static final BeastsTrigger HAMMERTIME = new BeastsTrigger("hammertime");

    static {
        for (ICriterionTrigger<? extends ICriterionInstance> trigger : BeastsRegistries.ADVANCEMENTS.get())
            CriteriaTriggers.register(trigger);
    }
}
