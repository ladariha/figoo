/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RenameDialog.java
 *
 * Created on 21.6.2010, 10:06:18
 */
package figoo;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.util.ServiceException;
import figoo.fileManager.DownloadAlbumPicasaTask;
import figoo.google.FigooPicasaClient;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Lada Riha
 */
public class DownloadPicasaDialog extends javax.swing.JDialog {

    /** Creates new form RenameDialog
     * @param fg
     * @param albumID
     * @param parent
     * @param modal
     * @param to
     * @param picasa
     * @param username
     * @param album
     * @param size
     */
    public DownloadPicasaDialog(FigooView fg, java.awt.Frame parent, boolean modal, String to, String size, PicasawebService picasa, String username, String albumID, boolean album) {
        super(parent, modal);
        try {
            this.setTitle("Download");
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            int w = this.getSize().width;
            int h = this.getSize().height;
            this.f = fg;
            int left = (d.width - w) / 2;
            int top = (d.height - h) / 2;
            this.setLocation(left, top);
            initComponents();

            if (album) {
                int photos = FigooPicasaClient.getTotalPhotosByAlbumID(picasa, username, albumID);
                jProgressBar2.setIndeterminate(false);
                jProgressBar2.setMinimum(0);
                jProgressBar2.setMaximum(photos);



                task = new DownloadAlbumPicasaTask(username, albumID, picasa, size, to, this);
                //task.addPropertyChangeListener(((JButton)evt.getSource()).getPropertyChangeListeners()[0]);
                task.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if ("progress".equals(evt.getPropertyName())) {
                            getjProgressBar2().setValue((Integer) evt.getNewValue());
                        }
                    }
                });
                task.execute();

            }

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            ErrorDialog ed = new ErrorDialog(new javax.swing.JFrame(), true, "DownloadPicasaDialog error", ex.getMessage());
            ed.setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
            ErrorDialog ed = new ErrorDialog(new javax.swing.JFrame(), true, "DownloadPicasaDialog error", ex.getMessage());
            ed.setVisible(true);
        } catch (ServiceException ex) {
            ex.printStackTrace();
            ErrorDialog ed = new ErrorDialog(new javax.swing.JFrame(), true, "DownloadPicasaDialog error", ex.getMessage());
            ed.setVisible(true);
        }
    }

    DownloadPicasaDialog(FigooView fg, JFrame parent, boolean modal, String text, String size, PicasawebService picasa, String username, ArrayList<String> todo, boolean album) {
        super(parent, modal);
        try {
            this.setTitle("Download");
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            int w = this.getSize().width;
            int h = this.getSize().height;
            this.f = fg;
            int left = (d.width - w) / 2;
            int top = (d.height - h) / 2;
            this.setLocation(left, top);
            initComponents();

            jProgressBar2.setIndeterminate(false);
            jProgressBar2.setMinimum(0);
            jProgressBar2.setMaximum(todo.size());

            task = new DownloadAlbumPicasaTask(username, picasa, size, text, this, todo);
            //task.addPropertyChangeListener(((JButton)evt.getSource()).getPropertyChangeListeners()[0]);
            task.addPropertyChangeListener(new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if ("progress".equals(evt.getPropertyName())) {
                        getjProgressBar2().setValue((Integer) evt.getNewValue());
                    }
                }
            });
            task.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorDialog ed = new ErrorDialog(new javax.swing.JFrame(), true, "DownloadPicasaDialog error", ex.getMessage());
            ed.setVisible(true);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jProgressBar2 = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(figoo.FigooApp.class).getContext().getResourceMap(DownloadPicasaDialog.class);
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jProgressBar2.setForeground(resourceMap.getColor("jProgressBar2.foreground")); // NOI18N
        jProgressBar2.setName("jProgressBar2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(9, 9, 9)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JProgressBar jProgressBar2;
    // End of variables declaration//GEN-END:variables
    private FigooView f;
    private DownloadAlbumPicasaTask task;

    /**
     * @return the f
     */
    public FigooView getF() {
        return f;
    }

    /**
     * @param f the f to set
     */
    public void setF(FigooView f) {
        this.f = f;
    }

    /**
     * @return the jLabel3
     */
    public javax.swing.JLabel getjLabel3() {
        return jLabel3;
    }

    /**
     * @param jLabel3 the jLabel3 to set
     */
    public void setjLabel3(javax.swing.JLabel jLabel3) {
        this.jLabel3 = jLabel3;
    }

    /**
     * @return the jProgressBar2
     */
    public javax.swing.JProgressBar getjProgressBar2() {
        return jProgressBar2;
    }

    /**
     * @param jProgressBar2 the jProgressBar2 to set
     */
    public void setjProgressBar2(javax.swing.JProgressBar jProgressBar2) {
        this.jProgressBar2 = jProgressBar2;
    }
}