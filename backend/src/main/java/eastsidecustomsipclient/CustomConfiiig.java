package eastsidecustomsipclient;

import java.net.InetAddress;
import java.net.UnknownHostException;

import net.sourceforge.peers.Config;
import net.sourceforge.peers.media.MediaMode;
import net.sourceforge.peers.sip.syntaxencoding.SipURI;

public class CustomConfiiig implements Config {

  private InetAddress publicIpAddress;

  public InetAddress getLocalInetAddress() {
    InetAddress inetAddress;
    try {
      // if you have only one active network interface, getLocalHost()
      // should be enough
      // inetAddress = InetAddress.getLocalHost();
      // if you have several network interfaces like I do,
      // select the right one after running ipconfig or ifconfig
      inetAddress = InetAddress.getByName("188.226.215.176");
    } catch (UnknownHostException e) {
      e.printStackTrace();
      return null;
    }
    return inetAddress;
  }

  public InetAddress getPublicInetAddress() {
    return publicIpAddress;
  }

  public String getUserPart() { return "2346003e0"; }
  public String getDomain() { return "sipgate.de"; }
  public String getPassword() { return ""; }

  public MediaMode getMediaMode() {
    return MediaMode.captureAndPlayback;
  }

  public void setPublicInetAddress(InetAddress inetAddress) {
    publicIpAddress = inetAddress;
  }

  public SipURI getOutboundProxy() {
    return null;
  }

  public int getSipPort() {
    return 0;
  }

  public boolean isMediaDebug() {
    return false;
  }

  public String getMediaFile() {
    return null;
  }

  public int getRtpPort() {
    return 0;
  }

  public void setLocalInetAddress(InetAddress inetAddress) {
  }

  public void setUserPart(String userPart) {
  }

  public void setDomain(String domain) {
  }

  public void setPassword(String password) {
  }

  public void setOutboundProxy(SipURI outboundProxy) {
  }

  public void setSipPort(int sipPort) {
  }

  public void setMediaMode(MediaMode mediaMode) {
  }

  public void setMediaDebug(boolean mediaDebug) {
  }

  public void setMediaFile(String mediaFile) {
  }

  public void setRtpPort(int rtpPort) {
  }

  public void save() {
  }

}
