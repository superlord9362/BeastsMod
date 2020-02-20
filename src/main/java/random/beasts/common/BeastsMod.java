package random.beasts.common;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import random.beasts.api.main.BeastsReference;
import random.beasts.proxy.CommonProxy;

@SuppressWarnings("unused")
@Mod(modid = BeastsReference.ID, name = BeastsReference.NAME, version = BeastsReference.CURRENT_VERSION, acceptableRemoteVersions = BeastsReference.VERSION_RANGE, guiFactory = BeastsReference.GUI_FACTORY)
public class BeastsMod {

    @SidedProxy(serverSide = BeastsReference.COMMON_PROXY, clientSide = BeastsReference.CLIENT_PROXY)
    public static CommonProxy proxy;
    @Mod.Instance(BeastsReference.ID)
    public static BeastsMod instance;
    private static Logger logger;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit();
    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @EventHandler
    public static void post(FMLPostInitializationEvent event) {
    }


    @EventHandler
    public static void serverStart(FMLServerStartingEvent event) {
        //final Map<String, ICommand> commandMap = ReflectionHelper.getPrivateValue(CommandHandler.class, (CommandHandler)event.getServer().getCommandManager(), "commandMap", "field_71562_a", "A");
        //commandMap.remove("locate");
        //event.registerServerCommand(new CommandLocateStructure());
    }

    public static Logger getLogger() {
        return logger;
    }
}
