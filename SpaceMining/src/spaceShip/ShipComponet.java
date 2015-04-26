package spaceShip;

import android.graphics.Bitmap;

public class ShipComponet {
	private Bitmap comImage;
	private int comTier;
	private int comPrice;
	private String comName;
	private int comValue;
	
	

	public Bitmap getComImage() {
		return comImage;
	}

	public void setComImage(Bitmap comImage) {
		this.comImage = comImage;
	}

	public int getComTier() {
		return comTier;
	}

	public void setComTier(int comTier) {
		this.comTier = comTier;
	}

	public int getComPrice() {
		return comPrice;
	}

	public void setComPrice(int comPrice) {
		this.comPrice = comPrice;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public int getComValue() {
		return comValue;
	}

	public void setComValue(int comValue) {
		this.comValue = comValue;
	}

}
