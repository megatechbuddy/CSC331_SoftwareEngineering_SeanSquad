package Tools;

import javax.swing.JOptionPane;

import com.angrydonkeykong.game.AngryDonkeyKongLibGDX;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import scenes.Hud;
import sprites.Barrel;
import sprites.Player;

/**
 * @author Sean Benson , Chuyang Wang
 * Followed https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt tutorial and modified things tremendously for our game.
 */
public class WorldContactListener implements ContactListener {
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#beginContact(com.badlogic.gdx.physics.box2d.Contact)
	 */
	@Override
	public void beginContact(Contact contact) {
		// Gdx.app.log("Begin Contact", "");

		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		if (fixA.getFilterData().categoryBits == AngryDonkeyKongLibGDX.PLAYER_BIT
				|| fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.PLAYER_BIT) {
			if (fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.BARREL_BIT) {
				Gdx.app.log("Player", " collided with Barrel");
				((Barrel) fixB.getUserData()).startExplosion();
				Hud.addScore(-500);
				JOptionPane.showMessageDialog(null, "You LOSE!!!!    :(");
			} else if (fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.KONG_BIT) {
				Gdx.app.log("Player", " collided with AngryDonkeyKong");
				Hud.addScore(-5000);
			} else if (fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.PRINCESS_BIT) {
				Gdx.app.log("Player", " collided with Princess");
				Hud.addScore(100000);
				JOptionPane.showMessageDialog(null, "You WON!!!!    :)");
			} else if (fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.LADDER_BIT) {
				Gdx.app.log("Player", " collided with Ladder");
				((Player) fixA.getUserData()).ladderCollision(true);
			} else if (fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.LADDER_GROUND) {
				Gdx.app.log("Player", " collided with Ladder_Ground");
			} else {

			}
		}

		if (fixA.getFilterData().categoryBits == AngryDonkeyKongLibGDX.BULLET_BIT
				|| fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.BULLET_BIT) {
			if (fixA.getFilterData().categoryBits == AngryDonkeyKongLibGDX.BARREL_BIT) {
				Gdx.app.log("Bullet", " collided with Barrel");
				((Barrel) fixA.getUserData()).startExplosion();
				Hud.addScore(500);
			} else if (fixB.getFilterData().categoryBits == AngryDonkeyKongLibGDX.BARREL_BIT) {
				Gdx.app.log("Bullet", " collided with Barrel");
				((Barrel) fixB.getUserData()).startExplosion();
				Hud.addScore(500);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#endContact(com.badlogic.gdx.physics.box2d.Contact)
	 */
	@Override
	public void endContact(Contact contact) {
		// Gdx.app.log("End Contact", "");

		Fixture fixA = contact.getFixtureA();
		if (fixA.getFilterData().categoryBits == AngryDonkeyKongLibGDX.PLAYER_BIT) {
			((Player) fixA.getUserData()).ladderCollision(false);
		}
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#preSolve(com.badlogic.gdx.physics.box2d.Contact, com.badlogic.gdx.physics.box2d.Manifold)
	 */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#postSolve(com.badlogic.gdx.physics.box2d.Contact, com.badlogic.gdx.physics.box2d.ContactImpulse)
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
