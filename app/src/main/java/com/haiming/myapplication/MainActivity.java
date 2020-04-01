package com.haiming.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        imageView = findViewById(R.id.image);

        imageView.post(new Runnable() {
            @Override
            public void run() {
                // byte[] bytes = img(BitmapFactory.decodeResource(getResources(),R.drawable.earth));

//                byte[] bytes = drawable2Byte(getDrawable(R.drawable.earth));

                String path = drawable2String(getDrawable(R.drawable.span));
                Log.d("数据",path);
                byte[] bytes = new byte[0];
                try {
                    bytes = path.getBytes("ISO-8859-1");
                    Log.d("数据bytes=", Arrays.toString(bytes));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Glide.with(MainActivity.this).load(bytes).into(imageView);
               // imageView.setImageDrawable(byte2Drawable(bytes));




             //   byte[] bytes1 = img(((BitmapDrawable)getResources().getDrawable(R.drawable.earth)).getBitmap());
            }
        });


//        Intent intent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, 1);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            Bitmap bm = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bm);
            c.close();
        }
    }

    public static String drawable2String(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bitmap = bd.getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        try {
            return new String(os.toByteArray(),"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Drawable string2Drawable(String strByte) {
        if (strByte.isEmpty()) {
            return null;
        }
        byte[] bytes = new byte[0];
        try {
            bytes = strByte.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        Drawable drawable = bitmapDrawable;
        return drawable;
    }

    /**
     * 将图片转换成二进制数组
     *
     * @param drawable
     * @return
     */
    public byte[] drawable2Byte(Drawable drawable) {
        if (drawable == null) {
            Log.d("数据","空");
            return null;
        }
        Log.d("数据","bu空");
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bitmap = bd.getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }

    public Drawable byte2Drawable(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        Drawable drawable = bitmapDrawable;
        return drawable;
    }

}
