package io.github.thanhvu_101;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class TitleScreen implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private Texture logoTexture;
    private Image logoImage;
    private TextButton btnStart, btnOptions, btnAbout, btnExit;
    private Music backgroundMusic;
    private Sound clickSound, hoverSound;
    private Skin skin;

    public TitleScreen() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(stage);
    // Load sounds & music
    backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/waves.mp3"));
    clickSound = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/ui/menu_select.wav"));
    hoverSound = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/ui/menu_focus.wav"));
    playBackgroundMusic();
        // Load logo
        try {
            logoTexture = new Texture(Gdx.files.internal("graphics/title.png"));
        } catch (Exception e) {
            // Nếu không có logo, dùng hình mặc định
            logoTexture = new Texture(Gdx.files.internal("libgdx.png"));
        }
        logoImage = new Image(logoTexture);
        logoImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        logoImage.setPosition(0, 0);
        logoImage.getColor().a = 0f;
        logoImage.addAction(Actions.fadeIn(2f));
        stage.addActor(logoImage);

        // Load button textures
        Drawable up, down;
            up = new TextureRegionDrawable(new Texture(Gdx.files.internal("graphics/ui/buttons/button.png")));
            down = new TextureRegionDrawable(new Texture(Gdx.files.internal("graphics/ui/buttons/button.png")));

        skin = new Skin();
        skin.add("default", up);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = up;
        style.down = down;
        style.font = new com.badlogic.gdx.graphics.g2d.BitmapFont(Gdx.files.internal("fonts/main_font.fnt"));
        skin.add("default", style);

    // ...existing code...

        // Create buttons
        btnStart = new TextButton("Start", style);
        btnOptions = new TextButton("Options", style);
        btnAbout = new TextButton("About", style);
        btnExit = new TextButton("Exit Game", style);

        float btnWidth = 300, btnHeight = 60;
        float x = Gdx.graphics.getWidth() * 0.1f; // Center left (10% from left)
        float y = Gdx.graphics.getHeight() / 2f + btnHeight;
        btnStart.setBounds(x, y, btnWidth, btnHeight);
        btnOptions.setBounds(x, y - 70, btnWidth, btnHeight);
        btnAbout.setBounds(x, y - 140, btnWidth, btnHeight);
        btnExit.setBounds(x, y - 210, btnWidth, btnHeight);

        // Add listeners
        addButtonListener(btnStart, () -> startGame());
        addButtonListener(btnOptions, () -> showOptions());
        addButtonListener(btnAbout, () -> showAbout());
        addButtonListener(btnExit, () -> exitGame());

        stage.addActor(btnStart);
        stage.addActor(btnOptions);
        stage.addActor(btnAbout);
        stage.addActor(btnExit);
    }

//music function
    private void playBackgroundMusic() {
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    private void addButtonListener(TextButton button, Runnable onClick) {
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttonCode) {
                clickSound.play();
                button.addAction(Actions.sequence(Actions.scaleTo(0.95f, 0.95f, 0.05f), Actions.scaleTo(1f, 1f, 0.05f)));
                onClick.run();
                return true;
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hoverSound.play();
                button.addAction(Actions.sequence(Actions.color(com.badlogic.gdx.graphics.Color.YELLOW, 0.1f), Actions.color(com.badlogic.gdx.graphics.Color.WHITE, 0.2f)));
            }
        });
    }

    private void startGame() {
        if (backgroundMusic != null) backgroundMusic.stop();
        if (Gdx.app.getApplicationListener() instanceof com.badlogic.gdx.Game) {
            ((com.badlogic.gdx.Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
        }
        //xóa các nút
        stage.clear();
    }

    private void showOptions() {
        // TODO: Hiển thị màn hình tùy chọn

    }
    private void showAbout() {
        // TODO: Hiển thị thông tin về game

    }
    private void exitGame() {
        Gdx.app.exit();
    }

    @Override
    public void show() {}
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        logoTexture.dispose();
        clickSound.dispose();
        hoverSound.dispose();
        skin.dispose();
        backgroundMusic.dispose();
    }
}
