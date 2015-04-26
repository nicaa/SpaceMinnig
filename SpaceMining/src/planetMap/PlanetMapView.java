package planetMap;

import java.util.ArrayList;

import sound.Effects;
import tiles.BossPlatform;
import tiles.OreTest1;
import tiles.Ore_Emerald;
import tiles.Ore_silver;
import tiles.Tile1_level2;
import tiles.Tile1_level3;
import tiles.Tile2_level2;
import tiles.Tile2_level3;
import tiles.Tile_test1;
import tiles.Tile_test2;

import level.BossLevel;
import level.Level;
import level.LevelActivity;
import miningDrill.MiningActivity;
import nicaa.project.dk.game.spacemining.GameScreenView;
import nicaa.project.dk.game.spacemining.R;
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

public class PlanetMapView  extends View{
	private int width;
	private int height;
	private Context context;
	//Bitmaps
	public Bitmap map1;
	public Bitmap map2;
	public Bitmap map3;
	public Bitmap map4;
	public Bitmap arrowLeft;
	public Bitmap arrowRight;
	public Bitmap go;
	public Bitmap back;
	public Bitmap go_p;
	public Bitmap back_p;
	private Paint paint = new Paint();
	private Paint planetLevel = new Paint();
	
	private ArrayList<Bitmap> maps = new ArrayList<Bitmap>();
	private ArrayList<Level>levels = new ArrayList<Level>();
	private int index = 0;
	
	private Effects effects;

	public PlanetMapView(Context context) {
		super(context);
		this.context = context;
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		width = displayMetrics.widthPixels;
		height = displayMetrics.heightPixels;
		paint.setTextSize((int)(width*0.025));
		paint.setColor(Color.BLACK);
		planetLevel.setTextSize((int)(width*0.050));
		planetLevel.setColor(Color.BLACK);
		effects = new Effects(context);
		index = GameScreenView.ship.getLevel().getLevel()-1;
		initBitmaps();
		initLevels();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawBitmap(maps.get(index), (int) (width/2) - (map1.getWidth()/2), (int) (height/2) - (map1.getHeight()/2), null);
		canvas.drawText("Level " + (index + 1), (int) (width/2) - (int)(width*0.08), (int)(height * 0.20), planetLevel);
		
		canvas.drawBitmap(arrowLeft, (int) (width/2) - (arrowLeft.getWidth()/2) - (int)(width * 0.35), (int) (height/2) - (arrowLeft.getHeight()/2), null);
		canvas.drawBitmap(arrowRight, (int) (width/2) - (arrowRight.getWidth()/2) + (int)(width * 0.35), (int) (height/2) - (arrowRight.getHeight()/2), null);
		if (checkTier()) {
			canvas.drawBitmap(go, (int)(width/2) - (go.getWidth()/2), (int)(height*0.78), null);
		}else{
			canvas.drawText("Tier level too low", (int)(width*0.40),(int)(height*0.85) , paint);
			canvas.drawText("Current Tier = " + GameScreenView.ship.getTotalTier(), (int)(width*0.40),(int)(height*0.90) , paint);
			canvas.drawText("Tier needed = " + levels.get(index).getTierLevel(), (int)(width*0.40),(int)(height*0.95) , paint);
		}
		
		canvas.drawBitmap(back, (int)(width- back.getWidth()), 0, null);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int eventAction = event.getAction();
		int X = (int)event.getX();
		int Y = (int)event.getY();
		
	    switch (eventAction) {
	        case MotionEvent.ACTION_DOWN: 
	        	//Go Button
	        	 if(checkTier() &&  X > (int)(width/2) - (go.getWidth()/2) && X <(int)(width/2) - (go.getWidth()/2) + go.getWidth() &&
	        			Y > (int)(height*0.78) && Y <(int)(height*0.78) + go.getHeight() )
		             {
	        			go = go_p;
		             }
	        	 	
	        	//back Button
	        	 if( X > (int)(width- back.getWidth()) && X <(int)(width- back.getWidth()) + back.getWidth() &&
	        			Y > 0 && Y < 0 + back.getHeight() )
		             {
	        		 	back = back_p;
		             }
	            break;

	        case MotionEvent.ACTION_MOVE:
	            break;

	        case MotionEvent.ACTION_UP:   
	        	
	        	//ArrowLeft
	        	if( X >(int) (width/2) - (arrowLeft.getWidth()/2) - (int)(width * 0.35) && X < (int) (width/2) - (arrowLeft.getWidth()/2) - (int)(width * 0.35) + arrowLeft.getWidth() &&
		        		Y > (int) (height/2) - (arrowLeft.getHeight()/2) && Y < (int) (height/2) - (arrowLeft.getHeight()/2) + arrowLeft.getHeight() )
		             {
	        		if(index == 0){
	        			
	        		}else {
	        			 effects.tapEffect();
	        			 index--;
					}
		        		
		        		 
		             }
	        	//ArrowRight
	        	if( X >(int) (width/2) - (arrowRight.getWidth()/2) + (int)(width * 0.35) && X < (int) (width/2) - (arrowRight.getWidth()/2) + (int)(width * 0.35) + arrowRight.getWidth() &&
		        		Y > (int) (height/2) - (arrowRight.getHeight()/2) && Y < (int) (height/2) - (arrowRight.getHeight()/2) + arrowRight.getHeight() )
		             {
	        		
	        			if(index + 1 == levels.size()){
	        				
	        			}else {
	        				effects.tapEffect();
	        				index++;	
						}
	        			
		             }
	        	//Go Button
	        	 if(checkTier() && X > (int)(width/2) - (go.getWidth()/2) && X <(int)(width/2) - (go.getWidth()/2) + go.getWidth() &&
	        			Y > (int)(height*0.78) && Y <(int)(height*0.78) + go.getHeight() )
		             {
	        		 	effects.fly();
	        			GameScreenView.ship.setLevel(levels.get(index));
	        			GameScreenView.ship.setSpaceshipHealth(((int)(width * 0.10)));
	        			Intent intent = new Intent(context, LevelActivity.class);
		 	        	context.startActivity(intent);
		 	        	((PlanetMapActivity)context).finish();
		             }
	        	 	
	        	//back Button
	        	 if( X > (int)(width- back.getWidth()) && X <(int)(width- back.getWidth()) + back.getWidth() &&
	        			Y > 0 && Y < 0 + back.getHeight() )
		             {
	        		 	effects.tapEffect();
		    			((PlanetMapActivity)context).finish();
		    			
		             }
	        	init_btn();
	            break;
	    }
	    invalidate();
		return true;
	}
	public void initBitmaps(){
		map1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.map_1);       
		map1 = Bitmap.createScaledBitmap(map1, (int)(width * 0.40), (int)(height * 0.60), true);
		maps.add(map1);
		
		map2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.map_2);       
		map2 = Bitmap.createScaledBitmap(map2, (int)(width * 0.40), (int)(height * 0.60), true);
		maps.add(map2);
		
		map3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.map_3);       
		map3 = Bitmap.createScaledBitmap(map3, (int)(width * 0.40), (int)(height * 0.60), true);
		maps.add(map3);
		
		map4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.map_4);       
		map4 = Bitmap.createScaledBitmap(map4, (int)(width * 0.40), (int)(height * 0.60), true);
		maps.add(map4);
		
		arrowLeft = BitmapFactory.decodeResource(this.getResources(), R.drawable.arrow_left);       
		arrowLeft = Bitmap.createScaledBitmap(arrowLeft, (int)(width * 0.20), (int)(height * 0.30), true);
		
		arrowRight = BitmapFactory.decodeResource(this.getResources(), R.drawable.arrow_right);       
		arrowRight = Bitmap.createScaledBitmap(arrowRight, (int)(width * 0.20), (int)(height * 0.30), true);
		
		go_p = BitmapFactory.decodeResource(this.getResources(), R.drawable.go_btn_p);       
		go_p = Bitmap.createScaledBitmap(go_p, (int)(width * 0.20), (int)(height * 0.17), true);
		
		back_p = BitmapFactory.decodeResource(this.getResources(), R.drawable.back_p);       
		back_p = Bitmap.createScaledBitmap(back_p, (int)(width * 0.20), (int)(height * 0.17), true);
		
		init_btn();
		
	}
	public void initLevels(){
		//Level 1
		OreTest1 ore1 =  new OreTest1(context ,width , height);
		Tile_test1 tile1 = new Tile_test1(context, width, height);
		Tile_test2 tile2 = new Tile_test2(context, width, height);
		Level level1 = new Level(tile1, tile2, ore1, 1, 3);
		levels.add(level1);
		//level 2
		Ore_silver silver = new Ore_silver(context, width, height);
		Tile1_level2 level2_tile1 = new Tile1_level2(context, width, height);
		Tile2_level2 level2_tile2 = new Tile2_level2(context, width, height);
		Level level2 = new Level(level2_tile1, level2_tile2, silver, 2, 3);
		levels.add(level2);
		//Level 3 Boss
		Ore_Emerald emerald = new Ore_Emerald(context, width, height);
		BossPlatform platform = new BossPlatform(context, width, height);
		Tile1_level3 level3_tile1 = new Tile1_level3(context, width, height);
		Tile2_level3 level3_tile2 = new Tile2_level3(context, width, height);
		Level level3 = new Level(level3_tile1, level3_tile1,emerald, 3, 0);
		level3.setPlatform(platform);
		level3.setType("boss");
		levels.add(level3);
		
		
	}
	
	
	public boolean checkTier(){
		boolean tierOk = false;
		if (GameScreenView.ship.getTotalTier() >= levels.get(index).getTierLevel()) {
			tierOk = true;
		}
		return tierOk;
	}
	public void init_btn(){
		go = BitmapFactory.decodeResource(this.getResources(), R.drawable.go_btn);       
		go = Bitmap.createScaledBitmap(go, (int)(width * 0.20), (int)(height * 0.17), true);
		
		back = BitmapFactory.decodeResource(this.getResources(), R.drawable.back);       
		back = Bitmap.createScaledBitmap(back, (int)(width * 0.20), (int)(height * 0.17), true);
		
	}

}
