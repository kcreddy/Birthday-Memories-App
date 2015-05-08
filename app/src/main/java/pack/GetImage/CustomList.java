package pack.GetImage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.CheckBox;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;
import static android.view.View.OnClickListener;

public class CustomList extends ArrayAdapter<pack.GetImage.SelectedItem>{
    private final Activity context;
    private final Integer[] imageId;
    private ArrayList<SelectedItem> listSelectedItem;

    public CustomList(Activity context, Integer[] imageId,
                      ArrayList<SelectedItem> listSelectedItem) {
        super(context, R.layout.singlepicture, listSelectedItem);

        this.context = context;
        this.imageId = imageId;
        this.listSelectedItem = listSelectedItem;
    }

    public ArrayList<SelectedItem> getListSelectedItem()
    {
        return listSelectedItem;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.singlepicture, null, true);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        final CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkbox);

        imageView.setImageResource(imageId[position]);
        imageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
                for(int i = 0; i<listSelectedItem.size(); i++)
                {
                    listSelectedItem.get(i).selected = false;
                    if(i == position)
                        listSelectedItem.get(position).selected = checkBox.isChecked();

                }

            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < listSelectedItem.size(); i++) {
                    listSelectedItem.get(i).selected = false;
                    if (i == position)
                        listSelectedItem.get(position).selected = checkBox.isChecked();

                }
            }
        });
        return rowView;
    }
}

