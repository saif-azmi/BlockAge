package maps;

import graph.Graph;
import graph.GraphNode;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : Hung Hoang; Contributors - Saif Azmi
 * @version : 23/03/2016;
 *          <p>
 *          This class handles saving of maps created via the map editor
 * @date : 28/01/16
 */
public class EditorParser {

    private static final Logger LOG = Logger.getLogger(EditorParser.class.getName());

    private MapEditor editor;
    private String SAVE_DIRECTORY;
    private String IMAGE_DIRECTORY;
    private String RTS_DIRECTORY;
    private boolean overwrite = true;

    /**
     * The constructor creates a new directory for the application to store its files
     * The files stored include both a .map file (our own extension but should be readable by any text editor)
     * and a .png file
     *
     * @param mapEditor The Map Editing manager which this class can get information from (about the graph etc.)
     */
    public EditorParser(MapEditor mapEditor) {

        String SEPERATOR = File.separator;

        String dir = System.getProperty("user.home");
        RTS_DIRECTORY = dir + SEPERATOR + "bestRTS";
        SAVE_DIRECTORY = dir + SEPERATOR + "bestRTS" + SEPERATOR + "data" + SEPERATOR;
        IMAGE_DIRECTORY = dir + SEPERATOR + "bestRTS" + SEPERATOR + "image" + SEPERATOR;

        LOG.log(Level.INFO, dir);
        LOG.log(Level.INFO, SAVE_DIRECTORY);
        LOG.log(Level.INFO, IMAGE_DIRECTORY);

        this.editor = mapEditor;
    }


    /**
     * Called when the 'saved' button is clicked
     * Gets the name of the map and ensures it isn't empty
     * Creates a new directory if necessary, i.e. a directory for the application doesn't exist yet
     * Goes through the map, obtained from the Map Editor instance, and for each node with a blockade,
     * place a 1 in the saved file, else place a 0
     * Saves the map in the form <name>.map and a .png image
     */
    public void saveToUserFile() {

        String fileName = editor.getInterface().getFileName() + ".map";
        String imageName = editor.getInterface().getFileName() + ".png";

        Graph graph = editor.getGraph();

        if (fileName.equals("")) {
            editor.getInterface().getSaveStatusBox().setText("The map must have a name, please enter a name above");

        } else {

            if (invalidName(editor.getInterface().getFileName())) {
                return;
            }

            LOG.log(Level.INFO, "Attempting to save now");

            try {

                boolean newDirectory = false;

                // Create or directories for image and data if don't exist
                File rtsDir = new File(RTS_DIRECTORY);

                if (!rtsDir.exists()) {

                    newDirectory = true;
                    rtsDir.mkdir();
                }

                File saveDir = new File(SAVE_DIRECTORY);

                if (!saveDir.exists()) {

                    newDirectory = true;
                    saveDir.mkdir();
                }

                File imageDir = new File(IMAGE_DIRECTORY);

                if (!imageDir.exists()) {
                    imageDir.mkdir();
                }

                // Create actual files, both image and map data
                File savedFile = new File(SAVE_DIRECTORY + fileName);
                File imageFile = new File(IMAGE_DIRECTORY + imageName);

                // pop up if data file already exist
                if (savedFile.exists()) {
                    editor.getInterface().getPopUpStage().showAndWait();
                }

                if (!overwrite) {
                    return;
                }

                // Begin writing data
                FileWriter fileWriter = new FileWriter(savedFile);

                BufferedWriter writer = new BufferedWriter(fileWriter);

                for (int y = 0; y < 20; y++) {
                    for (int x = 0; x < 20; x++) {

                        if (x < 19) {

                            if (graph.nodeWith(new GraphNode(x, y)).getBlockade() != null) {
                                writer.write("1 ");
                            } else {
                                writer.write("0 ");
                            }

                        } else {

                            if (graph.nodeWith(new GraphNode(x, y)).getBlockade() != null) {
                                writer.write("1");
                            } else {
                                writer.write("0");
                            }
                        }
                    }
                    writer.newLine();
                }

                // Save Image
                WritableImage mapImage = editor.getRenderer().snapshot(new SnapshotParameters(), null);

                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(mapImage, null), "png", imageFile);
                } catch (IOException e) {
                    LOG.log(Level.SEVERE, e.toString(), e);
                }


                if (newDirectory) {
                    editor.getInterface().getSaveStatusBox().setText(
                            "New directory created at " + SAVE_DIRECTORY + ", map saved: " + fileName
                    );
                }

                editor.getInterface().getSaveStatusBox().setText("map saved: " + SAVE_DIRECTORY + fileName);

                writer.close();

            } catch (IOException e) {

                editor.getInterface().getSaveStatusBox().setText("Save failed, game error");
                LOG.log(Level.SEVERE, e.toString(), e);

            } finally {
                overwrite = true;
            }
        }
    }

    /**
     * Checks the file name is invalid
     * File name is invalid if:
     * It contains a '.'
     *
     * @param fileName The name to invalidate
     * @return whether the file name is invalid
     */
    private boolean invalidName(String fileName) {

        if (fileName.contains(".")) {

            editor.getInterface().getSaveStatusBox().setText(
                    "File name can only be contain alphanumerical characters, no special symbols allowed"
            );

            return true;
        }

        return false;
    }

    /**
     * Sets the boolean to overwrite an existing map if it already exists, called when user press 'yes' when prompted
     *
     * @param overwrite whether to overwrite or not
     */
    public void setOverwrite(boolean overwrite) {

        this.overwrite = overwrite;
    }
}
