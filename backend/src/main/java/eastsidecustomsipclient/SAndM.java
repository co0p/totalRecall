package eastsidecustomsipclient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sourceforge.peers.FileLogger;
import net.sourceforge.peers.media.AbstractSoundManager;
import net.sourceforge.peers.media.FileReader;

public class SAndM extends AbstractSoundManager {
  OutputStream out;
  FileReader in = null;
Client c;
  // private AudioFormat audioFormat;

  public SAndM(Client c) {
    // audioFormat = new AudioFormat(8000, 16, 1, true, false);
this.c = c;
    try {
      out = new FileOutputStream("out.raw");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

  public void queuePlay(String filename) {
    try {
      in = new FileReader(filename, new FileLogger(null));
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  byte emptybuf[] = new byte[0];

  public byte[] readData() {
    if (in == null) {
      return emptybuf;
    }
    byte[] buf = in.readData();
    if (buf != null && buf.length > 0) {
      return buf;
    } else {
      in = null;
      return emptybuf;
    }
  }

  @Override
  public void close() {
    // TODO
  }

  @Override
  public void init() {
  }

  private long lastts = 0;
  private long silencems = 0;
  
  @Override
  public int writeData(byte[] b, int off, int len) {
    long ts = System.currentTimeMillis();
    if (lastts == 0) {
      lastts = ts;
    } else {
      silencems += ts - lastts;
    }
    
    short max = 0;
    for (int i=0; i<b.length-2;i+=2) {
      short amp = (short) (b[i] << 256 + b[i+1]);
      max = (short) Math.max(Math.abs(amp), max);
    }
    if (max > 1000) {
      silencems = 0;
    }
    
    if (silencems > 1500 && in == null) {
      System.out.println("3 seconds of silence.");
      silencems = 0;
      queuePlay("wahlton.wav");
    }
    
    lastts = ts;
    
    try {
      out.write(b, off, len);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return len;
  }

}
