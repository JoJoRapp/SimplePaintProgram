package com.example.simplepaintprogram.controller;

import com.example.simplepaintprogram.model.RectangleEditable;

public class ChangeRectangleWidth implements ShapeInteraction {

    private final RectangleEditable rectangle;
    private final double width;
    private final double oldWidth;

    public ChangeRectangleWidth(RectangleEditable rectangle, double width) {
        this.rectangle = rectangle;
        this.width = width;
        if (rectangle != null)
            oldWidth = rectangle.getWidth();
        else
            oldWidth = 0;
    }

    @Override
    public void execute() {
        if (rectangle != null)
            rectangle.setWidth(width);
    }

    @Override
    public void undo() {
        rectangle.setWidth(oldWidth);
    }

    @Override
    public String toString() {
        return "width " + rectangle.toXml();
    }
}
