package com.wyc.foldimageview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FoldImageVeiw extends ImageView {

	private List<Bitmap> mbitmaps;
	private Paint paint;
	private Xfermode xfermode;
	private boolean change; // 当前是否变换
	private int bhjg = 5000; // 变换间隔时间

	private int bhsj = 50; // 变换时间

	private int frontindex;// 前景索引
	private int backindex;// 背景索引

	private boolean setbitmap = false; // 是否设置bitmap;

	private Matrix matrix;

	private int increment = 0;

	private int height; // 获取imgview 高度
	private int width; // 获取imgview 宽度

	private Handler handler = new Handler();

	private Runnable r = new Runnable() {

		@Override
		public void run() {

			if (change) {
				handler.postDelayed(r, bhjg);
				change = false;
			} else {
				FoldImageVeiw.this.invalidate();
				handler.postDelayed(r, bhsj);
			}
		}

	};

	public FoldImageVeiw(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		paint = new Paint();
		paint.setAntiAlias(true);
		xfermode = new PorterDuffXfermode(Mode.SRC_OVER);
		change = false;
		matrix = new Matrix();

	
		bhjg = (int) Math.random() * (5000 - 2000) + 2000;
	}

	public FoldImageVeiw(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public FoldImageVeiw(Context context) {
		this(context, null);
	}
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    	height = this.getMeasuredHeight();
		width = this.getMeasuredWidth();
		//System.out.print("height"+height+"width"+width);
    }
	public void SetBitmap(List<Bitmap> mbitmaps) {
		this.mbitmaps =mbitmaps;
		if(mbitmaps.size()==1){
			frontindex=0;// 前景索引
			backindex=0;// 背景索引
		}
		else{
			frontindex=0;// 前景索引
			backindex=1;// 背景索引
		}
		setbitmap = true;
		handler.postDelayed(r, bhjg);
	}
  
	

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		if (setbitmap) {

			Bitmap bmp = mbitmaps.get(frontindex);
			Bitmap bmp1 = mbitmaps.get(backindex);

			increment++;
			if (bmp1.getHeight() - 5 * increment >= bmp1.getHeight() / 2) {

				// 绘制上半部分图像
				canvas.save();

				canvas.clipRect(0, 0, bmp1.getWidth(), bmp1.getHeight() / 2);
				canvas.drawBitmap(bmp1, 0, 0, paint);

				canvas.restore();

				// 绘制下班部分图像
				canvas.save();

				canvas.clipRect(0, bmp1.getHeight() / 2, bmp1.getWidth(),
						bmp1.getHeight());
				canvas.drawBitmap(bmp, 0, 0, paint);

				canvas.restore();

				// 动态改变下半部分图像
				float[] src = { 0, bmp1.getHeight() / 2,//
						bmp1.getWidth(), bmp1.getHeight() / 2,//
						bmp1.getWidth(), bmp1.getHeight(),//
						0, bmp1.getHeight() };

				height = bmp1.getHeight() - 5 * increment;
				float[] dst = { 0, bmp1.getHeight() / 2,//
						bmp1.getWidth(), bmp1.getHeight() / 2,//
						bmp1.getWidth(), height,//
						0, height };

				canvas.save();
				matrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);

				canvas.clipRect(0, bmp1.getHeight() / 2, bmp1.getWidth(),
						height);
				paint.setXfermode(null);

				paint.setXfermode(xfermode);

				canvas.drawBitmap(bmp1, matrix, paint);
				canvas.restore();
				paint.setXfermode(null);

			} else {

				// 绘制上半部分图像
				canvas.save();

				canvas.clipRect(0, 0, bmp1.getWidth(), bmp1.getHeight() / 2);
				canvas.drawBitmap(bmp1, 0, 0, paint);

				canvas.restore();
				// 绘制下班部分图像
				canvas.save();

				canvas.clipRect(0, bmp1.getHeight() / 2, bmp1.getWidth(),
						bmp1.getHeight());
				canvas.drawBitmap(bmp, 0, 0, paint);

				canvas.restore();

				// 动态改变上半部分图像
				float[] src = { 0, 0,//
						bmp1.getWidth(), 0,//
						bmp1.getWidth(), bmp1.getHeight() / 2,//
						0, bmp1.getHeight() / 2 };

				height = bmp1.getHeight() - 5 * increment;

				float[] dst = { 0, height,//
						bmp1.getWidth(), height,//
						bmp1.getWidth(), bmp1.getHeight() / 2,//
						0, bmp1.getHeight() / 2 };

				System.out.println(increment + "");
				canvas.save();
				matrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);

				canvas.clipRect(0, bmp1.getHeight() / 2, bmp1.getWidth(),
						height);
				paint.setXfermode(null);

				paint.setXfermode(xfermode);

				canvas.drawBitmap(bmp, matrix, paint);
				
				canvas.restore();
				paint.setXfermode(null);

				if (height <= 0) { // 说明动画结束 重置动画参数
					increment = 0;
					change = true;
					
					backindex=frontindex;
					frontindex = (++frontindex)%(mbitmaps.size());
					

				}

			}

		} else {

		}
	}

}
