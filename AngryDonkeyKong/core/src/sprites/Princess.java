package sprites;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import screens.PlayScreen;

/**
 * @author Sean Benson
 * Followed https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial and modified things tremendously for our game.
 */
public class Princess extends Sprite {
	public enum State {
		STILL, WALKING, EXPLODING_IN_JOY
	};

	public World world;
	public Body b2body;
	public State currentState;
	public State previousState;
	private Animation<TextureRegion> barrellRoll;
	private TextureRegion playerStand;
	private Animation<TextureRegion> barrellExplode;
	private boolean runningRight;
	private float stateTimer;
	private boolean startExplosion;
	public static int speed = 20;

	/**
	 * Constructor
	 */
	public Princess(PlayScreen screen) {
		super(screen.getAtlas().findRegion("Lady"));
		this.world = screen.getWorld();

		//change picture animation
		currentState = State.STILL;
		previousState = State.STILL;
		stateTimer = 0;
		runningRight = true;

		//setters
		startExplosion = false;

		//Rolling frames
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(screen.getAtlas().findRegion("Lady"));

		barrellRoll = new Animation<TextureRegion>(0.1f, frames);
		frames.clear();

		//StartExplosion Frames
		frames.add(screen.getAtlas().findRegion("Lady"));

		barrellExplode = new Animation<TextureRegion>(0.4f, frames);
		frames.clear();

		playerStand = new TextureRegion(screen.getAtlas().findRegion("Lady"));

		//show picture
		defineSprite();
		setBounds(0, 0, 32 / AngryDonkeyKongLibGDX.PPM, 32 / AngryDonkeyKongLibGDX.PPM);
		setRegion(playerStand);
	}

	/**
	 * Updates the Princess
	 */
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
	}

	/**
	 * This updates the Princess's frame.
	 */
	public TextureRegion getFrame(float dt) {
		currentState = getState();
		TextureRegion region;
		switch (currentState) {
		case STILL:
			// region = playerJump.getKeyFrame(stateTimer);
			// break;
		case WALKING:
			// System.out.println("running");
			region = barrellRoll.getKeyFrame(stateTimer, true);
			break;

		case EXPLODING_IN_JOY:
			// debugging
			// System.out.println("gun");
			region = barrellExplode.getKeyFrame(stateTimer);
			if (barrellExplode.isAnimationFinished(stateTimer)) {
				startExplosion = false;
			}
			break;
		default:
			// System.out.println("stand");
			currentState = State.STILL;
			region = playerStand;
			break;
		}

		if (!runningRight && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		} else if (runningRight && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;
	}

	/**
	 * @return Gets the state of Princess.
	 */
	public State getState() {
		if (startExplosion) {
			return State.EXPLODING_IN_JOY;
		} else {
			return State.WALKING;
		}
	}

	/**
	 * Defines the box, parameters, and sensors of the sprite.
	 */
	public void defineSprite() {
		BodyDef bdef = new BodyDef();
		Vector2 start_position = new Vector2(11, 52);
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(start_position);
		b2body = world.createBody(bdef);

		FixtureDef fDef = new FixtureDef();

		CircleShape shape = new CircleShape();
		shape.setRadius(16 / AngryDonkeyKongLibGDX.PPM);
		fDef.shape = shape;
		fDef.density = 1f;
		fDef.filter.categoryBits = AngryDonkeyKongLibGDX.PRINCESS_BIT;
		fDef.filter.maskBits = AngryDonkeyKongLibGDX.BRICK_BIT | AngryDonkeyKongLibGDX.BARREL_BIT
				| AngryDonkeyKongLibGDX.PLAYER_BIT | AngryDonkeyKongLibGDX.KONG_BIT | AngryDonkeyKongLibGDX.PRINCESS_BIT
				| AngryDonkeyKongLibGDX.LADDER_GROUND | AngryDonkeyKongLibGDX.ATEAMMAN_BIT;

		b2body.createFixture(fDef);
		shape.dispose();
	}
}
