package mod.steamnsteel.api.voxbox;

import org.lwjgl.util.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Bret 'Horfius' Dusseault on 9/23/2015.
 *
 * @author Bret 'Horfius' Dusseault
 * @version 0.1-Beta
 */
public abstract class ListElement {
    private Color _nameColor = new Color(0xAF, 0xAF, 0xAF);
    private Color _statusColor = new Color(0x00, 0x66, 0x00);
    private String _name = "NULL";
    private String _status = "";
    private boolean isVisible = true;
    private boolean statusVisible = false;
    private boolean _selected = false;
    private ListElement _parent = null;
    private boolean hasChildren = false;
    private List<ListElement> children = null;
    private final String ID;

    public ListElement(String name, String id){
        this(name, id, null, false);
    }

    public ListElement(String name, String id, ListElement parent, boolean hasChildren){
        this._name = name;
        this._parent = parent;
        this.hasChildren = hasChildren;
        this.ID = id;
        if(hasChildren)
            children = new ArrayList<>();
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visibility) {
        this.isVisible = visibility;
    }

    public boolean isStatusVisible() {
        return statusVisible;
    }

    public void setStatusVisible(boolean visibility) {
        this.statusVisible = visibility;
    }

    public String getStatus() {
        return _status;
    }

    public void setStatus(String status) {
        this._status = status;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public Color getNameColor(int renderTicks) {
        return _nameColor;
    }

    public Color getDefaultNameColor(){
        return _nameColor;
    }

    public void setNameColor(Color color) {
        this._nameColor = color;
    }

    public Color getStatusColor(int renderTicks) {
        return _statusColor;
    }

    public Color getDefaultStatusColor(){
        return _statusColor;
    }

    public void setStatusColor(Color color) {
        this._statusColor = color;
    }

    public ListElement getParent() {
        return _parent;
    }

    public void setParent(ListElement parent){
        this._parent = parent;
    }

    public boolean isSelected(){
        return _selected;
    }

    public void setSelected(boolean selected){
        this._selected = selected;
    }

    public boolean hasChildren() {
        return hasChildren;
    }

    public void addChild(ListElement element){
        if(!hasChildren){
            this.hasChildren = true;
            children = new ArrayList<>();
        }

        children.add(element);
        element.setParent(this);
        children.sort(new Comparator<ListElement>() {
            @Override
            public int compare(ListElement o1, ListElement o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public String getID() {
        return ID;
    }
}
