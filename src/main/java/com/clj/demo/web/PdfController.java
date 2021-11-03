package com.clj.demo.web;

import com.clj.demo.common.OutCome;
import com.clj.demo.enumeration.AccountStateEnum;
import com.clj.demo.enumeration.CertificationTypeEnum;
import com.clj.demo.enumeration.PdfTypeEnum;
import com.clj.demo.model.PdfTransAccountDTO;
import com.clj.demo.model.PdfTransFlowDTO;
import com.clj.demo.service.PdfTransAccountService;
import com.clj.demo.service.PdfTransFlowService;
import com.clj.demo.util.HttpResult;
import com.clj.demo.util.HttpUtil;
import com.clj.demo.util.TransferUtils;
import com.google.common.collect.Maps;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.security.CertificateInfo;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import technology.tabula.*;
import technology.tabula.extractors.BasicExtractionAlgorithm;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.security.Security;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
@Api(tags = "PDF文件管理测试")
@RestController
@RequestMapping(value = "/pdf")
public class PdfController {

    @Resource
    private PdfTransAccountService pdfTransAccountService;
    @Resource
    private PdfTransFlowService pdfTransFlowService;

    @PostMapping(value = "wechat_alipay", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "微信支付宝PDF解析测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outReqNo", value = "外部请求编号", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "file", value = "文件流对象,接收数组格式", required = true, dataType = "__File", dataTypeClass = MultipartFile.class)
    })
    public OutCome resolveWxAndZhifubaoPdf(@RequestParam("file") MultipartFile file, String outReqNo) {
        long start = System.currentTimeMillis();
        try {

            File transferToFile = TransferUtils.transferToFile(file.getInputStream(), file.getOriginalFilename());
            InputStream resourceInput = new FileInputStream(transferToFile);
            OutCome outCome = validCaSign(resourceInput, file.getOriginalFilename());
            if (outCome.getCode() == 0) {
                return outCome;
            }
            PDDocument pd = PDDocument.load(transferToFile);
            int totalPages = pd.getNumberOfPages();
            log.info("该{}页数为：{}", file.getOriginalFilename(), totalPages);
            ObjectExtractor oe = new ObjectExtractor(pd);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            Map<String, Object> map = transAccount(pd, outReqNo);
            Boolean exists = (Boolean) map.get("exists");
            if (exists) {
                return OutCome.success("文件已存在，不在进行解析");
            }
            String flag = map.get("flag").toString();
            PageIterator extract1 = oe.extract();
            List<PdfTransFlowDTO> pdfTransFlowDTOList = new ArrayList<>();

            while (extract1.hasNext()) {
                Page page = extract1.next();
                int pageNumber = page.getPageNumber();
                List<Table> extract2 = sea.extract(page);
                if (extract2.isEmpty()) {
                    continue;
                }
                Table table = extract2.get(0);
                List<List<RectangularTextContainer>> rows = table.getRows();
                for (int i = 0; i < rows.size(); i++) {
                    List<RectangularTextContainer> cells = rows.get(i);
                    if (pageNumber == 1 && (i == 0 || i == 1 || i == 2)) {
                        continue;
                    }
                    if (cells.size() == 1) {
                        continue;
                    }
                    PdfTransFlowDTO pdfTransFlowDTO = new PdfTransFlowDTO();
                    pdfTransFlowDTO.setOutReqNo(outReqNo);
                    pdfTransFlowDTO.setAccountId((Long) map.get("accountId"));
                    if (flag.equals("wechat")) {
                        pdfTransFlowDTO.setTransNo(cells.get(0).getText().replaceAll("\r", ""));
                        pdfTransFlowDTO.setTransTime(TransferUtils.transStringToDate(cells.get(1).getText().replaceAll("\r", " ")));
                        pdfTransFlowDTO.setTransType(cells.get(2).getText().replaceAll("\r", ""));
                        pdfTransFlowDTO.setTransVariable(cells.get(3).getText().replaceAll("\r", ""));
                        pdfTransFlowDTO.setTransMode(cells.get(4).getText().replaceAll("\r", ""));
                        pdfTransFlowDTO.setTransAmt(BigDecimal.valueOf(Double.parseDouble(cells.get(5).getText().replaceAll("\r", ""))));
                        pdfTransFlowDTO.setOpponentName(cells.get(6).getText().replaceAll("\r", ""));
                        pdfTransFlowDTO.setBusinessNo(cells.get(7).getText().replaceAll("\r", ""));
                        pdfTransFlowDTOList.add(pdfTransFlowDTO);

                    } else {
                        pdfTransFlowDTO.setTransNo(cells.get(5).getText().replaceAll("\r", ""));
                        pdfTransFlowDTO.setTransTime(TransferUtils.transStringToDate(cells.get(7).getText().replaceAll("\r", " ")));
                        pdfTransFlowDTO.setTransVariable(cells.get(0).getText().replaceAll("\r", ""));
                        pdfTransFlowDTO.setTransMode(cells.get(3).getText().replaceAll("\r", ""));
                        pdfTransFlowDTO.setTransAmt(BigDecimal.valueOf(Double.parseDouble(cells.get(4).getText().replaceAll("\r", ""))));
                        pdfTransFlowDTO.setOpponentName(cells.get(1).getText().replaceAll("\r", ""));
                        pdfTransFlowDTO.setBusinessNo(cells.get(6).getText().replaceAll("\r", ""));
                        pdfTransFlowDTO.setRemark(cells.get(2).getText().replaceAll("\r", ""));
                        pdfTransFlowDTOList.add(pdfTransFlowDTO);
                    }
                }

            }
            pdfTransFlowService.saveAll(pdfTransFlowDTOList);
            long end = System.currentTimeMillis();
            log.info("页数为：" + totalPages, "解析成功,耗时：" + TimeUnit.MILLISECONDS.toSeconds(end - start) + "s");
            return OutCome.success("解析成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return OutCome.failure("解析失败");
    }

    /**
     * 支付宝微信pdf流水账户解析落库
     */
    private Map<String, Object> transAccount(PDDocument pd, String outReqNo) {
        String flag = null;
        Map<String, Object> map = Maps.newHashMap();
        ObjectExtractor oe = new ObjectExtractor(pd);
        BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();
        Page page = oe.extract(1);
        Table table = bea.extract(page).get(0);
        List<String> strings = new ArrayList<>();
        List<List<RectangularTextContainer>> rows = table.getRows();
        for (int i = 0; i < 8; i++) {
            List<RectangularTextContainer> cells = rows.get(i);
            for (int j = 0; j < cells.size(); j++) {
                String text = cells.get(j).getText();
                if (text.equals("")) {
                    continue;
                }
                if (text.contains(PdfTypeEnum.WECHAT_PAY.getName())) {
                    flag = "wechat";
                }
                if (text.contains(PdfTypeEnum.ALIPAY.getName())) {
                    flag = "alipay";
                }
                if (i >= 6 && flag.equals("wechat")) {
                    continue;
                }
                strings.add(text);
            }
        }
        String billNumber = strings.get(0).substring(3);
        // 判断文件是否已经存在
        Boolean isExists = pdfTransAccountService.findByBillNumber(billNumber);
        if (isExists) {
            map.put("exists", true);
            return map;
        }
        map.put("exists", false);
        PdfTransAccountDTO pdfAccount = new PdfTransAccountDTO();
        pdfAccount.setOutReqNo(outReqNo);
        pdfAccount.setIdType(CertificationTypeEnum.ID_CARD.name());
        pdfAccount.setDataSource(flag);
        pdfAccount.setAccountState(Integer.valueOf(AccountStateEnum.NORMAL.getCode()));
        pdfAccount.setCreateTime(Instant.now());
        if (flag.equals("wechat")) {
            pdfAccount.setBillNumber(billNumber);
            String s1 = strings.get(2);
            String reg = ".*?(?=\\()";
            Pattern compile = Pattern.compile(reg);
            Matcher matcher = compile.matcher(s1.substring(4));
            if (matcher.find()) {
                pdfAccount.setAccountName(matcher.group());
            }
            String idNameReg = "(?<=身份证:)(.*?)(?=\\))";
            Pattern compile1 = Pattern.compile(idNameReg);
            Matcher matcher1 = compile1.matcher(s1);
            if (matcher1.find()) {
                pdfAccount.setIdCardNo(matcher1.group());
            }
            String alipayAccount = "(?<=微信号:)(.*?)(?=中)";
            Pattern compile2 = Pattern.compile(alipayAccount);
            Matcher matcher2 = compile2.matcher(s1);
            if (matcher2.find()) {
                pdfAccount.setOnlineName(matcher2.group());
            }
            String startDateReg = "(?<=交易明细对应时间段 )(.*?)(?=至)";
            Pattern compile3 = Pattern.compile(startDateReg);
            Matcher matcher3 = compile3.matcher(strings.get(4));
            if (matcher3.find()) {
                pdfAccount.setStartTime(TransferUtils.transStringToDate(matcher3.group()));
                ;
            }
            String endDateReg = "(?<=至).*";
            Pattern compile4 = Pattern.compile(endDateReg);
            Matcher matcher4 = compile4.matcher(strings.get(4));
            if (matcher4.find()) {
                pdfAccount.setEndTime(TransferUtils.transStringToDate(matcher4.group()));
            }
        } else {
            String s1 = strings.get(3);
            pdfAccount.setBillNumber(billNumber);
            String reg = ".*?(?=\\()";
            Pattern compile = Pattern.compile(reg);
            Matcher matcher = compile.matcher(s1.substring(4));
            if (matcher.find()) {
                pdfAccount.setAccountName(matcher.group());
            }
            String idNameReg = "(?<=证件号码:)(.*?)(?=\\))";
            Pattern compile1 = Pattern.compile(idNameReg);
            Matcher matcher1 = compile1.matcher(s1);
            if (matcher1.find()) {
                pdfAccount.setIdCardNo(matcher1.group());
            }
            String alipayAccount = "(?<=支付宝账号)(.*?)(?=中)";
            Pattern compile2 = Pattern.compile(alipayAccount);
            Matcher matcher2 = compile2.matcher(s1);
            if (matcher2.find()) {
                pdfAccount.setOnlineName(matcher2.group());
            }
            String startDateReg = "(?<=交易时间段:)(.*?)(?= 至)";
            Pattern compile3 = Pattern.compile(startDateReg);
            Matcher matcher3 = compile3.matcher(strings.get(5));
            if (matcher3.find()) {
                pdfAccount.setStartTime(TransferUtils.transStringToDate(matcher3.group()));
            }
            String endDateReg = "(?<=至 ).*";
            Pattern compile4 = Pattern.compile(endDateReg);
            Matcher matcher4 = compile4.matcher(strings.get(5));
            if (matcher4.find()) {
                pdfAccount.setEndTime(TransferUtils.transStringToDate(matcher4.group()));
            }
        }
        PdfTransAccountDTO save = pdfTransAccountService.save(pdfAccount);
        Long id = save.getId();

        map.put("accountId", id);
        map.put("flag", flag);
        return map;
    }

    private static void setUp() {
        BouncyCastleProvider bcp = new BouncyCastleProvider();
        Security.insertProviderAt(bcp, 1);
    }

    /**
     * 验证ca签名
     *
     * @param resource         -inputStream文件流
     * @param originalFileName -文件名
     * @return
     */
    public OutCome validCaSign(InputStream resource, String originalFileName) {
        setUp();
        int verified = 0;
        try {

            PdfReader reader = new PdfReader(resource);
            AcroFields acroFields = reader.getAcroFields();
            List<String> names = acroFields.getSignatureNames();
            if (names.isEmpty()) {
                log.info("该{}PDF没有签名", originalFileName);
                return OutCome.failure(originalFileName + "该PDF文件没有签名");
            }
            for (String name : names) {
                log.info("签名: " + name);
                log.info("签字是否覆盖整份文件: " + acroFields.signatureCoversWholeDocument(name));
                PdfPKCS7 pk = acroFields.verifySignature(name);
                CertificateInfo.X500Name subjectFields = CertificateInfo.getSubjectFields(pk.getSigningCertificate());
                Map<String, ArrayList<String>> fields = subjectFields.getFields();
                String out = fields.get("OU").get(0);
                if (out.equals(PdfTypeEnum.ALI_INTERFACE.getOut())) {
                    String organization = fields.get("C").get(0);
                    if (!organization.equals(PdfTypeEnum.ALI_INTERFACE.getOrganization())) {
                        return OutCome.failure("支付宝PDF认证机构有误");
                    }
                } else if (out.equals(PdfTypeEnum.WECHAT_INTERFACE.getOut())) {
                    String organization = fields.get("O").get(0);
                    if (!organization.equals(PdfTypeEnum.WECHAT_INTERFACE.getOrganization())) {
                        return OutCome.failure("微信PDF认证机构有误");
                    }
                }
                log.info("Subject: " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()));
                log.info("文档验证: " + pk.verify());
                if (pk.verify()) {
                    verified++;
                }
            }
            return Objects.equals(names.size(), verified) ? OutCome.success("文档验证为真") : OutCome.failure("文档验证失败");

        } catch (Exception e) {
            e.printStackTrace();
            log.error("签名验证失败{}", e.getMessage());
            return OutCome.failure("签名验证失败:" + e.getMessage());
        }
    }
}