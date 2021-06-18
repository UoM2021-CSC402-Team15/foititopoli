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
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Logic.Cards.Card;
import com.mygdx.game.Logic.Cards.JailCard;
import com.mygdx.game.Logic.Cards.MoveCard;
import com.mygdx.game.Logic.Dice;
import com.mygdx.game.Logic.GameInstance;
import com.mygdx.game.Logic.Pawn;
import com.mygdx.game.Logic.Player;
import com.mygdx.game.Logic.Squares.CourseSquare;
import com.mygdx.game.UI.Components.BoardGroup;
import com.mygdx.game.UI.Components.DiceActor;
import com.mygdx.game.UI.Components.SquareActor;
import com.mygdx.game.UI.Windows.*;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

public class GameScreen implements Screen {

    private final Stage stage;
    private final Batch batch;
    private final OrthographicCamera camera;
    private final BitmapFont font;
    private final BitmapFont font2;
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
        this.font2 = new BitmapFont();
        font2.getData().setScale(1.5f);
        this.camera = new OrthographicCamera();

        camera.setToOrtho(false, 1280, 720);
        final Viewport viewport = new StretchViewport(1280,720, camera);
        this.stage = new Stage(viewport, batch);
        TooltipManager.getInstance().initialTime = 0;
        TooltipManager.getInstance().hideAll();

        //Initialize Game
        game.getGameInstance().initialize();

        /* Dice Sprites */
        DiceActor dice1 = new DiceActor();
        dice1.setPosition(1020,140);
        dice1.setSize(100,100);
        DiceActor dice2 = new DiceActor();
        dice2.setPosition(1140,140);
        dice2.setSize(100,100);

        /* Roll-Turn Button */
        final TextButton rollButton = new TextButton("", Foititopoli.gameSkin);
        rollButton.setSize(150,50);
        rollButton.setTransform(true);
        rollButton.setScale(1.8f);
        rollButton.setPosition(1000, 20);
        updateRollButton(rollButton);
        rollButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (game.getGameInstance().getCurrentPlayer().getTurnsToPlay() > 0){
                    int roll1 = Dice.roll();
                    int roll2 = Dice.roll();
                    dice1.rollAnimation(roll1);
                    dice2.rollAnimation(roll2);

                    rollButton.setDisabled(true);
                    rollButton.setTouchable(Touchable.disabled);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            rollButton.setDisabled(false);
                            rollButton.setTouchable(Touchable.enabled);
                            game.getGameInstance().gameLoop(roll1 + roll2);
                        }
                    }, 2);
                } else {
                    game.getGameInstance().endTurn();
                }
                updateRollButton(rollButton);
            }
        });

        /* Pause Button */
        final TextButton pauseButton = new TextButton("Pause Menu", Foititopoli.gameSkin);
        pauseButton.setSize(150,40);
        pauseButton.setPosition(1100,660);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addActor(pauseWindow);
            }
        });

        /* Board Group */
        boardGroup = new BoardGroup(game.getGameInstance().getBoard(), 680, game.getGameInstance().getPlayers());
        boardGroup.setPosition((camera.viewportWidth- boardGroup.getWidth())/2, 20);

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
                money = new Label(player.getStudyHours() + " Hours", Foititopoli.gameSkin);
                add(money).expand().fill();
                padLeft(30);
                padRight(30);
                pad(20);
            }
            public void update() {
                money.setText(player.getStudyHours() + " Hours");
            }
        }

        /* Player Buttons */
        final VerticalGroup playerGroup = new VerticalGroup();
        for (final Player player: game.getGameInstance().getPlayers()) {
            PlayerButton playerButton = new PlayerButton(player);
            playerButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (game.getGameInstance().getCurrentPlayer()!=player && player.getStudyHours() >= 0) {
                        stage.addActor(new TradeWindow(game.getGameInstance().getCurrentPlayer(), player, player -> game.getGameInstance().getListener().playerUpdated(player)));
                    }
                }
            });
            playerGroup.addActor(playerButton);
        }
        playerGroup.setPosition(10, 10);
        playerGroup.setSize(200,690);

        /* Square Click Listener */
        for (SquareActor[] side: boardGroup.getSquareActors()) {
            for (final SquareActor square: side) {
                square.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        if (square.getSquare() instanceof CourseSquare) {
                            CourseInfoWindow infoWindow = new CourseInfoWindow((CourseSquare) square.getSquare(), game.getGameInstance().getCurrentPlayer(), player -> game.getGameInstance().getListener().playerUpdated(player));
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
        stage.addActor(dice1);
        stage.addActor(dice2);

        /* Game Listener */
        game.getGameInstance().setListener(new GameInstance.GameInstanceListener() {
            @Override
            public void pawnPositionUpdated(final Pawn pawn) {
                int time = boardGroup.movePawn(pawn);
                // Disable End Turn Button while pawn is moving
                rollButton.setDisabled(true);
                rollButton.setTouchable(Touchable.disabled);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        // Update Player
                        playerUpdated(game.getGameInstance().getCurrentPlayer());
                        // Re-enable End Turn Button when pawn has finished moving
                        rollButton.setDisabled(false);
                        rollButton.setTouchable(Touchable.enabled);
                        // If new square is a CourseSquare (and no other window is open), open a new CourseInfoWindow
                        if (pawn.getCurrentSquare() instanceof CourseSquare && !((stage.getActors().get(stage.getActors().size-1)) instanceof Window) ) {
                            CourseInfoWindow infoWindow = new CourseInfoWindow((CourseSquare) pawn.getCurrentSquare(), game.getGameInstance().getCurrentPlayer(), player -> playerUpdated(player));
                            stage.addActor(infoWindow);
                        }
                    }
                }, time);
            }

            @Override
            public void playerUpdated(Player aPlayer) {
                PlayerButton playerButton = (PlayerButton) playerGroup.getChild(game.getGameInstance().getPlayers().indexOf(aPlayer));
                playerButton.update();
                updateRollButton(rollButton);
            }

            @Override
            public void playerDrewCard(Card aCard) {
                stage.addActor(new CardWindow(game.getGameInstance().getCurrentPlayer(), aCard, player -> {
                    playerUpdated(player);
                    if ( aCard instanceof MoveCard || aCard instanceof JailCard) {
                        pawnPositionUpdated(player.getPawn());
                        game.getGameInstance().gameLoop(0);
                    }
                } ));
            }

            @Override
            public void playerWon(Player aPlayer) {
                stage.addActor(new WinWindow(aPlayer,game));
            }

            @Override
            public void playerLost(Player aPlayer) {
                stage.addActor(new LoseWindow(aPlayer));
                boardGroup.removePlayer(aPlayer);
                PlayerButton playerButton = (PlayerButton) playerGroup.getChild(game.getGameInstance().getPlayers().indexOf(aPlayer));
                playerButton.money.setText("Player Lost");
            }
        });

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 1030, 685);
        font2.draw(batch, "Now Playing:      " + game.getGameInstance().getCurrentPlayer().getName(),1000, 130);
        batch.end();

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (pauseWindow.hasParent()) {
                pauseWindow.remove();
            } else {
                stage.addActor(pauseWindow);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.GRAVE)) {
            console.activate();
        }
    }

    private void updateRollButton(TextButton button) {
        if (game.getGameInstance().getCurrentPlayer().getTurnsToPlay() > 0) {
            button.setText("Roll");
        } else {
            button.setText("End Turn");
        }
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen","show");
        Gdx.input.setInputProcessor(stage);
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(fadeIn(1f));
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
