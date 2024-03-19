package com.example.simplepaintprogram.controller;

import java.util.ArrayList;
import java.util.List;

public class InteractionInvoker {
    private final List<ShapeInteraction> interactionList = new ArrayList<>();

    public void runCommand(ShapeInteraction shapeInteraction) {
        interactionList.add(shapeInteraction);
        shapeInteraction.execute();
    }

    public void undo() {

    }
}
