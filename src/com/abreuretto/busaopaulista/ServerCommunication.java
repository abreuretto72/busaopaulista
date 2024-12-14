package com.abreuretto.busaopaulista;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class ServerCommunication { 
public static final String Logger = ServerCommunication.class.getName();
private static String slurp(InputStream in) throws IOException {
    StringBuffer out = new StringBuffer();
       byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
          out.append(new String(b, 0, n));
       }
     return out.toString();
 }
public static String post_string(String url, String urlParameters) throws IOException {
HttpURLConnection conn = null;
   try {
     conn = (HttpURLConnection) new URL(url).openConnection();
    } catch (MalformedURLException e) {
   Log.e(Logger, "MalformedURLException While Creating URL Connection - " + e.getMessage());
   throw e;
  } catch (IOException e) {
    Log.e(Logger, "IOException While Creating URL Connection - " + e.getMessage());
    throw e;
 }
  conn.setDoOutput(true);
  conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
  conn.setRequestProperty("Content-Length", Integer.toString(urlParameters.length()));
  OutputStream os = null; 
  try {
      os = conn.getOutputStream();
  } catch (IOException e) {
     Log.e(Logger, "IOException While Creating URL OutputStream - " + e.getMessage());
     throw e;
  } 
  try { 
       os.write(urlParameters.toString().getBytes());
    } catch (IOException e) {
  Log.e(Logger, "IOException While writting URL OutputStream - " + e.getMessage());
  throw e; 
   }
  InputStream in = null; 
   try {
      in = conn.getInputStream();
   } catch (IOException e) {
     Log.e(Logger, "IOException While Creating URL InputStream - " + e.getMessage());
     throw e;
   }
    String output = null;
    try {
        output = slurp(in);
    } catch (IOException e) { 
      Log.e(Logger, "IOException While Reading URL OutputStream - " + e.getMessage());
      throw e;
    } finally {
     try {
      os.close();
      in.close();
      } catch (IOException e) {
      Log.e(Logger, "IOException While Closing URL Output and Input Stream - " + e.getMessage());
    }
  }
 conn.disconnect();return output;
}
}


/*USO
 String url ="http://example.com/post/url/to/remote/server.php"
 Log.i(Logger, "URL : - " + url);
 TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
 String parameter = "userid=" + telephonyManager.getDeviceId(); // construct the parameter string
 String data = ServerCommunication.post_string(url, parameter);
 Log.i(Logger, "URL Result: - " + data);
 * 
 */


