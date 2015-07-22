package mod.steamnsteel.entity;

import mod.steamnsteel.entity.ai.AIFactorySpawnEntity;
import mod.steamnsteel.factory.IFactoryEntity;
import mod.steamnsteel.utility.NBTHelper;
import mod.steamnsteel.utility.log.Logger;
import mod.steamnsteel.utility.position.WorldBlockCoord;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class SpiderFactoryEntity extends EntityMob implements IFactoryEntity {

    public static final String NAME = "spiderFactory";
    private Swarm swarm;
    private WorldBlockCoord masterLocation;
    private long buildStartTime;
    private long lastSpawnTime;
    public final int timeBetweenSpawns = 600, buildTime = 600; //For use with animating the door and whatever

    public SpiderFactoryEntity(World p_i1738_1_) {
        super(p_i1738_1_);
        setSize(1.36F, 1.32F);
        tasks.addTask(0, new AIFactorySpawnEntity<>(this, timeBetweenSpawns, buildTime));
    }

    private static final int INDEX_LAST_SPAWN_TIME_MSB = 12;
    private static final int INDEX_LAST_SPAWN_TIME_LSB = 13;
    private static final int INDEX_SPAWN_START_TIME_MSB = 14;
    private static final int INDEX_SPAWN_START_TIME_LSB = 15;

    @Override
    protected void entityInit()
    {
        super.entityInit();
        DataWatcher dataWatcher = this.getDataWatcher();

        buildStartTime = -1;
        lastSpawnTime = 0;

        dataWatcher.addObject(INDEX_LAST_SPAWN_TIME_MSB, (int)lastSpawnTime); //The time that the last spawn happened
        dataWatcher.addObject(INDEX_LAST_SPAWN_TIME_LSB, (int)(lastSpawnTime >> 32)); //The time that the last spawn happened
        dataWatcher.addObject(INDEX_SPAWN_START_TIME_MSB, (int) buildStartTime); //The time a recent spawn has started
        dataWatcher.addObject(INDEX_SPAWN_START_TIME_LSB, (int)(buildStartTime >> 32));
    }

    @Override
    protected void applyEntityAttributes()
    {
        //TODO attributes
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
        getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(10000000D);
    }

    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        this.posX = this.prevPosX;
        this.posZ = this.prevPosZ;
        this.posY = this.prevPosY;
    }


    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    public void addVelocity(double p_70024_1_, double p_70024_3_, double p_70024_5_)
    {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);

        masterLocation = NBTHelper.readWorldBlockCoord(nbt, "masterBlockLocation");
        Logger.info("Reading %s", nbt);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);

        NBTHelper.writeWorldBlockCoord(nbt, "masterBlockLocation", masterLocation);
        Logger.info("Writing %s", nbt);
    }

    @Override
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
        return super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }

    public void setMasterBlockLocation(WorldBlockCoord worldBlockCoord) {
        masterLocation = worldBlockCoord;
    }

    @Override
    public long getLastBuildTime() {
        return lastSpawnTime;
    }

    @Override
    public boolean isSpawning() {
        return buildStartTime != -1;
    }

    @Override
    public void startBuildCycle() {
        updateBuildStartTime(worldObj.getTotalWorldTime());
    }

    private void updateBuildStartTime(long newTime) {
        buildStartTime = newTime;
        this.dataWatcher.updateObject(INDEX_SPAWN_START_TIME_LSB, (int)buildStartTime);
        this.dataWatcher.updateObject(INDEX_SPAWN_START_TIME_MSB, (int) (buildStartTime >> 32));
    }

    private void updateLastSpawnTime(long newTime) {
        lastSpawnTime = newTime;
        this.dataWatcher.updateObject(INDEX_LAST_SPAWN_TIME_LSB, (int)buildStartTime);
        this.dataWatcher.updateObject(INDEX_LAST_SPAWN_TIME_MSB, (int) (buildStartTime >> 32));
    }

    @Override
    public long getBuildStartTime() {
        return buildStartTime;
    }

    @Override
    public void finishBuildCycle() {
        updateBuildStartTime(-1);
        updateLastSpawnTime(worldObj.getTotalWorldTime());
    }

    @Override
    public void spawnEntity() {
        String entityName = (String) EntityList.classToStringMapping.get(SteamSpiderEntity.class);
        Entity entity = EntityList.createEntityByName(entityName, worldObj);
        entity.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        entity.motionX = (this.rotationYaw % 180) / 90 * (-2 + this.rotationYaw / 90);//90, 270
        //No clue if yaw could end up at 360, extra moduli potentially unnecessary
        entity.motionZ = Math.abs((this.rotationYaw % 360 - 90) % 180) / 90 * ((this.rotationYaw % 360 - 90) / 90); //0, 180, maybe 360

        worldObj.spawnEntityInWorld(entity);
    }

    @Override
    public void onDeath(DamageSource p_70645_1_) {
        super.onDeath(p_70645_1_);

        if (!worldObj.isRemote) {
            masterLocation.setBlock(worldObj, Blocks.air, 0, 3);
            boolean flag = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
            worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2.0f, flag);
        }

        worldObj.removeEntity(this);
    }
}
