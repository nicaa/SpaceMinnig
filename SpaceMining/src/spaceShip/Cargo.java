package spaceShip;

import java.util.ArrayList;

import tiles.Ore;

import android.graphics.Bitmap;

public class Cargo extends ShipComponet{
	private int cargoMin;
	private int cargoMax;
	private int totalCargo;
	public static ArrayList<Ore> cargoList = new ArrayList<Ore>();

	public Cargo(Bitmap comImage, int comTier, int comPrice, String comName, int comValue, int cargoMin, int cargoMax){
		super.setComImage(comImage);
		super.setComTier(comTier);
		super.setComPrice(comPrice);
		super.setComName(comName);
		super.setComValue(comValue);
		this.setCargoMin(cargoMin);
		this.setCargoMax(cargoMax);
		
	}
	public Cargo(int comTier, int comPrice, String comName, int comValue, int cargoMin, int cargoMax){
		super.setComTier(comTier);
		super.setComPrice(comPrice);
		super.setComName(comName);
		super.setComValue(comValue);
		this.setCargoMin(cargoMin);
		this.setCargoMax(cargoMax);
		
	}

	public int getCargoMin() {
		return cargoMin;
	}

	public void setCargoMin(int cargoMin) {
		this.cargoMin = cargoMin;
	}

	public int getCargoMax() {
		return cargoMax;
	}

	public void setCargoMax(int cargoMax) {
		this.cargoMax = cargoMax;
	}

	public int getTotalCargo() {
		return totalCargo;
	}

	public void setTotalCargo(int totalCargo) {
		this.totalCargo = totalCargo;
	}
	
}
