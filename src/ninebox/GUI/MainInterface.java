package ninebox.GUI;

import ninebox.DataStructure.Array99Solve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainInterface {
  public static void main(String[] args){
    NineBlockBoxFill box = new NineBlockBoxFill();
    Array99Solve content = new Array99Solve();
    JFrame f = new JFrame();
    JPanel p = new JPanel();
    NumberInputPanel number = new NumberInputPanel(box,content);
    Container contentPane = f.getContentPane();
    p.add(box.panel);
    p.add(number.panel);
    contentPane.add(p);
    f.pack();
    f.setVisible(true);
    f.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
