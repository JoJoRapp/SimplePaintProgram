package com.example.simplepaintprogram.controller;

import com.example.simplepaintprogram.model.RectangleEditable;

public class ChangeRectangleWidth implements ShapeInteraction {

    private final RectangleEditable rectangle;
    private final double width;

    public ChangeRectangleWidth(RectangleEditable rectangle, double width) {
        this.rectangle = rectangle;
        this.width = width;
    }

    @Override
    public void execute() {
        rectangle.setWidth(width);
    }

    @Override
    public void undo() {

    }
}
