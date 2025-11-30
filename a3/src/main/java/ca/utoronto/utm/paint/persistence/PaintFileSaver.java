package ca.utoronto.utm.paint.persistence;
import ca.utoronto.utm.paint.model.PaintModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PaintFileSaver {
    /**
     * Save the given paintModel to the open file
     *
     * @param model The PaintModel to be written
     * @param file  The file to save the current model in
     * @return whether we saved successfully
     */
    public static boolean save(PaintModel model, File file) {
        boolean retVal = true;
        try (PrintWriter writer = new PrintWriter(file)) {
            String s = model.getPaintFileString();
            // then write it to the file
            writer.write(s);

        } catch (FileNotFoundException e) {
            retVal = false;
            e.printStackTrace();
        }
        return retVal;
    }
}
