package spaceShip;

import android.graphics.Bitmap;

public class Drill extends ShipComponet{

	public Drill(Bitmap comImage, int comTier, int comPrice, String comName, int comValue){
		super.setComImage(comImage);
		super.setComTier(comTier);
		super.setComPrice(comPrice);
		super.setComName(comName);
		super.setComValue(comValue);// defines which ore's it can mine
	}
	public Drill(int comTier, int comPrice, String comName, int comValue){
		super.setComTier(comTier);
		super.setComPrice(comPrice);
		super.setComName(comName);
		super.setComValue(comValue);// defines which ore's it can mine
	}
}
