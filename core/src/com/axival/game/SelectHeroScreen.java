package com.axival.game;

import com.axival.Network.Packets;
import com.axival.game.input.MyNameInputListener;
import com.axival.game.screen.ScreenPlay;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.esotericsoftware.kryonet.Client;

public class  SelectHeroScreen implements Screen {
    private Client client;

    private CardPlay cardPlay;

    private Stage stage;

    private Image darkTempImg, wizardImg, priestImg, darkTempOn, wizardOn, priestOn, textbg;

    private int selectHero = -1;

    private Animation<TextureRegion> countdown;

    private TextureAtlas countdownAtlas;
    public static String friendName = "BlkScorpion";
    public static String teamPick;
    public static Integer playerNo;
    public static Boolean locatePlayer;

    private BitmapFont teamFriend;

    private float countdownPlay=20f;



    public SelectHeroScreen(final CardPlay cardPlay, final Client client){

        teamFriend = new BitmapFont();
        teamFriend.setColor(Color.BLACK);
        cardPlay.soundManager.stopBgm(4);
        cardPlay.soundManager.playBgm(1);
        this.cardPlay = cardPlay;
        this.client = client;
        this.stage = new Stage(new StretchViewport(CardPlay.V_WIDTH, CardPlay.V_HEIGHT, cardPlay.camera));
        Gdx.input.setInputProcessor(stage);

        darkTempImg = new Image(cardPlay.assetManager.get("hero-select/DT.jpg", Texture.class));
        darkTempOn = new Image(cardPlay.assetManager.get("hero-select/DTHover.jpg", Texture.class));
        wizardImg = new Image(cardPlay.assetManager.get("hero-select/Mage.jpg", Texture.class));
        wizardOn = new Image(cardPlay.assetManager.get("hero-select/MageHover.jpg", Texture.class));
        priestImg = new Image(cardPlay.assetManager.get("hero-select/Priest.jpg", Texture.class));
        priestOn = new Image(cardPlay.assetManager.get("hero-select/PriestHover.jpg", Texture.class));

        this.textbg = new Image(new Texture("hero-select/Select bg.jpg"));

        countdownAtlas = cardPlay.assetManager.get("hero-select/count/countdown.atlas", TextureAtlas.class);
        countdown = new Animation<TextureRegion>(1f, countdownAtlas.getRegions());





        darkTempImg.setScale(.16f);
        darkTempImg.setPosition(35, 40);
        darkTempOn.setScale(.16f);
        darkTempOn.setPosition(35, 40);
        darkTempImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                selectHero = 0;
                Packets.BufferLivePick hello = new Packets.BufferLivePick();
                hello.playerNo = playerNo;
                hello.liveClassNo = 1;
                hello.liveClassPick = "Dark Templar";
                client.sendTCP(hello);
                System.out.println("Sended!");



                cardPlay.soundManager.playSfx(1);
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                darkTempOn.addAction(Actions.sequence(Actions.fadeIn(0f)));
                stage.addActor(darkTempOn);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                darkTempImg.addAction(Actions.sequence(Actions.fadeIn(0f)));
            }
        });
        darkTempOn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectHero = 0;
                Packets.BufferLivePick hello = new Packets.BufferLivePick();
                hello.playerNo = playerNo;
                hello.liveClassNo = 1;
                hello.liveClassPick = "Dark Templar";
                client.sendTCP(hello);
                System.out.println("Sended!");

//                Packets.BufferPlayerClass playerClass = new Packets.BufferPlayerClass();
//                playerClass.heroSelect = selectHero;
//                System.out.println("Test");
//                client.sendTCP(playerClass);
//                System.out.println("Class send");

                //cardPlay.setScreen(new ScreenPlay(cardPlay));
                cardPlay.soundManager.playSfx(1);
                System.out.println("Click selected");


            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                darkTempOn.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.removeActor()));
                stage.addActor(darkTempImg);
            }
        });

        wizardImg.setScale(.16f);
        wizardImg.setPosition(darkTempImg.getWidth()*.16f+45, 40);
        wizardOn.setScale(.16f);
        wizardOn.setPosition(darkTempImg.getWidth()*.16f+45, 40);
        wizardImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                selectHero = 1;
                Packets.BufferLivePick hello = new Packets.BufferLivePick();
                hello.playerNo = playerNo;
                hello.liveClassNo = 2;
                hello.liveClassPick = "Wizard";
                client.sendTCP(hello);
                cardPlay.soundManager.playSfx(1);
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                wizardOn.addAction(Actions.sequence(Actions.fadeIn(0f)));
                stage.addActor(wizardOn);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                wizardImg.addAction(Actions.sequence(Actions.fadeIn(0f)));
            }
        });
        wizardOn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                selectHero = 1;
                cardPlay.soundManager.playSfx(1);
                Packets.BufferLivePick hello = new Packets.BufferLivePick();
                hello.playerNo = playerNo;
                hello.liveClassNo = 2;
                hello.liveClassPick = "Wizard";
                client.sendTCP(hello);
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                wizardOn.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.removeActor()));
                stage.addActor(wizardImg);
            }
        });

        priestImg.setScale(.16f);
        priestImg.setPosition(wizardImg.getWidth()*.16f + darkTempImg.getWidth()*.16f +55, 40);
        priestOn.setScale(.16f);
        priestOn.setPosition(wizardImg.getWidth()*.16f + darkTempImg.getWidth()*.16f +55, 40);
        priestImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                selectHero = 2;
                Packets.BufferLivePick hello = new Packets.BufferLivePick();
                hello.playerNo = playerNo;
                hello.liveClassNo = 3;
                hello.liveClassPick = "Priest";
                client.sendTCP(hello);
                cardPlay.soundManager.playSfx(1);
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                priestOn.addAction(Actions.sequence(Actions.fadeIn(0f)));
                stage.addActor(priestOn);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                priestImg.addAction(Actions.sequence(Actions.fadeIn(0f)));
            }
        });
        priestOn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
//                selectHero = 2;
                Packets.BufferLivePick hello = new Packets.BufferLivePick();
                hello.playerNo = playerNo;
                hello.liveClassNo = 3;
                hello.liveClassPick = "Priest";
                client.sendTCP(hello);
                cardPlay.soundManager.playSfx(1);
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                priestOn.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.removeActor()));
                stage.addActor(priestImg);
            }
        });

        stage.addActor(textbg);
        stage.addActor(priestImg);
        stage.addActor(wizardImg);
        stage.addActor(darkTempImg);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        countdownPlay -= delta;
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        cardPlay.batch.setProjectionMatrix(cardPlay.camera.combined);
        stage.draw();
        cardPlay.batch.begin();
        cardPlay.batch.draw(countdown.getKeyFrame(countdownPlay, false), 1170, 625);
        teamFriend.draw(cardPlay.batch, friendName + " is choosing " + teamPick, 230, 642);
        cardPlay.batch.end();
        System.out.println(countdownPlay);
        if(countdownPlay<=0) {
            Packets.BufferTellReady ready = new Packets.BufferTellReady();
            client.sendTCP(ready);
            cardPlay.setScreen(new ScreenPlay(cardPlay, client));
        }
    }

    public void update(float delta){
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
        stage.dispose();
    }
}
