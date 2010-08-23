package figoo.tasks;

import figoo.CombineFileDialog;
import figoo.fileManager.FileManager;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.swing.SwingWorker;

/**
 *
 * @author Lada Riha
 */
public class CombineTask extends SwingWorker<Void, Void> {

    /**
     * File to copy
     */
    private String from;
    /**
     * Target directory to copy file
     */
    private String to;
    /**
     *
     */
    private CombineFileDialog cd;

    /**
     *
     * @param fileToMerge file to split
     * @param target where to split
     * @param d
     */
    public CombineTask(String  fileToMerge, String target, CombineFileDialog d) {
        this.from = fileToMerge;
        this.to = target;
        this.cd = d;
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
        FileManager.combineFile(this.from, this.to, this.cd);
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
