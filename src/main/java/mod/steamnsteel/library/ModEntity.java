package mod.steamnsteel.library;

import cpw.mods.fml.common.registry.EntityRegistry;
import mod.steamnsteel.TheMod;
import mod.steamnsteel.entity.SteamProjectileEntity;
import mod.steamnsteel.entity.SteamSpiderEntity;

@SuppressWarnings("UtilityClass")
public final class ModEntity
{
    private ModEntity()
    {
        throw new AssertionError();
    }

    public static void init()
    {
        EntityRegistry.registerModEntity(SteamSpiderEntity.class, SteamSpiderEntity.NAME, 1, TheMod.instance, 64, 1, true);
        EntityRegistry.registerModEntity(SteamProjectileEntity.class, SteamProjectileEntity.NAME, 2, TheMod.instance, 32, 1, true);
    }
}