package tiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class Tile {
	
	private Context context;
	public Bitmap bitmap;
	private int x;
	private int y;
	private String type;
	private Rect rect;
	
	
	public Tile(Context context){
		setContext(context);
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}

	public boolean collision(Rect rect1, Rect rect2) {
		if (rect1.intersect(rect2)) {
			return true;
		} else {
			return false;
		}
	}
	
	

}
