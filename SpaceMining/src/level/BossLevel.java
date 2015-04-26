package level;


import tiles.BossPlatform;
import tiles.Ore;
import tiles.Tile;


public class BossLevel extends Level{

	private String text = ""; 
	private BossPlatform platfrom;
	
	public BossLevel(Tile tile1, Tile tile2, Ore ore1, int level, int tierLevel, BossPlatform platfrom) {
		super(tile1, tile2, ore1, level, tierLevel);
		super.setType("boss");
		this.platfrom = platfrom;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	
	

}
