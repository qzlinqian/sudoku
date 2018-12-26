// Load the selected file in the HistoryLoaderPanel. Continue to solve it.
package ninebox.Auxiliary;

import ninebox.DataStructure.Array99Mother;
import ninebox.DataStructure.Array99Solve;
import ninebox.GUI.NineBlockBoxFill;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HistoryArrayLoader {
  public static void loadHistory(File file, Array99Solve boxContents, NineBlockBoxFill box, StopWatch timer) throws IOException {
    BufferedReader in = new BufferedReader(new FileReader(file));
    String lastTime = in.readLine();
    timer.setText("You last modified this on "+lastTime);


    String content = in.readLine();
    String[] contentArray = content.split(" ");
    for (int i=0;i<81;i++){
      int temp = Integer.valueOf(contentArray[i]);
      boxContents.setContent(i, temp);
      if (temp == 0){
        box.resetCellNumber(i);
        continue;
      }
      temp = Array99Mother.findNumber(temp);
      box.setCellNumber(i,temp+1);
    }

    content = in.readLine();
    contentArray = content.split(" ");
    for (int i=0;i<81;i++){
      int temp = Integer.valueOf(contentArray[i]);
      if (temp == 0) {
        box.cellIsGiven(i);
        box.setWritable(i, false);
      }
      if (temp == 1) {
        box.cellIsCorrect(i);
        box.setWritable(i, true);
      }
      if (temp == 2) {
        box.cellIsWrong(i);
        box.setWritable(i, true);
      }
    }



    long time = Long.valueOf(in.readLine());
    timer.addUpTime(time);
  }
}
