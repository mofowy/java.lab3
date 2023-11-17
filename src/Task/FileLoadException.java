package Task;

public class FileLoadException extends Exception {

    public FileLoadException() {
        super("An error occurred while loading the file.");
    }

    public FileLoadException(final String message) {
        super("Error loading the file: " + message);
    }
}
