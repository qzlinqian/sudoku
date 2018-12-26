package ninebox.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class HistoryLoaderPanel extends JPanel {
  private static String path = "HistoryData";
  private ArrayList<File> fileList;
  JList list;
  JButton confirmButton, deleteButton;

  HistoryLoaderPanel(){
    this.setLayout(new BorderLayout());
    fileList = getListFiles(path);

    confirmButton = new JButton("Confirm");
    deleteButton = new JButton("Delete");

    JPanel auxPanel = new JPanel();
    BoxLayout boxLayout = new BoxLayout(auxPanel, BoxLayout.X_AXIS);
    auxPanel.setLayout(boxLayout);
    auxPanel.add(confirmButton);
    auxPanel.add(deleteButton);

    deleteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        File toBeDeleted = (File) list.getSelectedValue();
        toBeDeleted.delete();
        historyUpd();
      }
    });

//    list = new JList(fileName.toArray());
    list = new JList(fileList.toArray());
    JScrollPane scrollPane = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    this.add(scrollPane, BorderLayout.CENTER);
    this.add(auxPanel, BorderLayout.SOUTH);
  }

  public File getChosenFile(){
    int chosen = list.getSelectedIndex();
    return fileList.get(chosen);
  }

  public void historyUpd(){
    fileList = getListFiles("HistoryData");
    list.setListData(fileList.toArray());
  }


  // Copied from https://blog.csdn.net/chinasxdtzhaoxinguo/article/details/84612716
  public static ArrayList<File> getListFiles(Object obj) {
    File directory = null;
    if (obj instanceof File) {
      directory = (File) obj;
    } else {
      directory = new File(obj.toString());
    }
    ArrayList<File> files = new ArrayList<>();
    if (directory.isFile()) {
      files.add(directory);
      return files;
    } else if (directory.isDirectory()) {
      File[] fileArr = directory.listFiles();
      for (int i = 0; i < fileArr.length; i++) {
        File fileOne = fileArr[i];
        files.addAll(getListFiles(fileOne));
      }
    }
    return files;
  }

  public static void main(String[] args){
    ArrayList<File> fileList = getListFiles("HistoryData");
    for (File temp:fileList){
      System.out.println(temp);
    }
  }

}
