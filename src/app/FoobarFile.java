package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FoobarFile {
    private String content;

    private final File fileObj;
    private static final String FILE_DIR = "src/files";

    public FoobarFile(String fileName) throws IOException {
        this.content = "";

        var pathDir = Paths.get(FILE_DIR);
        if (!pathDir.toFile().exists()) {
            Files.createDirectories(pathDir);
        }

        this.fileObj = new File(FILE_DIR, fileName);
        if (!this.fileObj.exists()) {
            var valid = this.fileObj.createNewFile();
            if (!valid) {
                throw new IOException("Can't create file");
            }
        }
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void save() {
        try (var fileWriter = new FileWriter(this.fileObj.toString());
             var bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(this.content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public FoobarFileMemento createMemento() {
        return new FoobarFileMemento(content);
    }

    private void restoreMemento(FoobarFileMemento memento) {
        this.content = memento.getContent();
    }
}
