package tiles;

import nicaa.project.dk.game.spacemining.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BossPlatform extends Tile{
	
	
	
	
	public BossPlatform(Context context,int screenWidth,int screenHeight ) {
		super(context);
		setType("Platform");
		bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss_platform);
		bitmap = Bitmap.createScaledBitmap(bitmap, (int)(screenWidth * 0.101),(int) (screenHeight * 0.150), false);
		
	}

	

}
