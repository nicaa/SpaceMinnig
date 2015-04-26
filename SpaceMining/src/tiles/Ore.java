package tiles;

import android.content.Context;

public class Ore extends Tile {
	private int weight;
	private int value;

	public Ore(Context context) {
		super(context);
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
