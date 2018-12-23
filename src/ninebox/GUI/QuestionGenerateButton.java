package ninebox.GUI;

import ninebox.DataStructure.Array99Generate;
import ninebox.DataStructure.Array99Solve;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionGenerateButton extends JButton {

  public QuestionGenerateButton(NineBlockBoxFill nineBlockBox, Array99Generate generateContents, Array99Solve fillingContents){
    super("New Question");
    this.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        generateContents.generateScheme();
        updateContent(nineBlockBox,generateContents,fillingContents);
      }
    });
  }

  private void updateContent(NineBlockBoxFill nineBlockBox, Array99Generate generateContents, Array99Solve fillingContents){
    nineBlockBox.boxContentsUpdate(generateContents);
    fillingContents.contentsUpd(generateContents);
  }
}
