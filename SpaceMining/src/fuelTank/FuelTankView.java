package fuelTank;

import java.util.ArrayList;

import sound.Effects;
import spaceShip.AllComponets;

import nicaa.project.dk.game.spacemining.GameScreenView;
import nicaa.project.dk.game.spacemining.R;

import miningDrill.MiningActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FuelTankView extends View {

	private int width;
	private int height;
	private Context context;
	// Bitmaps
	public Bitmap fuel1;
	public Bitmap fuel2;
	public Bitmap fuel3;
	public Bitmap fuel4;
	public Bitmap arrowLeft;
	public Bitmap arrowRight;
	public Bitmap buy;
	public Bitmap back;
	public Bitmap buy_p;
	public Bitmap back_p;
	private Effects effects;
	
	private Paint paint = new Paint();

	private ArrayList<Bitmap> fuelTanks = new ArrayList<Bitmap>();
	private int index = GameScreenView.ship.getFuel().getComTier()-1;

	public FuelTankView(Context context) {
		super(context);
		this.context = context;
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		width = displayMetrics.widthPixels;
		height = displayMetrics.heightPixels;
		paint.setTextSize((int)(width*0.025));
		paint.setColor(Color.BLACK);
		effects = new Effects(context);
		initBitmaps();
		this.setBackgroundColor(Color.WHITE);
		
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		canvas.drawBitmap(fuelTanks.get(index), (int) (width/2) - (fuel1.getWidth()/2), (int) (height/2) - (fuel1.getHeight()/2), null);
		
		canvas.drawBitmap(arrowLeft, (int) (width/2) - (arrowLeft.getWidth()/2) - (int)(width * 0.35), (int) (height/2) - (arrowLeft.getHeight()/2), null);
		canvas.drawBitmap(arrowRight, (int) (width/2) - (arrowRight.getWidth()/2) + (int)(width * 0.35), (int) (height/2) - (arrowRight.getHeight()/2), null);
		if (checkMoney()) {
			canvas.drawBitmap(buy, (int)(width/2) - (buy.getWidth()/2), (int)(height*0.78), null);
		}else {
			canvas.drawText("Not enough money ", (int)(width*0.40), (int)(height*0.80), paint);
		}
		
		canvas.drawBitmap(back, (int)(width- back.getWidth()), 0, null);
		drawText(canvas);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int eventAction = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();

		switch (eventAction) {
		case MotionEvent.ACTION_DOWN:
			 if( checkMoney() &&X > (int)(width/2) - (buy.getWidth()/2) && X <(int)(width/2) - (buy.getWidth()/2) + buy.getWidth() &&
	        			Y > (int)(height*0.78) && Y <(int)(height*0.78) + buy.getHeight() )
		             {
	        		 	buy = buy_p;
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
        		
        			if(index + 1 == fuelTanks.size()){
        				
        			}else {
        				effects.tapEffect();
        				index++;	
					}
        			
	             }
        	//Buy Button
        	 if( checkMoney() && X > (int)(width/2) - (buy.getWidth()/2) && X <(int)(width/2) - (buy.getWidth()/2) + buy.getWidth() &&
        			Y > (int)(height*0.78) && Y <(int)(height*0.78) + buy.getHeight() )
	             {
        		 		effects.buyEffect();
        				int totalFuel = GameScreenView.ship.getFuel().getTotalFuel();
        				GameScreenView.ship.setMoney(GameScreenView.ship.getMoney()- AllComponets.fuelList.get(index).getComPrice());
        				GameScreenView.ship.setFuel(AllComponets.fuelList.get(index));
        				GameScreenView.ship.getFuel().setTotalFuel(totalFuel);
        				GameScreenView.ship.updateTier();
	             }
        	 	
        	//back Button
        	 if( X > (int)(width- back.getWidth()) && X <(int)(width- back.getWidth()) + back.getWidth() &&
        			Y > 0 && Y < 0 + back.getHeight() )
	             {
        		 	effects.tapEffect();
	    			((FuelActivity)context).finish();
	             }
        	 init_btn();
			break;
		}
		invalidate();
		return true;
	}
	
	public void initBitmaps(){
		fuel1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fuel_1);       
		fuel1 = Bitmap.createScaledBitmap(fuel1, (int)(width * 0.35), (int)(height * 0.45), true);
		fuelTanks.add(fuel1);
		
		fuel2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fuel_2);       
		fuel2 = Bitmap.createScaledBitmap(fuel2, (int)(width * 0.35), (int)(height * 0.45), true);
		fuelTanks.add(fuel2);
		
		fuel3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fuel_3);       
		fuel3 = Bitmap.createScaledBitmap(fuel3, (int)(width * 0.35), (int)(height * 0.45), true);
		fuelTanks.add(fuel3);
		
		fuel4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.fuel_4);       
		fuel4 = Bitmap.createScaledBitmap(fuel4, (int)(width * 0.35), (int)(height * 0.45), true);
		fuelTanks.add(fuel4);
		
		arrowLeft = BitmapFactory.decodeResource(this.getResources(), R.drawable.arrow_left);       
		arrowLeft = Bitmap.createScaledBitmap(arrowLeft, (int)(width * 0.20), (int)(height * 0.30), true);
		
		arrowRight = BitmapFactory.decodeResource(this.getResources(), R.drawable.arrow_right);       
		arrowRight = Bitmap.createScaledBitmap(arrowRight, (int)(width * 0.20), (int)(height * 0.30), true);
		
		buy_p = BitmapFactory.decodeResource(this.getResources(), R.drawable.buy_p);       
		buy_p = Bitmap.createScaledBitmap(buy_p, (int)(width * 0.20), (int)(height * 0.17), true);
		
		back_p = BitmapFactory.decodeResource(this.getResources(), R.drawable.back_p);       
		back_p = Bitmap.createScaledBitmap(back_p, (int)(width * 0.20), (int)(height * 0.17), true);
		
		init_btn();
		
		
		
	}
	public boolean checkMoney(){
		boolean money = false;
		if (GameScreenView.ship.getMoney() >= AllComponets.fuelList.get(index).getComPrice()) {
			money = true;
		}
		return money;
	}
	
	public void drawText(Canvas canvas){
		canvas.drawText("Fuel Tier = " + AllComponets.fuelList.get(index).getComTier(), (int)(width*0.05), (int)(height*0.80), paint);
		canvas.drawText("Fuel Price = " + AllComponets.fuelList.get(index).getComPrice(), (int)(width*0.05), (int)(height*0.85), paint);
		
		canvas.drawText("Current Money = " + GameScreenView.ship.getMoney(), (int)(width*0.70), (int)(height*0.80), paint);
		canvas.drawText("Current Tier = " + GameScreenView.ship.getFuel().getComTier(), (int)(width*0.70), (int)(height*0.85), paint);
	}
	public void init_btn(){
		buy = BitmapFactory.decodeResource(this.getResources(), R.drawable.buy);       
		buy = Bitmap.createScaledBitmap(buy, (int)(width * 0.20), (int)(height * 0.17), true);
		
		back = BitmapFactory.decodeResource(this.getResources(), R.drawable.back);       
		back = Bitmap.createScaledBitmap(back, (int)(width * 0.20), (int)(height * 0.17), true);
		
	}

}
