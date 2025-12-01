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
            // create SaveVisitor for model to accept
            SaveVisitor saveVisitor = new SaveVisitor();
            model.accept(saveVisitor);

            // Build complete string
            String s = "Paint Save File Version 1.0\n";
            s += saveVisitor.getSaveString();
            s += "End Paint Save File";
            writer.write(s);
            retVal = true;

        } catch (FileNotFoundException e) {
            retVal = false;
            e.printStackTrace();
        }
        return retVal;
    }
}
