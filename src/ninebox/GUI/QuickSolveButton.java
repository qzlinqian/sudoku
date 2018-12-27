package ninebox.GUI;

import ninebox.DataStructure.Array99Compute;
import ninebox.DataStructure.Array99Mother;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuickSolveButton extends JButton {
  int index;
  // Do not use it when the problem is a little difficult, cause the algorithm is not efficient
  QuickSolveButton(NineBlockBox nineBlockBox, Array99Mother boxContents){
    super("Solve");
    index = 0;
    this.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {  // TODO: This's erroneous. Update computer at GeneratorButton in MainInterface!
        Array99Compute computeContents = new Array99Compute(boxContents);
        computeContents.solve();
        int size = computeContents.solutions.size();
        if (size > 0){
          nineBlockBox.boxContentFill(computeContents,index);
          if (++index > size -1)
            index -= size;
        }
//        else no solution, implement later
      }
    });

  }
}
