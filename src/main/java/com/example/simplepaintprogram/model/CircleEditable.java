package com.example.simplepaintprogram.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.util.List;

public class CircleEditable extends Circle implements ShapeEditable {

    public CircleEditable(double v, double v1, double v2) {
        super(v, v1, v2);
    }

    @Override
    public boolean wasClicked(MouseEvent mouseEvent) {
        double dX = getCenterX() + getRadius() - mouseEvent.getX();
        double dY = getCenterY() + getRadius() - mouseEvent.getY();
        return Math.sqrt((dX * dX) + (dY * dY)) <= getRadius();
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.fillOval(getCenterX(), getCenterY(), getRadius() * 2, getRadius() * 2);
        graphicsContext.strokeOval(getCenterX(), getCenterY(), getRadius() * 2, getRadius() * 2);
    }

    @Override
    public void setSpinners(List<SpinnerValueFactory.DoubleSpinnerValueFactory> spinnerValueFactories) {
        spinnerValueFactories.get(2).setValue(getRadius());
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder("<circle cx=\"");
        sb.append(getCenterX() + getRadius());
        sb.append("\" cy=\"");
        sb.append(getCenterY() + getRadius());
        sb.append("\" r=\"");
        sb.append(getRadius());
        sb.append("\" stroke=\"black\" fill=\"");
        sb.append(getFill());
        sb.replace(sb.lastIndexOf("0x"), sb.lastIndexOf("0x") + 2, "#");
        sb.append("\"/>");
        return sb.toString();
    }
}
