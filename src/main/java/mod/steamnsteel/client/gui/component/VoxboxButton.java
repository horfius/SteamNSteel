package mod.steamnsteel.client.gui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

/**
 * Created by Bret 'Horfius' Dusseault on 9/29/2015.
 *
 * @author Bret 'Horfius' Dusseault
 * @version 0.1-Beta
 */
public class VoxboxButton extends GuiButton {
    private final Textures texture;

    public enum Textures{
        //TODO Actual dimensions for buttons
        PAGE_LEFT(0, 0, 0, 0), PAGE_RIGHT(0, 0, 0, 0), MUTE(0, 0, 0, 0), PLAY(0, 0, 0, 0), PROJECT(0, 0, 0, 0);
        public final int x, y, width, height;

        Textures(int x, int y, int width, int height){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    public VoxboxButton(int id, int xCoord, int yCoord, Textures texture) {
        super(id, xCoord, yCoord, texture.width, texture.height, "");
        this.texture = texture;
    }

    @Override
    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {
        super.drawButton(p_146112_1_, p_146112_2_, p_146112_3_);
    }
}
