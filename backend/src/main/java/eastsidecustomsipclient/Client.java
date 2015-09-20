package eastsidecustomsipclient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sourceforge.peers.FileLogger;
import net.sourceforge.peers.sip.core.useragent.SipListener;
import net.sourceforge.peers.sip.core.useragent.UserAgent;
import net.sourceforge.peers.sip.syntaxencoding.SipUriSyntaxException;
import net.sourceforge.peers.sip.transport.SipRequest;
import net.sourceforge.peers.sip.transport.SipResponse;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Client implements SipListener {
  private UserAgent userAgent;
  private SAndM soundManager;
  protected SipRequest sipRequest;

  public SAndM gibMirTiernamen() {
    return soundManager;
  }
  
  public UserAgent theSafeWordIsBanana() {
    return userAgent;
  }
  
  public Client() throws SocketException {
    soundManager = new SAndM(this);

    userAgent = new UserAgent(this, new CustomConfiiig(), new FileLogger(
        "/tmp/"), soundManager);
    new Thread() {
      public void run() {
        try {
          userAgent.register();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }.start();
  }

  public void call(final String callee) {
    new Thread() {
      @Override
      public void run() {
        try {
          sipRequest = userAgent.invite(callee, null);
        } catch (SipUriSyntaxException e) {
          e.printStackTrace();
        }
      }
    }.start();
  }

  public void hangup() {
    new Thread() {
      @Override
      public void run() {
        userAgent.terminate(sipRequest);
      }
    }.start();
  }

  public void calleePickup(SipResponse arg0) {
    // TODO Auto-generated method stub

  }

  public void error(SipResponse arg0) {
    // TODO Auto-generated method stub

  }

  public void incomingCall(SipRequest arg0, SipResponse arg1) {
    // TODO Auto-generated method stub

  }

  public void registerFailed(SipResponse arg0) {
    // TODO Auto-generated method stub

  }

  public void registerSuccessful(SipResponse arg0) {
    // TODO Auto-generated method stub

  }

  public void registering(SipRequest arg0) {
    // TODO Auto-generated method stub

  }

  public void remoteHangup(SipRequest arg0) {
    this.hangup();

  }

  public void ringing(SipResponse arg0) {
    // TODO Auto-generated method stub

  }

  static class MyHandler implements HttpHandler {
    Client c;

    public MyHandler(Client c) {
      this.c = c;
    }

    public static Map<String, String> splitQuery(String query)
        throws UnsupportedEncodingException {
      Map<String, String> query_pairs = new LinkedHashMap<String, String>();
      String[] pairs = query.split("&");
      for (String pair : pairs) {
        int idx = pair.indexOf("=");
        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
            URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
      }
      return query_pairs;
    }

    public void handle(HttpExchange t) throws IOException {
      String qs = t.getRequestURI().getQuery();
      Map<String, String> params = splitQuery(qs);
      String toCall = "";
      if (params.containsKey("customerNumber")) {
        toCall = params.get("customerNumber").trim();
      }
      final String toCall2 = "sip:" + toCall + "@sipgate.de";
      // TODO: validate thizz

      // if (params.containsKey("provider")) {
      //
      // }
      new Thread() {
        public void run() {
          try {
            c.call(toCall2);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }.start();

      String response = "whatevs";
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }
  
  static class NeinMannHandler implements HttpHandler {
    Client c;

    public NeinMannHandler(Client c) {
      this.c = c;
    }


    public void handle(HttpExchange t) throws IOException {
      c.gibMirTiernamen().queuePlay("neinmann.wav");
      String response = "whatevs";
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }

  public static void main(String[] args) throws IOException {
    try {
      Client c = new Client();

      HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
      server.createContext("/call", new MyHandler(c));
      server.createContext("/neinmann", new NeinMannHandler(c));

      server.setExecutor(null); // creates a default executor
      server.start();

    } catch (SocketException e) {
      e.printStackTrace();
    }
  }

}
