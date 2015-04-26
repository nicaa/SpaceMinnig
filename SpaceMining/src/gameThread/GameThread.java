package gameThread;

import java.util.ArrayList;
import java.util.Random;


import level.Level;
import mobs.Boss;

import sound.Effects;
import spaceShip.Cargo;
import tiles.BossPlatform;
import tiles.Ore;
import tiles.OreTest1;
import tiles.Tile;
import tiles.Tile_test1;
import tiles.Tile_test2;
import nicaa.project.dk.game.spacemining.GameScreenView;
import nicaa.project.dk.game.spacemining.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class GameThread extends Thread{
	
	boolean running;
	private GameView gameView;
	private Context context;
	private SurfaceHolder surfaceHolder;
	
	private boolean debug = false; // <-- used for easier testing in game! true = debug functions enable
	private int width;
	private int height;
	
	private int shipX;
	private int shipY;
	
	private ArrayList<Tile> tileList = new ArrayList<Tile>();
	private ArrayList<Ore> oreList = new ArrayList<Ore>();
	private ArrayList<Tile> platforms = new ArrayList<Tile>();
	private Random randoms = new Random();
	
	private Bitmap arr_left;
	private Bitmap arr_right;
	private Bitmap arr_up;
	private Bitmap arr_down;
	private Bitmap mine_btn;
	private Bitmap spaceShip;
	
	private boolean mineDetect = false;
	private boolean mineOre = false;
	private boolean minePlatform = false;
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	
	private Paint paintFuel = new Paint();
	private Paint paintCargo = new Paint();
	private Paint paintFade = new Paint();
	
	private Level level;
	private int move;
	
	private final Effects effects;
	private boolean fadeStart = false;
	private int xFade = (int)(width *0.40);
	private int yFade = (int)(height *0.60);
	private String oreFade = "";
	private int oreValue = 0;
	private Thread thread;
	public Boss boss; 
	public Boss boss2;
	private int removeBossHealth = 0;
	private int removeShipHealth = 0;
	
	
	
	public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
		this.gameView = gameView;
		this.surfaceHolder = surfaceHolder;
		context = gameView.getContext();
		DisplayMetrics display = gameView.getResources().getDisplayMetrics();
		width = display.widthPixels;
		height = display.heightPixels;
		paintFuel.setTextSize((int)(width*0.025));
		paintFuel.setColor(Color.BLACK);
		paintCargo.setTextSize((int)(width*0.025));
		paintCargo.setColor(Color.BLACK);
		paintFade.setTextSize((int)(width*0.040));
		paintFade.setColor(Color.GREEN);
		level = GameScreenView.ship.getLevel();
		if(GameScreenView.ship.getFuel().getTotalFuel() <= 0 && level.getType() == "boss"){
			GameScreenView.ship.getFuel().setTotalFuel(1);
		}
		boss = new Boss(width, height, context);
		boss2 = new Boss(width, height, context);
		boss2.setX(width-(int)(boss2.getBossSprite().getWidth()));
		effects = new Effects(context);
		removeBossHealth = (int)(width * 0.20);
		removeShipHealth = (int)(width * 0.04);
		initBitmaps();
		generateTiles();
		generateMap();
		generateOres();
		
		shipX = (int)(width/2)-(int)(spaceShip.getWidth()/2);
		shipY = (int)(height/2)-(int)(spaceShip.getHeight()/2);
		textFade();
	}
	
	

	public void setRunning(boolean running){
		this.running = running;
	}
	
	@Override
	public void run() {

		while (running) {
			Canvas c = new Canvas();

			try {
				c = surfaceHolder.lockCanvas(null);
				// Syncronized så der kun er en der kan tegne af gangen da vi
				// arbejder med threads
				synchronized (surfaceHolder) {

					if (c != null) {
						c.drawColor(Color.DKGRAY);
						if (fadeStart) {
							textFade();	
						}
						doDraw(c);
						oreCollision();
						missileCollision(boss);
						platformCollision();
						move();
						checkResources();
						
						
						//System.out.println(GameScreenView.ship.getRect().width() + "    " +GameScreenView.ship.getRect().height() );
					}

				}// end of synchronized
			} catch (Exception e) {                 
                e.printStackTrace();
            
			} finally {
				if (c != null) {
					surfaceHolder.unlockCanvasAndPost(c); // unlocker så der kan
					// tegnes igen.
				}

			}
		}

	}
	public void doDraw(Canvas canvas){
		drawMap(canvas);
		drawOres(canvas);
		drawButtons(canvas);
		drawText(canvas);
		drawSpaceship(canvas);
		if (boss.isRunning()) {
			boss.drawBoss(canvas);	
			boss2.drawBoss(canvas);
			GameScreenView.ship.drawHealthBar(canvas);
		}
		drawWinner(canvas);
	}
	
	
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (surfaceHolder) {
			int eventAction = event.getAction();
			int X = (int) event.getX();
			int Y = (int) event.getY();
			move = (int)(width*0.004);
			switch (eventAction) {
			case MotionEvent.ACTION_DOWN:
				// left arrow
	        	if ( X >= (int) (width*0.02) && X < ((int) (width*0.02) + arr_down.getWidth())   				
	            && Y >= (int)(height*0.80) && Y < ((int)(height*0.80) + arr_down.getHeight())) { 
	        		if(shipX - move<=0 || GameScreenView.ship.getFuel().getTotalFuel() <= 0){
	        			left = false;
	        			effects.error();
	        		}else {
	        			left = true;
	        		}
	        	}
	        	// up Arrow
	        	if ( X >= (int) (width*0.14) && X < ((int) (width*0.14) + arr_down.getWidth())   				
	    	    && Y >= (int)(height*0.80) && Y < ((int)(height*0.80) + arr_down.getHeight())) { 
	        		if (shipY - move <= 0 || GameScreenView.ship.getFuel().getTotalFuel() <= 0) {
	        			up = false;
	        			effects.error();
					}else{
						up = true;
					}
	        	}
	        	// right Arrow
	        	if ( X >= (int) (width*0.98)-arr_right.getWidth() && X < ((width*0.98)-arr_right.getWidth() + arr_down.getWidth())   				
	    	    && Y >= (int)(height*0.80) && Y < ((int)(height*0.80) + arr_down.getHeight())) { 
	        		if (shipX + move >= width - GameScreenView.ship.getShipLeft().getWidth() || GameScreenView.ship.getFuel().getTotalFuel() <= 0) {
	        			right = false;
	        			effects.error();
					}else {
						right = true;
					}
	        	}
	        	// down Arrow
	        	if ( X >= (int) (width*0.86)-arr_right.getWidth() && X < ((width*0.86)-arr_right.getWidth() + arr_down.getWidth())   				
	    	    && Y >= (int)(height*0.80) && Y < ((int)(height*0.80) + arr_down.getHeight())) { 
	        		if (shipY + move >=  (tileList.get(1).getBitmap().getHeight() * 5)- GameScreenView.ship.getShipLeft().getHeight() || GameScreenView.ship.getFuel().getTotalFuel() <= 0) {
	        			down = false;
	        			effects.error();
					}else {
						down = true;
					}
	        	}
	        	// mine btn
	        	if ( X >= (int) (int)(width/2)-(int)(mine_btn.getWidth()/2) && X < (int)(width/2)-(int)(mine_btn.getWidth()/2) + mine_btn.getWidth()  				
	    	    	    && Y >= (int)(height*0.80) && Y < ((int)(height*0.80) + mine_btn.getHeight())) { 
	        		
	        		mineOre = true;
	        		minePlatform = true;
	        	}
								
				break;
			case MotionEvent.ACTION_UP:
				mineCollision(X, Y, oreList); // Check for spaceship collision with ore's
				
				//reset move varibles.
				left = false;
				up = false;
				right = false;
				down = false;
				
				break;
			case MotionEvent.ACTION_MOVE:
				
			}
			 
			return true;
		}

	}
	public void mineCollision(int X, int Y, ArrayList<Ore> list){
		for (int i = 0; i < list.size(); i++) {
			
			if ( X >= (int)(list.get(i).getX()) && X < ((int)(list.get(i).getX()) + list.get(i).getBitmap().getWidth())   				
	            && Y >= (int)(list.get(i).getY()) && Y < ((int)(list.get(i).getY()) + list.get(i).getBitmap().getHeight())) { 

				System.out.println(oreList.get(i).getType());
				
			}
		}
	}
	
	public void move(){
		//left
		if (shipX - move <=0 || checkFuel()) {
			left = false;
		}else if(left){
		
			shipX = shipX - move;	
			spaceShip = GameScreenView.ship.getShipLeft();
			if (level.getType() == "normal") {
				GameScreenView.ship.fuelUse();
			}
		
		}//left end
		
		// up
		if (shipY - move <= 0|| checkFuel()) {
			up = false;
		} else if (up) {

			shipY = shipY - move;
			spaceShip = GameScreenView.ship.getShipUp();
			if (level.getType() == "normal") {
				GameScreenView.ship.fuelUse();
			}
			

		}// up end
		
		// right
		if (shipX + move >= width - GameScreenView.ship.getShipLeft().getWidth() || checkFuel()) {
			right = false;
		} else if (right) {

			shipX = shipX + move;
			spaceShip = GameScreenView.ship.getShipRight();
			if (level.getType() == "normal") {
				GameScreenView.ship.fuelUse();
			}
			

		}// right end
		
		//down
		if (shipY + move >=  (tileList.get(1).getBitmap().getHeight() * 5)- GameScreenView.ship.getShipLeft().getHeight() || checkFuel()) {
			down = false;
		} else if (down) {

			shipY = shipY + move;	
			spaceShip = GameScreenView.ship.getShipDown();
			if (level.getType() == "normal") {
				GameScreenView.ship.fuelUse();
			}
			

		}// down end
		
		// chance Ships Rect! if confused draw ships rect by using --->canvas.drawRect(r, paint)<----
		GameScreenView.ship.getRect().set(shipX, shipY + (int)(height * 0.04),// <-- lower rect height!
				(shipX +spaceShip.getWidth()), shipY + spaceShip.getHeight() - (int)(height * 0.05));// <-- set Rect for collision detection
		
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
		if (level.getType() == "boss") {
			System.out.println("This is a boss level");
			for (int i = 0; i < 5 ; i++) {
				BossPlatform platfrom = new BossPlatform(context, width, height);
				platforms.add(platfrom);
			}
		}
	}
	
	public void generateOres(){
		oreList.clear();
		ArrayList<Tile> copy = new ArrayList<Tile>(tileList);
		int r = 0;
		boss.setRunning(false);
		boss2.setRunning(false);
		if (level.getType() == "normal") {
			
			for (int i = 0; i < 10; i++) {
				r = randoms.nextInt(copy.size());
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
		if(level.getType() == "boss") { // if Boss fight do boss stuff here!
			boss.setRunning(true);
			boss2.setRunning(true);
			
			for (int i = 0; i < platforms.size(); i++) {
				r = randoms.nextInt(copy.size() - 10) + 10;
				int x = copy.get(r).getX();
				int y = copy.get(r).getY();
				Bitmap bitmap = level.getPlatform().getBitmap();
				platforms.get(i).setX(x);
				platforms.get(i).setY(y);
				platforms.get(i).setRect(new Rect(x, y,  x + bitmap.getWidth(), y + bitmap.getHeight()));
				
				copy.remove(r);
			}
			
			
			Thread thread = new Thread(boss); //<-- init boss thread.
			thread.start();
			Thread thread2 = new Thread(boss2); //<-- init boss thread.
			thread2.start();
		}
	}
	
	public void drawMap(Canvas canvas) {
		//draws Tiles
		for (int i = 0; i < tileList.size(); i++) {
			canvas.drawBitmap(tileList.get(i).getBitmap(), tileList.get(i).getX(), tileList.get(i).getY(), null);
		}
		//Draws Platforms for boss battle
		if (level.getType() == "boss") {
			for (int i = 0; i < platforms.size(); i++) {
				if (i == 0) {
					canvas.drawBitmap(platforms.get(i).getBitmap(), platforms.get(i).getX(), platforms.get(i).getY(), null);
				}
				//canvas.drawBitmap(platforms.get(i).getBitmap(), platforms.get(i).getX(), platforms.get(i).getY(), null);
			}
		}
		// Debug draws rect of spaceship = easier to see collision detection!
		if (debug) {
			canvas.drawRect(GameScreenView.ship.getRect(), paintFade);
		}
	}
	
	public void drawOres(Canvas canvas){
		//draws ore!
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
	
	public void drawWinner(Canvas canvas){
		if (GameScreenView.ship.getSpaceshipHealth() <= 0) {
			canvas.drawText("You have lost!", (int)(width * 0.40), (int)(height * 0.45), paintFade);
		}else if(boss.getBossHealth() <= 0){
			canvas.drawText("You won congratulations!", (int)(width * 0.30), (int)(height * 0.45), paintFade);
		}
	}
	public void oreCollision(){
		for (int i = 0; i < oreList.size(); i++) { //  TODO Fix this collision detection with ores by using Rect.intersects!!! ugly code!!!
			if ( shipX + (int)(spaceShip.getWidth()/2)>= (int)(oreList.get(i).getX()) && shipX + (int)(spaceShip.getWidth()/2)< ((int)(oreList.get(i).getX()) + oreList.get(i).getBitmap().getWidth())   				
		            && shipY + (int)(spaceShip.getHeight()/2) >= (int)(oreList.get(i).getY()) && shipY + (int)(spaceShip.getHeight()/2) < ((int)(oreList.get(i).getY()) + oreList.get(i).getBitmap().getHeight()))
			{ 
					mineDetect = true;
					
					if (mineOre == true) {
						
						if (GameScreenView.ship.getCargo().getTotalCargo() + oreList.get(i).getWeight() <= GameScreenView.ship.getCargo().getCargoMax()) {
							effects.mine();
							oreFade = oreList.get(i).getType();
							oreValue = oreList.get(i).getValue();
							fadeStart = true;
							Cargo.cargoList.add((Ore) oreList.get(i));
							GameScreenView.ship.getCargo().setTotalCargo(GameScreenView.ship.getCargo().getTotalCargo() + oreList.get(i).getWeight());
							oreList.remove(i);
						}else{
							effects.error();
						}

						mineOre = false;
					}
			}
		}
		
	}
	

	
	public void missileCollision(Boss boss){
		if (boss.getMissiles().size() >= 0 && boss.isRunning()) {
			for (int i = 0; i < boss.getMissiles().size(); i++) {
				if (Rect.intersects(boss.getMissiles().get(i).getRect(),GameScreenView.ship.getRect()))
				{ 
						System.out.println("Ship collision with missile!!!!");
						GameScreenView.ship.setSpaceshipHealth(GameScreenView.ship.getSpaceshipHealth() - (int) (removeShipHealth));
						boss.missiles.remove(i);
						effects.missileCollision();
						// if your healht is 0 boss is stopped
						if (GameScreenView.ship.getSpaceshipHealth() <= 0) {
							boss.setRunning(false);
							boss2.setRunning(false);
						}
				}
			}
		}
	}
	
	public void platformCollision(){
		if (platforms.size() >= 0 && boss.isRunning()) {
			for (int i = 0; i < platforms.size(); i++) {
				if (i == 0 && Rect.intersects(platforms.get(i).getRect(), GameScreenView.ship.getRect()))    
				{ 
					mineDetect = true;
					if (minePlatform) {
						effects.mine();
						// change boss health.
						boss.setBossHealth(boss.getBossHealth() - (int) (removeBossHealth * 0.22));
						boss2.setBossHealth(boss2.getBossHealth() - (int) (removeBossHealth * 0.22));
						platforms.remove(i);
					}
					minePlatform = false;
					// check if boss health is less than 0 then battle is over
					if (boss.getBossHealth() <= 0) {
						boss.setRunning(false);
						boss2.setRunning(false);
					}
					// boss.setRunning(false); // eliminate the boss and stops
					// the rendering of boss.
						
				}
			}
		}
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
		if (level.getType() == "normal") {
			canvas.drawText("Fuel", (int)(width/2)-(int)(width*0.20), (int)(height*0.85), paintFuel);
			canvas.drawText(GameScreenView.ship.getFuel().getTotalFuel() + "/" + GameScreenView.ship.getFuel().getFuelMax(), (int)(width/2)-(int)(width*0.20), (int)(height*0.90), paintFuel);
			canvas.drawText("Cargo", (int)(width/2)+(int)(width*0.13), (int)(height*0.85), paintCargo);
			canvas.drawText(GameScreenView.ship.getCargo().getTotalCargo() + "/" + GameScreenView.ship.getCargo().getCargoMax(), (int)(width/2)+(int)(width*0.13), (int)(height*0.90), paintCargo);
		}
		
		if (fadeStart) {
			canvas.drawText("+"+oreFade + " $"+oreValue   , (int)xFade, (int)yFade,paintFade);
		}
	}
	public void checkResources(){
		if(GameScreenView.ship.getFuel().getTotalFuel() < (int)(GameScreenView.ship.getFuel().getFuelMax() * 0.25)){
			paintFuel.setColor(Color.RED);
		}else{
			paintFuel.setColor(Color.BLACK);
		}
		
		if (GameScreenView.ship.getCargo().getTotalCargo() > GameScreenView.ship.getCargo().getCargoMax() - (int)(GameScreenView.ship.getCargo().getCargoMax() * 0.25)) {
			paintCargo.setColor(Color.RED);
		}else{
			paintCargo.setColor(Color.BLACK);
		}
	}
	public boolean checkFuel(){
		boolean fuel;
		if(GameScreenView.ship.getFuel().getTotalFuel() <= 0){
			fuel = true;
		}else{
			fuel = false;
		}
		return fuel;
	}
	public void textFade(){
		
			thread = new Thread() {
			    @Override
			    public void run() {
			    	if (!fadeStart) {
			    		xFade = (int)(width *0.40);
			        	yFade = (int)(height *0.60);
					}
			    	
			        try {
			            while(fadeStart) {
			            		yFade -= 1;
			            		paintFade.setAlpha(paintFade.getAlpha() - 2 );
				            	sleep(200);
				            	if (paintFade.getAlpha() <= 20) {
				            		fadeStart = false;
				            		yFade =(int) (height *0.60);
									paintFade.setAlpha(255);
								}
			            }
			        } catch (Exception e) {
			            e.printStackTrace();
			            Log.v("Exception Occured", e.getMessage());
			        }
			    }
			};
			thread.start();
	}
	
	

}
