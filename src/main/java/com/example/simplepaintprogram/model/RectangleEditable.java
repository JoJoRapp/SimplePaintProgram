package com.example.simplepaintprogram.model;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class RectangleEditable extends Rectangle {
    public RectangleEditable(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public boolean wasClicked(MouseEvent mouseEvent) {
        return mouseEvent.getX() >= getX() && mouseEvent.getX() <= getX() + getWidth() &&
                mouseEvent.getY() >= getY() && mouseEvent.getY() <= getY() + getHeight();
    }
}
