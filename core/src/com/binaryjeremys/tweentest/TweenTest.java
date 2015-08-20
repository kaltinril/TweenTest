package com.binaryjeremys.tweentest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.binaryjeremys.tweentest.accessors.SpriteAccessor;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Bounce;

public class TweenTest extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    Sprite sprite;
	Sprite sprite2;
	Sprite sprite3;
	Sprite sprite4;

	TweenManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("center.png");

        sprite = new Sprite(img);
		sprite2 = new Sprite(img);
		sprite3 = new Sprite(img);
		sprite4 = new Sprite(img);

        sprite.setCenter(100, 100);
		sprite2.setCenter(100, 100);
		sprite3.setCenter(100, 100);
		sprite4.setCenter(200, 200);

		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		// We need a manager to handle every tween.
		manager = new TweenManager();

		// Move a sprite to a location over time
		Tween.to(sprite, SpriteAccessor.POSITION_XY, 1.0f)
				.target(100, 200)
				.start(manager);

		// Move a sprite to a location using the BOUNCE ease function, repeat that in a YOYO effect 10 times
		Tween.to(sprite2, SpriteAccessor.POSITION_XY, 0.5f)
				.target(0, 0)
				.ease(Bounce.OUT)
				.delay(1.0f)
				.repeat(Tween.INFINITY, 0.5f)
				.start(manager);

		//Allow a series of tweens to happen 1 after the other
		Timeline.createSequence()
				.push(Tween.to(sprite3, SpriteAccessor.POSITION_X, 0.5f).target(200))
				.push(Tween.to(sprite3, SpriteAccessor.POSITION_X, 0.5f).target(0))
				.pushPause(1.0f)
				.push(Tween.to(sprite3, SpriteAccessor.POSITION_X, 0.5f).target(500))
				.repeatYoyo(5, 0.5f)
				.start(manager);

		Tween.to(sprite4, SpriteAccessor.SCALE_XY, 0.5f)
				.target(0.5f, 0.5f)
				.repeatYoyo(2, 0.5f)
				.start(manager);

	}

	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();

		manager.update(delta);

		Gdx.gl.glClearColor(0.5f, 0.1f, 0.6f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		sprite.draw(batch);
		sprite2.draw(batch);
		sprite3.draw(batch);
		sprite4.draw(batch);
		batch.end();
	}
}
