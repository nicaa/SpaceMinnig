package spaceShip;

import java.util.ArrayList;

import tiles.Ore;

import level.Level;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class SpaceShip {

	private Context context;
	private int width;
	private int height;
	private Bitmap shipLeft;
	private Bitmap shipRight;
	private Bitmap shipDown;
	private Bitmap shipUp;
	private Drill drill;
	private Cargo cargo;
	private Fuel fuel;
	private Level level;
	private int money;
	private int totalTier;
	private int fuelUse;
	private Rect rect;
	private int spaceshipHealth;
	private Paint healthPaint;
	private int startHealthX;

	public SpaceShip() {
		
	}
	
	
	public SpaceShip(Bitmap ship, Drill drill, Cargo cargo, Fuel fuel, int width, int height, Context context) {
		
		this.width = width;
		this.height = height;
		this.context = context;
		this.shipLeft = ship;
		this.drill = drill;
		this.cargo = cargo;
		this.fuel = fuel;
		setTotalTier(drill.getComTier() + fuel.getComTier() + cargo.getComTier());
		setFuelUse(1);
		setRect(new Rect());
		// init healt and paint health
		setSpaceshipHealth((int)(width * 0.10));
		healthPaint = new Paint();
		healthPaint.setColor(Color.GREEN);
		startHealthX = (int)(spaceshipHealth/2);
	}

	
	public Bitmap getShipLeft() {
		return shipLeft;
	}

	public void setShipLeft(Bitmap ship) {
		this.shipLeft = ship;
	}

	public Drill getDrill() {
		return drill;
	}

	public void setDrill(Drill drill) {
		this.drill = drill;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Fuel getFuel() {
		return fuel;
	}

	public void setFuel(Fuel fuel) {
		this.fuel = fuel;
	}


	public Bitmap getShipRight() {
		return shipRight;
	}


	public void setShipRight(Bitmap shipRight) {
		this.shipRight = shipRight;
	}


	public Bitmap getShipDown() {
		return shipDown;
	}


	public void setShipDown(Bitmap shipDown) {
		this.shipDown = shipDown;
	}


	public Bitmap getShipUp() {
		return shipUp;
	}


	public void setShipUp(Bitmap shipUp) {
		this.shipUp = shipUp;
	}


	public Level getLevel() {
		return level;
	}


	public void setLevel(Level level) {
		this.level = level;
	}


	public int getMoney() {
		return money;
	}


	public void setMoney(int money) {
		this.money = money;
	}


	public int getTotalTier() {
		return totalTier;
	}


	public void setTotalTier(int totalTier) {
		this.totalTier = totalTier;
	}
	public void updateTier(){
		setTotalTier(drill.getComTier() + fuel.getComTier() + cargo.getComTier());
	}


	public int getFuelUse() {
		return fuelUse;
	}


	public void setFuelUse(int fuelUse) {
		this.fuelUse = fuelUse;
		
	}
	
	public void fuelUse(){
		fuel.setTotalFuel(fuel.getTotalFuel()-getFuelUse());
		
		boolean debug = true;
		
		if (debug == true && fuel.getTotalFuel() < 1) {
			fuel.setTotalFuel(100);
		}
	}
	
	public void updateMoney(ArrayList<Ore>ores){
		for (int i = 0; i < ores.size(); i++) {
			setMoney(getMoney() + ores.get(i).getValue());
			
		}
		Cargo.cargoList.clear();
		cargo.setTotalCargo(0);
	}


	public Rect getRect() {
		return rect;
	}


	public void setRect(Rect rect) {
		this.rect = rect;
	}


	public int getSpaceshipHealth() {
		return spaceshipHealth;
	}


	public void setSpaceshipHealth(int spaceshipHealth) {
		this.spaceshipHealth = spaceshipHealth;
	}
	public void drawHealthBar(Canvas canvas){
		canvas.drawRect((int)((width / 2)-(int)(spaceshipHealth/2)), (int)(height * 0.77), (int)(width / 2)+(int)(spaceshipHealth/2),(int)(height * 0.79), healthPaint);
	}

}
