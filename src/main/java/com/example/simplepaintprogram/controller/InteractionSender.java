package com.example.simplepaintprogram.controller;

public class InteractionSender extends InteractionInvoker{

    private final Client client;

    public InteractionSender(Client client) {
        this.client = client;
    }

    @Override
    public void runCommand(ShapeInteraction shapeInteraction) {
        super.runCommand(shapeInteraction);
        if (client.isConnected()) {
            client.sendCommand(shapeInteraction.toString());
        }
    }

    @Override
    public void undo() {
        super.undo();
        if (client.isConnected()) {
            client.sendCommand("/undo");
        }
    }

    @Override
    public void redo() {
        super.redo();
        if (client.isConnected()) {
            client.sendCommand("/redo");
        }
    }
}
