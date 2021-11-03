package com.clj.demo.util;

import cn.hutool.http.HttpUtil;
import com.aspose.pdf.Document;
import com.aspose.pdf.ExcelSaveOptions;
import com.google.common.collect.Lists;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.security.CertificateInfo;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.fields.PdfField;
import com.spire.pdf.general.find.PdfTextFind;
import com.spire.pdf.general.find.PdfTextFindCollection;

import com.spire.pdf.widget.PdfFormWidget;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.web.multipart.MultipartFile;
import org.testng.collections.Maps;
import technology.tabula.*;
import technology.tabula.extractors.BasicExtractionAlgorithm;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.awt.geom.Rectangle2D;
import java.io.*;
import java.lang.reflect.Field;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2021/9/23
 * @description pdf解析
 * @date 2021/9/23
 **/
@Slf4j
public class PdfUtil {

    public static List<Map<String, Rectangle2D.Float>> getCroeXYForString(String filePath, int index) throws Exception {
        PdfDocument pdf = new PdfDocument(filePath);
        List<Map<String, Rectangle2D.Float>> mapList = new ArrayList<>();
        //遍历PDF文档中每页
        PdfPageBase page;
        page = pdf.getPages().get(index);
        //调用extractText()方法提取文本
        String s = page.extractText(true);
        System.out.println("是否包含换行：" + isContains(s));
        System.out.println(">>>>page.extractText" + s);
        PdfTextFindCollection allText = page.findAllText();
        PdfTextFind[] findss = allText.getFinds();
        List<String> tables = new ArrayList<>();
        for (PdfTextFind find : findss) {
            String outerText = find.getOuterText();
            System.out.println(outerText);
            tables.add(outerText);
//            System.out.println(find.getMatchText());
//            String str = find.getMatchText();
//            tables.add(str);


        }
        pdf.close();
        System.out.println(tables);
        return mapList;
    }

    private static final Pattern pattern = Pattern.compile("\n");

    public static int countColumns(PdfTextFind[] findss) {

        int beginColumn = 0;
        int endColumn = 0;
        int num = 0;
        for (PdfTextFind find : findss) {
            String str = find.getMatchText();
            if (str.equals("交易日期")) {
                beginColumn = num;
            } else if (isValidDate(str) && beginColumn != 0) {
                endColumn = num;
                break;
            }
            num++;
        }
        return (endColumn - beginColumn);
    }

    public static boolean isContains(String string) {
        return pattern.matcher(string).find();
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");

    public static boolean isValidDate(String s) {
        try {
            dateFormat.parse(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void tabula_pdf_5(String path) {
        List<List<String>> flowList = new ArrayList<>();
        try {
            PDDocument pd = PDDocument.load(new File(path));
            int totalPages = pd.getNumberOfPages();
            System.out.println("页数为：" + totalPages);
            ObjectExtractor oe = new ObjectExtractor(pd);
            BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();
            Page page = oe.extract(2);
            Table table = bea.extract(page).get(0);
            List<List<RectangularTextContainer>> rows = table.getRows();
            List<String> tempStrings = new ArrayList<>();
            for (int i = 0; i < rows.size(); i++) {
                List<RectangularTextContainer> cells = rows.get(i);
                List<String> strings = new ArrayList<>();
                int temCount = 0;
                for (int j = 0; j < cells.size(); j++) {
                    String text = cells.get(j).getText();
                    if (j < 3 && text.equals("")) {
                        temCount++;
                        continue;
                    }

                    //计算浦东支行PDF流水
                    if (!text.isEmpty()) {
                        if (i > 4 && j == 1) {
                            String[] s = text.split("  ");
                            strings.addAll(Arrays.asList(s));
                            //计算农行流水PDF算法
                        } else if (i > 4 && j == (cells.size() - 1) && temCount >= 3) {
                            tempStrings.add(text);
                        } else {
                            String[] s = text.split(" ");
                            strings.addAll(Arrays.asList(s));
                        }
                    } else if (j > 4 && text.isEmpty()) {
                        strings.add(text);
                    }
                }
                //计算农行流水PDF
                if (tempStrings.size() == 2) {
                    List<String> lastList = flowList.get(flowList.size() - 1);
                    boolean equalsFlag = lastList.get(lastList.size() - 1).equals("");
                    if (lastList.size() == flowList.get(0).size() && !equalsFlag) {
                        List<String> lastSecondList = flowList.get(flowList.size() - 2);
                        int indexNumber = lastSecondList.size() - 1;
                        String s = lastSecondList.get(indexNumber);
                        lastSecondList.set(indexNumber, s + tempStrings.get(0));
                        tempStrings.remove(0);
                    } else {
                        if (equalsFlag) {
                            String join = String.join("", tempStrings);
                            flowList.get(flowList.size() - 1).set(lastList.size() - 1, join);
                            tempStrings.clear();

                        } else {
                            String join = String.join("", tempStrings);
                            flowList.get(flowList.size() - 1).add(join);
                            tempStrings.clear();
                        }
                    }
                } else if (tempStrings.size() == 1) {
                    int index = flowList.get(0).size();
                    if (strings.size() == index && !strings.get(index - 1).equals("")) {
                        strings.set(index - 1, tempStrings.get(0) + strings.get(index - 1));
                        tempStrings.remove(0);
                    }
                }
                if (strings.size() > 3) {
                    flowList.add(strings);
                }
            }

            for (List<String> strings : flowList) {
                System.out.println(strings);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void tabula_pdf_4(String path) {
        List<List<String>> flowList = new ArrayList<>();
        try {
            PDDocument pd = PDDocument.load(new File(path));
            int totalPages = pd.getNumberOfPages();
            System.out.println("页数为：" + totalPages);
            ObjectExtractor oe = new ObjectExtractor(pd);
            BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();
            PageIterator extract1 = oe.extract();
            while (extract1.hasNext()) {
                Page next = extract1.next();
                Table table = bea.extract(next).get(0);
                List<List<RectangularTextContainer>> rows = table.getRows();
                List<String> tempStrings = new ArrayList<>();
                for (int i = 0; i < rows.size(); i++) {
                    List<RectangularTextContainer> cells = rows.get(i);
                    List<String> strings = new ArrayList<>();
                    int temCount = 0;
                    for (int j = 0; j < cells.size(); j++) {
                        String text = cells.get(j).getText();
                        if (j < 2 && text.equals("")) {
                            temCount++;
                            continue;
                        }

                        //计算浦东支行PDF流水
                        if (!text.isEmpty()) {
                            if (i > 4 && j == 2) {
                                String[] s = text.split("  ");
                                strings.addAll(Arrays.asList(s));
                                //计算农行流水PDF算法
                            } else if (i > 4 && j == (cells.size() - 1) && temCount >= 3) {
                                tempStrings.add(text);
                            } else {
                                String[] s = text.split(" ");
                                strings.addAll(Arrays.asList(s));
                            }
                        } else if (j > 4 && text.isEmpty()) {
                            strings.add(text);
                        }
                    }
                    //计算农行流水PDF
                    if (tempStrings.size() == 2) {
                        List<String> lastList = flowList.get(flowList.size() - 1);
                        boolean equalsFlag = lastList.get(lastList.size() - 1).equals("");
                        if (lastList.size() == flowList.get(0).size() && !equalsFlag) {
                            List<String> lastSecondList = flowList.get(flowList.size() - 2);
                            int indexNumber = lastSecondList.size() - 1;
                            String s = lastSecondList.get(indexNumber);
                            lastSecondList.set(indexNumber, s + tempStrings.get(0));
                            tempStrings.remove(0);
                        } else {
                            if (equalsFlag) {
                                String join = String.join("", tempStrings);
                                flowList.get(flowList.size() - 1).set(lastList.size() - 1, join);
                                tempStrings.clear();

                            } else {

                                String join = String.join("", tempStrings);
                                flowList.get(flowList.size() - 1).add(join);
                                tempStrings.clear();
                            }
                        }
                    } else if (tempStrings.size() == 1) {
                        int index = flowList.get(0).size();
                        if (strings.size() == index && !strings.get(index - 1).equals("")) {
                            strings.set(index - 1, tempStrings.get(0) + strings.get(index - 1));
                            tempStrings.remove(0);
                        }
                    }
                    if (strings.size() > 3) {
                        flowList.add(strings);
                    }
                }
            }
            for (List<String> strings : flowList) {
                System.out.println(strings);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void tabula_pdf_6(String path) {
        List<List<String>> flowList = new ArrayList<>();
        try {
            PDDocument pd = PDDocument.load(new File(path));
            int totalPages = pd.getNumberOfPages();
            System.out.println("页数为：" + totalPages);
            ObjectExtractor oe = new ObjectExtractor(pd);
            BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();
            PageIterator extract1 = oe.extract();
            while (extract1.hasNext()) {
                Page next = extract1.next();
                Table table = bea.extract(next).get(0);
                List<List<RectangularTextContainer>> rows = table.getRows();
                List<String> tempStrings = new ArrayList<>();
                for (int i = 0; i < rows.size(); i++) {
                    List<RectangularTextContainer> cells = rows.get(i);
                    List<String> strings = new ArrayList<>();
                    int countBlankIndex = 0;
                    int temCount = 0;
                    for (int j = 0; j < cells.size(); j++) {
                        String text = cells.get(j).getText();
                        if (j < 2 && text.equals("")) {
                            temCount++;
                            continue;
                        }
                        //计算浦东支行PDF流水
                        if (i > 4 && j == (cells.size() - 1) && temCount >= 2) {
                            tempStrings.add(text);
                            countBlankIndex = i;
                        } else {
                            String[] s = text.split(" ");
                            strings.addAll(Arrays.asList(s));
                        }

                    }
                    if (strings.size() > 5) {
                        flowList.add(strings);
                    }
                    //计算农行流水PDF

                    if (tempStrings.size() == 2) {
                        List<String> lastList = flowList.get(flowList.size() - 1);
                        boolean equalsFlag = lastList.get(lastList.size() - 1).equals("");
                        if (lastList.size() == flowList.get(0).size() && !equalsFlag) {
                            if((countBlankIndex-i)==1){

                            }
                            List<String> lastSecondList = flowList.get(flowList.size() - 2);
                            int indexNumber = lastSecondList.size() - 1;
                            String s = lastSecondList.get(indexNumber);
                            lastSecondList.set(indexNumber, s + tempStrings.get(0));
                            tempStrings.remove(0);
                        }else if((countBlankIndex-i)==1&&lastList.size() == flowList.get(0).size()){
                            List<String> lastSecondList = flowList.get(flowList.size() - 2);
                            int indexNumber = lastSecondList.size() - 1;
                            String s = lastSecondList.get(indexNumber);
                            lastSecondList.set(indexNumber, s + tempStrings.get(0));
                            tempStrings.remove(0);
                        } else {
                            if (equalsFlag) {
                                String join = String.join("", tempStrings);
                                flowList.get(flowList.size() - 1).set(lastList.size() - 1, join);
                                tempStrings.clear();

                            } else {

                                String join = String.join("", tempStrings);
                                flowList.get(flowList.size() - 1).add(join);
                                tempStrings.clear();
                            }
                        }
                    } else if (tempStrings.size() == 1) {
                        int index = flowList.get(0).size();
                        if (strings.size() == index && !strings.get(index - 1).equals("")) {
                            strings.set(index - 1, tempStrings.get(0) + strings.get(index - 1));
                            tempStrings.remove(0);
                        }
                    }

                }
            }
            for (List<String> strings : flowList) {
                System.out.println(strings);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void tabula_pdf_3(String path) {
        PDDocument pd = null;
        try {
            pd = PDDocument.load(new File(path));
            List<List<String>> flowList = new ArrayList<>();
            int totalPages = pd.getNumberOfPages();
            System.out.println("页数为：" + totalPages);
            ObjectExtractor oe = new ObjectExtractor(pd);
            BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();
            Page extract3 = oe.extract(1);
            Table table = bea.extract(extract3).get(0);
            List<List<RectangularTextContainer>> rows = table.getRows();
            for (int i = 0; i < rows.size(); i++) {
                List<RectangularTextContainer> cells = rows.get(i);
                List<String> table1 = new ArrayList<>();
                for (int j = 0; j < cells.size(); j++) {
                    String text = cells.get(j).getText();
                    String[] s = text.split(" ");
                    table1.addAll(Arrays.asList(s));
                }

                flowList.add(table1);
            }
            for (List<String> list : flowList) {
                System.out.println(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void tabula_pdf_2(String path) {
        PDDocument pd = null;
        try {
            long startTime = System.currentTimeMillis();
            pd = PDDocument.load(new File(path));
            List<List<String>> flowList = new ArrayList<>();
            List<List<String>> accountList = new ArrayList<>();
            int totalPages = pd.getNumberOfPages();
            System.out.println("页数为：" + totalPages);
            ObjectExtractor oe = new ObjectExtractor(pd);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();
            PageIterator extract1 = oe.extract();
            Page extract3 = oe.extract(1);
            Table table = bea.extract(extract3).get(0);
            String flag = null;
            List<String> strings = new ArrayList<>();
            List<List<RectangularTextContainer>> rows = table.getRows();
            for (int i = 0; i < 8; i++) {
                List<RectangularTextContainer> cells = rows.get(i);
                for (int j = 0; j < cells.size(); j++) {
                    String text = cells.get(j).getText();
                    if (text.equals("")) {
                        continue;
                    }
                    if (text.contains("支付宝")) {
                        flag = "alipay";
                    }
                    if (text.contains("微信支付")) {
                        flag = "wechat";
                    }
                    if (i >= 6 && flag.equals("wechat")) {
                        continue;
                    }
                    strings.add(text);
                }

            }
            if (flag.equals("alipay")) {
                String s1 = strings.get(3);
                String billNumber = strings.get(0).substring(3);
                String reg = ".*?(?=\\()";
                Pattern compile = Pattern.compile(reg);
                Matcher matcher = compile.matcher(s1.substring(4));
                String accountName = "";
                if (matcher.find()) {
                    accountName = matcher.group();
                }
                String idNameReg = "(?<=证件号码:)(.*?)(?=\\))";
                Pattern compile1 = Pattern.compile(idNameReg);
                Matcher matcher1 = compile1.matcher(s1);
                String idCardNo = "";
                if (matcher1.find()) {
                    idCardNo = matcher1.group();
                }
                String alipayAccount = "(?<=支付宝账号)(.*?)(?=中)";
                Pattern compile2 = Pattern.compile(alipayAccount);
                Matcher matcher2 = compile2.matcher(s1);
                String onlineAccount = "";
                if (matcher2.find()) {
                    onlineAccount = matcher2.group();
                }
                String startDateReg = "(?<=交易时间段:)(.*?)(?= 至)";
                Pattern compile3 = Pattern.compile(startDateReg);
                Matcher matcher3 = compile3.matcher(strings.get(5));
                String startDate = "";
                if (matcher3.find()) {
                    startDate = matcher3.group();
                }
                String endDateReg = "(?<=至 ).*";
                Pattern compile4 = Pattern.compile(endDateReg);
                Matcher matcher4 = compile4.matcher(strings.get(5));
                String endDate = "";
                if (matcher4.find()) {
                    endDate = matcher4.group();
                }
            } else {
                String s1 = strings.get(2);
                String billNumber = strings.get(0).substring(3);
                String reg = ".*?(?=\\()";
                Pattern compile = Pattern.compile(reg);
                Matcher matcher = compile.matcher(s1.substring(4));
                String accountName = "";
                if (matcher.find()) {
                    accountName = matcher.group();
                }
                String idNameReg = "(?<=身份证:)(.*?)(?=\\))";
                Pattern compile1 = Pattern.compile(idNameReg);
                Matcher matcher1 = compile1.matcher(s1);
                String idCardNo = "";
                if (matcher1.find()) {
                    idCardNo = matcher1.group();
                }
                String alipayAccount = "(?<=微信号:)(.*?)(?=中)";
                Pattern compile2 = Pattern.compile(alipayAccount);
                Matcher matcher2 = compile2.matcher(s1);
                String onlineAccount = "";
                if (matcher2.find()) {
                    onlineAccount = matcher2.group();
                }
                String startDateReg = "(?<=交易明细对应时间段 )(.*?)(?=至)";
                Pattern compile3 = Pattern.compile(startDateReg);
                Matcher matcher3 = compile3.matcher(strings.get(4));
                String startDate = "";
                if (matcher3.find()) {
                    startDate = matcher3.group();
                }
                String endDateReg = "(?<=至).*";
                Pattern compile4 = Pattern.compile(endDateReg);
                Matcher matcher4 = compile4.matcher(strings.get(4));
                String endDate = "";
                if (matcher4.find()) {
                    endDate = matcher4.group();
                }
            }

            while (extract1.hasNext()) {
                Page page = extract1.next();
                int pageNumber = page.getPageNumber();
                List<Table> extract2 = sea.extract(page);
                if (extract2.isEmpty()) {
                    continue;
                }
                Table tables = extract2.get(0);

                List<List<RectangularTextContainer>> rows1 = tables.getRows();
                for (int i = 0; i < rows1.size(); i++) {
                    List<RectangularTextContainer> cells = rows1.get(i);
                    if (pageNumber == 1 && (i == 0 || i == 1 || i == 2)) {
                        continue;
                    }
                    if (cells.size() == 1) {
                        continue;
                    }
                    List<String> table1 = new ArrayList<>();
                    for (int j = 0; j < cells.size(); j++) {
                        String text = cells.get(j).getText();
                        if (flag.equals("wechat") && j == 1) {
                            String s = text.replaceAll("\r", " ");
                            table1.add(s);
                        } else if (flag.equals("alipay") && j == 7) {
                            String s = text.replaceAll("\r", " ");
                            table1.add(s);
                        } else {
                            String s = text.replaceAll("\r", "");
                            table1.add(s);
                        }
                    }

                    flowList.add(table1);
                }
            }

            long endTime = System.currentTimeMillis();
            System.out.println("流水账户：" + JSONUtil.toJson(strings));
            for (List<String> list : flowList) {
                String s = list.get(2);

            }
            System.out.println("流水详情：" + JSONUtil.toJson(flowList));
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

    public static void tabula_pdf(String path) {
        try {
            //-f导出格式,默认CSV  (一定要大写)
            //-p 指导出哪页,all是所有
            //path　D:\\1xx.pdf
            //-l 强制使用点阵模式提取PDF　（关键在于这儿）
            String[] argsa = new String[]{"-f=JSON", "-p=all", path, "-l"};
            //CommandLineApp.main(argsa);
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(CommandLineApp.buildOptions(), argsa);
            StringBuilder stringBuilder = new StringBuilder();
            new CommandLineApp(stringBuilder, cmd).extractTables(cmd);
            System.out.println("打印返回数据:  " + stringBuilder.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void pdfToExcel(String path, String outputPath) {
        PdfDocument pdfDocument = new PdfDocument();
        System.out.println("页数为：" + pdfDocument.getPages().getCount());
        pdfDocument.loadFromFile(path);
        //设置covertToMultipleSheet为True
        pdfDocument.saveToFile(outputPath, FileFormat.XLSX);
        pdfDocument.close();
    }

    public static void aspose_pdf(String path, String outputPath) {
        Document doc = new Document(path);
        ExcelSaveOptions options = new ExcelSaveOptions();
        options.setFormat(ExcelSaveOptions.ExcelFormat.XLSX);
        options.setMinimizeTheNumberOfWorksheets(true);
        options.setInsertBlankColumnAtFirst(false);
        doc.save(outputPath, options);
//        doc.save(outputPath, SaveFormat.DocX);
    }

    public static void pdf_test1(String path) {
        PDDocument pd = null;
        try {
            long startTime = System.currentTimeMillis();
            pd = PDDocument.load(new File(path));
            List<List<String>> flowList = new ArrayList<>();
            List<List<String>> accountList = new ArrayList<>();
            int totalPages = pd.getNumberOfPages();
            System.out.println("页数为：" + totalPages);
            ObjectExtractor oe = new ObjectExtractor(pd);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();
            PageIterator extract1 = oe.extract();
            Page extract3 = oe.extract(1);
            List<Table> extract4 = sea.extract(extract3);
            Table table = bea.extract(extract3).get(0);
            List<Table> extract = bea.extract(extract3);
            String flag = null;

            List<List<RectangularTextContainer>> rows = table.getRows();


            while (extract1.hasNext()) {
                Page page = extract1.next();
                int pageNumber = page.getPageNumber();
                List<Table> extract2 = sea.extract(page);
                if (extract2.isEmpty()) {
                    extract2 = bea.extract(page);
                }
                Table tables = extract2.get(0);

                List<List<RectangularTextContainer>> rows1 = tables.getRows();
                for (int i = 0; i < rows1.size(); i++) {
                    List<RectangularTextContainer> cells = rows1.get(i);
                    if (pageNumber == 1 && (i == 0 || i == 1 || i == 2)) {
                        continue;
                    }
                    if (cells.size() == 1) {
                        continue;
                    }
                    List<String> table1 = new ArrayList<>();
                    for (int j = 0; j < cells.size(); j++) {
                        String text = cells.get(j).getText();
                        String s = text.replaceAll("\r", "");
                        table1.add(s);
                    }

                    flowList.add(table1);
                }
            }

            long endTime = System.currentTimeMillis();
            System.out.println("流水详情：" + JSONUtil.toJson(flowList));
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

    /**
     * 验证ca签名
     *
     * @param
     * @return
     */
    public static String validCaSign(String path) {
        List<String> names = Lists.newArrayList();
        int verified = 0;
        try {

            Field rsaDataField = PdfPKCS7.class.getDeclaredField("RSAdata");
            rsaDataField.setAccessible(true);
            File file = new File(path);
            InputStream inputStream = new FileInputStream(file);


            PdfReader reader = new PdfReader(inputStream);
            AcroFields acroFields = reader.getAcroFields();

            names.addAll(acroFields.getSignatureNames());
            if (names.isEmpty()) {
                return "未签名";
            }

            for (String name : names) {
                PdfPKCS7 pk = acroFields.verifySignature(name);
                X509Certificate signingCertificate = pk.getSigningCertificate();

                log.info("验签流水:{}  ca:【public key:{},issuer:{},Signature name:{},Document revision:{}】",
                        file.getName(), signingCertificate.getPublicKey(), CertificateInfo.getIssuerFields(signingCertificate), name,
                        acroFields.getRevision(name) + " of " + acroFields.getTotalRevisions());

                CertificateInfo.X500Name issuerFields = CertificateInfo.getIssuerFields(signingCertificate);
                String orgName = issuerFields.getField("O");
                String authorizedCaOrgStr = "";
                ArrayList<String> authOrg = Lists.newArrayList(authorizedCaOrgStr.split(","));
                if (!authOrg.contains(orgName)) {
                    return String.format("未授信的ca认证机构,%s", orgName);
                }
                Object rsaDataFieldContent = ((Field) rsaDataField).get(pk);
                if (rsaDataFieldContent != null && ((byte[]) rsaDataFieldContent).length == 0) {
                    log.info("Found zero-length encapsulated content: ignoring");
                    rsaDataField.set(pk, null);
                }
                if (pk.verify()) {
                    verified++;
                }
            }
        } catch (Exception e) {
            return String.format("签名验证失败,error:%s", e.getMessage());
        }
        return Objects.equals(names.size(), 0) ? "签名验证失败,error:该文件没有签名" :
                Objects.equals(names.size(), verified) ? "验证成功" : "签名验证失败";
    }

    public static void setUp() throws Exception {
        BouncyCastleProvider bcp = new BouncyCastleProvider();
        //Security.addProvider(bcp);
        Security.insertProviderAt(bcp, 1);
    }

    public static void testVerifyTestMGomez(String path) throws Exception {
        System.out.println("\n\ntest pdf\n==============");
        setUp();
        try (InputStream resource = new FileInputStream(path)) {
            Map<String, Object> map= Maps.newHashMap();
            map.put("inputStream",resource);
            String post = HttpUtil.post("", map);

            PdfReader reader = new PdfReader(resource);
            AcroFields acroFields = reader.getAcroFields();

            List<String> names = acroFields.getSignatureNames();
            if (names.isEmpty()) {
                System.out.println("该PDF没有签名");
            }
            for (String name : names) {
                System.out.println("签名: " + name);
                System.out.println("签字是否覆盖整份文件: " + acroFields.signatureCoversWholeDocument(name));
                PdfPKCS7 pk = acroFields.verifySignature(name);
                CertificateInfo.X500Name subjectFields = CertificateInfo.getSubjectFields(pk.getSigningCertificate());
                Map<String, ArrayList<String>> fields = subjectFields.getFields();
                String out = fields.get("OU").get(0);
                if (out.equals("阿里巴巴接口")) {
                    String organization = fields.get("C").get(0);
                    if (!organization.equals("CN")) {
                        System.out.println("支付宝PDF认证机构有误");
                    }
                } else if (out.equals("财经线-印章组")) {
                    String organization = fields.get("O").get(0);
                    if (!organization.equals("天威诚信数字认证中心")) {
                        System.out.println("微信PDF认证机构有误");
                    }
                } else {
                    System.out.println("机构信息不对");
                }
                System.out.println(fields);
                System.out.println("Subject: " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()));

                System.out.println("文档验证: " + pk.verify());
            }
        }

//        System.out.println();
//        Field rsaDataField = PdfPKCS7.class.getDeclaredField("RSAdata");
//        rsaDataField.setAccessible(true);
//        try (InputStream resource = new FileInputStream(path)) {
//            PdfReader reader = new PdfReader(resource);
//            AcroFields acroFields = reader.getAcroFields();
//            List<String> names = acroFields.getSignatureNames();
//            for (String name : names) {
//                log.info("签名: " + name);
//                log.info("签字是否覆盖整份文件: " + acroFields.signatureCoversWholeDocument(name));
//                PdfPKCS7 pk = acroFields.verifySignature(name);
//                log.info("Subject: " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()));
//                Object rsaDataFieldContent = rsaDataField.get(pk);
//                if (rsaDataFieldContent != null && ((byte[]) rsaDataFieldContent).length == 0) {
//                    System.out.println("发现长度为零的封装内容: ignoring");
//                    rsaDataField.set(pk, null);
//                }
//                System.out.println("文档验证:" + pk.verify());
//            }
//        }
    }
}
