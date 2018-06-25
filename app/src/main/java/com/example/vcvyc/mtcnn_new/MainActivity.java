package com.example.vcvyc.mtcnn_new;
/*
  MTCNN For Android
  by cjf@xmu 20180625
 */
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    String TAG="MainActivity";
    private  Bitmap readFromAssets(String filename){
        Bitmap bitmap;
        AssetManager asm=getAssets();
        try {
            InputStream is=asm.open(filename);
            bitmap= BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            Log.e("MainActivity","[*]failed to open "+filename);
            e.printStackTrace();
            return null;
        }
        return Utils.copyBitmap(bitmap); //返回mutable的image
    }
    public void showImage(Bitmap bm,int rid){
        ((ImageView)findViewById(rid)).setImageBitmap(bm);
        Log.i(TAG,"[*]showImage width:"+bm.getWidth()+" Height:"+bm.getHeight());
    }
    public void showPixel(int v){
        Log.i(TAG,"[*]Pixel:R"+((v>>16)&0xff)+"G:"+((v>>8)&0xff)+ " B:"+(v&0xff));
    }
    public void myMain(){
        String TAG="MainActivity";
        MTCNN mtcnn=new MTCNN(getAssets());
        Bitmap bitmap=readFromAssets("hz.JPG");
        /*
        try {
            int[] tmp=new int[bitmap.getWidth()*bitmap.getHeight()];
            bitmap.getPixels(tmp,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
            showPixel(tmp[bitmap.getWidth()*50+100]);
        }catch (Exception e){
            Log.i(TAG,"[*]exception:"+e);
        }*/
        //Bitmap bitmap=readFromAssets("ttt.jpg");
        try {
            Vector<Box> boxes=mtcnn.detectFaces(bitmap,35);
            for (int i=0;i<boxes.size();i++){
                Utils.drawRect(bitmap,boxes.get(i).transform2Rect());
                Utils.drawLandmark(bitmap,boxes.get(i).landmark);
            }
            showImage(bitmap,R.id.imageView);
            if (mtcnn.tmp_bm!=null)
                showImage(mtcnn.tmp_bm,R.id.imageView2);
        }catch (Exception e){
            Log.e(TAG,"[*]detect false:"+e);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myMain();
    }
}
