package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes writable objects to destination disk files.
public class Writer {

    // REQUIRES: non-null writable object ∧ non-null destination path string
    // EFFECTS: writes given writable object to disk file whose path is the given destination.
    //          throws FileNotFoundException if destination file cannot be opened for writing.
    // CITATIONS:
    //  [1]: https://stackoverflow.com/a/2885224/13992057
    public void write(Writable object, String destinationPath) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new File(destinationPath))) {
            pw.print(object.jsonRepr().toString(4));
        }
    }

}
