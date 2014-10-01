package mod.steamnsteel.texturing.feature;

import mod.steamnsteel.texturing.*;
import mod.steamnsteel.texturing.wall.RuinWallTexture;
import mod.steamnsteel.utility.position.ChunkCoord;
import mod.steamnsteel.utility.position.WorldBlockCoord;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class TopBandWallFeature extends ProceduralWallFeatureBase
{
    private final ProceduralConnectedTexture texture;

    public TopBandWallFeature(ProceduralConnectedTexture texture, String name, int layer)
    {

        super(name, layer);
        this.texture = texture;
    }

    @Override
    public boolean isFeatureValid(TextureContext context)
    {
        return !texture.isBlockPartOfWallAndUnobstructed(context, TextureDirection.ABOVE);
    }

    @Override
    public Collection<FeatureInstance> getFeatureAreasFor(ChunkCoord chunkCoord)
    {
        List<FeatureInstance> oneInstance = new ArrayList<FeatureInstance>(1);
        oneInstance.add(new FeatureInstance(this, WorldBlockCoord.of(0, 0, 0), 16, 256, 16));
        return oneInstance;
    }

    @Override
    public long getSubProperties(TextureContext context)
    {
        long properties = 0;
        if (texture.isBlockPartOfWallAndUnobstructed(context, TextureDirection.LEFT, TextureDirection.ABOVE))
        {
            properties |= ProceduralConnectedTexture.LEFT;
        }
        if (texture.isBlockPartOfWallAndUnobstructed(context, TextureDirection.RIGHT, TextureDirection.ABOVE))
        {
            properties |= ProceduralConnectedTexture.RIGHT;
        }

        //Break up the bases
        if ((getCrownSplitOpportunity(context.getWorldBlockCoord()) & 14) == 0)
        {
            properties |= ProceduralConnectedTexture.LEFT;
        }
        if ((getCrownSplitOpportunity(context.getWorldBlockCoord().offset(context.getRightDirection())) & 14) == 0)
        {
            properties |= ProceduralConnectedTexture.RIGHT;
        }

        properties |= ProceduralConnectedTexture.TOP;

        return properties;
    }

    @Override
    public Behaviour getBehaviourAgainst(IProceduralWallFeature otherLayerFeature, long featureProperties)
    {
        if (otherLayerFeature instanceof BottomBandWallFeature)
        {
            return Behaviour.CANNOT_EXIST;
        }
        return Behaviour.COEXIST;
    }

    @Override
    public long getIncompatibleProperties()
    {
        return RuinWallTexture.ALTERNATE;
    }

    private int getCrownSplitOpportunity(WorldBlockCoord worldBlockCoord)
    {
        int x = worldBlockCoord.getX();
        int y = worldBlockCoord.getY();
        int z = worldBlockCoord.getZ();
        Random r = new Random(x * y * z * 31);
        return r.nextInt();
    }
}
