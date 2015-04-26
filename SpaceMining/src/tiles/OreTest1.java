package tiles;

import nicaa.project.dk.game.spacemining.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class OreTest1 extends Ore{
	
	public OreTest1(Context context,int screenWidth,int screenHeight) {
		super(context);
		setType("Copper Ore");
		setValue(20);
		setWeight(30);
		bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_ore);
		bitmap = Bitmap.createScaledBitmap(bitmap, (int)(screenWidth * 0.101),(int) (screenHeight * 0.150 ), false);
	}

}
