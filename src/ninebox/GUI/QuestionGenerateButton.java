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

  public static void main(String[] args){
    NineBlockBoxFill boxFill = new NineBlockBoxFill();
    Array99Generate generate = new Array99Generate();
    Array99Solve solve = new Array99Solve();
    QuestionGenerateButton button = new QuestionGenerateButton(boxFill, generate, solve);
    JFrame f = new JFrame();
    JPanel p = new JPanel();
    p.add(boxFill);
    p.add(button);
    f.add(p);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setBounds(20,20, 500,500);
    f.setVisible(true);
  }
}
