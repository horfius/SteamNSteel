package mod.steamnsteel.block.machine;

import cpw.mods.fml.common.Mod;
import mod.steamnsteel.block.SteamNSteelBlock;
import mod.steamnsteel.entity.SpiderFactoryEntity;
import mod.steamnsteel.item.factory.SpiderFactoryItem;
import mod.steamnsteel.library.ModBlock;
import mod.steamnsteel.tileentity.SpiderFactoryTE;
import mod.steamnsteel.utility.log.Logger;
import mod.steamnsteel.utility.position.WorldBlockCoord;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class SpiderFactoryBlock extends SteamNSteelBlock implements ITileEntityProvider {

    public static final String NAME = "spiderFactory";

    public SpiderFactoryBlock() {
        super(Material.iron, false);
        setBlockName(NAME);
        setHardness(Float.MAX_VALUE);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new SpiderFactoryTE();
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        SpiderFactoryItem.validateFactory(world, x, y, z);
    }

    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int p_149696_5_, int p_149696_6_)
    {
        super.onBlockEventReceived(world, x, y, z, p_149696_5_, p_149696_6_);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        return tileentity != null && tileentity.receiveClientEvent(p_149696_5_, p_149696_6_);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB boundingBox, List collisionList, Entity collindingEntity) {
        if (collindingEntity instanceof SpiderFactoryEntity) return;
        //if (collindingEntity instanceof EntityPlayer) return;
        super.addCollisionBoxesToList(world, x, y, z, boundingBox, collisionList, collindingEntity);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }

    @Override
    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_) {
        return false;
    }
}
