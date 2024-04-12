package com.example.simplepaintprogram.controller;

import com.example.simplepaintprogram.model.RectangleEditable;

public class ChangeRectangleHeight implements ShapeInteraction {

    private final RectangleEditable rectangle;
    private final double height;
    private final double oldHeight;

    public ChangeRectangleHeight(RectangleEditable rectangle, double height) {
        this.rectangle = rectangle;
        this.height = height;
        if (rectangle != null)
            oldHeight = rectangle.getHeight();
        else
            oldHeight = 0;
    }

    @Override
    public void execute() {
        if (rectangle != null)
            rectangle.setHeight(height);
    }

    @Override
    public void undo() {
        rectangle.setHeight(oldHeight);
    }

    @Override
    public String toString() {
        return "height " + rectangle.toXml();
    }
}
