package mod.steamnsteel.api.voxbox;

import mod.steamnsteel.utility.voxbox.ProjectionRenderWrapper;
import mod.steamnsteel.utility.voxbox.RecipeViewHandler;

import java.util.List;

/**
 * Created by Bret 'Horfius' Dusseault on 9/28/2015.
 *
 * @author Bret 'Horfius' Dusseault
 * @version 0.1-Beta
 */
public interface IVoxBoxEntryManager {

    public void addListItem(ListElement element);
    public void addUnlocalizedTextEntry(String id, String unlocalizedKey);
    public void addProjectionRenderer(String id, ProjectionRenderWrapper wrapper);
    public void addRecipeViewHandler(String id, RecipeViewHandler handler);
    public ListElement getElement(String id);
    public String getUnlocalizedTextKey(String id);
    public ProjectionRenderWrapper getProjectionRenderer(String id);
    public RecipeViewHandler getRecipeViewHandler(String id);
    public int getIDFromString(String id);
    public String getIDFromInt(int id);
    public int getNumListElements();
    public List<ListElement> getListElements();
}
