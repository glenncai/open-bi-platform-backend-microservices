package com.glenncai.openbiplatform.common.utils;

import java.net.InetAddress;
import javax.servlet.http.HttpServletRequest;

/**
 * Network common utils
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
public class NetUtils {

  /**
   * Localhost IPv4 address
   */
  private static final String LOCALHOST_IPv4 = "127.0.0.1";

  /**
   * Localhost IPv6 address
   */
  private static final String LOCALHOST_IPv6 = "0:0:0:0:0:0:0:1";

  private NetUtils() {
  }

  /**
   * Get client ip address
   *
   * @param request http request
   * @return client ip address
   */
  public static String getClientIpAddress(HttpServletRequest request) {

    String ip = getIpFromHeader(request, "x-forwarded-for");

    if (isUnknownIp(ip)) {
      ip = getIpFromHeader(request, "Proxy-Client-IP");
    }

    if (isUnknownIp(ip)) {
      ip = getIpFromHeader(request, "WL-Proxy-Client-IP");
    }

    if (isUnknownIp(ip)) {
      ip = getIpFromRemoteAddr(request);
    }

    if (isLocalhostIp(ip)) {
      ip = getLocalInetAddress();
    }

    if (isManyProxies(ip)) {
      ip = truncateIp(ip);
    }

    if (ip == null) {
      return "127.0.0.1";
    }

    return LOCALHOST_IPv6.equals(ip) ? LOCALHOST_IPv4 : ip;
  }

  /**
   * Get ip from header
   *
   * @param request    http request
   * @param headerName header name
   * @return ip address
   */
  private static String getIpFromHeader(HttpServletRequest request, String headerName) {
    return request.getHeader(headerName);
  }

  /**
   * Check if ip is unknown
   *
   * @param ip ip address
   * @return true if ip is unknown
   */
  private static boolean isUnknownIp(String ip) {
    return ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip);
  }

  /**
   * Get ip from remote address
   *
   * @param request http request
   * @return ip address
   */
  private static String getIpFromRemoteAddr(HttpServletRequest request) {
    String ip = request.getRemoteAddr();
    if (ip.equals("127.0.0.1")) {
      return getLocalInetAddress();
    }
    return ip;
  }

  /**
   * Get local ip address
   *
   * @return local ip address
   */
  private static String getLocalInetAddress() {
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
      return "127.0.0.1";
    }
  }

  /**
   * Check if ip is localhost
   *
   * @param ip ip address
   * @return true if ip is localhost
   */
  private static boolean isLocalhostIp(String ip) {
    return "127.0.0.1".equals(ip);
  }

  /**
   * For the case of passing through multiple proxies, the first IP is the real IP of the client.
   *
   * @param ip ip address
   * @return true if ip is many proxies
   */
  private static boolean isManyProxies(String ip) {
    return ip != null && ip.length() > 15 && ip.contains(",");
  }

  /**
   * Truncate ip
   *
   * @param ip ip address
   * @return truncated ip address
   */
  private static String truncateIp(String ip) {
    return ip.substring(0, ip.indexOf(","));
  }
}
