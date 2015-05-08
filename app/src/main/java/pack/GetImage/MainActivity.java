package pack.GetImage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;
    private ImageView img;
    private Button framebutton;
    TextView textSource1;
    TextView Browse;
    Uri selectedImageUri;
    ImageButton button;
    Bitmap bmap;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.bm_icon_text);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Birthday Memories");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00853c")));


        img = (ImageView)findViewById(R.id.ImageView01);
        textSource1 = (TextView)findViewById(R.id.sourceuri1);
        Browse = (TextView)findViewById(R.id.browse);
        Browse.setTextColor(Color.parseColor("#000000"));
        framebutton = (Button)findViewById(R.id.fbutton);
        framebutton.setVisibility(View.GONE);

        findViewById(R.id.gbutton)
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {

                      /*  Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, SELECT_PICTURE); */

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

                        framebutton.setVisibility(View.VISIBLE);
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == SELECT_PICTURE)
            {
                selectedImageUri = data.getData();
                textSource1.setText(selectedImageUri.toString());
                img.setImageURI(selectedImageUri);
            }
        }
    }

    public void frame(View view)
    {
        Intent intent = new Intent(this, Frame.class);
        intent.putExtra("imageUri", selectedImageUri);
        startActivity(intent);
    }



}
