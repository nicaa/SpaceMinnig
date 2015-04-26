package nicaa.project.dk.game.spacemining;

import sound.Effects;
import level.LevelView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends Activity {

    private Point size;
    private ImageButton play;
    private ImageButton test;
    private Bitmap play_btn;
    private Bitmap test_btn;
    private Effects effects;
    
	@SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Sætter mine flags for min screen. Her er det landscape og fullscreen.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //henter screen size.
        Display display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getRealSize(size);
        effects = new Effects(this);
        // init bitmaps til knapper
        initBitmaps();
        
        
        setContentView(R.layout.activity_main);
        
        addListenerButton();
        
    }
	public void addListenerButton(){
		play = (ImageButton) this.findViewById(R.id.playButton);
		play.setImageBitmap(play_btn);
		
		play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				effects.tapEffect();
				play(v);
//				Toast.makeText(MainActivity.this,
//						"ImageButton is clicked!", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	public void play(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, GameScreenActivity.class);
		startActivity(intent);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void initBitmaps(){
    	play_btn = BitmapFactory.decodeResource(this.getResources(), R.drawable.play_button_up);       
        play_btn = Bitmap.createScaledBitmap(play_btn, (int)(size.x * 0.35), (int)(size.y * 0.25), true);
        
    }
     
}
