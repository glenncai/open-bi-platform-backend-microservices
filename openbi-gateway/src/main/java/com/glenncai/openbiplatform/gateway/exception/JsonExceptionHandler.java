package com.glenncai.openbiplatform.gateway.exception;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Customized exception handler
 *
 * @author Glenn Cai
 * @version 1.0 05/09/2023
 */
public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {
  public JsonExceptionHandler(
      ErrorAttributes errorAttributes,
      WebProperties.Resources resources,
      ErrorProperties errorProperties,
      ApplicationContext applicationContext) {
    super(errorAttributes, resources, errorProperties, applicationContext);
  }

  /**
   * Customize the response body
   *
   * @param status       http status
   * @param errorMessage error message
   * @return response body
   */
  public static Map<String, Object> response(int status, String errorMessage) {
    Map<String, Object> map = new HashMap<>();
    map.put("code", status);
    map.put("message", errorMessage);
    map.put("data", null);
    return map;
  }

  @Override
  protected Map<String, Object> getErrorAttributes(ServerRequest request,
                                                   ErrorAttributeOptions options) {
    int code = 401;
    Throwable error = super.getError(request);
    if (error instanceof NotFoundException) {
      code = 404;
    }
    if (error instanceof IllegalArgumentException) {
      code = 400;
    }
    return response(code, this.buildMessage(request, error));
  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
  }

  @Override
  protected int getHttpStatus(Map<String, Object> errorAttributes) {
    return (int) errorAttributes.get("code");
  }

  /**
   * Build error message
   *
   * @param request request
   * @param ex      exception
   * @return error message
   */
  private String buildMessage(ServerRequest request, Throwable ex) {
    StringBuilder message = new StringBuilder("Failed to handle request [");
    message.append(request.methodName());
    message.append(" ");
    message.append(request.uri());
    message.append("]");
    if (ex != null) {
      message.append(": ");
      message.append(ex.getMessage());
    }
    return message.toString();
  }
}
