package com.example.simplepaintprogram.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class RectangleEditable extends Rectangle implements ShapeEditable {

    public RectangleEditable(double v, double v1, double v2, double v3) {
        super(v, v1, v2, v3);
        setId(Long.toString(random.nextLong()));
    }

    public RectangleEditable(double v, double v1, double v2, double v3, String id) {
        super(v, v1, v2, v3);
        setId(id);
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
    public void setSpinners(List<SpinnerValueFactory.DoubleSpinnerValueFactory> spinnerValueFactories) {
        spinnerValueFactories.get(0).setValue(getWidth());
        spinnerValueFactories.get(1).setValue(getHeight());
    }


    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder("<rect x=\"");
        sb.append(getX())
                .append("\" y=\"")
                .append(getY())
                .append("\" width=\"")
                .append(getWidth())
                .append("\" height=\"")
                .append(getHeight())
                .append("\" stroke=\"black\" fill=\"")
                .append(getFill())
                .replace(sb.lastIndexOf("0x"), sb.lastIndexOf("0x") + 2, "#")
                .append("\" id=\"")
                .append(getId())
                .append("\"/>");
        return sb.toString();
    }
}
