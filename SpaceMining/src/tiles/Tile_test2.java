package tiles;

import nicaa.project.dk.game.spacemining.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Tile_test2 extends Tile{

	public Tile_test2(Context context,int screenWidth,int screenHeight) {
		super(context);
		setType("test2");
		bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_2);
		bitmap = Bitmap.createScaledBitmap(bitmap, (int)(screenWidth * 0.101),(int) (screenHeight * 0.150 ), false);
	}

}
