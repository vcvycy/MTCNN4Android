package com.example.vcvyc.mtcnn_new;
/*
  MTCNN For Android
  by cjf@xmu 20180625
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.widget.ImageView;

import java.util.Vector;

public class Utils {
    //复制图片，并设置isMutable=true
    public static Bitmap copyBitmap(Bitmap bitmap){
        return bitmap.copy(bitmap.getConfig(),true);
    }
    //在bitmap中画矩形
    public static void drawRect(Bitmap bitmap,Rect rect){
        try {
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            int r=255;//(int)(Math.random()*255);
            int g=0;//(int)(Math.random()*255);
            int b=0;//(int)(Math.random()*255);
            paint.setColor(Color.rgb(r, g, b));
            paint.setStrokeWidth(1+bitmap.getWidth()/500 );
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(rect, paint);
        }catch (Exception e){
            Log.i("Utils","[*] error"+e);
        }
    }
    //在图中画点
    public static void drawPoints(Bitmap bitmap, Point[] landmark){
        for (int i=0;i<landmark.length;i++){
            int x=landmark[i].x;
            int y=landmark[i].y;
            //Log.i("Utils","[*] landmarkd "+x+ "  "+y);
            drawRect(bitmap,new Rect(x-1,y-1,x+1,y+1));
        }
    }
    //Flip alone diagonal
    //对角线翻转。data大小原先为h*w*stride，翻转后变成w*h*stride
    public static void flip_diag(float[]data,int h,int w,int stride){
        float[] tmp=new float[w*h*stride];
        for (int i=0;i<w*h*stride;i++) tmp[i]=data[i];
        for (int y=0;y<h;y++)
            for (int x=0;x<w;x++){
               for (int z=0;z<stride;z++)
                 data[(x*h+y)*stride+z]=tmp[(y*w+x)*stride+z];
            }
    }
    //src转为二维存放到dst中
    public static void expand(float[] src,float[][]dst){
        int idx=0;
        for (int y=0;y<dst.length;y++)
            for (int x=0;x<dst[0].length;x++)
                dst[y][x]=src[idx++];
    }
    //src转为三维存放到dst中
    public static void expand(float[] src,float[][][] dst){
        int idx=0;
        for (int y=0;y<dst.length;y++)
            for (int x=0;x<dst[0].length;x++)
                for (int c=0;c<dst[0][0].length;c++)
                dst[y][x][c]=src[idx++];

    }
    //dst=src[:,:,1]
    public static void expandProb(float[] src,float[][]dst){
        int idx=0;
        for (int y=0;y<dst.length;y++)
            for (int x=0;x<dst[0].length;x++)
                dst[y][x]=src[idx++*2+1];
    }
    //box转化为rect
    public static Rect[] boxes2rects(Vector<Box> boxes){
        int cnt=0;
        for (int i=0;i<boxes.size();i++) if (!boxes.get(i).deleted) cnt++;
        Rect[] r=new Rect[cnt];
        int idx=0;
        for (int i=0;i<boxes.size();i++)
            if (!boxes.get(i).deleted)
                r[idx++]=boxes.get(i).transform2Rect();
        return r;
    }
    //删除做了delete标记的box
    public static Vector<Box> updateBoxes(Vector<Box> boxes){
        Vector<Box> b=new Vector<Box>();
        for (int i=0;i<boxes.size();i++)
            if (!boxes.get(i).deleted)
                b.addElement(boxes.get(i));
        return b;
    }
    //
    static public void showPixel(int v){
        Log.i("MainActivity","[*]Pixel:R"+((v>>16)&0xff)+"G:"+((v>>8)&0xff)+ " B:"+(v&0xff));
    }
}
