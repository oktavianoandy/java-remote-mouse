/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javaremotedestop;

import com.mycompany.javaremotedestop.backend.ClickMouse;
import com.mycompany.javaremotedestop.backend.Action;
import com.mycompany.javaremotedestop.backend.MoveMouse;
import static com.mycompany.javaremotedestop.SETUP.*;
import com.mycompany.javaremotedestop.backend.KeyPress;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ACER Class untuk client/ orang yang melakukan remote
 */
public class Client extends javax.swing.JFrame {

//    private static ClickMouse cm;
    private static MoveMouse mm;
    private static Image image1;

    Thread receive = new Thread() {
        @Override
        public void run() {
//            pake UDP
//            try {
//                byte[] dataMsg = new byte[1024 * 1024];
//                DatagramPacket dp = new DatagramPacket(dataMsg, dataMsg.length);
//                DatagramSocket ds = new DatagramSocket(CLIENT_PORT);
//
//                while (true) {
//                    try {
//                        ds.receive(dp);
//                        InputStream is = new ByteArrayInputStream(dataMsg);
//                        BufferedImage image = ImageIO.read(is);
//                        Graphics graphics = jPanel1.getGraphics();
//                        graphics.drawImage(image, 0, 0, jPanel1.getWidth(), jPanel1.getHeight(), jPanel1);
//                    } catch (Exception e) {
//                        System.out.println(e);
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//            /pakai udp

//          pake TCP
            try {
                ServerSocket ss = new ServerSocket(SETUP.CLIENT_PORT);
                Socket sk = ss.accept();
                InputStream is = sk.getInputStream();
                while (true) {
                    byte[] bytes = new byte[1024 * 1024];
                    int count = 0;
                    do {
                        count += is.read(bytes, count, bytes.length - count);
                    } while (!(count > 4 && bytes[count - 2] == (byte) -1 && bytes[count - 1] == (byte) -39));

                    image1 = ImageIO.read(new ByteArrayInputStream(bytes));

                    Graphics graphics = jPanel1.getGraphics();
                    graphics.drawImage(image1, 0, 0, jPanel1.getWidth(), jPanel1.getHeight(), jPanel1);
                }
            } catch (SocketException e) {
                System.out.println(e);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
//          /pake TCP
        }
    };

    MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent evt) {
            sendPaket(new ClickMouse(evt), null);
        }

        @Override
        public void mouseMoved(MouseEvent evt) {
            mm = new MoveMouse(evt);
            System.out.println(mm.toString());
            sendPaket(null, null);
        }
    };

    KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent evt) {
            sendPaket(null, new KeyPress(evt));
        }
    };

    /**
     * Creates new form jFormServer
     */
    public Client() {
        initComponents();
        this.setExtendedState(this.getExtendedState() | Client.MAXIMIZED_BOTH);
        receive.start();

        addKeyListener(keyAdapter);
        jPanel1.addMouseListener(adapter);
        jPanel1.addMouseMotionListener(adapter);
    }

    public void sendPaket(ClickMouse cm, KeyPress kp) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(baos);
            oo.writeObject(new Action(mm, cm, kp));
            byte[] bs = baos.toByteArray();

            //send action data
            Thread.sleep(100);
            DatagramPacket dp = new DatagramPacket(
                    bs, bs.length, new InetSocketAddress(IPSERVER, SERVER_PORT));
            DatagramSocket ds = new DatagramSocket();
            ds.send(dp);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setName(""); // NOI18N

        jLabel1.setAlignmentY(0.0F);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.getAccessibleContext().setAccessibleName("");
        jPanel1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
