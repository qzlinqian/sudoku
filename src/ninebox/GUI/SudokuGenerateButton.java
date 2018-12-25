package ninebox.GUI;

import ninebox.DataStructure.Array99Generate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGenerateButton extends JButton {
  SudokuGenerateButton(NineBlockBoxDisplay boxDisplay, Array99Generate generateContents){
    super("New Scheme");
    this.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        generateContents.generateScheme(0);
        boxDisplay.boxContentsUpdate(generateContents);
      }
    });
  }

  public static void main(String[] args){
    Array99Generate boxContents = new Array99Generate();
    NineBlockBoxDisplay blockBoxDisplay = new NineBlockBoxDisplay();
    SudokuGenerateButton button = new SudokuGenerateButton(blockBoxDisplay, boxContents);
    JFrame f = new JFrame();
    JPanel p = new JPanel();
    p.add(blockBoxDisplay);
    p.add(button);
    f.add(p);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setBounds(20,20, 500,500);
    f.setVisible(true);
  }
}
