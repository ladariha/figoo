package figoo.tasks;

import figoo.SplitFileDialog;
import figoo.fileManager.FileManager;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.swing.SwingWorker;

/**
 *
 * @author Lada Riha
 */
public class SplitTask extends SwingWorker<Void, Void> {

    /**
     * File to copy
     */
    private File from;
    private int size;
    /**
     * Target directory to copy file
     */
    private String to;
    /**
     *
     */
    private SplitFileDialog cd;

    /**
     *
     * @param fileToSplit file to split
     * @param target where to split
     * @param d
     * @param size part size
     */
    public SplitTask(File fileToSplit, String target, SplitFileDialog d, int size) {
        this.from = fileToSplit;
        this.to = target;
        this.cd = d;
        this.size = size;
    }

    @Override
    public Void doInBackground() {
        try {
            splitFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void splitFile() throws IOException {

        FileManager.splitFile(this.size,this.from, this.to, this.cd);

    }


    /*
     * Executed in event dispatching thread
     */
    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
        cd.setVisible(false);
        cd.getF().refresh();
    }
}
