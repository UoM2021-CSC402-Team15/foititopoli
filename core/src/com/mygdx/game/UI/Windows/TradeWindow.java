package com.mygdx.game.UI.Windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Logic.Player;
import com.mygdx.game.Logic.Squares.CourseSquare;
import com.mygdx.game.Logic.Trade;
import com.mygdx.game.UI.Screens.GameScreen;

import java.util.ArrayList;
import java.util.HashMap;

public class TradeWindow extends Window {

    Player sender;
    Player receiver;

    final Slider senderMoneySlider;
    final Slider receiverMoneySlider;

    HashMap<String, CourseSquare> senderCourseMap = new HashMap<>();
    HashMap<String, CourseSquare> receiverCourseMap = new HashMap<>();

    public TradeWindow(Player sender, Player receiver, GameScreen.UI ui) {
        super("Trade with "+receiver.getName(), Foititopoli.gameSkin);
        this.sender = sender;
        this.receiver = receiver;

        for (CourseSquare course: sender.getCourseList()) {
            senderCourseMap.put(course.getName(),course);
        }

        for (CourseSquare course: receiver.getCourseList()) {
            receiverCourseMap.put(course.getName(),course);
        }

        setSize(700,550);
        setScale(1.2f);
        setPosition((1280-getWidth()*getScaleX())/2,(720-getHeight()*getScaleY())/2 );

        // TOP BUTTONS
        TextButton cancelButton = new TextButton("Cancel", Foititopoli.gameSkin);
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                TradeWindow.this.remove();
            }
        });

        TextButton acceptButton = new TextButton("Accept", Foititopoli.gameSkin);

        // Money Sliders
        final Label senderMoneyLabel = new Label("0 Study Hours", Foititopoli.gameSkin);
        senderMoneySlider = new Slider(0,sender.getStudyHours(),1,false, Foititopoli.gameSkin);
        senderMoneySlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                senderMoneyLabel.setText( (int)senderMoneySlider.getValue() + " Study Hours" );
            }
        });
        senderMoneySlider.setValue(0);

        final Label receiverMoneyLabel = new Label("0 Study Hours", Foititopoli.gameSkin);
        receiverMoneySlider = new Slider(0,receiver.getStudyHours(),1,false, Foititopoli.gameSkin);
        receiverMoneySlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                receiverMoneyLabel.setText( (int)receiverMoneySlider.getValue() + " Study Hours" );
            }
        });
        receiverMoneySlider.setValue(0);

        final ButtonGroup<TextButton> senderCourseGroup = new ButtonGroup<>();
        for (CourseSquare course: senderCourseMap.values()) {
            senderCourseGroup.add( new TextButton(course.getName(), Foititopoli.gameSkin, "menu"));
        }
        senderCourseGroup.uncheckAll();
        senderCourseGroup.setMaxCheckCount(100);
        senderCourseGroup.setMinCheckCount(0);

        Table senderTable = new Table(Foititopoli.gameSkin);
        for (TextButton button: senderCourseGroup.getButtons()) {
            senderTable.add(button).width(Value.percentWidth(0.8f, senderTable)).padBottom(10).row();
            button.getLabel().setWrap(true);
        }

        final ButtonGroup<TextButton> receiverCourseGroup = new ButtonGroup<>();
        for (CourseSquare course: receiverCourseMap.values()) {
            receiverCourseGroup.add( new TextButton(course.getName(), Foititopoli.gameSkin, "menu"));
        }
        receiverCourseGroup.uncheckAll();
        receiverCourseGroup.setMaxCheckCount(100);
        receiverCourseGroup.setMinCheckCount(0);

        Table receiverTable = new Table(Foititopoli.gameSkin);
        for (TextButton button: receiverCourseGroup.getButtons()) {
            receiverTable.add(button).width(Value.percentWidth(0.8f, receiverTable)).padBottom(10).row();
            button.getLabel().setWrap(true);
        }

        acceptButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Trade trade = new Trade(TradeWindow.this.sender, TradeWindow.this.receiver);

                ArrayList<CourseSquare> senderSelectedCourses = new ArrayList<>(0);
                Array.ArrayIterator<TextButton> iterator = new Array.ArrayIterator<>(senderCourseGroup.getAllChecked());
                while (iterator.hasNext()) {
                    senderSelectedCourses.add(senderCourseMap.get(iterator.next().getText().toString()));
                }
                trade.setSenderOffer(senderSelectedCourses, (int) senderMoneySlider.getValue());

                ArrayList<CourseSquare> receiverSelectedCourses = new ArrayList<>(0);
                iterator = new Array.ArrayIterator<>(receiverCourseGroup.getAllChecked());
                while (iterator.hasNext()) {
                    receiverSelectedCourses.add(receiverCourseMap.get(iterator.next().getText().toString()));
                }
                trade.setReceiverOffer(receiverSelectedCourses, (int) receiverMoneySlider.getValue());

                trade.accept();
                ui.updatePlayer(sender);
                ui.updatePlayer(receiver);
                TradeWindow.this.remove();
            }
        });

        defaults().width(Value.percentWidth(0.48f,this)).fill();
        add(cancelButton);
        add(acceptButton).row();
        add(senderMoneyLabel).align(Align.center);
        add(receiverMoneyLabel).align(Align.center).row();
        add(senderMoneySlider);
        add(receiverMoneySlider).row();
        add(senderTable).expandY();
        add(receiverTable).expandY().row();
        
        setModal(true);
    }
}

