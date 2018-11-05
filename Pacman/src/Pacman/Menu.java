package Pacman;

import java.awt.Color;

public class Menu extends javax.swing.JFrame {
    //Declaro variables

    public Menu() {
        //Inicializa los componenestes del Frame
        initComponents();
        //Setea el titulo
        setTitle("Pacman");
        //Que se cierre el programa al cerrarse el frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Setea el tamaño
        setSize(380, 420);
        setLocationRelativeTo(null);
        //Pinta el fondo de negro
        //setBackground(Color.BLACK);
        //setForeground(Color.BLACK);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        Titulo = new javax.swing.JLabel();
        Comenzar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pacman");
        setResizable(false);
        setSize(new java.awt.Dimension(380, 420));

        jPanel.setBackground(java.awt.Color.black);

        Titulo.setIcon(new javax.swing.ImageIcon("D:\\Google Drive\\6to Año\\Programación sobre Redes\\TP - Juego IA\\Java2DPacmanGame-master\\src\\res\\title.png")); // NOI18N

        Comenzar.setText("Comenzar");
        Comenzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComenzarActionPerformed(evt);
            }
        });

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(Titulo))
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(Comenzar))
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(Titulo)
                .addGap(76, 76, 76)
                .addComponent(Comenzar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void ComenzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComenzarActionPerformed
        //Inicializa el juego llamando al constructor de la clase pacman
        new Game().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ComenzarActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Comenzar;
    private javax.swing.JLabel Titulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel;
    // End of variables declaration//GEN-END:variables
}
