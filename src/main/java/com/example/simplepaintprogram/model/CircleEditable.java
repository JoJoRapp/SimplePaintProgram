package com.example.simplepaintprogram.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Spinner;
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
        return mouseEvent.getX() >= getCenterX() && mouseEvent.getX() <= getCenterX() + getRadius() * 2 &&
                mouseEvent.getY() >= getCenterY() && mouseEvent.getY() <= getCenterY() + getRadius() * 2;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.fillOval(getCenterX(), getCenterY(), getRadius() * 2, getRadius() * 2);
        graphicsContext.strokeOval(getCenterX(), getCenterY(), getRadius() * 2, getRadius() * 2);
    }

    @Override
    public void bindToSpinners(List<SpinnerValueFactory.DoubleSpinnerValueFactory> spinnerValueFactories, List<Spinner<Double>> spinners) {
        spinnerValueFactories.get(2).setValue(getRadius());
        radiusProperty().bind(spinners.get(2).valueProperty());
    }

    @Override
    public void unbindAll() {
        radiusProperty().unbind();
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
