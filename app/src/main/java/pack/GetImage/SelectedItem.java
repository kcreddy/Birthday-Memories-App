package pack.GetImage;

public class SelectedItem {
    public Integer imageID;
    public boolean selected = false;

    public SelectedItem(Integer imageID, boolean selected)
    {
        this.imageID = imageID;
        this.selected = selected;
    }

    public boolean IsSelected()
    {
        return selected;
    }
}
