package ninebox.GUI;

import ninebox.Auxiliary.StopWatch;
import ninebox.DataStructure.Array99;
import ninebox.DataStructure.Array99Solve;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class SaveButton extends JButton {
  SaveButton(Array99Solve boxContents, NineBlockBoxFill box, StopWatch timer, HistoryLoaderPanel loader){
    super("Save");

    this.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          saveCurrent(boxContents, box, timer);
          loader.historyUpd();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    });

  }

  private void saveCurrent(Array99Solve boxContents, NineBlockBoxFill box, StopWatch timer) throws IOException {
    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String currentTime = date.format(new Date());
    String fileName = "HistoryData" + File.separator + currentTime + ".txt";
    File file = new File(fileName);
    if (!file.exists()) {	// If file !exists, then create it
      File dir = new File(file.getParent());
      dir.mkdirs();
      file.createNewFile();
    }

    BufferedWriter out = new BufferedWriter(new FileWriter(fileName, false));


    out.write(currentTime);
    out.newLine();

    out.write(boxContents.toPlainText());  // Write boxContents
    out.newLine();
    out.write(box.toPlainText());
    out.newLine();
//    out.write(Long.toString(time));
    out.write(Long.toString(timer.getTime()));

    out.close();
  }
}
