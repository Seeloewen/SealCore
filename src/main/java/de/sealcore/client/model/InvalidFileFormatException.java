package de.sealcore.client.model;

public class InvalidFileFormatException extends Exception {
    private int line;

    public InvalidFileFormatException(int line, String message) {
        super(message);
        this.line = line;
    }

}