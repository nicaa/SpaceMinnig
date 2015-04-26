package level;

import java.util.ArrayList;
import java.util.Random;

import nicaa.project.dk.game.spacemining.GameScreenView;
import nicaa.project.dk.game.spacemining.R;

import spaceShip.Cargo;
import tiles.Ore;
import tiles.OreTest1;
import tiles.Ore_silver;
import tiles.Tile;
import tiles.Tile_test1;
import tiles.Tile_test2;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;


public class LevelView extends View {

	private Context context;
	private int width;
	private int height;
	
	private int shipX;
	private int shipY;
	
	private ArrayList<Tile> tileList = new ArrayList<Tile>();
	private ArrayList<Ore> oreList = new ArrayList<Ore>();
	private Random randoms = new Random();
	
	private Bitmap arr_left;
	private Bitmap arr_right;
	private Bitmap arr_up;
	private Bitmap arr_down;
	private Bitmap mine_btn;
	private Bitmap spaceShip;
	private Bitmap mineBack_btn;
	
	private boolean mineDetect = false;
	private boolean mineOre = false;
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	private Paint paint = new Paint();
	
	private Level level;
	private int move;

	public LevelView(Context context) {
		super(context);
		this.context = context;
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		width = displayMetrics.widthPixels;
		height = displayMetrics.heightPixels;
		setBackgroundColor(Color.DKGRAY);
		paint.setTextSize((int)(width*0.025));
		paint.setColor(Color.BLACK);
		level = GameScreenView.ship.getLevel();
		initBitmaps();
		generateTiles();
		generateMap();
		generateOres();
		shipX = (int)(width/2)-(int)(spaceShip.getWidth()/2);
		shipY = (int)(height/2)-(int)(spaceShip.getHeight()/2);
		
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		drawMap(canvas);
		drawOres(canvas);
		drawButtons(canvas);
		drawText(canvas);
		drawSpaceship(canvas);
		oreCollision(canvas);
		
		
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int eventAction = event.getAction();
		int X = (int)event.getX();
		int Y = (int)event.getY();
		move = (int)(width*0.030);
		
	    switch (eventAction) {
	        case MotionEvent.ACTION_DOWN: 
	        	// left arrow
	        	if ( X >= (int) (width*0.02) && X < ((int) (width*0.02) + arr_down.getWidth())   				
	            && Y >= (int)(height*0.80) && Y < ((int)(height*0.80) + arr_down.getHeight())) { 
	        		if(shipX - move<=0 || GameScreenView.ship.getFuel().getTotalFuel() <= 0){
	        			
	        		}else {
	        			shipX = shipX - move;	
	        			spaceShip = GameScreenView.ship.getShipLeft();
	        			GameScreenView.ship.fuelUse();					
	        		}
	        		
				System.out.println("left");
	        	}
	        	// up Arrow
	        	if ( X >= (int) (width*0.14) && X < ((int) (width*0.14) + arr_down.getWidth())   				
	    	    && Y >= (int)(height*0.80) && Y < ((int)(height*0.80) + arr_down.getHeight())) { 
	        		if (shipY - move <= 0 || GameScreenView.ship.getFuel().getTotalFuel() <= 0) {
						
					}else{
						shipY = shipY - move;	
						spaceShip = GameScreenView.ship.getShipUp();
						GameScreenView.ship.fuelUse();		
						
					}
	        		
	    				System.out.println("up");
	        	}
	        	// right Arrow
	        	if ( X >= (int) (width*0.98)-arr_right.getWidth() && X < ((width*0.98)-arr_right.getWidth() + arr_down.getWidth())   				
	    	    && Y >= (int)(height*0.80) && Y < ((int)(height*0.80) + arr_down.getHeight())) { 
	        		if (shipX + move >= width - GameScreenView.ship.getShipLeft().getWidth() || GameScreenView.ship.getFuel().getTotalFuel() <= 0) {
						
					}else {
						shipX = shipX + move;	
						spaceShip = GameScreenView.ship.getShipRight();
						GameScreenView.ship.fuelUse();
					}
	        		
	    				System.out.println("right");
	        	}
	        	// down Arrow
	        	if ( X >= (int) (width*0.86)-arr_right.getWidth() && X < ((width*0.86)-arr_right.getWidth() + arr_down.getWidth())   				
	    	    && Y >= (int)(height*0.80) && Y < ((int)(height*0.80) + arr_down.getHeight())) { 
	        		if (shipY + move >=  (tileList.get(1).getBitmap().getHeight() * 5)- GameScreenView.ship.getShipLeft().getHeight() || GameScreenView.ship.getFuel().getTotalFuel() <= 0) {
						
					}else {
						shipY = shipY + move;	
						spaceShip = GameScreenView.ship.getShipDown();
						GameScreenView.ship.fuelUse();
					}
	        		
	    				System.out.println("down");
	        	}
	        	// mine btn
	        	if ( X >= (int) (int)(width/2)-(int)(mine_btn.getWidth()/2) && X < (int)(width/2)-(int)(mine_btn.getWidth()/2) + mine_btn.getWidth()  				
	    	    	    && Y >= (int)(height*0.80) && Y < ((int)(height*0.80) + mine_btn.getHeight())) { 
	        		mineOre = true;
	        		
	        	}
	        
	        	
	            break;

	        case MotionEvent.ACTION_MOVE:
	        	
	        	
	            break;

	        case MotionEvent.ACTION_UP:   
	        	for (int i = 0; i < oreList.size(); i++) {
					
					if ( X >= (int)(oreList.get(i).getX()) && X < ((int)(oreList.get(i).getX()) + oreList.get(i).getBitmap().getWidth())   				
			            && Y >= (int)(oreList.get(i).getY()) && Y < ((int)(oreList.get(i).getY()) + oreList.get(i).getBitmap().getHeight())) { 

						System.out.println(oreList.get(i).getType());
						
					}
				}
	        	
	            break;
	    }
	    invalidate();
		return true;
	}
	
	
	
	public void generateMap(){
		int offsetX = 0;
		int offsetY = 0;
		int tileIndex = 0;
		
		for (int i = 0; i < 5; i++) {
			offsetX = 0;
			offsetY = offsetY + tileList.get(tileIndex).getBitmap().getHeight();
			
			if (i == 0) {
				offsetY = 0;
			}

			for (int j = 0; j < 10; j++) {
				tileList.get(tileIndex).setX((int)(offsetX));
				tileList.get(tileIndex).setY((int)(offsetY));
				
				offsetX = offsetX + tileList.get(tileIndex).getBitmap().getWidth();

				tileIndex++;

			}
			
		}
	}
	
	
	public void generateTiles(){
		tileList.clear();
		
		for (int i = 0; i < 50; i++) {
			int r = randoms.nextInt(7);
			if(r == 0){
				Tile_test2 tile2 = new Tile_test2(context, width, height);
				tile2.setBitmap(level.getTile2().getBitmap());
				tileList.add(tile2);
								
			}else{
				Tile_test1 tile1 = new Tile_test1(context, width, height);
				tile1.setBitmap(level.getTile1().getBitmap());
				tileList.add(tile1);
			}
		}
	}
	
	public void generateOres(){
		oreList.clear();
		ArrayList<Tile> copy = new ArrayList<Tile>(tileList);
		for (int i = 0; i < 7; i++) {
			int r = randoms.nextInt(copy.size());
			
			
			OreTest1 oreTest1 = new OreTest1(context, width, height);
			
			
			oreTest1.setBitmap(level.getOre1().getBitmap());
			oreTest1.setType(level.getOre1().getType());
			oreTest1.setValue(level.getOre1().getValue());
			oreTest1.setWeight(level.getOre1().getWeight());
			oreTest1.setX(copy.get(r).getX());
			oreTest1.setY(copy.get(r).getY());
			oreList.add(oreTest1);
			copy.remove(r);
			
		}
		
	}
	
	public void drawMap(Canvas canvas) {
		for (int i = 0; i < tileList.size(); i++) {
			canvas.drawBitmap(tileList.get(i).getBitmap(), tileList.get(i).getX(), tileList.get(i).getY(), null);
		}
	}
	
	public void drawOres(Canvas canvas){
		for (int i = 0; i < oreList.size(); i++) {
			canvas.drawBitmap(oreList.get(i).getBitmap(), oreList.get(i).getX(), oreList.get(i).getY(), null);
		}
		
	}
	
	public void drawButtons(Canvas canvas){
		canvas.drawBitmap(arr_left, (int) (width*0.02), (int)(height*0.80), null);
		canvas.drawBitmap(arr_up, (int) (width*0.14), (int)(height*0.80), null);
		canvas.drawBitmap(arr_right, (int) (width*0.98)-arr_right.getWidth(), (int)(height*0.80), null);
		canvas.drawBitmap(arr_down, (int) (width*0.86)-arr_down.getWidth(), (int)(height*0.80), null);
		if (mineDetect) {
			canvas.drawBitmap(mine_btn, (int)(width/2)-(int)(mine_btn.getWidth()/2), (int)(height*0.80), null);
			mineDetect = false;
		}
		
		
	}
	public void drawSpaceship(Canvas canvas){
		canvas.drawBitmap(spaceShip, shipX, shipY, null);
	}
	public void oreCollision(Canvas canvas){
		for (int i = 0; i < oreList.size(); i++) {
			if ( shipX + (int)(spaceShip.getWidth()/2)>= (int)(oreList.get(i).getX()) && shipX + (int)(spaceShip.getWidth()/2)< ((int)(oreList.get(i).getX()) + oreList.get(i).getBitmap().getWidth())   				
		            && shipY + (int)(spaceShip.getHeight()/2) >= (int)(oreList.get(i).getY()) && shipY + (int)(spaceShip.getHeight()/2) < ((int)(oreList.get(i).getY()) + oreList.get(i).getBitmap().getHeight()))
			{ 
					mineDetect = true;
					
					if (mineOre == true) {
						if (GameScreenView.ship.getCargo().getTotalCargo() + oreList.get(i).getWeight() <= GameScreenView.ship.getCargo().getCargoMax()) {
							Cargo.cargoList.add((Ore) oreList.get(i));
							GameScreenView.ship.getCargo().setTotalCargo(GameScreenView.ship.getCargo().getTotalCargo() + oreList.get(i).getWeight());
							oreList.remove(i);
						}

						mineOre = false;
					}
			}
		}
		System.out.println(Cargo.cargoList.size());
		
	}
	
	public void printTile(){
		for (int i = 0; i < tileList.size(); i++) {
			System.out.println("x = " + tileList.get(i).getX() + "y = " + tileList.get(i).getY());
		}
	}
	
	public void initBitmaps(){
		arr_down = BitmapFactory.decodeResource(context.getResources(), R.drawable.arr_down);
		arr_down = Bitmap.createScaledBitmap(arr_down, (int)(width * 0.101),(int) (height * 0.150 ), true);
		
		arr_up = BitmapFactory.decodeResource(context.getResources(), R.drawable.arr_up);
		arr_up = Bitmap.createScaledBitmap(arr_up, (int)(width * 0.101),(int) (height * 0.150 ), true);
		
		arr_left = BitmapFactory.decodeResource(context.getResources(), R.drawable.arr_left);
		arr_left = Bitmap.createScaledBitmap(arr_left, (int)(width * 0.101),(int) (height * 0.150 ), true);
		
		arr_right = BitmapFactory.decodeResource(context.getResources(), R.drawable.arr_right);
		arr_right = Bitmap.createScaledBitmap(arr_right, (int)(width * 0.101),(int) (height * 0.150 ), true);
		
		mine_btn = BitmapFactory.decodeResource(context.getResources(), R.drawable.mine_btn);
		mine_btn = Bitmap.createScaledBitmap(mine_btn, (int)(width * 0.15),(int) (height * 0.15 ), true);
		
		spaceShip = GameScreenView.ship.getShipLeft();
	}
	
	public void drawText(Canvas canvas){
		canvas.drawText("Fuel", (int)(width/2)-(int)(width*0.20), (int)(height*0.85), paint);
		canvas.drawText(GameScreenView.ship.getFuel().getTotalFuel() + "/" + GameScreenView.ship.getFuel().getFuelMax(), (int)(width/2)-(int)(width*0.20), (int)(height*0.90), paint);
		
		
		canvas.drawText("Cargo", (int)(width/2)+(int)(width*0.13), (int)(height*0.85), paint);
		canvas.drawText(GameScreenView.ship.getCargo().getTotalCargo() + "/" + GameScreenView.ship.getCargo().getCargoMax(), (int)(width/2)+(int)(width*0.13), (int)(height*0.90), paint);
	}

	
	
	

}
