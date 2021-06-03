package com.mygdx.game.UI.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Logic.Pawn;
import com.mygdx.game.UI.Components.PawnActor;

import java.util.ArrayList;


public class PlayerCreateScreen implements Screen {

    private final Stage stage;

    public PlayerCreateScreen(final Foititopoli game, final ArrayList<Pawn> availablePawns, final float studyHours) {

        Viewport viewport = new StretchViewport(720,400);;
        stage = new Stage(viewport);

        int currentPlayerNumber = game.getGameInstance().getPlayers().size()+1;
        Label title = new Label("Create Player " + currentPlayerNumber, Foititopoli.gameSkin);

        final TextField nameText = new TextField("Player "+currentPlayerNumber, Foititopoli.gameSkin);

        // Pawn Selector
        String[] strings = new String[availablePawns.size()];
        for (int i = 0; i < availablePawns.size(); i++) {
            strings[i] = availablePawns.get(i).toString();
        }
        final SelectBox<String> pawnSelect = new SelectBox<>(Foititopoli.gameSkin);
        pawnSelect.setItems(strings);

        // Next Player Button / Start Game Button
        TextButton NextPlayerOrStartGameButton = new TextButton("Next Player", Foititopoli.gameSkin);
        final boolean isLastPlayer = game.getGameInstance().getPlayers().size() == ( game.getGameInstance().getNumberOfPlayers()-1 );
        if (isLastPlayer) {
            NextPlayerOrStartGameButton.setText("Start Game");
        }
        NextPlayerOrStartGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Take player selected pawn
                Pawn selectedPawn = null;
                for (Pawn pawn: availablePawns) {
                    if (pawn.toString().equals(pawnSelect.getSelected())) {
                        selectedPawn = pawn;
                    }
                }
                // Setup player
                game.getGameInstance().setupPlayer(nameText.getText(), selectedPawn,studyHours);
                // Remove pawn from available pawns
                availablePawns.remove(selectedPawn);
                if (isLastPlayer) {
                    game.setScreen(new GameScreen(game));
                } else {
                    game.setScreen(new PlayerCreateScreen(game, availablePawns,studyHours));
                }
            }
        });

        TextButton backButton = new TextButton("Back", Foititopoli.gameSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
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
        ScreenUtils.clear(1, 1, 1, 1);
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
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }
}
