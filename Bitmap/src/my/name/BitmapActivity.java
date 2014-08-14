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
import android.view.View;

public class BitmapActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// BitmapVieインスタンスを作成して画面に配置
		setContentView(new BitmapView(this));
	}

	// BitmapViewクラス定義
	class BitmapView extends View {
		// 画像
		Bitmap bitmap;

		// コンストラクタ
		public BitmapView(Context c) {
			super(c);

			//リソースの取得
			Resources r = c.getResources();

			// リソースから画像を取得
			this.bitmap = BitmapFactory.decodeResource(r, R.drawable.character);
		}

		// 描画
		@Override
		protected void onDraw(Canvas c) {
			// 背景色の設定
			c.drawColor(Color.WHITE);

			// Paintインスタンスの作成
			Paint p = new Paint();

			// 画像の描画
			//c.drawBitmap(bitmap, (c.getWidth()-bitmap.getWidth())/2, 0, p);
		}
	}
}
