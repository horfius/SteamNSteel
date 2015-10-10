package mod.steamnsteel.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import mod.steamnsteel.TheMod;
import mod.steamnsteel.api.voxbox.IVoxBoxEntryManager;
import mod.steamnsteel.api.voxbox.ListElement;
import mod.steamnsteel.api.voxbox.VoxboxEntryManager;
import mod.steamnsteel.client.gui.component.VoxboxButton;
import mod.steamnsteel.configuration.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Bret 'Horfius' Dusseault on 9/29/2015.
 *
 * @author Bret 'Horfius' Dusseault
 * @version 0.1-Beta
 */
public class VoxboxGui extends GuiScreen {
    /**Texture for other voxbox components to access */
    public static final ResourceLocation texture = new ResourceLocation(TheMod.MOD_ID.toLowerCase(), "textures/gui/PGVGUI.png");
    /**Page buttons for the recipe viewer */
    private VoxboxButton leftPage;
    private VoxboxButton rightPage;

    private VoxboxButton muteButton;
    private VoxboxButton playEntry;
    private VoxboxButton projectButton;

    private GuiTextField searchBox;
    //TODO use correct dimension with correct texture
    private static final Range<Float> SCROLL_DIMENSIONS = Range.closed(0.0F, 1.0F);
    private float listScroll = SCROLL_DIMENSIONS.lowerEndpoint();
    private float textScroll = SCROLL_DIMENSIONS.lowerEndpoint();
    /**The steps to a multiblock or number of pages for recipes */
    private int recipeSteps = 1;
    private int currentItemID = -1;
    /**The ticks that the current recipe view has been through, reset when the item is changed */
    private long ticksRecipeView = 0;
    /**The ticks that the projection has been enabled for, reset when it is closed */
    private long ticksProjection = 0;
    private boolean muted = false;
    private boolean projectionEnabled = false;
    /**Gui Size */
    private final int xSize = 229;
    private final int ySize = 209;

    /**Cached list structure */
    private static List<ListElement> parentList;

    static {
        buildStructureList();
    }

    public static void rebuildListStructure(){
        parentList.clear();
        buildStructureList();
    }

    private static void buildStructureList(){
        parentList = Lists.newLinkedList();
        IVoxBoxEntryManager manager = VoxboxEntryManager.instance.get();
        for(ListElement l : manager.getListElements()){
            if(l.getParent() == null){
                parentList.add(l);
            }
        }

        parentList.sort(new Comparator<ListElement>() {
            @Override
            public int compare(ListElement o1, ListElement o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }


    public VoxboxGui() {
        super();
    }

    @Override
    public void drawScreen(int x, int y, float p_73863_3_) {
        GL11.glColor4f(1F, 1F, 1F, 1F);

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;

        mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
        System.out.println(Minecraft.getMinecraft().gameSettings.guiScale);

        super.drawScreen(x, y, p_73863_3_);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();

        int movement = Mouse.getEventDWheel();
        int x = Mouse.getX();
        int y = Display.getHeight() - Mouse.getY();
//        System.out.printf("X=%d, Y=%d, Delta Z=%d\n", x, y, movement);


        checkScrollOverflow();
    }

    @Override
    protected void keyTyped(char key, int keyCode) {
        super.keyTyped(key, keyCode);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        super.initGui();
        muted = Settings.Voxbox.isVoxboxMuted();
        buttonList.clear();


        leftPage = new VoxboxButton(0, 1, 1, VoxboxButton.Textures.PAGE_LEFT);
        leftPage.visible = false;
        leftPage.enabled = false;
        rightPage = new VoxboxButton(1, 2, 1, VoxboxButton.Textures.PAGE_RIGHT);
        rightPage.visible = false;
        rightPage.enabled = false;
        muteButton = new VoxboxButton(2, 3, 1, VoxboxButton.Textures.MUTE);
        muteButton.visible = true;
        muteButton.enabled = muted;
        playEntry = new VoxboxButton(3, 4, 1, VoxboxButton.Textures.PLAY);
        playEntry.visible = true;
        playEntry.enabled = true;
        projectButton = new VoxboxButton(4, 5, 1, VoxboxButton.Textures.PROJECT);
        projectButton.visible = true;
        projectButton.enabled = true;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    private void checkScrollOverflow(){
        if(!SCROLL_DIMENSIONS.contains(listScroll)){
            if(listScroll < SCROLL_DIMENSIONS.lowerEndpoint()){
                listScroll = SCROLL_DIMENSIONS.lowerEndpoint();
            }else{
                listScroll = SCROLL_DIMENSIONS.upperEndpoint();
            }
        }

        if(!SCROLL_DIMENSIONS.contains(textScroll)){
            if(textScroll < SCROLL_DIMENSIONS.lowerEndpoint()){
                textScroll = SCROLL_DIMENSIONS.lowerEndpoint();
            }else{
                textScroll = SCROLL_DIMENSIONS.upperEndpoint();
            }
        }
    }
}
