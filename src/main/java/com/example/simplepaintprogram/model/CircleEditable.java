package com.example.simplepaintprogram.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.util.List;

public class CircleEditable extends Circle implements ShapeEditable {

    public CircleEditable(double v, double v1, double v2) {
        super(v, v1, v2);
        setId(Long.toString(random.nextLong()));
    }

    public CircleEditable(double v, double v1, double v2, String id) {
        super(v, v1, v2);
        setId(id);
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
        sb.append(getCenterX() + getRadius())
                .append("\" cy=\"")
                .append(getCenterY() + getRadius())
                .append("\" r=\"")
                .append(getRadius())
                .append("\" stroke=\"black\" fill=\"")
                .append(getFill())
                .replace(sb.lastIndexOf("0x"), sb.lastIndexOf("0x") + 2, "#")
                .append("\" id=\"")
                .append(getId())
                .append("\"/>");
        return sb.toString();
    }
}
