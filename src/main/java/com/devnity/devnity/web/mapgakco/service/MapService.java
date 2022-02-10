package com.devnity.devnity.web.mapgakco.service;

import org.springframework.stereotype.Service;

@Service
public class MapService {

  public Double maxDistanceByTwoPoint(
    Double centerY, Double centerX,
    Double ney, Double nex, Double swy, Double swx,
    String unit
  ) {
    Double result = 0.0;
    result = Math.max(result, distance(centerY, centerX, ney, nex, unit));
    result = Math.max(result, distance(centerY, centerX, swy, swx, unit));
    result = Math.max(result, distance(centerY, centerX, ney, swx, unit));
    result = Math.max(result, distance(centerY, centerX, swy, nex, unit));
    return result;
  }

  /**
   * 위경도를 이용한 거리계산 unit - "" : 마일 - "kilometer" : kilo 단위 - "meter" : meter 단위
   */
  public Double distance(Double lat1, Double lon1, Double lat2, Double lon2, String unit) {
    Double theta = lon1 - lon2;
    Double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

    dist = Math.acos(dist);
    dist = rad2deg(dist);
    dist = dist * 60 * 1.1515;

    if (unit == "kilometer") {
      dist = dist * 1.609344;
    } else if (unit == "meter") {
      dist = dist * 1609.344;
    }

    return (dist);
  }

  /**
   * degrees to radians
   */
  public Double deg2rad(Double deg) {
    return (deg * Math.PI / 180.0);
  }

  /**
   * radians to degrees
   */
  public Double rad2deg(Double rad) {
    return (rad * 180 / Math.PI);
  }



}
