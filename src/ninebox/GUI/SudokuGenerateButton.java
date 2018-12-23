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
}
