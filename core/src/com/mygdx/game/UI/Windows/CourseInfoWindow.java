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

        final TextButton upgradeButton = new TextButton("Upgrade", Foititopoli.gameSkin);
        add(upgradeButton).row();

        buyButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentPlayer.buySquare(course);
                moneyLabel.setText(aPlayer.getStudyHours()+"");
                if (!checkCanBuy()){
                    buyButton.setDisabled(true);
                    buyButton.setTouchable(Touchable.disabled);
                }
                if (checkCanUpgrade()){
                    upgradeButton.setDisabled(false);
                    upgradeButton.setTouchable(Touchable.enabled);
                }
            }
        });
        if (!checkCanBuy()){
            buyButton.setDisabled(true);
            buyButton.setTouchable(Touchable.disabled);
        }
        upgradeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                course.upgrade(currentPlayer);
                moneyLabel.setText(aPlayer.getStudyHours()+"");
                if (!checkCanUpgrade()){
                    upgradeButton.setDisabled(true);
                    upgradeButton.setTouchable(Touchable.disabled);
                }

            }
        });
        if (!checkCanUpgrade()){
            upgradeButton.setDisabled(true);
            upgradeButton.setTouchable(Touchable.disabled);
        }

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
