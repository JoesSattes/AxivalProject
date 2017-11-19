package com.axival.game.screen;

import com.axival.game.CardPlay;
import com.axival.game.fade.FadeScence;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FillViewport;


public class LoadingComponent implements Screen {

    private final CardPlay cardPlay;
    private ShapeRenderer shapeRenderer;
    private float progress;

    //add new loading screen
    private Stage stage;
    private TextureAtlas textureAtPack;
    private Image logo;
    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image screenBg;
    private Image loadingBg;
    private Animation anim;
    private Actor loadingBar;
    private float startX, endX;
    private float percent;

    private FadeScence fadeScence;

    public LoadingComponent(final CardPlay cardPlay){
        this.cardPlay = cardPlay;
        this.shapeRenderer = new ShapeRenderer();
        this.progress=0f;
        this.fadeScence = new FadeScence(cardPlay);

        queueAssets();
    }

    @Override
    public void show() {
        System.out.println("Loading");
        //add new loading screen
        stage = new Stage(new FillViewport(CardPlay.V_WIDTH,CardPlay.V_HEIGHT));
        textureAtPack = cardPlay.assetManager.get("load/loading.pack", TextureAtlas.class);
        logo = new Image(new Texture("load/logo3.png"));
        logo.setScale(.55f);
        loadingFrame = new Image(textureAtPack.findRegion("loading-frame"));
        loadingBarHidden = new Image(textureAtPack.findRegion("loading-bar-hidden"));
        screenBg = new Image(textureAtPack.findRegion("screen-bg"));
        loadingBg = new Image(textureAtPack.findRegion("loading-frame-bg"));
        anim = new Animation(0.05f, textureAtPack.findRegions("loading-bar-anim"));
        loadingBar = new LoadingBar(anim);
        loadingBar = new Image(textureAtPack.findRegion("loading-bar1"));
        stage.addActor(screenBg);
        stage.addActor(loadingBar);
        stage.addActor(loadingBg);
        stage.addActor(loadingBarHidden);
        stage.addActor(loadingFrame);
        stage.addActor(logo);
        fadeScence.screenfadeIn(new Image(cardPlay.assetManager.get("tone/white.jpg", Texture.class)), "menu", 1);
        //cardPlay.screenfadeOut(new Image(new Texture("tone/black.jpg")));

    }

    private void update(float delta){
        //progress + (get-pro)*lerp
        progress = MathUtils.lerp(progress, cardPlay.assetManager.getProgress(), .1f);
        if(cardPlay.assetManager.update() && progress >= cardPlay.assetManager.getProgress() - .01f){
            cardPlay.setScreen(new ScreenPlay(cardPlay));
            //cardPlay.setScreen(new TransitionScreen(cardPlay, new Menu(cardPlay)));
            //cardPlay.setScreen(new TransitionScreen(cardPlay));
            //cardPlay.fadeScreenStage.act(delta);
            //cardPlay.setScreen(new Menu(cardPlay));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.2f,.5f,.7f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32, cardPlay.camera.viewportHeight/2-8, (cardPlay.camera.viewportWidth-64),16);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(32, cardPlay.camera.viewportHeight/2-8, progress*(cardPlay.camera.viewportWidth-64),16);
        shapeRenderer.end();

        cardPlay.batch.begin();
        cardPlay.bitmapFont.draw(cardPlay.batch, "Screen : Loading", 20, 20);
        cardPlay.batch.end();

        //add new loading screen
        percent = Interpolation.linear.apply(percent, cardPlay.assetManager.getProgress(), 0.1f);
        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setWidth(450 - 450 * percent);
        loadingBg.invalidate();
        // Show the loading screen
        stage.act();
        stage.draw();

        //fade effect
        cardPlay.fadeScreenStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //add new loading screen
        width = 1280;
        height = 720;
        //stage.setViewport(width, height, false);
        stage.getViewport().update(width,height);

        // Make the background fill the screen
        screenBg.setSize(width, height);

        // Place the logo in the middle of the screen and 100 px up
        logo.setX((width - logo.getWidth()) / 2 +150);
        logo.setY((height - logo.getHeight()) / 2 + 100);

        // Place the loading frame in the middle of the screen
        loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
        loadingFrame.setY((stage.getHeight() - loadingFrame.getHeight()) / 2);

        // Place the loading bar at the same spot as the frame, adjusted a few px
        loadingBar.setX(loadingFrame.getX() + 15);
        loadingBar.setY(loadingFrame.getY() + 5);

        // Place the image that will hide the bar on top of the bar, adjusted a few px
        loadingBarHidden.setX(loadingBar.getX() + 35);
        loadingBarHidden.setY(loadingBar.getY() - 3);
        // The start position and how far to move the hidden loading bar
        startX = loadingBarHidden.getX();
        endX = 440;

        // The rest of the hidden bar
        loadingBg.setSize(450, 50);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setY(loadingBarHidden.getY() + 3);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    private void queueAssets(){
        /*
        textureBg = new Texture("bg1.jpg");
        logoMenu = new Image(new Texture("Main-Menu/Game Logo.png"));
        logoMenu.setPosition(59, 630);
        buttonImgPlay = new Image(new Texture("Main-Menu/Play.png"));
        buttonImgPlay.setScale(.95f);
        buttonImgPlay.setPosition(40, 15);
        buttonImgSetting = new Image(new Texture("Main-Menu/Setting.png"));
        buttonImgSetting.setScale(.95f);
        buttonImgSetting.setPosition(620, 15);
        buttonImgTutorial = new Image(new Texture("Main-Menu/Tutorial.png"));
        buttonImgTutorial.setScale(.95f);
        buttonImgTutorial.setPosition(390, 15);
        buttonImgExit = new Image(new Texture("Main-Menu/Exit.png"));
        buttonImgExit.setScale(.95f);
         */
        //cardPlay.assetManager.load("card01.png", Texture.class);
        cardPlay.assetManager.load("cardani/001.png", Texture.class);
        cardPlay.assetManager.load("cardani/002.png", Texture.class);
        cardPlay.assetManager.load("cardani/spritesheet/cardAni.atlas", TextureAtlas.class);
        cardPlay.assetManager.load("cursorImage2.png", Texture.class);
        cardPlay.assetManager.load("bgM.jpg", Texture.class);
        cardPlay.assetManager.load("effect01.party", ParticleEffect.class);

        //loading Menu Component
        //cardPlay.assetManager.load("bg1.jpg", Texture.class);
        cardPlay.assetManager.load("Main-Menu/Game Logo.png", Texture.class);
        cardPlay.assetManager.load("Main-Menu/Play.png", Texture.class);
        cardPlay.assetManager.load("Main-Menu/Setting.png", Texture.class);
        cardPlay.assetManager.load("Main-Menu/Tutorial.png", Texture.class);
        cardPlay.assetManager.load("Main-Menu/Exit.png", Texture.class);

        //add new loading screen
        cardPlay.assetManager.load("load/loading.pack", TextureAtlas.class);
        cardPlay.assetManager.load("load/logo.png", Texture.class);

        //loading setting screen
        cardPlay.assetManager.load("setting/BG.png", Texture.class);
        cardPlay.assetManager.load("setting/Off.png", Texture.class);
        cardPlay.assetManager.load("setting/On.png", Texture.class);
        cardPlay.assetManager.load("setting/setting.png", Texture.class);

        //loading bgm and sfx
        cardPlay.assetManager.load("sound/bgm/bgChase.ogg", Music.class);
        cardPlay.assetManager.load("sound/bgm/bgFantasy.ogg", Music.class);
        cardPlay.assetManager.load("sound/fx/Draw.ogg", Music.class);

        //fade screen
        cardPlay.assetManager.load("tone/white.jpg", Texture.class);
        cardPlay.assetManager.load("tone/black.jpg", Texture.class);

        //waiting screen
        //cardPlay.assetManager.load("waiting/loading2.gif", Animation.class);

        //selected hero screen
        cardPlay.assetManager.load("hero-select/DT.jpg", Texture.class);
        cardPlay.assetManager.load("hero-select/DTHover.jpg", Texture.class);
        cardPlay.assetManager.load("hero-select/Mage.jpg", Texture.class);
        cardPlay.assetManager.load("hero-select/MageHover.jpg", Texture.class);
        cardPlay.assetManager.load("hero-select/Priest.jpg", Texture.class);
        cardPlay.assetManager.load("hero-select/PriestHover.jpg", Texture.class);

        cardPlay.assetManager.finishLoading();
    }

}
