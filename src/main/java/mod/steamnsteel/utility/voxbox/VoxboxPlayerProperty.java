package mod.steamnsteel.utility.voxbox;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Created by Bret 'Horfius' Dusseault on 10/4/2015.
 *
 * @author Bret 'Horfius' Dusseault
 * @version 0.1-Beta
 */
public class VoxboxPlayerProperty implements IExtendedEntityProperties {
    public boolean enabled = false;
    public static final String PROPERTY_ID = "steamnsteel.voxbox.enabled";

    public static final void register(EntityPlayer player){
        player.registerExtendedProperties(PROPERTY_ID, new VoxboxPlayerProperty());
    }

    public static final VoxboxPlayerProperty get(EntityPlayer player){
        return (VoxboxPlayerProperty)player.getExtendedProperties(PROPERTY_ID);
    }

    @Override
    public void init(Entity entity, World world) {

    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setBoolean("enabled", enabled);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        enabled = compound.getBoolean("enabled");
    }
}
