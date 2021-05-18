package com.mygdx.game.UI.Windows;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Logic.Squares.CourseSquare;

public class CourseInfoWindow extends Window {

    CourseSquare course;

    public CourseInfoWindow(CourseSquare course) {
        super(course.getName(), Foititopoli.gameSkin);
        this.course = course;

        Label priceLabel = new Label("Τιμή: " + course.getPrice(), Foititopoli.gameSkin);
        add(priceLabel).row();

        Label directionLabel = new Label(course.getDirection(), Foititopoli.gameSkin);
        add(directionLabel).row();

        TextButton closeButton = new TextButton("Close", Foititopoli.gameSkin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                CourseInfoWindow.this.remove();
            }
        });

        add(closeButton).expand().fillX().bottom().fillX();
        setModal(true);
    }

}
