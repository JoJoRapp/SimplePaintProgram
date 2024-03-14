package com.example.simplepaintprogram.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class RectangleEditable extends Rectangle implements ShapeEditable {

    public RectangleEditable(double v, double v1, double v2, double v3) {
        super(v, v1, v2, v3);
    }

    @Override
    public boolean wasClicked(MouseEvent mouseEvent) {
        return mouseEvent.getX() >= getX() && mouseEvent.getX() <= getX() + getWidth() &&
                mouseEvent.getY() >= getY() && mouseEvent.getY() <= getY() + getHeight();
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.fillRect(getX(), getY(), getWidth(), getHeight());
        graphicsContext.strokeRect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void bindToSpinners(List<SpinnerValueFactory.DoubleSpinnerValueFactory> spinnerValueFactories, List<Spinner<Double>> spinners) {
        spinnerValueFactories.get(0).setValue(getWidth());
        spinnerValueFactories.get(1).setValue(getHeight());
        widthProperty().bind(spinners.get(0).valueProperty());
        heightProperty().bind(spinners.get(1).valueProperty());
    }

    @Override
    public void unbindAll() {
        widthProperty().unbind();
        heightProperty().unbind();
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder("<rect x=\"");
        sb.append(getX());
        sb.append("\" y=\"");
        sb.append(getY());
        sb.append("\" width=\"");
        sb.append(getWidth());
        sb.append("\" height=\"");
        sb.append(getHeight());
        sb.append("\" stroke=\"black\" fill=\"");
        sb.append(getFill());
        sb.replace(sb.lastIndexOf("0x"), sb.lastIndexOf("0x") + 2, "#");
        sb.append("\"/>");
        return sb.toString();
    }
}
