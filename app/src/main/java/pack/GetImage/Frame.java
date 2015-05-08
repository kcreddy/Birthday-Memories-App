package pack.GetImage;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.widget.ShareDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Frame extends ActionBarActivity {

    ListView list;
    boolean isSelectPicture = true;
    Uri selectedImageUri2;
    Uri selectedImageUri3;
    Bitmap merged = null;
    private CallbackManager callbackManager;
    ShareDialog shareDialog;


    Integer[] frameId = {
            R.mipmap.frame1,
            R.mipmap.frame2,
            R.mipmap.frame3,
            R.mipmap.frame4,
            R.mipmap.frame5,
            R.mipmap.frame6,
            R.mipmap.frame7,
            R.mipmap.frame8,
            R.mipmap.frame9,
            R.mipmap.frame10,
            R.mipmap.frame11,
            R.mipmap.frame12,
            R.mipmap.frame13,
            R.mipmap.frame14,
            R.mipmap.frame15
    };

    SelectedItem selectedItem ;
    ArrayList<SelectedItem> listSelectedFrame = new ArrayList<>();
    TextView TextFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.bm_icon_text);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Birthday Memories");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00853c")));

        TextFrame=(TextView) findViewById(R.id.textframe);
        TextFrame.setTextColor(Color.parseColor("#000000"));

        isSelectPicture = true;
        InitListView(false);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                facebookCallback);
        shareDialog = new ShareDialog(this);
      //  shareDialog.registerCallback(callbackManager,shareCallback );
        final Button mergebutton = (Button) findViewById(R.id.mbutton);
        mergebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setContentView(R.layout.merged_picture);
                ImageView img = (ImageView) findViewById(R.id.mergedImg);

                img.setImageDrawable(new BitmapDrawable(getResources(), getMergeBitmap()));
            }
        });

    }

    public void Post(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("public_profile", "user_friends"));
        LoginManager.getInstance().logInWithPublishPermissions(this,
                Arrays.asList("publish_actions"));

        /*selectedImageUri3 = getImageUri(merged);
        Bitmap bit = null;
        try
        {
            bit = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImageUri3);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println(bit.toString());
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bit).build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        System.out.println(bit.toString());
        ShareButton shareButton = (ShareButton)findViewById(R.id.btPost);
        shareButton.setShareContent(content);
        System.out.println(content.toString());
        ShareDialog.show(Frame.this, content); */
    }

    private final FacebookCallback<LoginResult> facebookCallback =
            new FacebookCallback<LoginResult>() {

                @Override
                public void onSuccess(LoginResult loginResult) {
                    selectedImageUri3 = getImageUri(merged);

                  initShareIntent("facebook");

                 /*   System.out.println(merged.toString());
                    Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                            R.drawable.abcd);
                            //Bitmap scalednewimgBitmap1 = getResizedBitmap(getMergeBitmap(),600,800);
                    if (ShareDialog.canShow(SharePhotoContent.class)) {
                        SharePhoto photo = new SharePhoto.Builder()
                                .setBitmap(icon).build();

                        SharePhotoContent content = new SharePhotoContent.Builder()
                                .addPhoto(photo)
                                .build();
                        System.out.println(content.toString());
                        //System.out.println(scalednewimgBitmap1.toString());
                        shareDialog.show(content, ShareDialog.Mode.NATIVE);
                       // System.out.println(scalednewimgBitmap1.toString());
                    } */


                /*    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/jpeg");
                    share.putExtra(Intent.EXTRA_STREAM, Uri.parse(selectedImageUri3.toString()));
                    startActivity(Intent.createChooser(share, "Share Image")); */
                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onError(FacebookException e) {
                }
            };

   private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>()
    {
        @Override
        public void onCancel() {
            Log.d("HelloFacebook", "Canceled");
        }

        @Override
        public void onError(FacebookException error)
        {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
            //String title = getString(R.string.error);
            String alertMessage = error.getMessage();
            ////showResult(title, alertMessage);
        }

        @Override
        public void onSuccess(Sharer.Result result)
        {
            Log.d("HelloFacebook", "Success!");
            if (result.getPostId() != null) {
                String title = getString(R.string.success);
                String id = result.getPostId();
                String alertMessage = getString(R.string.successfully_posted_post, id);
                showResult(title, alertMessage);
            }
        }

        private void showResult(String title, String alertMessage) {
            new AlertDialog.Builder(Frame.this)
                    .setTitle(title)
                    .setMessage(alertMessage)
                    .setPositiveButton(R.string.app_name, null)
                    .show();
        }
    };

    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }



    @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void InitListView(boolean isChooseFrame) {
        CustomList adapter;
         for(int i = 0; i<frameId.length; i++)
         {
                selectedItem = new SelectedItem(frameId[i], false);
                listSelectedFrame.add(selectedItem);
         }

        adapter = new CustomList(this, frameId, listSelectedFrame);

        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
    }


    private Bitmap getImageBitmap(Bitmap frm)
    {
        int width = 0;
        int height = 0;

        for(int i = 0; i<listSelectedFrame.size(); i++)
        {
            if(listSelectedFrame.get(i).selected)
            {
             switch (listSelectedFrame.get(i).imageID)
             {
                 case 2130903040 : //frame1-position1
                     width = frm.getWidth()-200;
                     height = frm.getHeight()-210;
                     break;
                 case 2130903047 : //frame2-position2
                     width = frm.getWidth()-280;
                     height = frm.getHeight()-210;
                     break;
                 case 2130903048 : //frame3-position3
                     width = frm.getWidth()-185;
                     height = frm.getHeight()-195;
                     break;
                 case 2130903049 : //frame4-position4
                     width = frm.getWidth()-198;
                     height = frm.getHeight()-257;
                     break;
                 case 2130903050 : //frame5-position5
                     width = frm.getWidth()-290;
                     height = frm.getHeight()-140;
                     break;
                 case 2130903051 : //frame6-position6
                     width = frm.getWidth()-133;
                     height = frm.getHeight()-125;
                     break;
                 case 2130903052 : //frame7-position7
                     width = frm.getWidth()-160;
                     height = frm.getHeight()-150;
                     break;
                 case 2130903053 : //frame8-position8
                     width = frm.getWidth()-170;
                     height = frm.getHeight()-155;
                     break;
                 case 2130903054 : //frame9-position9
                     width = frm.getWidth()-390;
                     height = frm.getHeight()-285;
                     break;
/*                 case 2130903055 :
                     width = frm.getWidth()-35;
                     height = frm.getHeight()-35;
                     break;*/
                 case 2130903041 : //frame10-position10
                     width = frm.getWidth()-190;
                     height = frm.getHeight()-180;
                     break;
                 case 2130903042 : //frame11-position11
                     width = frm.getWidth()-180;
                     height = frm.getHeight()-160;
                     break;
                 case 2130903043 : //frame12-position12
                     width = frm.getWidth()-220;
                     height = frm.getHeight()-220;
                     break;
                 case 2130903044 : //frame13-position13
                     width = frm.getWidth()-300;
                     height = frm.getHeight()-256;
                     break;
                 case 2130903045 : //frame14-position14
                     width = frm.getWidth()-240;
                     height = frm.getHeight()-240;
                     break;
                 case 2130903046 : //frame15-position15
                     width = frm.getWidth()-80;
                     height = frm.getHeight()-100;
                     break;
             }
            }
        }

        Bitmap imgBitmap = null;

        Intent intent2 = getIntent();
        Uri uri = intent2.getParcelableExtra("imageUri");

        try {
            imgBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        selectedImageUri2 = uri;

        Bitmap scalednewimgBitmap = getResizedBitmap(imgBitmap,height,width);
        return  scalednewimgBitmap;
    }

    private Bitmap getFrameBitmap()
    {
        Bitmap frmBitmap = Bitmap.createBitmap(350, 350, Bitmap.Config.ARGB_8888);
        for(int i = 0; i<listSelectedFrame.size(); i++)
        {
            if(listSelectedFrame.get(i).selected)
                frmBitmap = BitmapFactory.decodeResource(getResources(), listSelectedFrame.get(i).imageID);
        }
        return frmBitmap;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth)
    {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    private Bitmap getMergeBitmap()
    {
        int height = 0;
        int width = 0;

        Bitmap frmBitmap = getFrameBitmap();
        Bitmap imgBitmap = getImageBitmap(frmBitmap);

        int maxWidth = (frmBitmap.getWidth() > imgBitmap.getWidth() ? frmBitmap.getWidth() : imgBitmap.getWidth());
        int maxHeight = (frmBitmap.getHeight() > imgBitmap.getHeight() ? frmBitmap.getHeight() : imgBitmap.getHeight());
        merged = Bitmap.createBitmap(maxWidth, maxHeight,  frmBitmap.getConfig());

        Canvas c = new Canvas(merged);
        c.drawBitmap(frmBitmap,0,0,null);

        for(int i = 0; i<listSelectedFrame.size(); i++)
        {
            if(listSelectedFrame.get(i).selected)
            {
                switch (listSelectedFrame.get(i).imageID)
                {
                    case 2130903040 : //frame1-position1
                        c.drawBitmap(imgBitmap,100,108, null);
                        break;
                    case 2130903047 : //frame2-position2
                        c.drawBitmap(imgBitmap,255,125, null);
                        break;
                    case 2130903048 : //frame3-position3
                        c.drawBitmap(imgBitmap,95,98, null);
                        break;
                    case 2130903049 : //frame4-position4
                        c.drawBitmap(imgBitmap,95,129, null);
                        break;
                    case 2130903050 : //frame5-position5
                        c.drawBitmap(imgBitmap,140,88, null);
                        break;
                    case 2130903051 : //frame6-position6
                        c.drawBitmap(imgBitmap,70,65, null);
                        break;
                    case 2130903052 : //frame7-position7
                        c.drawBitmap(imgBitmap,85,80, null);
                        break;
                    case 2130903053 : //frame8-position8
                        c.drawBitmap(imgBitmap,85,80, null);
                        break;
                    case 2130903054 : //frame9-position9
                        c.drawBitmap(imgBitmap,200,150, null);
                        break;
                    case 2130903041 : //frame10-position10
                        c.drawBitmap(imgBitmap,95,90, null);
                        break;
                    case 2130903042 : //frame11-position11
                        c.drawBitmap(imgBitmap,85,80, null);
                        break;
                    case 2130903043 : //frame12-position12
                        c.drawBitmap(imgBitmap,110,110, null);
                        break;
                    case 2130903044 : //frame13-position13
                        c.drawBitmap(imgBitmap,155,138, null);
                        break;
                    case 2130903045 : //frame14-position14
                        c.drawBitmap(imgBitmap,120,120, null);
                        break;
                    case 2130903046 : //frame15-position15
                        c.drawBitmap(imgBitmap,40,50, null);
                        break;

                }
            }
        }


        return merged;
    }


    public Uri getImageUri(Bitmap inImage) {

        String path = Environment.getExternalStorageDirectory().toString();
        System.out.println("path is "+path);
        OutputStream fOut = null;
    //    File file = new File(path+"/images/","FacebookMerge.jpg");
        File file = new File(path+ "/Download/"+"FacebookMerge.jpg");
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        inImage.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String abc = Environment.getExternalStorageDirectory().getPath();
        System.out.println("abc is "+abc);
  //      Uri uri3 = Uri.parse("file:///"+path+"/images/"+"FacebookMerge.jpg");
        Uri uri3 = Uri.parse("file:///"+path+"/Download/"+"FacebookMerge.jpg");
        System.out.println(uri3.toString());
        return uri3;
    }


    private void initShareIntent(String type) {
        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
       // share.setType("text/plain");
        share.setType("image/jpeg");

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()){
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type) ) {

                    share.putExtra(Intent.EXTRA_STREAM, Uri.parse(selectedImageUri3.toString()));
                  /*  EditText text2 = (EditText) findViewById(R.id.txtWish);
                    System.out.println(text2.getText().toString());
                    share.putExtra(Intent.EXTRA_TEXT,text2.getText().toString()); */
                    share.setPackage(info.activityInfo.packageName);
                    System.out.println(share.getPackage());
                    found = true;
                    startActivity(share);
                    break;
                }
            }
            if (!found)
                return;

           // startActivity(Intent.createChooser(share, "Select"));
        }
    }

}
