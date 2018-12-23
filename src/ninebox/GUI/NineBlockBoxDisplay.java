package ninebox.GUI;

import ninebox.DataStructure.Array99Generate;
import ninebox.DataStructure.Array99Mother;

public class NineBlockBoxDisplay extends NineBlockBox implements NineBlockBox.boxContentsUpdate{
  NineBlockBoxDisplay(){
    super();
  }

  @Override
  public void boxContentsUpdate(Array99Generate generatedContents) {
    // Display with contents
    for (int index=0;index<81;index++){
      int displayIndex = Array99Mother.findNumber(generatedContents.getContent(index));
      setCellNumber(index, displayIndex+1);
    }
  }
}
