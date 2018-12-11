package ninebox.GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class NineBlockBox {
  static Color activeCellColor = new Color(0.9f,0.8f,0.8f,0.8f);  // selected box
  static Color inactiveCellColor = new Color(0.95f,0.9f,0.9f,0.4f);  // unselected box
  static Color wrongCellColor = new Color(1.0f, 0.2f,0.2f,0.95f);  // box with wrong number
  static Color inertCellColor = new Color(0.6f,0.8f,0.9f,0.95f);  // unchangeable box
//  Array99Mother boxContent; // = new Array99Mother boxContent;
  public JPanel panel;
  JPanel[] panels = new JPanel[9];
  JButton[] cells = new JButton[81];
//  static JFrame f = null;
//  DocumentListener docListener;
  int activeCellIndex;


  NineBlockBox(/*JFrame f*/)/* throws NumberFormatException*/{
//    f = new JFrame("test");
    GridLayout generalGridLayout = new GridLayout(3,3,10,10);
    GridLayout subGridLayout = new GridLayout(3,3,1,1);
    panel = new JPanel();
    panel.setLayout(generalGridLayout);
    for (int i=0;i<9;i++){
      panels[i] = new JPanel();
      panels[i].setLayout(subGridLayout);
      panel.add(panels[i]);
    }

    activeCellIndex = -1; // init


    /*f.add(panel);
    f.setVisible(true);
    f.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });*/
  }

  public int getActiveCellIndex() {
    return activeCellIndex;
  }
}
