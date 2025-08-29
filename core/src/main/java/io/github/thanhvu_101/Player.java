package io.github.thanhvu_101;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;

public class Player {
    private Texture playerTexture;
    private TextureRegion[] frames;
    private int currentFrame = 0;
    private float frameTime = 0f;
    private final float FRAME_DURATION = 0.1f;
    public float x, y;
    public float speed = 200f;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        playerTexture = new Texture(Gdx.files.internal("graphics/characters/player/player_sprite.png"));
        frames = new TextureRegion[77];
        int frameWidth = 64, frameHeight = 64;
        int cols = playerTexture.getWidth() / frameWidth;
        int rows = playerTexture.getHeight() / frameHeight;
        int frame = 0;
        outer:
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (frame >= 77) break outer;
                frames[frame] = new TextureRegion(playerTexture, col * frameWidth, row * frameHeight, frameWidth, frameHeight);
                frame++;
            }
        }
    }

    public void update(float delta, float dx, float dy) {
        x += dx * speed * delta;
        y += dy * speed * delta;
        frameTime += delta;
        if (frameTime > FRAME_DURATION) {
            currentFrame = (currentFrame + 1) % 77;
            frameTime = 0f;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(frames[currentFrame], x, y);
    }

    public void dispose() {
        playerTexture.dispose();
    }
}
