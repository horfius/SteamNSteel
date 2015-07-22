package mod.steamnsteel.entity.ai;

import mod.steamnsteel.factory.IFactoryEntity;
import mod.steamnsteel.utility.log.Logger;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * Created by Steven on 7/04/2015.
 */
public class AIFactorySpawnEntity<T extends EntityLiving & IFactoryEntity> extends EntityAIBase {

    private final int timeToCreateEntity;
    protected T entity;
    private final int timeBetweenSpawns;
    private int randTime = 0; //Used to randomize the time between spawns a little bit

    public AIFactorySpawnEntity(T entity, int timeBetweenSpawns, int timeToCreateEntity) {
        this.entity = entity;
        this.timeBetweenSpawns = timeBetweenSpawns;
        this.timeToCreateEntity = timeToCreateEntity;
        randTime = entity.worldObj.rand.nextInt(timeBetweenSpawns / 5);
    }

    @Override
    public boolean shouldExecute() {
        return !entity.isDead && !entity.isSpawning() && entity.getLastBuildTime() < (entity.worldObj.getTotalWorldTime() - timeBetweenSpawns - randTime);
    }

    @Override
    public void startExecuting() {
        Logger.info("Starting Build Cycle");
        entity.startBuildCycle();
    }

    @Override
    public void updateTask() {
        if (entity.worldObj.getTotalWorldTime() > entity.getBuildStartTime() + timeToCreateEntity) {
            Logger.info("Spawning Entity");
            entity.finishBuildCycle();
            entity.spawnEntity();
            randTime = entity.worldObj.rand.nextInt(timeBetweenSpawns / 5);
        }
    }

    @Override
    public boolean continueExecuting() {
        return entity.isSpawning();
    }
}
