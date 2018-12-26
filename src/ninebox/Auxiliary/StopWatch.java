package ninebox.Auxiliary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopWatch extends Thread implements ActionListener {
  private long time;
  private long startTime;
  private long prevTime;
  private JTextField showTime;
  private JLabel label;
  private TimeGroup timeRecorder;
  public JPanel timerPanel;
  private boolean active;

  public StopWatch(){
    time = 0;
    prevTime = 0;
    active = false;
    startTime = System.currentTimeMillis();
    timeRecorder = new TimeGroup();
    showTime = new JTextField(2);
    label = new JLabel("Time used:");
    timerPanel = new JPanel();
    BoxLayout layout = new BoxLayout(timerPanel, BoxLayout.X_AXIS);
    timerPanel.setLayout(layout);
    timerPanel.add(label);
    timerPanel.add(showTime);
  }

  public void run(){
    time = System.currentTimeMillis() - startTime + prevTime;
    timeRecorder.setTime(time);
    showTime.setText(timeRecorder.toString());

    while (this.isAlive() && !this.isInterrupted()){
      if (active){
        time = System.currentTimeMillis() - startTime + prevTime;
        timeRecorder.setTime(time);
        showTime.setText(timeRecorder.toString());
        try {
          Thread.sleep(100);
        } catch (InterruptedException e){}
      }
    }
  }

  public void addUpTime(long prevTime){
    this.prevTime = prevTime;
  }


  public void stopCommand(){
    prevTime = time;
    active = false;
  }

  public void startCommand(){  // Can also be used as reset.
    startTime = System.currentTimeMillis();
    prevTime = 0;
    active = true;
  }

  public void continueCommand(){
    startTime = System.currentTimeMillis();
    active = true;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    startCommand();
  }

  @Override
  public String toString() {
    return timeRecorder.toString();
  }

  private class TimeGroup{
    int hour;
    int minute;
    int second;

    TimeGroup(int hour, int minute, int second){
      this.hour = hour;
      this.minute = minute;
      this.second = second;
    }

    TimeGroup(){
      this(0,0,0);
    }

    void setTime(long millis){
      millis /= 1000;
      this.second = (int) (millis % 60);
      millis /= 60;
      this.minute = (int) (millis % 60);
      this.hour = (int) (millis/60);
    }

    @Override
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(String.format("%02d", hour)).append(":").
          append(String.format("%02d", minute)).append(":").append(String.format("%02d", second));
      return stringBuilder.toString();
    }
  }

  public static void main(String[] argv){
    StopWatch test = new StopWatch();
    JFrame f = new JFrame();
    f.add(test.timerPanel);
    test.start();
    test.startCommand();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setBounds(20,20,100,100);
    f.setVisible(true);
  }

  void setText(String text){
    showTime.setText(text);
  }

  public long getTime() {
    return time;
  }
}
