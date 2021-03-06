package Tools;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import screens.PlayScreen;
import sprites.tileObjects.Brick;
import sprites.tileObjects.Ladder;
import sprites.tileObjects.Ladder_Ground;

/**
 * @author Sean Benson
 * Followed https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial and modified things tremendously for our game.
 */
public class B2WorldCreator {

	/**
	 * Constructor
	 */
	public B2WorldCreator(PlayScreen screen) {
		World world = screen.getWorld();
		TiledMap map = screen.getMap();
		// create body and fixture variables
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;

		// create background
		for (MapObject object : map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / AngryDonkeyKongLibGDX.PPM,
					(rect.getY() + rect.getHeight() / 2) / AngryDonkeyKongLibGDX.PPM);

			body = world.createBody(bdef);

			shape.setAsBox((rect.getWidth() / 2) / AngryDonkeyKongLibGDX.PPM,
					(rect.getHeight() / 2) / AngryDonkeyKongLibGDX.PPM);
			fdef.shape = shape;
			body.createFixture(fdef);
		}

		// create brick bodies/fixtures
		for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
			new Brick(screen, object);
		}

		// create ladders bodies/fixtures
		for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
			new Ladder(screen, object);
		}

		// create ladder's ground bodies/fixtures
		for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
			new Ladder_Ground(screen, object);
		}
	}
}
