package Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TugasAI;

public class hud implements Disposable {
	public Stage stage;
	private Viewport viewport;
	private Integer worldTimer;
	
	public hud(SpriteBatch sb)
	{
		viewport = new StretchViewport(TugasAI.V_WIDTH,TugasAI.V_HEIGHT,new OrthographicCamera());
		stage = new Stage(viewport,sb);
		Table table = new Table();
		table.top();
		table.setFillParent(true);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}

}
