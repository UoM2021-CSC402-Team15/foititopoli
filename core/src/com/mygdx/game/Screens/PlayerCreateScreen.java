package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Pawn;


public class PlayerCreateScreen implements Screen {

    Foititopoli game;
    Stage stage;

    public PlayerCreateScreen(final Foititopoli game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        final boolean isLastPlayer = !game.getGameInstance().nextPlayerToSetupExists();

        int currentPlayerNumber = game.getGameInstance().getPlayers().size()+1;
        Label title = new Label("Create Player " + currentPlayerNumber, Foititopoli.gameSkin);

        final TextField nameText = new TextField("Enter a name", Foititopoli.gameSkin);

        String[] availablePawnsStrings = game.getGameInstance().getAvailablePawnStrings();
        final SelectBox pawnSelect = new SelectBox(Foititopoli.gameSkin);
        pawnSelect.setItems(availablePawnsStrings);

        TextButton backButton = new TextButton("Back", Foititopoli.gameSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        TextButton NextPlayerOrStartGameButton = new TextButton("Next Player", Foititopoli.gameSkin);
        if (isLastPlayer) {
            NextPlayerOrStartGameButton.setText("Start Game");
        }
        NextPlayerOrStartGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Pawn pawn = game.getGameInstance().getPawnFromString((String) pawnSelect.getSelected());
                game.getGameInstance().setupPlayer(nameText.getText(), pawn );
                game.getGameInstance().removeFromAvailablePawns(pawn);
                if (isLastPlayer) {
                    game.setScreen(new GameScreen(game));
                } else {
                    game.setScreen(new PlayerCreateScreen(game));
                }
            }
        });

        Table mainTable = new Table();

        mainTable.setFillParent(true);
        mainTable.add(title).padTop(10).padBottom(20).row();
        mainTable.add(nameText).row();
        mainTable.add(pawnSelect).row();
        mainTable.add(NextPlayerOrStartGameButton).row();
        mainTable.add(backButton).padTop(20).padBottom(10);

        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        stage.act();
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.app.log("GameSetupScreen", "show");
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

    }
}
