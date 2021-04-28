package com.mygdx.game.Windows;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.Board;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Pawn;

import java.util.ArrayList;

public class DebugConsole extends Window {

    private Stage stage;
    private Boolean isActive = false;
    private TextField text;
    private Label label;
    private Board board;
    private ArrayList<Pawn> pawns;
    private ScrollPane scrollPane;

    public DebugConsole(Stage stage) {
        super("Debug", Foititopoli.gameSkin);
        this.stage = stage;
        setPosition(0,stage.getHeight());
        setSize(600,200);

        label = new Label("Secret Debug Stuff!!!", Foititopoli.gameSkin);
        label.setWrap(true);

        scrollPane = new ScrollPane(label, Foititopoli.gameSkin);

        text = new TextField("", Foititopoli.gameSkin);
        text.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if ( c == '\n' ) {
                    runCommand(text.getText().trim());
                    text.setText("");
                }
            }
        });
        add(scrollPane).expand().fill().row();
        add(text).expand().bottom().fillX();
    }

    public void setPawns(ArrayList<Pawn> pawns) {
        this.pawns = pawns;
    }

    public void setBoard(Board board) {
        this.board = board;
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
            switch (splitCommand[0]) {
                case "move":
                    int side = Integer.parseInt(splitCommand[2].split(",")[0]);
                    int square = Integer.parseInt(splitCommand[2].split(",")[1]);
                    SequenceAction sequence = board.movePawn(pawns.get(Integer.parseInt(splitCommand[1])), board.squares[side][square], new SequenceAction());
                    pawns.get(Integer.parseInt(splitCommand[1])).addAction(sequence);
                    print("Executed sequence of " + sequence.getActions().size +  " actions");
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
