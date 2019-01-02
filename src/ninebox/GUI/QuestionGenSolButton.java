package ninebox.GUI;

import ninebox.DataStructure.Array99Compute;
import ninebox.DataStructure.Array99Generate;
import ninebox.DataStructure.Array99Solve;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionGenSolButton extends JPanel {
  private Array99Compute computer;
  JButton generateButton, solveButton;
  private int index, size;

  public QuestionGenSolButton(NineBlockBoxFill nineBlockBox, Array99Generate generateContents, Array99Solve fillingContents){
    generateButton = new JButton("New Question");
    solveButton = new JButton("Solve");
    index = 0;
    size = 0;
//    super("New Question");
    generateButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        generateContents.generateScheme();
        updateContent(nineBlockBox,generateContents,fillingContents);
        computerUpd(fillingContents);
      }
    });

    solveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (size > 0){
          nineBlockBox.boxContentFill(computer, index);
          if (++index > size -1)
            index -= size;
        }
      }
    });

    BoxLayout layout = new BoxLayout(this,BoxLayout.X_AXIS);
    this.setLayout(layout);
    this.add(generateButton);
    this.add(solveButton);
  }

  private void updateContent(NineBlockBoxFill nineBlockBox, Array99Generate generateContents, Array99Solve fillingContents){
    nineBlockBox.boxContentsUpdate(generateContents);
    fillingContents.contentsUpd(generateContents);
  }


  public void computerUpd(Array99Solve fillingContents){
    computer = new Array99Compute(fillingContents);
    computer.solve();
    size = computer.solutions.size();
    index = 0;
  }


  public static void main(String[] args){
    NineBlockBoxFill boxFill = new NineBlockBoxFill();
    Array99Generate generate = new Array99Generate();
    Array99Solve solve = new Array99Solve();
    QuestionGenSolButton button = new QuestionGenSolButton(boxFill, generate, solve);
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
