package ninebox.DataStructure;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;

public class Array99Generate extends Array99{
  int[] boxUnit;
//  int[] swapSerial;
  private ArrayList<Boolean> visible;
  private int difficulty;

  public Array99Generate(){
    // Initiate two list
    boxUnit = new int[]{0,1,2,3,4,5,6,7,8};
    visible = new ArrayList<>(Collections.nCopies(81,true));
    difficulty = 30;
//    swapSerial = new int[]{1,2,3,4,5,6,7,8,9};
//    shuffle();
//    fillBoxes();
    // Do not generate scheme in the initFunc. Generate in button press action
  }

  private int setVisible(double constant) {
//    int blankBlocks = (int) Math.floor(constant * 81);
//    Calendar calendar;
    Random random = new Random();
//    calendar = Calendar.getInstance();  // Java have done this for you
//    random.setSeed(calendar.get(Calendar.SECOND));
    int i = 0;
//    while (i < blankBlocks) {
//      calendar = Calendar.getInstance();
//      random.setSeed(calendar.get(Calendar.SECOND));
//
//      int temp = random.nextInt(81);
//      if (visible.get(temp)) {
//        visible.set(temp, false);
//        i++;
//      }
//    }
//    return i;
    // The above method is too time consuming cause the random number is highly possible to fall into the chosen position, use possibility instead
    for (int index=0;index<81;index++){
      double temp = random.nextDouble();
      if (temp > constant)
        visible.set(index, true);
      else {
        visible.set(index,false);
        i++;
      }
    }

    return i;
  }

  public void generateScheme(double constant){
    shuffle();
    fillBoxes();
    setVisible(constant);
  }

  public void generateScheme(){
    generateScheme(difficulty/100.0);
  }

  // Reset boxUnit serial to get a new fill scheme
  private void shuffle(){
//    Calendar calendar = Calendar.getInstance();  // Java do this for you
    Random random = new Random(/*calendar.get(Calendar.SECOND)*/);
    int circulate = random.nextInt(20)+10; // Random swap times

    // Swap two numbers
    for (int i=0;i<circulate;i++){
//      calendar = Calendar.getInstance();
//      random.setSeed(calendar.get(Calendar.SECOND));
      int index1 = random.nextInt(9);
      int index2 = random.nextInt(9);
      swapReference(index1, index2);
      // Already have a random serial, no need to change the serial by swapSerial[]
//      index1 = random.nextInt(9);
//      index2 = random.nextInt(9);
//      swapReference(index1, index2, swapSerial);
    }
  }

  public boolean getVisible(int index){
    return visible.get(index);
  }

  public boolean getVisible(int row, int column){
    return visible.get(row * 9 + column);
  }

  private void fillBoxes(){
    // Fill the center box first
    for (int i=0;i<9;i++)
      this.setContent(1, 1, i/3, i % 3, num[boxUnit[i]]);

    // Left box, from center
    for (int i=0;i<9;i++)
      this.setContent(1,0,(i/3+1)%3,i%3,this.getContent(1,1,i/3,i%3));

    // Right box, from center
    for (int i=0;i<9;i++)
      this.setContent(1,2,(i/3+2)%3,i%3,this.getContent(1,1,i/3,i%3));

    // Top box, from center
    for (int i=0;i<9;i++)
      this.setContent(0,1,i/3,(i%3+1)%3,this.getContent(1,1,i/3,i%3));

    // Bottom box, from center
    for (int i=0;i<9;i++)
      this.setContent(2,1,i/3,(i%3+2)%3,this.getContent(1,1,i/3,i%3));

    // Top Left box, from Top
    for (int i=0;i<9;i++)
      this.setContent(0,0,(i/3+1)%3,i%3,this.getContent(0,1,i/3,i%3));

    // Top Right box, from Top
    for (int i=0;i<9;i++)
      this.setContent(0,2,(i/3+2)%3,i%3,this.getContent(0,1,i/3,i%3));

    // Bottom Left box, from Bottom
    for (int i=0;i<9;i++)
      this.setContent(2,0,(i/3+1)%3,i%3,this.getContent(2,1,i/3,i%3));

    // Bottom Right box, from Bottom
    for (int i=0;i<9;i++)
      this.setContent(2,2,(i/3+2)%3,i%3,this.getContent(2,1,i/3,i%3));

  }

  private void swapReference(int i,int j){
    int temp = boxUnit[i];
    boxUnit[i] = boxUnit[j];
    boxUnit[j] = temp;
  }

  public void setDifficulty(int difficulty){
    this.difficulty = difficulty;
  }
}
