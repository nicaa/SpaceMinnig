package mobs;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Shoot {

	private int x;
	private int y;
	private Bitmap sprite;
	private Rect rect;

	public Shoot(int x, int y, Bitmap sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		setRect(new Rect(x, y,  x + sprite.getWidth(), x + sprite.getHeight()));
		
	}

	public Bitmap getSprite() {
		return sprite;
	}

	public void setSprite(Bitmap sprite) {
		this.sprite = sprite;
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
	public boolean collision(Rect rect1, Rect rect2){
		if (rect1.intersect(rect2)) {
			return true;
		}else{
			return false;
		}
	}

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}

}
