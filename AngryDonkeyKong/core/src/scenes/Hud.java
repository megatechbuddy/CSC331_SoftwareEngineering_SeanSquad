package scenes;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Sean Benson - 
 * Followed https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial and modified things tremendously for our game.
 */
public class Hud implements Disposable{
	public Stage stage;
	private Viewport viewport;
	
	private Integer worldTimer;
    private boolean timeUp; 
	private float timeCount;
	private static Integer score;

    private Label countDownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label marioLabel;

    /**
     * Constructor initializes the labels that are displayed on the screen.
     */
	public Hud(SpriteBatch sb) {
		worldTimer = 300;
		timeCount = 0;
		score = 0;
   		
		viewport = new FitViewport(AngryDonkeyKongLibGDX.V_WIDTH, AngryDonkeyKongLibGDX.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);

		BitmapFont font = new BitmapFont(Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.fnt"), Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.png"), false);
	
		countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(font, Color.WHITE));
		scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(font, Color.WHITE));
		timeLabel = new Label("TIME", new Label.LabelStyle(font, Color.WHITE));
		levelLabel =  new Label("1-1", new Label.LabelStyle(font, Color.WHITE));
		worldLabel =  new Label("WORLD", new Label.LabelStyle(font, Color.WHITE));
		marioLabel =  new Label("MARIO", new Label.LabelStyle(font, Color.WHITE));

		table.add(marioLabel).expandX().padTop(10);
		table.add(worldLabel).expandX().padTop(10);
		table.add(timeLabel).expandX().padTop(10);
		table.row();
		table.add(scoreLabel).expandX();
		table.add(levelLabel).expandX();
		table.add(countDownLabel).expandX();
		
		stage.addActor(table);		
	}


    /**
     * Update the games text.
     */
    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
            }
            countDownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    /**
     * Update the games score.
     */
    public static void addScore(int value){
    	score += value;
    	
    	//Make sure the score is strictly positive.
    	if(score < 0) 
    		score = 0;
    		
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() { stage.dispose(); }

    public boolean isTimeUp() { return timeUp; }
}

