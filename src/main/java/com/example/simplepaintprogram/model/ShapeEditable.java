package com.example.simplepaintprogram.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.util.List;

public interface ShapeEditable {
    boolean wasClicked(MouseEvent mouseEvent);

    void draw(GraphicsContext graphicsContext);

    void setSpinners(List<SpinnerValueFactory.DoubleSpinnerValueFactory> spinnerValueFactories);

    String toXml();

    void setFill(Paint paint);

    Paint getFill();
}
