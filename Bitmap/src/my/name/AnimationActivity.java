package my.name;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class AnimationActivity extends Activity {
	BitmapView view;
	Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(view=new BitmapView(this));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		handler=new Handler() {
			@Override
			public void handleMessage(Message m) {
				view.invalidate();
			}
		};
		handler.sendEmptyMessage(0);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		handler=null;
	}	
	
	class BitmapView extends View {
		Bitmap bitmap[][][]=new Bitmap[4][3][];

		public BitmapView(Context c) {
			super(c);
			int n[]={1, 3, 3};
			for (int i=0; i<bitmap.length; i++) {
				for (int j=0; j<bitmap[i].length; j++) {
					bitmap[i][j]=new Bitmap[n[j]];
				}
			}
		}
		
		Bitmap getBitmap(int i, int j, int k) {
			String s[]={"body", "eye", "mouth"};
			if (bitmap[i][j][k]==null) {
				Resources r=getContext().getResources();
				bitmap[i][j][k]=BitmapFactory.decodeResource(
					r, r.getIdentifier("pose"+i+s[j]+k, "drawable", 
						getContext().getPackageName()));
			}
			return bitmap[i][j][k];
		}
		
		@Override
		protected void onDraw(Canvas c) {
			c.drawColor(Color.WHITE);
			Paint p=new Paint();
			int pose=(int)(Math.random()*bitmap.length);
			int eye=(int)(Math.random()*bitmap[pose][1].length);
			int mouth=(int)(Math.random()*bitmap[pose][2].length);
			int x=(c.getWidth()-getBitmap(pose, 0, 0).getWidth())/2;
			c.drawBitmap(getBitmap(pose, 0, 0), x, 0, p);
			c.drawBitmap(getBitmap(pose, 1, eye), x, 0, p);
			c.drawBitmap(getBitmap(pose, 2, mouth), x, 0, p);

			if (handler!=null) handler.sendEmptyMessageDelayed(0, 3000);
		}		
	}	
}