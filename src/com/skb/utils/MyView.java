package com.skb.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.ImageView;
 
public class MyView extends ImageView {
 
    Path path;
    public PaintFlagsDrawFilter mPaintFlagsDrawFilter;// ë�߹���
    Paint paint;
    
    int image;
    
    
    public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
 
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
 
    public MyView(Context context) {
        super(context);
        init();
    }
    public void init(){
    	//Paint�˲���
        mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0,
                Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        paint = new Paint();
        //�����仰����Ϊ�˿����
        paint.setAntiAlias(true);
        //����ͼƬ�˲�
        //�������Ϊtrue,���ڲ��Ŷ�����ʱ����˵�Bitmapͼ����Ż��������ӿ���ʾ�ٶ�
        paint.setFilterBitmap(true);
        paint.setColor(Color.WHITE);
         
    }
     
    @Override
    protected void onDraw(Canvas cns) {
        float h = getMeasuredHeight();
        float w = getMeasuredWidth();
        if (path == null) {
            path = new Path();
            path.addCircle(
                 115
                    , 95
                    , (float) Math.min(w/2.5f, (h / 2.5f))
                    , Path.Direction.CCW);
            path.close();
        }
        cns.drawCircle(500,500,  Math.min(w/4.0f, h / 4.0f) , paint);
         int saveCount = cns.getSaveCount();
        cns.save();
        //��Canvas�ӿ����
        cns.setDrawFilter(mPaintFlagsDrawFilter);
        cns.clipPath(path,Region.Op.REPLACE);
        //�����˲���
        cns.setDrawFilter(mPaintFlagsDrawFilter);
        cns.drawColor(Color.WHITE);
        super.onDraw(cns);
        cns.restoreToCount(saveCount);
    }
}
