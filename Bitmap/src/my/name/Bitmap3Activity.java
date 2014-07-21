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

public class Bitmap3Activity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// BitmapVieインスタンスを作成して画面に配置
		setContentView(new BitmapView(this));
	}

	// BitmapViewクラス定義
	class BitmapView extends View {
		// 画像の配列
		Bitmap bitmap[][][] = new Bitmap[4][3][];

		// コンストラクタ
		public BitmapView(Context c) {
			super(c);

			// 各ポーズに関して、ベース・目・口の画像数
			int n[] = { 1, 3, 3 };

			// 配列の初期化
			for (int i = 0; i < bitmap.length; i++) {
				for ( int j = 0; j < bitmap[i].length; j++) {
					bitmap[i][j] = new Bitmap[n[j]];
				}
			}
		}

		// 指定したポーズ・種類・状態の画像を取得する
		Bitmap getBitmap(int i, int j, int k) {
			// 画像の種類(ベース、目、口)
			String s[] = {"body", "eye", "mouth"};

			// 指定された画像が未初期化ならリソースから取得する。
			if (bitmap[i][j][k] == null) {
				// リソースの取得
				Resources r = getContext().getResources();

				// リソースから画像を取得
				bitmap[i][j][k] = BitmapFactory.decodeResource(
						r, r.getIdentifier("pose"+i+s[j]+k, "drawable", getContext().getPackageName()));
			}

			// 指定された画像を返す
			return bitmap[i][j][k];
		}
		// 描画
		@Override
		protected void onDraw(Canvas c) {
			// 背景色の設定
			c.drawColor(Color.WHITE);

			// Paintインスタンスの作成
			Paint p = new Paint();

			// ポーズ・目・口をランダムに決める
			int pose = (int)(Math.random() * bitmap.length);
			int eye = (int)(Math.random() * bitmap[pose][1].length);
			int mouth = (int)(Math.random() * bitmap[pose][2].length);

			// 画像を表示する左座標
			int x = (c.getWidth() - getBitmap(pose, 0, 0).getWidth()) / 2;

			// ベース、目、口の画像を重ねて表示する
			c.drawBitmap(getBitmap(pose, 0, 0), x, 0, p);
			c.drawBitmap(getBitmap(pose, 1, eye), x, 0, p);
			c.drawBitmap(getBitmap(pose, 2, mouth), x, 0, p);
		}
	}
}
