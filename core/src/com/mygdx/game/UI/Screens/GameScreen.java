package com.mygdx.game.UI.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.*;
import com.mygdx.game.Logic.Cards.Card;
import com.mygdx.game.Logic.GameInstance;
import com.mygdx.game.Logic.Pawn;
import com.mygdx.game.Logic.Player;
import com.mygdx.game.Logic.Squares.CourseSquare;
import com.mygdx.game.UI.Components.BoardGroup;
import com.mygdx.game.UI.Components.SquareActor;
import com.mygdx.game.UI.Windows.*;

import java.util.function.Consumer;

public class GameScreen implements Screen {

    private final Stage stage;
    private final Batch batch;
    private final OrthographicCamera camera;
    private final BitmapFont font;
    private final PauseWindow pauseWindow;
    private final BoardGroup boardGroup;
    private final DebugConsole console;
    private Foititopoli game;

    public interface UI {
        void updatePlayer(Player player);
    }

    public GameScreen(final Foititopoli game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.camera = new OrthographicCamera();

        camera.setToOrtho(false, 1280, 720);
        final Viewport viewport = new StretchViewport(1280,720, camera);
        this.stage = new Stage(viewport, batch);

        /* Roll-Turn Button */
        final TextButton rollButton = new TextButton("Roll", Foititopoli.gameSkin);
        rollButton.setWidth(Gdx.graphics.getWidth()/2f);
        rollButton.setPosition(camera.viewportWidth/2f-rollButton.getWidth()/2,50);
        rollButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (rollButton.getText().toString().equals("Roll")){
                    game.getGameInstance().gameLoop();
                    rollButton.setText("End Turn");
                }
                else{
                    game.getGameInstance().endTurn();
                    rollButton.setText("Roll");
                }

            }
        });

        /* Pause Button */
        final TextButton pauseButton = new TextButton("Pause Menu", Foititopoli.gameSkin);
        pauseButton.setWidth(Gdx.graphics.getWidth()/2f);
        pauseButton.setPosition(camera.viewportWidth/2f-pauseButton.getWidth()/2,20);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseWindow.setSize(300,200);
                pauseWindow.setPosition(camera.viewportWidth/2,camera.viewportHeight/2);
                stage.addActor(pauseWindow);
            }
        });

        /* Board Group */
        boardGroup = new BoardGroup(game.getGameInstance().getBoard(), 600, game.getGameInstance().getPlayers());
        boardGroup.setPosition((camera.viewportWidth- boardGroup.getWidth())/2, 100);

        /* In Game Windows (Not the OS :]) */
        pauseWindow = new PauseWindow("Game paused", Foititopoli.gameSkin, game);
        console = new DebugConsole(game.getGameInstance(), stage);

        /* Player Button  Class */
        class PlayerButton extends Button {
            Player player;
            Label money;
            public PlayerButton(Player player) {
                super(Foititopoli.gameSkin);
                this.player = player;
                Label name = new Label(player.getName(), Foititopoli.gameSkin);
                add(name).expand().fill().row();
                money = new Label("Money: " + player.getStudyHours(), Foititopoli.gameSkin);
                add(money).expand().fill();
                padLeft(30);
                padRight(30);
                pad(20);
            }
            public void update() {
                money.setText("Money: " + player.getStudyHours());
            }
        }

        /* Player Buttons */
        final VerticalGroup playerGroup = new VerticalGroup();
        for (final Player player: game.getGameInstance().getPlayers()) {
            PlayerButton playerButton = new PlayerButton(player);
            playerButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (game.getGameInstance().getCurrentPlayer()!=player) {
                        stage.addActor(new TradeWindow(game.getGameInstance().getCurrentPlayer(), player));
                    }
                }
            });
            playerGroup.addActor(playerButton);
        }
        playerGroup.setPosition(900, 200);
        playerGroup.setSize(500,500);

        /* Square Click Listener */
        for (SquareActor[] side: boardGroup.getSquareActors()) {
            for (final SquareActor square: side) {
                square.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        if (square.getSquare() instanceof CourseSquare) {
                            CourseInfoWindow infoWindow = new CourseInfoWindow((CourseSquare) square.getSquare(), game.getGameInstance().getCurrentPlayer(), player -> {
                                PlayerButton playerButton = (PlayerButton) playerGroup.getChild(game.getGameInstance().getPlayers().indexOf(player));
                                playerButton.update();
                            });
                            infoWindow.setSize(500,150);
                            infoWindow.setPosition(viewport.getScreenWidth()/2f-infoWindow.getWidth()/2f, viewport.getScreenHeight()/2f-infoWindow.getHeight()/2f);
                            stage.addActor(infoWindow);
                        }
                    }
                });
            }
        }

        /* Adding to Stage */
        stage.addActor(rollButton);
        stage.addActor(pauseButton);
        stage.addActor(boardGroup);
        stage.addActor(playerGroup);

        //Initialize Game
        game.getGameInstance().initialize();

        /* Game Listener */
        game.getGameInstance().setListener(new GameInstance.GameInstanceListener() {
            @Override
            public void pawnPositionUpdated(final Pawn pawn) {
                int time = boardGroup.movePawn(pawn);
                rollButton.setDisabled(true);
                rollButton.setTouchable(Touchable.disabled);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        rollButton.setDisabled(false);
                        rollButton.setTouchable(Touchable.enabled);
                        if (pawn.getCurrentSquare() instanceof CourseSquare) {
                            CourseInfoWindow infoWindow = new CourseInfoWindow((CourseSquare) pawn.getCurrentSquare(), game.getGameInstance().getCurrentPlayer(), player -> {
                                PlayerButton playerButton = (PlayerButton) playerGroup.getChild(game.getGameInstance().getPlayers().indexOf(player));
                                playerButton.update();
                            });
                            infoWindow.setSize(500,150);
                            infoWindow.setPosition(viewport.getScreenWidth()/2f-infoWindow.getWidth()/2, viewport.getScreenHeight()/2f-infoWindow.getHeight()/2);
                            stage.addActor(infoWindow);
                        }
                    }
                }, time);
            }

            @Override
            public void playerUpdated(Player aPlayer) {
                PlayerButton playerButton = (PlayerButton) playerGroup.getChild(game.getGameInstance().getPlayers().indexOf(aPlayer));
                playerButton.update();
            }

            @Override
            public void playerDrewCard(Card aCard) {
                stage.addActor(new CardWindow(aCard));

            }

            @Override
            public void playerWon(Player aPlayer) {
                stage.addActor(new WinWindow(aPlayer,game));
            }

            @Override
            public void playerLost(Player aPlayer) {
                stage.addActor(new LoseWindow(aPlayer));
            }
        });

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (pauseWindow.hasParent()) {
                pauseWindow.remove();
            } else {
                pauseWindow.setSize(300,200);
                pauseWindow.setPosition(camera.viewportWidth/2,camera.viewportHeight/2);
                stage.addActor(pauseWindow);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.GRAVE)) {
            console.activate();
        }
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen","show");
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
