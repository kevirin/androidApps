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

		Bitmap poseImage;
		Bitmap eyeImage;
		Bitmap mouseImage;

		public BitmapView(Context c) {
			super(c);
		}

		Bitmap getBitmap(int i, int j, int k) {
			String s[]={"body", "eye", "mouth"};
			Resources r=getContext().getResources();
			return BitmapFactory.decodeResource(
				r, r.getIdentifier("pose"+i+s[j]+k, "drawable",
					getContext().getPackageName()));
		}

		@Override
		protected void onDraw(Canvas c) {
			c.drawColor(Color.WHITE);
			Paint p=new Paint();
			int pose=(int)(Math.random()*4);
			int eye=(int)(Math.random()*3);
			int mouth=(int)(Math.random()*3);

			poseImage =  getBitmap(pose, 0, 0);
			eyeImage = getBitmap(pose, 1, eye);
			mouseImage = getBitmap(pose, 2, mouth);
			int x=(c.getWidth()-poseImage.getWidth())/2;
			c.drawBitmap(poseImage, x, 0, p);
			c.drawBitmap(eyeImage, x, 0, p);
			c.drawBitmap(mouseImage, x, 0, p);

			if (handler!=null) handler.sendEmptyMessageDelayed(0, 3000);
		}
	}
}