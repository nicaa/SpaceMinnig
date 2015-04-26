package mobs;

import java.util.ArrayList;
import java.util.Random;

import nicaa.project.dk.game.spacemining.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Boss implements Runnable{
	private int width;
	private int height;
	private Context context;
	private int bossHealth = 0;
	private int x = 1;
	private int y = 1;
	private Bitmap bossSprite;
	private Bitmap missile;
	private boolean running;
	public static ArrayList<Shoot> missiles;
	private Random r = new Random();
	private Paint healthPaint;
	
	
	
	public Boss(int width, int height, Context context){
		this.width = width;
		this.height = height;
		this.context = context;
		initSprites();
		setMissiles(new ArrayList<Shoot>());
		bossHealth = (int)(width* 0.20);
		healthPaint = new Paint();
		healthPaint.setColor(Color.GREEN);
	}
	
	public void initSprites(){
		setBossSprite(BitmapFactory.decodeResource(context.getResources(), R.drawable.boss_1));
		setBossSprite(Bitmap.createScaledBitmap(getBossSprite(), (int)(width * 0.20),(int) (height * 0.15), false));
		
		missile = BitmapFactory.decodeResource(context.getResources(), R.drawable.missile_1);
		missile = Bitmap.createScaledBitmap(missile, (int)(width * 0.030),(int) (height * 0.060), false);
	}

	@Override
	public void run() {
		boolean right= true;
		boolean left = false;
		int moveX = (int)(width*0.0025);
		while(isRunning()){
			if (getX() < (int)(width - bossSprite.getWidth()) && right) {
				setX(getX() + moveX);
			}else {
				left = true;
				right = false;
			}
			
			if (getX() > 0 && left) {
				setX(getX() - moveX);
			}else {
				left = false;
				right = true;
			}
			
			//System.out.println("i am running!");
			synchronized (missiles) {
				loadMissile();
				fireMissile();
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}

	public Bitmap getBossSprite() {
		return bossSprite;
	}

	public void setBossSprite(Bitmap bossSprite) {
		this.bossSprite = bossSprite;
	}
	public void bossMove(int move, boolean right, boolean left){
		if (getX() < (int)(width - bossSprite.getWidth()) && right) {
			setX(getX() + move);
		}else {
			left = true;
			right = false;
		}
		
		if (getX() > 0 && left) {
			setX(getX() - move);
		}else {
			left = false;
			right = true;
		}
	}
	
	public void drawBoss(Canvas canvas){
		//draws the boss sprite
		canvas.drawBitmap(bossSprite, getX(), (int)(getY() + (height *0.02)), null);
		// Draws the missiles from Missile ArrayList
		if (getMissiles().size() > 0 ) { 
			for (int i = 0; i < getMissiles().size(); i++) {
				canvas.drawBitmap(getMissiles().get(i).getSprite(),getMissiles().get(i).getX(), getMissiles().get(i).getY(), null);	
			}
		}
		drawHealthBar(canvas);
	}
	
	public void drawHealthBar(Canvas canvas){
		canvas.drawRect(getX(), getY()+ (int)(height*0.009), getX() + bossHealth, getY() + (int)(height*0.03), healthPaint);
	}
	
	public void loadMissile(){
		int ammo = r.nextInt(100);
		if (ammo == 0) {
			//Create new missile and add them to missile list
			Shoot shoot = new Shoot((int)(getX()  + width * 0.07), (int)(getY() + getBossSprite().getHeight()), missile);
			getMissiles().add(shoot);
			System.out.println("Shoot");
		}
	}
	
	public void fireMissile(){
		if (missiles.size() > 0 ) {
			for (int i = 0; i < getMissiles().size(); i++) {
				getMissiles().get(i).setY(getMissiles().get(i).getY()+(int)(width*0.0017)); //<-- set y coordinate to the coordinate plus 1: 1 = speed;
				//missile Collision
				getMissiles().get(i).getRect().set(missiles.get(i).getX() , missiles.get(i).getY(), 
						missiles.get(i).getX() + missiles.get(i).getSprite().getWidth(),missiles.get(i).getY() + missiles.get(i).getSprite().getHeight()); //<-- Colision detection på Rect
				
				//Missile hit bottom of screen = remove
				if (getMissiles().get(i).getY() > (int)(height * 0.69)) {
					getMissiles().remove(i);
				}
			}
		}
		
	}

	public ArrayList<Shoot> getMissiles() {
		return missiles;
	}

	public void setMissiles(ArrayList<Shoot> missiles) {
		this.missiles = missiles;
	}

	public int getBossHealth() {
		return bossHealth;
	}

	public void setBossHealth(int bossHealth) {
		this.bossHealth = bossHealth;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
