package com.clj.demo.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.BasicExtractionAlgorithm;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2021/9/28
 * @description pdf总解析
 * @date 2021/9/28
 **/
public class PdfUilts {

    public static void pdfToExcel(String path) {
        PDDocument pd = null;
        try {
            long startTime = System.currentTimeMillis();
            List<List<String>> tableList = new ArrayList<>();
            pd = PDDocument.load(new File(path));
            int totalPages = pd.getNumberOfPages();
            System.out.println("页数为：" + totalPages);
            ObjectExtractor oe = new ObjectExtractor(pd);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();
            PageIterator extract1 = oe.extract();
            List<Table> extract;
            while (extract1.hasNext()) {
                Page page = extract1.next();
                extract = sea.extract(page);
                if (extract.isEmpty()) {
                    extract = bea.extract(page);
                }
                for (Table tables : extract) {
                    List<List<RectangularTextContainer>> rows = tables.getRows();
                    for (int i = 0; i < rows.size(); i++) {
                        List<RectangularTextContainer> cells = rows.get(i);
                        List<String> table = new ArrayList<>();
                        System.out.println("每列数量:" + cells.size());
                        for (int j = 0; j < cells.size(); j++) {
                            String text = cells.get(j).getText();
                            if (!text.isEmpty()) {
                                String[] strings = text.split(" ");

                            }
                        }
                        tableList.add(table);
                    }
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println(JSONUtil.toJson(tableList));
            System.out.println("耗时：" + TimeUnit.MILLISECONDS.toSeconds(endTime - startTime) + "s");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pd != null) {
                    pd.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
