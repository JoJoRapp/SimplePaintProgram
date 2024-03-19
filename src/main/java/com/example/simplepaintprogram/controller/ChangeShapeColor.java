package com.example.simplepaintprogram.controller;

import com.example.simplepaintprogram.model.ShapeEditable;
import javafx.scene.paint.Paint;

public class ChangeShapeColor implements ShapeInteraction {

    private final ShapeEditable shape;
    private final Paint paint;

    public ChangeShapeColor(ShapeEditable shape, Paint paint) {
        this.shape = shape;
        this.paint = paint;
    }

    @Override
    public void execute() {
        shape.setFill(paint);
    }

    @Override
    public void undo() {

    }
}
