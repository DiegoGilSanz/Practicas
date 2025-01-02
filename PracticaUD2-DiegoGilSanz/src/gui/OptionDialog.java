package gui;

import javax.swing.*;
import java.awt.*;

public class OptionDialog extends JDialog {
    private JPanel panel1;
    private JButton btnGuardarOpciones;
     JTextField txtIp;
     JTextField txtUser;
     JPasswordField txtPass;
     JPasswordField txtAdminPass;
    private Frame owner;
     public OptionDialog(Frame owner) {
         super(owner,"Opciones", true);
            this.owner=owner;
         initDialog();

     }

     private void initDialog(){
         setContentPane(panel1);
         this.panel1.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
         this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         this.pack();
         //doy dimension
         this.setSize(new Dimension(this.getWidth()+200,this.getHeight()));
         //lo colocamos en owner
         this.setLocationRelativeTo(owner);
     }


}
