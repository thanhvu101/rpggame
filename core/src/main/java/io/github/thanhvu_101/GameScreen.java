package io.github.thanhvu_101;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private Player player;
    private Texture joystickTexture;
    private TextureRegion baseRegion, handleRegion;
    private float joystickBaseX, joystickBaseY, joystickRadius;
    private float handleX, handleY;
    private boolean dragging = false;
    private Vector2 direction = new Vector2(0, 0);

    public GameScreen() {
        batch = new SpriteBatch();
        player = new Player(Gdx.graphics.getWidth()/2f - 32, Gdx.graphics.getHeight()/2f - 32);
        joystickTexture = new Texture(Gdx.files.internal("graphics/misc/JoystickPack.png"));
        // Ô 3: base, Ô 4: handle (mỗi ô 360x360)
        baseRegion = new TextureRegion(joystickTexture, 720, 0, 360, 360); // row 1, col 3
        handleRegion = new TextureRegion(joystickTexture, 0, 720, 360, 360); // row 2, col 1
        joystickBaseX = 180;
        joystickBaseY = 180;
        joystickRadius = 120;
        handleX = joystickBaseX;
        handleY = joystickBaseY;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Xử lý input joystick
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            float dx = touchX - joystickBaseX;
            float dy = touchY - joystickBaseY;
            float dist = (float)Math.sqrt(dx*dx + dy*dy);
            if (dist < joystickRadius + 60) { // trong vùng joystick
                dragging = true;
                if (dist > joystickRadius) {
                    dx *= joystickRadius/dist;
                    dy *= joystickRadius/dist;
                }
                handleX = joystickBaseX + dx;
                handleY = joystickBaseY + dy;
                direction.set(dx, dy).nor();
            } else {
                dragging = false;
                handleX = joystickBaseX;
                handleY = joystickBaseY;
                direction.set(0, 0);
            }
        } else {
            dragging = false;
            handleX = joystickBaseX;
            handleY = joystickBaseY;
            direction.set(0, 0);
        }

        player.update(delta, direction.x, direction.y);

        batch.begin();
        // Vẽ joystick
        //vị trí base
        batch.draw(baseRegion, joystickBaseX-180, joystickBaseY-180, 360, 360);
        //vị trí handle
        batch.draw(handleRegion, handleX-60, handleY-60, 120, 120);
        // Vẽ player
        player.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        joystickTexture.dispose();
    }
}
