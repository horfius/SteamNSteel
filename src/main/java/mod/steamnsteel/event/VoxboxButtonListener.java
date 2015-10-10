package mod.steamnsteel.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mod.steamnsteel.client.gui.VoxboxGui;
import mod.steamnsteel.client.gui.component.VoxboxInventoryButton;
import mod.steamnsteel.utility.voxbox.VoxboxPlayerProperty;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiScreenEvent;

import java.awt.*;
import java.awt.geom.Area;

/**
 * Created by Bret 'Horfius' Dusseault on 10/4/2015.
 *
 * @author Bret 'Horfius' Dusseault
 * @version 0.1-Beta
 */
public class VoxboxButtonListener {
    @SubscribeEvent
    public void guiInit(GuiScreenEvent.InitGuiEvent.Post event){
        if(event.gui instanceof GuiInventory){
            VoxboxPlayerProperty property = VoxboxPlayerProperty.get(event.gui.mc.thePlayer);
            if(property != null && property.enabled == true){
                int offsetWidth = 0; //The offset from other buttons in the area we're interested in
                int startX = event.gui.width / 2 - 176 / 2;
                int startY = event.gui.height / 2 - 166 / 2 - VoxboxInventoryButton.HEIGHT - 3;

                if(event.buttonList.size() > 0) {
                    int endX = event.gui.width / 2 + 176 / 2;
                    int endY = event.gui.height / 2 - 166 / 2 - 1;
                    Area buttonArea = new Area(new Rectangle(startX, startY, endX - startX, endY - startY));

                    for (Object g0 : event.buttonList) {
                        GuiButton g1 = (GuiButton) g0;
                        if (buttonArea.contains((double) g1.xPosition, (double) g1.yPosition)
                                || buttonArea.contains((double) (g1.xPosition + g1.width), (double) (g1.yPosition + g1.height))) {
                            offsetWidth += g1.width;
                        }
                    }
                }

                VoxboxInventoryButton button = new VoxboxInventoryButton(VoxboxInventoryButton.ID, startX + offsetWidth, startY);
                System.out.println("Adding Voxbox Inventory button: " + button.toString());
                System.out.println("StartX: " + startX + ", OffsetWidth: " + offsetWidth + ", StartY: " + startY);
                event.buttonList.add(button);
            }
        }
    }

    @SubscribeEvent
    public void guiButtonPress(GuiScreenEvent.ActionPerformedEvent.Pre event){
        if(event.gui instanceof GuiInventory){
            if(event.button.id == VoxboxInventoryButton.ID){
                event.gui.mc.displayGuiScreen(new VoxboxGui());
                event.setCanceled(true);
            }
        }
    }
}
