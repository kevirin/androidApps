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
import android.util.Log;
import android.view.View;

public class Animation2Activity extends Activity {

	// ビューインスタンス
	BitmapView view;

	// ハンドラ
	Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// BitmapVieインスタンスを作成して画面に配置
		setContentView(view = new BitmapView(this));
	}

	// アクティビティが操作可能になったときに呼び出されるメソッド
	@Override
	protected void onResume() {
		super.onResume();

		// ハンドラの初期化
		handler = new Handler() {
			// メッセージの処理
			@Override
			public void handleMessage(Message m) {
				// ビューの再描画
				view.invalidate();
			}
		};

		// ハンドラの初回メッセージを送信
		handler.sendEmptyMessage(0);
	}

	// 他のアクティビティが実行中になった時に呼び出されるメソッド
	@Override
	protected void onPause() {
		super.onPause();

		// ハンドラにnullを設定
		handler = null;
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

				String fileName = "pose"+i+s[j]+k;
				// リソースから画像を取得
				bitmap[i][j][k] = BitmapFactory.decodeResource(
						r, r.getIdentifier(fileName, "drawable", getContext().getPackageName()));
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

			// ハンドラにメッセージを遅延して表示
			if (handler != null) {
				handler.sendEmptyMessageDelayed(0, 3000);
			}
		}
	}
}
