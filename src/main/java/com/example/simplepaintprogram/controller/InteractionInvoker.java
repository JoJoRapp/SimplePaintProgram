package com.example.simplepaintprogram.controller;

import java.util.Stack;

public class InteractionInvoker {
    private final Stack<ShapeInteraction> interactionStack = new Stack<>();
    private final Stack<ShapeInteraction> redoStack = new Stack<>();

    public void runCommand(ShapeInteraction shapeInteraction) {
        interactionStack.push(shapeInteraction);
        shapeInteraction.execute();
        redoStack.clear();
    }

    public void undo() {
        if (!interactionStack.empty()) {
            interactionStack.peek().undo();
            redoStack.push(interactionStack.pop());
        }
    }

    public void redo() {
        if (!redoStack.empty()) {
            redoStack.peek().execute();
            interactionStack.push(redoStack.pop());
        }
    }
}
