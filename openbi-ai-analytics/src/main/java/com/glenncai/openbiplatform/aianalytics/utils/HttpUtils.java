package com.glenncai.openbiplatform.aianalytics.utils;

import com.glenncai.openbiplatform.common.common.ErrorCode;
import com.glenncai.openbiplatform.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * HTTP request common utils
 *
 * @author Glenn Cai
 * @version 1.0 27/07/2023
 */
@Slf4j
public class HttpUtils {

  /**
   * JSON media type
   */
  private static final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");

  private HttpUtils() {
  }

  /**
   * HTTP POST request
   *
   * @param url  request URL
   * @param data request body data
   * @return response with string type
   */
  public static String post(String url, String data) {
    String result = StringUtils.EMPTY;
    RequestBody requestBody = RequestBody.create(data, MEDIA_TYPE_JSON);
    Request.Builder builder = new Request.Builder();
    Request request = builder.url(url).post(requestBody).build();

    try (Response response = okHttpClient().newCall(request).execute()) {
      if (response.isSuccessful() && response.body() != null) {
        result = response.body().string();
        log.info("HTTP POST request success... Url: {}, Body: {}, Response: {}", url, data, result);
      }
    } catch (IOException e) {
      log.error("HTTP POST request error... Url: {}, Body: {}, Error: {}", url, data,
                e.getMessage());
      throw new BusinessException(ErrorCode.SERVER_ERROR);
    }
    return result;
  }

  /**
   * HTTP GET request
   *
   * @param url request URL
   * @return response with string type
   */
  public static String get(String url) {
    String result = StringUtils.EMPTY;
    Request.Builder builder = new Request.Builder();
    Request request = builder.url(url).get().build();

    try (Response response = okHttpClient().newCall(request).execute()) {
      if (response.isSuccessful() && response.body() != null) {
        result = response.body().string();
        log.info("HTTP GET request success... Url: {}, Response: {}", url, result);
      }
    } catch (IOException e) {
      log.error("HTTP GET request error... Url: {}, Error: {}", url, e.getMessage());
      throw new BusinessException(ErrorCode.SERVER_ERROR);
    }
    return result;
  }

  /**
   * Connection pool configuration
   *
   * @return okhttp3.ConnectionPool
   */
  private static ConnectionPool connectionPool() {
    return new ConnectionPool(256, 5, TimeUnit.MINUTES);
  }

  /**
   * OkHttp3 client configuration
   *
   * @return okhttp3.OkHttpClient
   */
  private static OkHttpClient okHttpClient() {
    return new OkHttpClient()
        .newBuilder()
        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .retryOnConnectionFailure(true)
        .connectionPool(connectionPool())
        .connectTimeout(120L, TimeUnit.SECONDS)
        .writeTimeout(120L, TimeUnit.SECONDS)
        .readTimeout(120L, TimeUnit.SECONDS)
        .build();
  }
}
