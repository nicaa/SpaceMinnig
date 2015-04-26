package sound;


import nicaa.project.dk.game.spacemining.R;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class Effects {
	
	private Context context;
	private MediaPlayer mp = new MediaPlayer();
	
	public Effects(Context context){
		this.context = context;
	}
	
	
	
	
	public void buyEffect(){
		mp = MediaPlayer.create(context, R.raw.buy);
		mp.start();
		onComplete();
	}
	
	public void tapEffect(){
		mp = MediaPlayer.create(context, R.raw.tap);
		mp.start();
		onComplete();
	}
	
	public void fly(){
		mp = MediaPlayer.create(context, R.raw.fly);
		mp.start();
		onComplete();
	}
	
	public void refill(){
		mp = MediaPlayer.create(context, R.raw.refill);
		mp.start();
		onComplete();
	}
	
	public void sell(){
		mp = MediaPlayer.create(context, R.raw.sell);
		mp.start();
		onComplete();
	}
	
	public void buttons(){
		mp = MediaPlayer.create(context, R.raw.refill);
		mp.start();
		onComplete();
	}
	
	public void error(){
		mp = MediaPlayer.create(context, R.raw.error);
		mp.start();
		onComplete();
	}
	
	public void mine(){
		mp = MediaPlayer.create(context, R.raw.mine);
		mp.start();
		onComplete();
	}
	
	public void shoot(){
		mp = MediaPlayer.create(context, R.raw.mine);
		mp.start();
		onComplete();
	}
	
	public void missileCollision(){
		mp = MediaPlayer.create(context, R.raw.spaceship_hit);
		mp.start();
		onComplete();
	}
	public void onComplete(){
		mp.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}
	
	
}
