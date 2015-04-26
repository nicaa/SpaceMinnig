package level;

import tiles.BossPlatform;
import tiles.Ore;
import tiles.Tile;

public class Level {

	private Tile tile1;
	private Tile tile2;
	private BossPlatform platform;
	private Ore ore1;
	private int level;
	private int tierLevel;
	private String type = "normal";
		
	public Level(Tile tile1, Tile tile2, Ore ore1, int level,int tierLevel) {
		this.tile1 = tile1;
		this.tile2 = tile2;
		setOre1(ore1);
		this.level = level;
		this.tierLevel = tierLevel;
		
	}

	public Tile getTile1() {
		return tile1;
	}

	public void setTile1(Tile tile1) {
		this.tile1 = tile1;
	}

	public Tile getTile2() {
		return tile2;
	}

	public void setTile2(Tile tile2) {
		this.tile2 = tile2;
	}

	public Ore getOre1() {
		return ore1;
	}

	public void setOre1(Ore ore1) {
		this.ore1 = ore1;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTierLevel() {
		return tierLevel;
	}

	public void setTierLevel(int tierLevel) {
		this.tierLevel = tierLevel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BossPlatform getPlatform() {
		return platform;
	}

	public void setPlatform(BossPlatform platform) {
		this.platform = platform;
	}

}
