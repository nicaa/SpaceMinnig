package spaceShip;

import android.graphics.Bitmap;

public class Fuel extends ShipComponet{
	private int fuelMin;
	private int fuelMax;
	private int totalFuel;
	
	public Fuel(Bitmap comImage, int comTier, int comPrice, String comName, int comValue, int fuelMin, int fuelMax){
		super.setComImage(comImage);
		super.setComTier(comTier);
		super.setComPrice(comPrice);
		super.setComName(comName);
		super.setComValue(comValue);
		this.setFuelMin(fuelMin);
		this.setFuelMax(fuelMax);
	}
	public Fuel(int comTier, int comPrice, String comName, int comValue, int fuelMin, int fuelMax){
		super.setComTier(comTier);
		super.setComPrice(comPrice);
		super.setComName(comName);
		super.setComValue(comValue);
		this.setFuelMin(fuelMin);
		this.setFuelMax(fuelMax);
	}

	public int getFuelMin() {
		return fuelMin;
	}

	public void setFuelMin(int fuelMin) {
		this.fuelMin = fuelMin;
	}

	public int getFuelMax() {
		return fuelMax;
	}

	public void setFuelMax(int fuelMax) {
		this.fuelMax = fuelMax;
	}

	public int getTotalFuel() {
		return totalFuel;
	}

	public void setTotalFuel(int totalFuel) {
		this.totalFuel = totalFuel;
	}

}
