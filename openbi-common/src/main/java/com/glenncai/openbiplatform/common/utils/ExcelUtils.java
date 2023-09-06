package com.glenncai.openbiplatform.common.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Excel utils
 *
 * @author Glenn Cai
 * @version 1.0 25/07/2023
 */
@Slf4j
public class ExcelUtils {

  private static final String XLS_MIME_TYPE = "application/vnd.ms-excel";

  private static final String XLSX_MIME_TYPE =
      "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

  private ExcelUtils() {
  }

  /**
   * Convert Excel to CSV
   *
   * @param multipartFile Excel file
   * @return CSV string
   */
  public static String excelToCsv(MultipartFile multipartFile) {
    ExcelTypeEnum excelType = getExcelType(multipartFile.getContentType());
    try (InputStream inputStream = multipartFile.getInputStream()) {
      List<Map<Integer, String>> data = EasyExcelFactory.read(inputStream)
                                                        .excelType(excelType)
                                                        .sheet()
                                                        .headRowNumber(0)
                                                        .doReadSync();
      if (CollectionUtils.isEmpty(data)) {
        return "";
      }

      StringJoiner output = new StringJoiner("\n");

      // Add header row
      output.add(String.join(",", data.get(0).values()));

      // Add data rows
      for (int i = 1; i < data.size(); i++) {
        output.add(String.join(",", data.get(i).values()));
      }

      return output.toString();
    } catch (IOException e) {
      log.error("Failed to read Excel file: {}, Error message: {}",
                multipartFile.getOriginalFilename(), e.getMessage());
    }

    return "";
  }

  /**
   * Determine Excel type used in EasyExcel
   *
   * @param mimeType mime type
   * @return the enum excel type
   */
  private static ExcelTypeEnum getExcelType(String mimeType) {
    if (XLS_MIME_TYPE.equals(mimeType)) {
      return ExcelTypeEnum.XLS;
    }

    if (XLSX_MIME_TYPE.equals(mimeType)) {
      return ExcelTypeEnum.XLSX;
    }

    return null;
  }
}
