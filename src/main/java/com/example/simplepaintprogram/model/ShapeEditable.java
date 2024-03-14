package com.example.simplepaintprogram.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.util.List;

public interface ShapeEditable {
    boolean wasClicked(MouseEvent mouseEvent);

    void draw(GraphicsContext graphicsContext);

    void unbindAll();

    String toXml();

    void bindToSpinners(List<SpinnerValueFactory.DoubleSpinnerValueFactory> spinnerValueFactories, List<Spinner<Double>> spinners);

    void setFill(Paint paint);

    Paint getFill();
}
