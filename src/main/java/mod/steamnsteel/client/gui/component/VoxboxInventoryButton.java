package mod.steamnsteel.client.gui.component;

import com.google.common.base.Objects;
import net.minecraft.client.gui.GuiButton;

/**
 * Created by Bret 'Horfius' Dusseault on 10/4/2015.
 *
 * @author Bret 'Horfius' Dusseault
 * @version 0.1-Beta
 */
public class VoxboxInventoryButton extends GuiButton {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static final int ID = 945;

    public VoxboxInventoryButton(int id, int x, int y) {
        super(id, x, y, WIDTH, HEIGHT, "gui.button.voxbox");
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("x", xPosition)
                .add("y", yPosition)
                .add("width", width)
                .add("height", height)
                .toString();
    }
}
