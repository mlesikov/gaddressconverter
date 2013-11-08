package com.mlesikov.addressconverter.pont;

import java.util.List;

/**
 * @author Mihail Lesikov (mlesikov@gmail.com)
 */
public class Point {
  private double latitude;
  private double longitude;

  public Point() {
  }

  public Point(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLongitude() {
    return longitude;
  }


  public boolean isInPolygon(List<Point> points) {

    int j = 0;
    boolean oddNodes = false;

    double x = longitude;
    double y = latitude;


    for (int i = 0; i < points.size(); i++) {
      j++;

      if (j == points.size()) {
        j = 0;
      }

      if (((points.get(i).getLatitude() < y) && (points.get(j).getLatitude() >= y)) || ((points.get(j).getLatitude() < y) && (points.get(i).getLatitude() >= y))) {
        if (points.get(i).getLongitude() + (y - points.get(i).getLatitude()) / (points.get(j).getLatitude() - points.get(i).getLatitude()) * (points.get(j).getLongitude() - points.get(i).getLongitude()) < x) {
          oddNodes = !oddNodes;
        }
      }
    }

    return oddNodes;
  }

}
