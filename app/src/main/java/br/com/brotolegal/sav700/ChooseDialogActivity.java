package br.com.brotolegal.sav700;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChooseDialogActivity  extends ListActivity {
    private List<String> item = null;
    private List<String> path = null;
    private String root;
    private String Storage;
    private TextView myPath;
    private File file;
    private String dirPathx;
    private String SelectFile;
    private ImageView imageView;
    private String RowText;
    private TextView tv;
    private   String extension;
    HashMap<String, Integer> meMap=new HashMap<String, Integer>();
    private Context context = ChooseDialogActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dialog);


        meMap.put(".jpg",R.drawable.file_jpg_48);
        meMap.put(".jpeg",R.drawable.file_jpg_48);
        meMap.put(".mp4",R.drawable.file_jpg_48);
        meMap.put(".txt",R.drawable.file_txt_48);
        meMap.put(".doc",R.drawable.file_txt_48);
        meMap.put(".png",R.drawable.file_png_48);
        meMap.put(".pdf",R.drawable.file_pdf_48);
        meMap.put(".***",R.drawable.generic_file_48);

        myPath = (TextView)findViewById(R.id.path);

        Storage = "/storage";

        root = Storage;

        getDir(root);
    }

    //----------------------------------------------------------------
    private void getDir(String dirPath)
    {
        myPath.setText("Local: " + dirPath );
        dirPathx= dirPath;
        item = new ArrayList<String>();
        path = new ArrayList<String>();
        File f = new File(dirPath);
        File[] files = f.listFiles();

        if(!dirPath.equals(root))
        {
            item.add(root);
            path.add(root);
            item.add("../");
            path.add(f.getParent());
        }

        for(int i=0; i < files.length; i++)
        {
            file = files[i];
            String FileName = file.getName();
            if (FileName.equals("emulated")) {
                continue;
            }
            if (FileName.equals("sdcard0")) {
                FileName = "Aparelho";
            }
            if (FileName.equals("extSdCard")) {
                FileName = "Cartão De Memória";
            }
            if(!file.isHidden() && file.canRead()){
                path.add(file.getPath());
                if(file.isDirectory()){
                    item.add(FileName + "/");
                }else{
                    item.add(FileName);
                }
            }
        }

        //---------------------------------------------
        ArrayAdapter<String> fileList =
                new ArrayAdapter<String>(this, R.layout.list_single,item){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){
                        LayoutInflater inflater = (LayoutInflater) context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view= inflater.inflate(R.layout.list_single, null, true);
                        imageView = (ImageView) view.findViewById(R.id.img);
                        tv = (TextView) view.findViewById(R.id.txt);
                        tv.setText(item.get(position));
                        RowText = tv.getText().toString();
                        //--------------------------
                        if (  file.isFile()) {
                            String myPath = dirPathx + "/" + RowText;
                            Extensione();
                            int value;
                            value =  (Integer)meMap.get(extension);

                            imageView.setImageResource(value);
                            if (extension.equals(".mp4")){
                                Uri myUri = Uri.parse(myPath);
                                String VideoPathString;
                                imageView.setImageDrawable(null);

                                Cursor cursor = getContentResolver().query(myUri, null, null, null, null);
                                if (cursor == null) {
                                    VideoPathString = myUri.getPath();
                                } else {
                                    cursor.moveToFirst();
                                    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                                    VideoPathString = cursor.getString(idx);
                                    cursor.close();
                                }
                                myPath= VideoPathString;
                            }
                            switch (extension) {
                                case ".jpg":
                                case ".JPG":
                                case ".png":
                                case ".mp4":
                                    imageView.setImageResource(R.drawable.file_jpg_48);
                                    break;
                            }
                        }
                        //--------------------------

                        if (  file.isDirectory()) {
                            imageView.setImageResource(R.drawable.folder_arancio_32);
                        }
                        int i = RowText.indexOf("../");
                        int ii =  RowText.indexOf("/");
                        if (i > -1){
                            imageView.setImageResource(R.drawable.upfolder_arancio_48);
                        }
                        else
                        if (ii > -1){
                            imageView.setImageResource(R.drawable.folder_arancio_32);
                        }
                        if ( RowText.equals("/storage/emulated/0")) {
                            imageView.setImageResource(R.drawable.folder_arancio_48);
                        }
                        tv.setTextColor(Color.BLUE);
                        return view;
                    }
                };
        setListAdapter(fileList);
    }
    //--------------------------
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        File file = new File(path.get(position));

        if (file.isDirectory())
        {
            if(file.canRead()){
                getDir(path.get(position));
            }else{

                new AlertDialog.Builder(this)
                        .setTitle("[" + path + "/" + file.getName() + "] folder can't be read!")
                        .setPositiveButton("OK", null).show();
            }
        }else {

            SelectFile= path.get(position);
            AskFileDialog();

        }
    }
    //--------------------------------------------------------------------------
    private void AskFileDialog ( )   {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.list_single, null);
        alert.setView(dialogView);

        ImageView imageView8 = (ImageView) dialogView.findViewById(R.id.img);
        android.view.ViewGroup.LayoutParams layoutParams =
                imageView8.getLayoutParams();
        layoutParams.width = 100;
        layoutParams.height =100;
        imageView8.setLayoutParams(layoutParams);
        alert.setMessage(SelectFile);
        alert.setTitle("Open File Dialog:");
        RowText = SelectFile;
        Extensione();
        int value;
        value =  (Integer)meMap.get(extension);

        if (extension.equals(".jpg" )|| extension.equals(".mp4") || extension.equals(".png")     )
        {
            imageView8.setImageResource(R.drawable.file_jpg_48);

        } else {

            imageView8.setImageResource(value);
        }

        alert.setPositiveButton("O.K.", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //         finish();
                //        startActivity(getIntent());
            }
        });
        alert.show();
    }
    //------------------------------------------------------------------------
    private void Extensione(){
        int pos = RowText.indexOf(".");
        if (pos>-1) {
            extension = RowText.substring(RowText.lastIndexOf("."));
        }else {
            extension = ".***";
        }
        if (meMap.get(extension) == null){
            extension = ".***";
        }
    }


}

