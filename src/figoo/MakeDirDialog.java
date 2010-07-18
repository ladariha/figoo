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

import figoo.fileManager.FileManager;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author Lada Riha
 */
public class MakeDirDialog extends javax.swing.JDialog {

    /** Creates new form RenameDialog
     * @param fg
     * @param parent
     * @param modal
     * @param path
     */
    public MakeDirDialog(FigooView fg, java.awt.Frame parent, boolean modal, String path) {
        super(parent, modal);
        this.setTitle(path);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int w = this.getSize().width;
        int h = this.getSize().height;
        this.f = fg;
        int left = (d.width - w) / 2;
        int top = (d.height - h) / 2;
        this.setLocation(left, top);
        initComponents();
        this.path = path;

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(figoo.FigooApp.class).getContext().getResourceMap(MakeDirDialog.class);
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelMakeDir(evt);
            }
        });

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeDir(evt);
            }
        });

        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelMakeDir(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelMakeDir
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_cancelMakeDir

    private void makeDir(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeDir
        // TODO add your handling code here:
        if (jTextField1.getText().trim().length() > 0) {
            try {
                FileManager.makeDir(this.path, jTextField1.getText().trim());
            } catch (Exception ex) {
                ErrorDialog ed = new ErrorDialog(new javax.swing.JFrame(), true, "Error on RenameDialog", ex.getMessage());
                ed.setVisible(true);
            }
            this.f.refresh();
        }
        this.setVisible(false);

    }//GEN-LAST:event_makeDir
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    private String path;
    private FigooView f;
}