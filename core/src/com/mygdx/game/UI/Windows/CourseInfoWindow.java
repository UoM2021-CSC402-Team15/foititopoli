package com.mygdx.game.UI.Windows;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Logic.Player;
import com.mygdx.game.Logic.Squares.CourseSquare;

public class CourseInfoWindow extends Window {

    private CourseSquare course;
    private Player currentPlayer;

    public CourseInfoWindow(final CourseSquare course, final Player aPlayer) {
        super(course.getName(), Foititopoli.gameSkin);
        this.course = course;
        this.currentPlayer = aPlayer;

        Label priceLabel = new Label("Τιμή: " + course.getPrice(), Foititopoli.gameSkin);
        add(priceLabel).row();

        Label directionLabel = new Label(course.getDirection(), Foititopoli.gameSkin);
        add(directionLabel).row();

        final Label moneyLabel = new Label(aPlayer.getStudyHours()+"", Foititopoli.gameSkin);
        add(moneyLabel).row();

        final TextButton buyButton = new TextButton("Buy", Foititopoli.gameSkin);
        add(buyButton).row();

        refreshBuyButton(buyButton);
        buyButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (buyButton.getText().toString().toLowerCase().contains("buy")) {
                    currentPlayer.buySquare(course);
                } else if (buyButton.getText().toString().toLowerCase().contains("upgrade")) {
                    course.upgrade(currentPlayer);
                }
                moneyLabel.setText(aPlayer.getStudyHours()+"");
                refreshBuyButton(buyButton);
            }
        });

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

    private void refreshBuyButton(TextButton button) {
        if (checkCanBuy()) {
            button.setTouchable(Touchable.enabled);
            button.setDisabled(false);
            button.setText("Buy Course");
        } else if (checkCanUpgrade()) {
            button.setTouchable(Touchable.enabled);
            button.setDisabled(false);
            button.setText("Upgrade Course");
        } else {
            button.setText("Max Upgrades");
            button.setTouchable(Touchable.disabled);
            button.setDisabled(true);
        }
    }

    private Boolean checkCanBuy() {
        Boolean isOnSameSquare = currentPlayer.getPawn().getCurrentSquare().equals(course);
        Boolean isAlreadyBought = course.getGrade() >= 5;
        Boolean hasEnoughMoney = currentPlayer.getStudyHours() >= course.getPrice();
        return !isAlreadyBought && hasEnoughMoney && isOnSameSquare;
    }

    private Boolean checkCanUpgrade() {
        if (course.getGrade()==0) return false;
        Boolean isAlreadyBoughtByPlayer = currentPlayer.getCourseList().contains(course);
        Boolean hasEnoughMoney = currentPlayer.getStudyHours() >= course.getUpgradeCost();
        Boolean hasMaximumGrade = course.getGrade() == 10;
        return isAlreadyBoughtByPlayer && hasEnoughMoney && !hasMaximumGrade;
    }
}
