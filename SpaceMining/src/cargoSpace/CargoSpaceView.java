package cargoSpace;

import java.util.ArrayList;

import sound.Effects;
import spaceShip.AllComponets;

import nicaa.project.dk.game.spacemining.GameScreenView;
import nicaa.project.dk.game.spacemining.R;
import fuelTank.FuelActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.effect.Effect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class CargoSpaceView extends View{
	
	private int width;
	private int height;
	private Context context;
	// Bitmaps
	public Bitmap cargo1;
	public Bitmap cargo2;
	public Bitmap cargo3;
	public Bitmap cargo4;
	public Bitmap arrowLeft;
	public Bitmap arrowRight;
	public Bitmap buy;
	public Bitmap back;
	public Bitmap buy_p;
	public Bitmap back_p;
	private Effects effects;
	
	private Paint paint = new Paint();

	private ArrayList<Bitmap> cargos = new ArrayList<Bitmap>();
	private int index = GameScreenView.ship.getCargo().getComTier()-1;

	public CargoSpaceView(Context context) {
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
		canvas.drawBitmap(cargos.get(index), (int) (width/2) - (cargo1.getWidth()/2), (int) (height/2) - (cargo1.getHeight()/2), null);
		
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
        		
        			if(index + 1 == cargos.size()){
        				
        			}else {
        				effects.tapEffect();
        				index++;	
					}
        			
	             }
        	//Buy Button
        	 if(checkMoney() && X > (int)(width/2) - (buy.getWidth()/2) && X <(int)(width/2) - (buy.getWidth()/2) + buy.getWidth() &&
        			Y > (int)(height*0.78) && Y <(int)(height*0.78) + buy.getHeight() )
	             {
        			effects.buyEffect();
        			int totalCargo = GameScreenView.ship.getCargo().getTotalCargo();
        			GameScreenView.ship.setMoney(GameScreenView.ship.getMoney()- AllComponets.cargoList.get(index).getComPrice());
        			GameScreenView.ship.setCargo(AllComponets.cargoList.get(index));
        			GameScreenView.ship.getCargo().setTotalCargo(totalCargo);
        			GameScreenView.ship.updateTier();
	             }
        	 	
        	//back Button
        	 if( X > (int)(width- back.getWidth()) && X <(int)(width- back.getWidth()) + back.getWidth() &&
        			Y > 0 && Y < 0 + back.getHeight() )
	             {
        		 	effects.tapEffect();
	    			((CargoActivity)context).finish();
	             }
        	 init_btn();
			break;
		}
		invalidate();
		return true;
	}
	
	public void initBitmaps(){
		cargo1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.cargo_1);       
		cargo1 = Bitmap.createScaledBitmap(cargo1, (int)(width * 0.35), (int)(height * 0.45), true);
		cargos.add(cargo1);
		
		cargo2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.cargo_2);       
		cargo2 = Bitmap.createScaledBitmap(cargo2, (int)(width * 0.35), (int)(height * 0.45), true);
		cargos.add(cargo2);
		
		cargo3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.cargo_3);       
		cargo3 = Bitmap.createScaledBitmap(cargo3, (int)(width * 0.35), (int)(height * 0.45), true);
		cargos.add(cargo3);
		
		cargo4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.cargo_4);       
		cargo4 = Bitmap.createScaledBitmap(cargo4, (int)(width * 0.35), (int)(height * 0.45), true);
		cargos.add(cargo4);
		
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
		if (GameScreenView.ship.getMoney() >= AllComponets.cargoList.get(index).getComPrice()) {
			money = true;
		}
		return money;
	}
	
	public void drawText(Canvas canvas){
		canvas.drawText("Cargo Tier = " + AllComponets.cargoList.get(index).getComTier(), (int)(width*0.05), (int)(height*0.80), paint);
		canvas.drawText("Cargo Price = " + AllComponets.cargoList.get(index).getComPrice(), (int)(width*0.05), (int)(height*0.85), paint);
		
		canvas.drawText("Current Money = " + GameScreenView.ship.getMoney(), (int)(width*0.70), (int)(height*0.80), paint);
		canvas.drawText("Current Tier = " + GameScreenView.ship.getCargo().getComTier(), (int)(width*0.70), (int)(height*0.85), paint);
	}
	public void init_btn(){
		buy = BitmapFactory.decodeResource(this.getResources(), R.drawable.buy);       
		buy = Bitmap.createScaledBitmap(buy, (int)(width * 0.20), (int)(height * 0.17), true);
		
		back = BitmapFactory.decodeResource(this.getResources(), R.drawable.back);       
		back = Bitmap.createScaledBitmap(back, (int)(width * 0.20), (int)(height * 0.17), true);
		
	}

}
