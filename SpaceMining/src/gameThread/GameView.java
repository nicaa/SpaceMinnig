package gameThread;

import nicaa.project.dk.game.spacemining.GameScreenView;
import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	
	private GameThread gameThread;
	private Context context;
	private Canvas canvas;
	private int width;
	private int height;
	

	public GameView(Context context) {
		super(context);
		this.context = context;
		
		getHolder().addCallback(this);
		gameThread = new GameThread(getHolder(), this);// henter SurfaceHolder
												// og denne classe.
//		setBackgroundColor(Color.BLUE);
		setFocusable(true);
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		postInvalidate();
		return gameThread.onTouchEvent(event);
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		gameThread.setRunning(true);
		gameThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
		this.height = height;
		this.height = width;
//		System.out.println("kig her!!" + height + "     " + width);
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		gameThread.setRunning(false);
		boolean retry = true;

		while (retry)
		{
			try
			{
				gameThread.join();
				retry = false;
				gameThread.boss.setRunning(false); //<-- Stops my Boss thread when surfaceview is Destroyed!
				gameThread.boss2.setRunning(false);
				//GameScreenView.ship.setSpaceshipHealth(((int)(width * 0.30))); //<-- Resets spaceship health!
			}
			catch (Exception e)
			{
				Log.v("Exception Occured", e.getMessage());
			}
		}
	}

}
