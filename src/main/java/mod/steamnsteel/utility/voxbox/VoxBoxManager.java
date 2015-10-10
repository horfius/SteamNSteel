package mod.steamnsteel.utility.voxbox;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import mod.steamnsteel.api.voxbox.IVoxBoxEntryManager;
import mod.steamnsteel.api.voxbox.ListElement;

import java.util.List;
import java.util.Map;

/**
 * Created by Bret 'Horfius' Dusseault on 9/23/2015.
 *
 * @author Bret 'Horfius' Dusseault
 * @version 0.1-Beta
 */
public enum VoxboxManager implements IVoxBoxEntryManager {
    INSTANCE;
    private BiMap<String, Integer> ids = HashBiMap.create();
    private Map<Integer, ListElement> listElements = Maps.newHashMap();
    private Map<Integer, String> textElements = Maps.newHashMap();
    private Map<Integer, RecipeViewHandler> recipeViews = Maps.newHashMap();
    private Map<Integer, ProjectionRenderWrapper> projectionWrappers = Maps.newHashMap();
    private short lastID = -1;

    public static void init(){

    }

    @Override
    public void addListItem(ListElement element) {
        if(!ids.containsKey(element.getID())){
            ids.put(element.getID(), getNextID());
        }
        listElements.put(ids.get(element.getID()), element);
    }

    @Override
    public void addUnlocalizedTextEntry(String id, String unlocalizedKey) {
        if(!ids.containsKey(id)){
            ids.put(id, getNextID());
        }
        textElements.put(ids.get(id), unlocalizedKey);
    }

    @Override
    public void addProjectionRenderer(String id, ProjectionRenderWrapper wrapper) {
        if(!ids.containsKey(id)){
            ids.put(id, getNextID());
        }
        projectionWrappers.put(ids.get(id), wrapper);
    }

    @Override
    public void addRecipeViewHandler(String id, RecipeViewHandler handler) {
        if(!ids.containsKey(id)){
            ids.put(id, getNextID());
        }
        recipeViews.put(ids.get(id), handler);
    }

    @Override
    public ListElement getElement(String id) {
        return listElements.get(ids.get(id));
    }

    @Override
    public String getUnlocalizedTextKey(String id) {
        return textElements.get(ids.get(id));
    }

    @Override
    public ProjectionRenderWrapper getProjectionRenderer(String id) {
        return projectionWrappers.get(ids.get(id));
    }

    @Override
    public RecipeViewHandler getRecipeViewHandler(String id) {
        return recipeViews.get(ids.get(id));
    }

    @Override
    public int getIDFromString(String id) {
        return ids.getOrDefault(id, -1);
    }

    @Override
    public String getIDFromInt(int id) {
        return ids.inverse().getOrDefault(id, null);
    }

    @Override
    public int getNumListElements() {
        return listElements.size();
    }

    @Override
    public List<ListElement> getListElements() {
        List<ListElement> elements = Lists.newLinkedList();

        for(Map.Entry<Integer, ListElement> e : listElements.entrySet()){
            elements.add(e.getValue());
        }

        return elements;
    }


    private int getNextID(){
        return ++lastID;
    }
}
