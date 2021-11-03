package com.clj.demo.util;

import org.junit.Test;

import java.io.File;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2021/9/23
 * @description
 * @date 2021/9/23
 **/
public class PdfUtilTest {
    @Test
    public void testPdf() {
        String path = "D:\\project\\SpringBoot-JPA\\src\\main\\resources\\file\\1629944502626_张安心微信银行流水.pdf";
    }

    @Test
    public void testPdf_2() throws Exception {
//        String path = "D:\\project\\SpringBoot-JPA\\src\\main\\resources\\file\\1629944502626_张安心微信银行流水.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\1632450289412_detail20200310.pdf";
        String path = "C:\\Users\\86175\\Desktop\\农行流水(1).pdf";
        PdfUtil.getCroeXYForString(path, 0);
    }

    @Test
    public void testPdf_3() throws Exception {
//        String path = "D:\\pythonProject\\pdf0830\\ali_pdf\\1629944498207_流水证明_2021082400085004712736086242240093449736.pdf";
//        String path = "D:\\project\\SpringBoot-JPA\\src\\main\\resources\\file\\1629944502626_张安心微信银行流水.pdf";
//        String path = "D:\\pythonProject\\pdf0830\\all_pdf\\1627894026780_徐艳艳微信流水.pdf";
        String outputPath = "C:\\Users\\86175\\Desktop\\excel12.xlsx";
//        String path="C:\\Users\\86175\\Desktop\\1632450289947_999003142410105-20200301-20200331.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\1632450289412_detail20200310.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\农行流水(1).pdf";
//        String path = "C:\\Users\\86175\\Desktop\\pdf集合\\1632544892104_汤玉娇农行.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\pdf集合\\1632562940459_p2020.9.pdf";
        //银座银行流水暂未解决
//        String path = "C:\\Users\\86175\\Desktop\\pdf集合\\1632562227603_20210324--20210924银座银行流水.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\pdf集合\\1632562940892_p2021.2.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\pdf集合\\1632563229693_2020.12-2.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\pdf集合\\1632569452512_曹庭秘银行流水.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\pdf集合\\1632572418054_民生银行 还利息账号.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\pdf集合\\1632572418134_招商银行.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\pdf集合\\1634642024309_4304202110130883701260_2.pdf";
        String path = "C:\\Users\\86175\\Desktop\\pdf集合\\1632544892104_汤玉娇农行.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\肖高胜PDF验签失败所有文件\\1629199483968_微信支付交易明细证明(20200801-20210731).pdf";
//        String path = "C:\\Users\\86175\\Desktop\\20210627231304.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\mx20210819112902623270639(1).pdf";
//        String path = "D:\\project\\SpringBoot-JPA\\src\\main\\resources\\file\\1632450289947_999003142410105-20200301-20200331.pdf";
//        PdfUtil.pdf_test1(path);
//        PdfUtil.tabula_pdf_2(path);
//        PdfUtil.tabula_pdf_4(path);
        PdfUtil.tabula_pdf_6(path);
//        System.out.println("---------------分割线-------------");
//        PdfUtil.tabula_pdf_3(path);
//        PdfUtil.tabula_pdf_5(path);
//        PdfUtil.pdfToExcel(path, outputPath);
//        PdfUtil.aspose_pdf(path, outputPath);
//        PdfUtil.tabula_pdf(path);
//        String[] argsa = new String[]{"-f=JSON", "-p=all", path, "-l"};
//        CommandLineApp.main(argsa);

    }

    @Test
    public void test4() throws Exception {
//        String path = "C:\\Users\\86175\\Desktop\\1632450289412_detail20200310.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\mx20210819112902623270639(1).pdf";
//        PdfUilts.pdfToExcel(path);
//        String path = "D:\\pythonProject\\pdf0830\\ali_pdf\\1629944498207_流水证明_2021082400085004712736086242240093449736.pdf";
//        String path = "D:\\project\\SpringBoot-JPA\\src\\main\\resources\\file\\1629944502626_张安心微信银行流水.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\微信\\1634090202870_微信流水截图.pdf";
//        String path = "C:\\Users\\86175\\Desktop\\1632450289412_detail20200310.pdf";
//        String s = PdfUtil.validCaSign(path);
//        System.out.println(s);
//        String path = "C:\\Users\\86175\\Desktop\\pdf集合\\1632563229693_2020.12-2.pdf";
        String path = "C:\\Users\\86175\\Desktop\\微信支付交易明细证明(20210721-20211021).pdf";
        PdfUtil.testVerifyTestMGomez(path);
    }

    @Test
    public void test5() throws Exception {
        String path = "C:\\Users\\86175\\Desktop\\支付宝\\";
//        String path = "C:\\Users\\86175\\Desktop\\微信\\";
        File file = new File(path);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            String paths = files[i].getName();
            System.out.println(paths);
//            PdfUtil.tabula_pdf_3(files[i].getPath());
            PdfUtil.testVerifyTestMGomez(path + paths);
        }
    }

    @Test
    public void test6(){
        System.loadLibrary("TiprayAPI");
    }
}