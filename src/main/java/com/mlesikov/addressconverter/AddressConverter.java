package com.mlesikov.addressconverter;


import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Logger;

/**
 * @author Mihail Lesikov (mlesikov@gmail.com)
 */
public class AddressConverter {
  private static final Logger log = Logger.getLogger(AddressConverter.class.getName());
 /*
  * Geocode request URL. Here see we are passing "json" it means we will get
  * the output in JSON format. You can also pass "xml" instead of "json" for
  * XML output. For XML output URL will be
  * "http://maps.googleapis.com/maps/api/geocode/xml";
  */

  private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json";

  /*
   * Here the fullAddress String is in format like
   * "address,city,state,zipcode". Here address means "street number + route"
   * .
   */
  public GoogleResponse convertToLatLong(String fullAddress) throws IOException {

  /*
   * Create an java.net.URL object by passing the request URL in
   * constructor. Here you can see I am converting the fullAddress String
   * in UTF-8 format. You will get Exception if you don't convert your
   * address in UTF-8 format. Perhaps google loves UTF-8 format. :) In
   * parameter we also need to pass "sensor" parameter. sensor (required
   * parameter) — Indicates whether or not the geocoding request comes
   * from a device with a location sensor. This value must be either true
   * or false.
   */
    URL url = new URL(URL + "?address="
            + URLEncoder.encode(fullAddress, "UTF-8") + "&sensor=false");
    // Open the Connection
    HttpURLConnection httpConn = openHttpConnection(url);

    InputStream in = httpConn.getInputStream();
    ObjectMapper mapper = new ObjectMapper();
    GoogleResponse response = (GoogleResponse) mapper.readValue(in, GoogleResponse.class);
    in.close();

    String responseStats = "response code : " + httpConn.getResponseCode() + "\n" +
            "response message : " + httpConn.getResponseMessage() + "\n";

    log.info(responseStats);

    return response;


  }

  private HttpURLConnection openHttpConnection(URL url) throws IOException {
    URLConnection conn = url.openConnection();
    conn.setReadTimeout(10000);
    conn.setConnectTimeout(10000);
    HttpURLConnection httpConn = (HttpURLConnection) conn;
    return httpConn;
  }

  public GoogleResponse convertFromLatLong(String latlongString) throws IOException {

  /*
   * Create an java.net.URL object by passing the request URL in
   * constructor. Here you can see I am converting the fullAddress String
   * in UTF-8 format. You will get Exception if you don't convert your
   * address in UTF-8 format. Perhaps google loves UTF-8 format. :) In
   * parameter we also need to pass "sensor" parameter. sensor (required
   * parameter) — Indicates whether or not the geocoding request comes
   * from a device with a location sensor. This value must be either true
   * or false.
   */
    URL url = new URL(URL + "?latlng="
            + URLEncoder.encode(latlongString, "UTF-8") + "&sensor=false&language=bg");
    // Open the Connection
    HttpURLConnection httpConn = openHttpConnection(url);

    InputStream in = httpConn.getInputStream();
    ObjectMapper mapper = new ObjectMapper();
    GoogleResponse response = (GoogleResponse) mapper.readValue(in, GoogleResponse.class);
    in.close();

    String responseStats = "response code : " + httpConn.getResponseCode() + "\n" +
            "response message : " + httpConn.getResponseMessage() + "\n";

    log.info(responseStats);
    return response;


  }
}