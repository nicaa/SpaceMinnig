package nicaa.project.dk.game.spacemining;


import planetMap.PlanetMapActivity;
import sound.Effects;
import spaceShip.AllComponets;
import spaceShip.Cargo;
import spaceShip.Drill;
import spaceShip.Fuel;
import spaceShip.SpaceShip;
import tiles.OreTest1;
import tiles.Tile_test1;
import tiles.Tile_test2;
import cargoSpace.CargoActivity;
import fuelTank.FuelActivity;
import gameThread.GameThread;
import level.Level;
import miningDrill.MiningActivity;
import android.R.bool;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameScreenView extends View{
	private Context context;
	private int width;
	private int height;
	private int x = 0;
	private int y = 0;
	private Paint paint = new Paint();
	
	//Bitmaps
	private Bitmap miningDrill;
	private Bitmap fuelTank;
	private Bitmap cargospace;
	private Bitmap planetMap;
	private Bitmap background;
	private Bitmap sellOre;
	private Bitmap addFuel;
	
	private Bitmap miningDrill_p;
	private Bitmap fuelTank_p;
	private Bitmap cargospace_p;
	private Bitmap planetMap_p;
	private Bitmap sellOre_p;
	private Bitmap addFuel_p;
	//Ship init Bitmaps
	private Bitmap drill1;
	private Bitmap cargo1;
	private Bitmap fuel1;
	//SpaceShip Global Variable
	public static SpaceShip ship;
	
	private Effects effects;
	
	private boolean start;
	
	
	public GameScreenView(Context context) {
		super(context);
		this.context = context;
		start = true;
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		width = displayMetrics.widthPixels;
		height = displayMetrics.heightPixels;
		paint.setTextSize((int)(width*0.025));
		paint.setColor(Color.BLACK);
		
		AllComponets allComponets = new AllComponets(); //<--- init components
		effects = new Effects(context);
		initBitmaps();
		initSpaceShip();
		
		
		
		
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(background, (int)(0), (int)(0), null);
		canvas.drawBitmap(miningDrill, (int)(width*0.10), (int)(height * 0.10), null);
		canvas.drawBitmap(fuelTank, (int)(width/2) - (fuelTank.getWidth()/2), (int)(height * 0.10), null);
		canvas.drawBitmap(cargospace, (int)(width*0.90) - (cargospace.getWidth()), (int)(height * 0.10), null);
		canvas.drawBitmap(planetMap, (int)(width/2) - (planetMap.getWidth()/2), (int)(height * 0.75), null);
		canvas.drawBitmap(sellOre, (int)(width*0.10) , (int)(height * 0.75), null);
		canvas.drawBitmap(addFuel, (int)(width*0.90) - (addFuel.getWidth()) , (int)(height * 0.75), null);
		drawText(canvas);
	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int eventAction = event.getAction();
		int X = (int)event.getX();
		int Y = (int)event.getY();
		x = X;
		y = Y;
	    switch (eventAction) {
	        case MotionEvent.ACTION_DOWN: 
	            // finger touches the screen
	        	 if( X > width * 0.10 && X < width*0.10 + miningDrill.getWidth() &&
	 	        		Y > height * 0.10 && Y < height * 0.10 + miningDrill.getHeight() )
	        	 {
	 	        		 miningDrill = miningDrill_p;	 	        		  	        		 
	 	         }
	        	 if( X > (width/2) - (fuelTank.getWidth()/2) && X < (width/2) - (fuelTank.getWidth()/2) + fuelTank.getWidth() && 
	        	 	Y > height * 0.10 && Y < height * 0.10 + fuelTank.getHeight() )
	             {
	        		fuelTank = fuelTank_p;
	             }
	        	 if( X > (width*0.90) - (cargospace.getWidth()) && X < (width*0.90) - (cargospace.getWidth()) + cargospace.getWidth() && 
	        	 	Y > height * 0.10 && Y < height * 0.10 + cargospace.getHeight() )
	             {
	        		 cargospace = cargospace_p;
	             }
	        	 if( X > (width/2) - (planetMap.getWidth()/2) && X < (width/2) - (planetMap.getWidth()/2) + planetMap.getWidth() &&
	        		Y > (height * 0.75) && Y < (height * 0.75) + planetMap.getHeight() )
	             {
	        		planetMap = planetMap_p;
	             }
	        	 if( X > width * 0.10 && X < width*0.10 + sellOre.getWidth() &&
	        		Y > height * 0.75 && Y < height * 0.75 + sellOre.getHeight() )
	             {
	        		 sellOre = sellOre_p;	        		 
	             }
	        	 
	        	 if( X > (int)(width*0.90) - (addFuel.getWidth()) && X < (int)(width*0.90) - (addFuel.getWidth()) + addFuel.getWidth() &&
	        		Y > height * 0.75 && Y < height * 0.75 + addFuel.getHeight() )
	             {
	        		 addFuel = addFuel_p;
	             }
	            break;

	        case MotionEvent.ACTION_MOVE:
	            // finger moves on the screen
	            break;

	        case MotionEvent.ACTION_UP:   
	        	 if( X > width * 0.10 && X < width*0.10 + miningDrill.getWidth() &&
	        		Y > height * 0.10 && Y < height * 0.10 + miningDrill.getHeight() )
	             {
	        		 effects.tapEffect();
	        		 Intent intent = new Intent(context, MiningActivity.class);
	 	        	 context.startActivity(intent);
	        		 
	             }
	        	 if( X > (width/2) - (fuelTank.getWidth()/2) && X < (width/2) - (fuelTank.getWidth()/2) + fuelTank.getWidth() && 
	        	 	Y > height * 0.10 && Y < height * 0.10 + fuelTank.getHeight() )
	             {
	        		 
	        		 effects.tapEffect();
	        		 Intent intent = new Intent(context, FuelActivity.class);
	 	        	 context.startActivity(intent);
	             }
	        	 if( X > (width*0.90) - (cargospace.getWidth()) && X < (width*0.90) - (cargospace.getWidth()) + cargospace.getWidth() && 
	        	 	Y > height * 0.10 && Y < height * 0.10 + cargospace.getHeight() )
	             {
	        		 
	        		 effects.tapEffect();
	        		 Intent intent = new Intent(context, CargoActivity.class);
	 	        	 context.startActivity(intent);
	             }
	        	 if( X > (width/2) - (planetMap.getWidth()/2) && X < (width/2) - (planetMap.getWidth()/2) + planetMap.getWidth() &&
	        		Y > (height * 0.75) && Y < (height * 0.75) + planetMap.getHeight() )
	             {
	        		 effects.tapEffect();
	        		 Intent intent = new Intent(context, PlanetMapActivity.class);
	 	        	 context.startActivity(intent);
	             }
	        	 if( X > width * 0.10 && X < width*0.10 + sellOre.getWidth() &&
	        		Y > height * 0.75 && Y < height * 0.75 + sellOre.getHeight() )
	             {
	        		 if (GameScreenView.ship.getCargo().getTotalCargo() > 0) {
	        			 effects.sell();
					}else {
						effects.error();
					}
	        		
	        		 ship.updateMoney(Cargo.cargoList);
	        		 
	             }
	        	 
	        	 if( X > (int)(width*0.90) - (addFuel.getWidth()) && X < (int)(width*0.90) - (addFuel.getWidth()) + addFuel.getWidth() &&
	        		Y > height * 0.75 && Y < height * 0.75 + addFuel.getHeight() )
	             {
	        		 if (GameScreenView.ship.getFuel().getTotalFuel() == GameScreenView.ship.getFuel().getFuelMax()) {
						effects.error();
					}else{
						effects.refill();
		        		addFuel();
					}
					
	        		
	             }
	        	 initBitmaps();
	            break;
	    }
	   
	    invalidate();
		return true;
	}
	public void initBitmaps(){
		
		//UI Bitmaps
		miningDrill = BitmapFactory.decodeResource(this.getResources(), R.drawable.mining_drill);       
		miningDrill = Bitmap.createScaledBitmap(miningDrill, (int)(width * 0.25), (int)(height * 0.20), true);
		
		miningDrill_p = BitmapFactory.decodeResource(this.getResources(), R.drawable.mining_drill_p);       
		miningDrill_p = Bitmap.createScaledBitmap(miningDrill_p, (int)(width * 0.25), (int)(height * 0.20), true);
		
		
		fuelTank = BitmapFactory.decodeResource(this.getResources(), R.drawable.fuel_tank);       
		fuelTank = Bitmap.createScaledBitmap(fuelTank, (int)(width * 0.25), (int)(height * 0.20), true);
		
		fuelTank_p = BitmapFactory.decodeResource(this.getResources(), R.drawable.fuel_tank_p);       
		fuelTank_p = Bitmap.createScaledBitmap(fuelTank_p, (int)(width * 0.25), (int)(height * 0.20), true);
		
		
		cargospace = BitmapFactory.decodeResource(this.getResources(), R.drawable.cargo_space);       
		cargospace = Bitmap.createScaledBitmap(cargospace, (int)(width * 0.25), (int)(height * 0.20), true);
		
		cargospace_p = BitmapFactory.decodeResource(this.getResources(), R.drawable.cargo_space_p);       
		cargospace_p = Bitmap.createScaledBitmap(cargospace_p, (int)(width * 0.25), (int)(height * 0.20), true);
		
		
		planetMap = BitmapFactory.decodeResource(this.getResources(), R.drawable.planet_map);       
		planetMap = Bitmap.createScaledBitmap(planetMap, (int)(width * 0.25), (int)(height * 0.20), true);
		
		planetMap_p = BitmapFactory.decodeResource(this.getResources(), R.drawable.planet_map_p);       
		planetMap_p = Bitmap.createScaledBitmap(planetMap_p, (int)(width * 0.25), (int)(height * 0.20), true);
		
		
		sellOre = BitmapFactory.decodeResource(this.getResources(), R.drawable.sell_ore);       
		sellOre = Bitmap.createScaledBitmap(sellOre, (int)(width * 0.25), (int)(height * 0.20), true);
		
		sellOre_p = BitmapFactory.decodeResource(this.getResources(), R.drawable.sell_ore_p);       
		sellOre_p = Bitmap.createScaledBitmap(sellOre_p, (int)(width * 0.25), (int)(height * 0.20), true);
		
		
		addFuel = BitmapFactory.decodeResource(this.getResources(), R.drawable.add_fuel);       
		addFuel = Bitmap.createScaledBitmap(addFuel, (int)(width * 0.25), (int)(height * 0.20), true);
		
		addFuel_p = BitmapFactory.decodeResource(this.getResources(), R.drawable.add_fuel_p);       
		addFuel_p = Bitmap.createScaledBitmap(addFuel_p, (int)(width * 0.25), (int)(height * 0.20), true);
		
		
		background = BitmapFactory.decodeResource(this.getResources(), R.drawable.spaceship_ui);       
		background = Bitmap.createScaledBitmap(background, (int)(width), (int)(height), true);
	
		
//		//SpaceShip Bitmaps
//		drill1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.drill_1);       
//		drill1 = Bitmap.createScaledBitmap(drill1, (int)(width * 0.35), (int)(height * 0.45), true);
//		
//		cargo1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.cargo_1);       
//		cargo1 = Bitmap.createScaledBitmap(cargo1, (int)(width * 0.35), (int)(height * 0.45), true);
//		
//		fuel1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fuel_1);       
//		fuel1 = Bitmap.createScaledBitmap(fuel1, (int)(width * 0.35), (int)(height * 0.45), true);
		
	}
	public void initSpaceShip(){
		if (start == true) {
			
			// laver mit start SpaceShip

			OreTest1 ore1 =  new OreTest1(context ,width , height);
			Tile_test1 tile1 = new Tile_test1(context, width, height);
			Tile_test2 tile2 = new Tile_test2(context, width, height);
			Level level = new Level(tile1, tile2, ore1, 1, 3);
			
			
			Bitmap spaceshipLeft;
			spaceshipLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship_left);
			spaceshipLeft = Bitmap.createScaledBitmap(spaceshipLeft, (int)(width * 0.101),(int) (height * 0.150 ), false);
			
			Bitmap spaceshipRight;
			spaceshipRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship_right);
			spaceshipRight = Bitmap.createScaledBitmap(spaceshipRight, (int)(width * 0.101),(int) (height * 0.150 ), false);
			
			Bitmap spaceshipUp;
			spaceshipUp = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship_up);
			spaceshipUp = Bitmap.createScaledBitmap(spaceshipUp, (int)(width * 0.101),(int) (height * 0.150 ), false);
			
			Bitmap spaceshipDown;
			spaceshipDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship_down);
			spaceshipDown = Bitmap.createScaledBitmap(spaceshipDown, (int)(width * 0.101),(int) (height * 0.150 ), false);
			
			ship = new SpaceShip(spaceshipLeft, AllComponets.drillList.get(0),AllComponets.cargoList.get(0),AllComponets.fuelList.get(0), width, height, context );
			ship.setShipLeft(spaceshipLeft);
			ship.setShipRight(spaceshipRight);
			ship.setShipDown(spaceshipDown);
			ship.setShipUp(spaceshipUp);
			ship.setLevel(level);
			ship.setMoney(2000);
			
			
			start = false;
		}
		
	}
	
	public void drawText(Canvas canvas){
		//drill text
		canvas.drawText("Drill Name: " + ship.getDrill().getComName(), (int)(width*0.17), (int)(height*0.44), paint);
		canvas.drawText("Drill Tier: " + ship.getDrill().getComTier(), (int)(width*0.17), (int)(height*0.49), paint);
		//Fuel Text
		canvas.drawText("Tank Name: " + ship.getFuel().getComName(), (int)(width*0.37), (int)(height*0.44), paint);
		canvas.drawText("Tank Tier: " + ship.getFuel().getComTier(), (int)(width*0.37), (int)(height*0.49), paint);
		canvas.drawText("Max Fuel: " + ship.getFuel().getFuelMax(), (int)(width*0.37), (int)(height*0.54), paint);
		canvas.drawText("Current Fuel: " + ship.getFuel().getTotalFuel(), (int)(width*0.37), (int)(height*0.59), paint);
		//Cargo Text
		canvas.drawText("Cargo Name: " + ship.getCargo().getComName(),(int) (width * 0.59), (int) (height * 0.44), paint);
		canvas.drawText("Cargo Tier: " + ship.getCargo().getComTier(), (int)(width*0.59), (int)(height*0.49), paint);
		canvas.drawText("Max Cargo: " + ship.getCargo().getCargoMax(), (int)(width*0.59), (int)(height*0.54), paint);
		canvas.drawText("Current Cargo: " + ship.getCargo().getTotalCargo(), (int)(width*0.59), (int)(height*0.59), paint);
		//Info Text
		canvas.drawText("Total Tier: " + ship.getTotalTier(), (int)(width*0.25), (int)(height*0.65), paint);
		canvas.drawText("Total Money: $" + ship.getMoney(), (int)(width*0.42), (int)(height*0.65), paint);
	}
	
	
	
	public void addFuel(){
		int amount = 50;
		int price = 10;
		if (ship.getMoney() >= price) {
				ship.getFuel().setTotalFuel(ship.getFuel().getTotalFuel() + amount);
				
				if (ship.getFuel().getTotalFuel() >= ship.getFuel().getFuelMax()) {
					
					ship.getFuel().setTotalFuel(ship.getFuel().getFuelMax());
				}
			ship.setMoney(ship.getMoney()-price);
		}
		
	}
	
	
	
	
	
}
