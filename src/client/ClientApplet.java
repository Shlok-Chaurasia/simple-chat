package client;
import java.applet.*;
import java.awt.*;
import java.io.*;
import java.net.*;
public class ClientApplet extends Applet
{
  public void init() {
    String host = "localhost";//getParameter( "host" );
    int port = 5000;//Integer.parseInt( getParameter( "port" ) );
    setLayout( new BorderLayout() );
    add( "Center", new Client( host, port ) );
} }
