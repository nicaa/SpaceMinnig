package tiles;

import nicaa.project.dk.game.spacemining.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Tile1_level2 extends Tile{

	public Tile1_level2(Context context,int screenWidth,int screenHeight) {
		super(context);
		setType("tile1_level2");
		bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile1_level2);
		bitmap = Bitmap.createScaledBitmap(bitmap, (int)(screenWidth * 0.101),(int) (screenHeight * 0.150), false);
	}

}
