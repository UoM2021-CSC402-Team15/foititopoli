package com.mygdx.game.UI.Windows;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Logic.DataProvider;
import com.mygdx.game.Logic.GameInstance;

public class DebugConsole extends Window {

    private final Stage stage;
    private Boolean isActive = false;
    private final TextField text;
    private final Label label;
    private final ScrollPane scrollPane;

    private final GameInstance game;

    public DebugConsole(GameInstance game, Stage stage) {
        super("Debug", Foititopoli.gameSkin);
        this.stage = stage;
        this.game = game;
        setPosition(0,stage.getHeight());
        setSize(600,200);

        label = new Label("Secret Debug Stuff!!!", Foititopoli.gameSkin);
        label.setWrap(true);

        scrollPane = new ScrollPane(label, Foititopoli.gameSkin);

        text = new TextField("", Foititopoli.gameSkin);
        text.setTextFieldListener((textField, c) -> {
            if ( c == '\n' ) {
                runCommand(text.getText().trim());
                text.setText("");
            }
        });
        add(scrollPane).expand().fill().row();
        add(text).expand().bottom().fillX();
    }

    public void activate() {
        text.setText("");
        if (isActive) {
            this.remove();
            isActive = false;
        } else {
            stage.addActor(this);
            isActive = true;
            stage.setKeyboardFocus(text);
        }
    }

    private void runCommand(String command) {
        String[] splitCommand = command.split(" ");
        print("->"+command);

        try {
            switch (splitCommand[0].trim()) {

                case "move":
                    int side = Integer.parseInt(splitCommand[1].split(",")[0]);
                    int square = Integer.parseInt(splitCommand[1].split(",")[1]);
                    game.getCurrentPlayer().getPawn().setCurrentSquare(game.getBoard().getSquare(side, square));
                    game.getListener().pawnPositionUpdated(game.getCurrentPlayer().getPawn());
                    game.gameLoop(0);
                    break;

                case "hours":
                    int hours = Integer.parseInt(splitCommand[1]);
                    game.getCurrentPlayer().setStudyHours(game.getCurrentPlayer().getStudyHours()+hours);
                    game.getListener().playerUpdated(game.getCurrentPlayer());
                    break;

                case "select":
                    int selection = Integer.parseInt(splitCommand[1])-1;
                    game.setCurrentPlayer(selection);
                    break;

                case "card":
                    game.drawCard(DataProvider.drawCard());
                    break;

                default:
                    print("Unknown command: "+command);
                    break;
            }
        } catch (Exception e) {
            print(e.toString());
        }
        scrollPane.scrollTo(0,0,0,0);
    }
    private void print(String string) {
        label.setText(label.getText().toString() +"\n"+ string);
    }
}
