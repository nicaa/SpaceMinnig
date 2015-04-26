package tiles;

import nicaa.project.dk.game.spacemining.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Ore_Emerald extends Ore {
	
	public Ore_Emerald(Context context,int screenWidth,int screenHeight) {
		super(context);
		setType("Emerald");
		setValue(50);
		setWeight(60);
		bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ore_emerald);
		bitmap = Bitmap.createScaledBitmap(bitmap, (int)(screenWidth * 0.101),(int) (screenHeight * 0.150 ), false);
	}
}
