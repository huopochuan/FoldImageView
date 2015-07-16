package com.wyc.foldimageview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;

public class MainActivity extends Activity {

	private FoldImageVeiw img_user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		img_user = (FoldImageVeiw) this.findViewById(R.id.img_user);
		Bitmap map1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.img1);
		Bitmap map2 = BitmapFactory.decodeResource(getResources(),
				R.drawable.img2);
		Bitmap map3 = BitmapFactory.decodeResource(getResources(),
				R.drawable.img3);
		Bitmap map4 = BitmapFactory.decodeResource(getResources(),
				R.drawable.img4);
		map1 = zoomBitmap(map1);
		map2 = zoomBitmap(map2);
		map3 = zoomBitmap(map3);
		map4 = zoomBitmap(map4);
		List<Bitmap> mbitmaps = new ArrayList<Bitmap>();
		mbitmaps.add(map1);
		mbitmaps.add(map2);
		mbitmaps.add(map3);
		mbitmaps.add(map4);
		img_user.SetBitmap(mbitmaps);
	}

	public List<Bitmap> zoombitmaps(List<Bitmap> mbitmaps) {
		List<Bitmap> list = new ArrayList<Bitmap>();
		for (int i = 0; i < mbitmaps.size(); i++) {
			list.add(zoomBitmap(mbitmaps.get(i)));
		}
		return list;
	}

	public Bitmap zoomBitmap(Bitmap bm) {
		int bmwidth = bm.getWidth();
		int bmheight = bm.getHeight();
	
		LayoutParams para;
		para = img_user.getLayoutParams();
		
		
		float scaleWidth =(para.width )* 1.0f / bmwidth;
		float scaleHeight = (para.height) * 1.0f / bmheight;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		

		return Bitmap.createBitmap(bm, 0, 0, bmwidth, bmheight, matrix, true);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
