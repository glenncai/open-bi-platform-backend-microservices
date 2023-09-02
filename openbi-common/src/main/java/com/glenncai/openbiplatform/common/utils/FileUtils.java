package com.glenncai.openbiplatform.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * File common utils
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
public class FileUtils {

  private FileUtils() {
  }

  /**
   * To check if file size is invalid
   *
   * @param file    file
   * @param maxSize max size
   * @param unit    unit (B, K, M, G)
   * @return true if the file size is larger than max size
   */
  public static boolean isInvalidFileSize(File file, int maxSize, String unit) {
    long bytes = file.length();
    double multiplier = switch (unit.toUpperCase()) {
      case "B" -> 1; // Bytes
      case "K" -> 1024; // Kilobytes
      case "M" -> 1024 * 1024; // Megabytes
      case "G" -> 1024 * 1024 * 1024; // Gigabytes
      default -> throw new IllegalArgumentException("Invalid unit: " + unit);
    };

    double fileSizeInUnits = bytes / multiplier;

    return fileSizeInUnits > maxSize;
  }

  /**
   * To check if multipart file size is invalid
   *
   * @param multipartFile multipart file
   * @param maxSize       max size
   * @param unit          unit (B, K, M, G)
   * @return true if the multipart file size is larger than max size (invalid)
   */
  public static boolean isInvalidFileSize(MultipartFile multipartFile, int maxSize, String unit) {
    long bytes = multipartFile.getSize();
    double multiplier = switch (unit.toUpperCase()) {
      case "B" -> 1; // Bytes
      case "K" -> 1024; // Kilobytes
      case "M" -> 1024 * 1024; // Megabytes
      case "G" -> 1024 * 1024 * 1024; // Gigabytes
      default -> throw new IllegalArgumentException("Invalid unit: " + unit);
    };

    double fileSizeInUnits = bytes / multiplier;

    return fileSizeInUnits > maxSize;
  }

  /**
   * To check if file extension is invalid
   *
   * @param filename            file name
   * @param validFileExtensions valid file extensions
   * @return true if file extension is invalid
   */
  public static boolean isInvalidFileExtension(String filename, List<String> validFileExtensions) {
    String extension = Optional.ofNullable(filename)
                               .filter(f -> f.contains("."))
                               .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                               .orElse("");

    return !(validFileExtensions.contains(extension));
  }
}
