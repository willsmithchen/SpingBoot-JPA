package com.clj.demo.entity;

import com.alibaba.fastjson.JSONArray;
import com.clj.demo.model.UserDto;
import com.clj.demo.service.UserService;
import com.clj.demo.util.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.testng.collections.Maps;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author lujia chen
 * @Created 2020/12/23
 * @Description
 * @date 2020/12/23
 * @Version 1.0.version
 **/
//@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    public void test1() {
//        List<User> users = userService.findUsers();
        /*List<User> collect = users.stream().sorted(Comparator.comparing(User::getCreateTime)).collect(Collectors.toList());
        List<User> collect1 = users.stream().sorted(Comparator.comparing(User::getCreateTime).reversed()).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(collect1);*/
       /* User user = User.builder()
                .userType(1)
                .createTime("2021-01-08")
                .name("魏晨")
                .password("weichen")
                .delFlag(1).build();
        System.out.println(user);*/
      /*  //获取所有id集合
        List<Long> ids = users.stream().map(User::getId).collect(Collectors.toList());
        System.out.println(ids);
        //根据id排序
        List<User> collect = users.stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
        System.out.println(collect);
        //分组
        Map<String, Long> map = users.stream().collect(Collectors.groupingBy(User::getName, Collectors.counting()));
        System.out.println(map);*/


    }

    @Test
    public void test() {
        List<String> list = Lists.newArrayList();
        List<String> list1 = Lists.newArrayList();
        List<String> list2 = Lists.newArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        for (String s : list) {
            if ("2".equals(s)) {
                list1.add(s);
                continue;
            } else {
                list2.add(s);
            }
        }
        System.out.println(list1);
        System.out.println(list2);
    }

    @Test
    public void test2() {
        List<String> list = Lists.newArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("3");
        list.add("4");
        list.add("4");
        list.add("4");
        System.out.println(list.get(0));
        System.out.println(list.get(1));

      /*  remove(list);
        list.forEach(System.out::println);
        System.out.println("-----------");
        list.stream().distinct().forEach(System.out::println);*/
    }

    public void remove(List<String> list) {
//        list= list.stream().filter(e -> e.equals("2")).collect(Collectors.toList());
        for (String e : list) {
            if (e.equals("2")) {
                e.contains("2.1");
            }
        }
        System.out.println("remove" + list);
    }

    @Test
    public void test3() {
        System.out.println(AmountUtil.unitTransWang(-532.0));
        System.out.println(AmountUtil.unitTransWangByYuan(-42836.28));
        System.out.println(AmountUtil.unitTransWangByYuan(42836.28));
        System.out.println(AmountUtil.unitTransWangByYuanInteger(BigDecimal.valueOf(42836.28)));
        System.out.println(AmountUtil.unitTransWang(-532.0));
        System.out.println(AmountUtil.unitTransWang(-532.0));
        String value = "-532.0";
        System.out.println(value.substring(1));

    }

    @Test
    public void test4() throws CloneNotSupportedException {
        UserDto userDto = new UserDto();
        UserDto clone = (UserDto) userDto.clone();
        System.out.println(userDto == clone);
    }

    @Test
    public void test5() {
        List<String> userDtoList = new ArrayList<>();
        Map<String, List<String>> map = Maps.newHashMap();
        map.put("user", userDtoList);
        userDtoList.add("lisan");
        userDtoList.add("zhangsi");
        System.out.println(map);
    }

    @Test
    public void test6() {
        List<User> list = getList();
        List<User> list2 = getList2();
        for (User user : list) {
            list2.stream().forEach(e -> e.equals(user.getId()));

        }
        System.out.println(list);
    }

    public List<User> getList() {
        List<User> list1 = new ArrayList<>();
        User user = new User();
       /* user.setId(1L);
        user.setName("張三");
        list1.add(user);*/
        return list1;
    }

    public List<User> getList2() {
        List<User> list1 = new ArrayList<>();
        User user = new User();
        /*user.setId(1L);
        user.setPassword("123456");
        list1.add(user);*/
        return list1;
    }

    @Test
    public void test7() {
        Object str = 8.0;
        System.out.println(nullDefault(str));
        System.out.println(str.toString());
        System.out.println(String.valueOf(str));
        System.out.println(new BigDecimal(String.valueOf(str)));

    }

    public Object nullDefault(Object value) {
        if (Objects.isNull(value) || value.equals("")) {
            return null;
        } else {
            return value;
        }
    }

    @Test
    public void test8() {
//        String num="1.1923E7";
//        System.out.println(new BigDecimal(num).toPlainString());
//        System.out.println(new DecimalFormat("0.0").format(num));
        String idNo = "372925202010270717";
        System.out.println(IsAdult(idNo));
    }

    public static Boolean IsAdult(String idNo) {
        Integer userBirthYear = Integer.valueOf(idNo.substring(6, 10));
        Integer userBirthMonth = Integer.valueOf(idNo.substring(10, 12));
        Integer userBirthDay = Integer.valueOf(idNo.substring(12, 14));
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        //如果年份查小于18岁，直接返回false，未成年
        if ((year - userBirthYear) < 18) {
            return false;
        } else if ((year - userBirthYear) == 18) {//如果年份差等于18，比较月份
            if (month < userBirthMonth) {
                return false;
            } else if ((month == userBirthMonth)) {
                if (day < userBirthDay) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void test9() throws Exception {
        String jsonString = "{\n" +
                "\t\"id\":null,\n" +
                "\t\"serno\":\"1234565432\",\n" +
                "\t\"applyNo\":\"14543\",\n" +
                "\t\"custNo\":\"13432\",\n" +
                "\t\"prdCode\":\"12343\",\n" +
                "\t\"rate\":14.2,\n" +
                "\t\"agrNo\":\"13443\",\n" +
                "\t\"lmtAmt\":12.4,\n" +
                "\t\"usedAmt\":14.2,\n" +
                "\t\"vaildAmt\":\"15.2\",\n" +
                "\t\"startDAte\":null,\n" +
                "\t\"endDate\":null,\n" +
                "\t\"agrSts\":\"01\",\n" +
                "\t\"cycleFlag\":\"Y\",\n" +
                "\t\"applyDate\":null,\n" +
                "\t\"newLprDate\":null,\n" +
                "\t\"newLprRate\":15.1\n" +
                "}";

        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANDFcWOaPCW4MnXtLUixLB5lZYFKy4J3c9nJXlu5N2rOJTwJxbdeXvpXt10dypJqCfberQEf3g3bgyc+z2wRbW+ofd00LiUj7vO7sJRTnhvYX/W+WJgyGPvav3iFYig4z/rSfoWvJFFTsqyUj18zeBzEG4UqeIs4Rnvg6TUez8InAgMBAAECgYApmM2I7PhDA7p6KLG3JCYIQGfDJSX7btWUdTzpxbO3AjAFEGVmrdT6yHNuiDsOhTZLC/8olDLoS+6L6UXm4GoPUzltaAxkWKpwoD0PI1pwv+XGnf0jRPsarsKoQXOua62NGyB82WT0hO3QhhJ23o/sP/IUjGGdWr2/fPq5qz0cQQJBAO0LBvvhKYzIaq5ZkeHjivvb54+InJPuojijGnn1USU5vsR17xAzbCCk58Hf9TaT8aSg2J+sJLHy03/5+T4UotcCQQDhd5umpAVBJfYKrgcuN8ywUPSBUHzPuBmHL8uiMAdHG+KQpiIhl2um1VtZ5mgf9+oJl/4/AMAmrDpXjyhQHEExAkAPP75bRcjefaYfQTPZTEIMocDrwuYPvjJBYny5i8aElopnKQ8QA77Y+sLN/hJQSIQrCw3kd7aEnBJvES2viLRRAkAWxXi/9NLfCJF39Kme/l269QIsyU9Bv2s8nxQm9kjV4rgYOd86m0txFx1Z0A52JBjMDUDNZisB/OULkNbWeHQRAkEA3bprQcdffODVpZaSnd+fFDoftB1g7XIunYWAIqRBSPM779pb3QDh2IKdezfrmW/tePRYQGraQRhe2pRRys+pCw==";
        String publicKey2 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQxXFjmjwluDJ17S1IsSweZWWBSsuCd3PZyV5buTdqziU8CcW3Xl76V7ddHcqSagn23q0BH94N24MnPs9sEW1vqH3dNC4lI+7zu7CUU54b2F/1vliYMhj72r94hWIoOM/60n6FryRRU7KslI9fM3gcxBuFKniLOEZ74Ok1Hs/CJwIDAQAB";
        String encrypt = AesEncryptUtil.encrypt("SCgdGQcUHGItg6vF", publicKey2);
        System.out.println("encrypt:" + encrypt);
        String data = AesEncryptUtil.encrypt(jsonString, "SCgdGQcUHGItg6vF", "7763164515579839");
        System.out.println("encryGroup:" + data);
        //解密
        String decode = AesEncryptUtil.decode(encrypt, privateKey);
        String s = AesEncryptUtil.desEncrypt(data, decode, "7763164515579839");
        System.out.println("解密后：" + s);
      /*  String s = AesUtil.publicKeyEncrypt(jsonString, publicKey2, "7763164515579839");
        System.out.println("加密后" + s);
        String s1 = AesUtil.privateKeyDecrypt(s, privateKey, "7763164515579839");
        System.out.println("解密后" + s1);*/
    }

    @Test
    public void test10() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dateFormat.parse("2022-04-21").toInstant());
    }

    @Test
    public void test11() {
        String str = "u=2831121865,3403163971&fm=26&gp=0.jpg";
        String[] split = str.split("\\.");
        System.out.println(split);

    }

    @Test
    public void test12() {
        String str = "nihao";
        List<String> collect = Stream.of(str).collect(Collectors.toList());
        collect.add("lili");
        collect.parallelStream().forEach(param -> {
            System.out.println(param);
        });

    }

    @Test
    public void test13() {
        /*String phone = "15301710193";
        System.out.println(mobilePhone(phone));
        System.out.println("=====>"+mobilePhone_(phone));*/
        String idNum = "410825199711256036";
        String idNum1 = "130503 670401 001";
        System.out.println(idCardNum_(idNum));
        System.out.println(idCardNum_(idNum1));
    }

    public static String mobilePhone_(final String num) {
        if (StringUtils.isBlank(num) || num.length() < 8) {
            return num;
        }
        return StringUtils.left(num, 3).concat(StringUtils
                .removeStart(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*"),
                        "***"));
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     */
    public static String mobilePhone(final String num) {
        if (StringUtils.isBlank(num) || num.length() < 8) {
            return num;
        }
        String right = StringUtils.right(num, 4);
        int length = StringUtils.length(num);
        String leftPad = StringUtils.leftPad(right, length, "*");
        String left = StringUtils.left(num, 3);
        String s = StringUtils.removeStart(leftPad, "***");
        String result = left.concat(s);
        System.out.println(result);
        return StringUtils.left(num, 5).concat(StringUtils
                .removeStart(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*"),
                        "*****"));

    }

    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：*************5762>
     */
    public static String idCardNum_(final String id) {
        if (StringUtils.isBlank(id) || id.length() < 11) {
            return id;
        }
        String idNum = id.replaceAll(" ", "");
        int middle = idNum.length() / 2;

        if (idNum.length() == 15) {
            return idNum.substring(0, middle - 1) + "******" + idNum.substring(middle + 5);
        }
        return idNum.substring(0, middle - 3) + "********" + idNum.substring(middle + 5);
    }

    public static String idCardNum(final String id) {
        if (StringUtils.isBlank(id) || id.length() < 11) {
            return id;
        }

        return StringUtils.rightPad(StringUtils.left(id, id.length() - 4), id.length(), "*");
    }

    @Test
    public void test14() {
        BigDecimal transAmt = new BigDecimal("0");
        BigDecimal transAmt2 = new BigDecimal("1.2");
        BigDecimal transAmt3 = new BigDecimal("-2.2");
//        BigDecimal transAmt4 = new BigDecimal("1000");
        List<Object> list = new ArrayList<>();
        list.add(transAmt);
        list.add(transAmt2);
        list.add(transAmt3);
//        list.add(transAmt4);
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        for (Object o : list) {
            BigDecimal ret = (BigDecimal) o;
            /*if (ret.compareTo(BigDecimal.ZERO) == 1) {
                atomicBoolean.set(true);
            }*/
            atomicBoolean.set(ret.compareTo(BigDecimal.ZERO) == 1 || atomicBoolean.get());
        }
        System.out.println("最终结果：" + atomicBoolean);
    }

    @Test
    public void test15() throws ParseException {
        BigDecimal transAmt = new BigDecimal("0");
        int i = transAmt.compareTo(BigDecimal.ZERO);
        if (i == 1) {
            System.out.println("对的");
        } else {
            System.out.println("错的");
        }

        System.out.println(TransferUtils.transStringFormat("2016-02-17 18:36:28", "yyyy-MM"));
    }

    public static Object camelCase(String str) {
        String camelCase = "";
        String[] arr = str.split("_");
        List<String> list = new ArrayList<>();
        //将数组中非空字符串添加至list
        for (String a : arr) {
            if (a.length() > 0) {
                list.add(a);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                char c = list.get(i).charAt(0);
                String s = String.valueOf(c).toUpperCase() + list.get(i).substring(1);
                camelCase += s;
            } else {
                camelCase += list.get(i);
            }
        }
        return JSONArray.parse(camelCase);
    }

    /**
     * 将key进行驼峰的处理
     *
     * @param key
     * @return
     */
    public static String camelToUnderline(String key) {
        String[] arr = key.split("_");
        String newKey = "";
        List<String> list = new ArrayList<>();
        //将数组中非空字符串添加至list
        for (String a : arr) {
            if (a.length() > 0) {
                list.add(a);
            }
        }
        return getNewKey(newKey, list);
    }

    /**
     * 获取到对应的新值的key信息
     *
     * @param newKey 新的key值
     * @param list   截取的字符信息
     * @return
     */
    private static String getNewKey(String newKey, List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                char c = list.get(i).charAt(0);
                String s = String.valueOf(c).toUpperCase().concat(list.get(i).substring(1).toLowerCase());
                newKey += s;
            } else {
                newKey += list.get(i).toLowerCase();
            }
        }
        return newKey;
    }

    @Test
    public void test16() {
//        Object bus_change_name = camelCase("bus_change_name");
        /*String bus_change_name = camelToUnderline("$.bus_change_name".substring(2));
        System.out.println(bus_change_name);*/
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        List<String> list4 = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("a");
        list.add("a1");
        list.add("a22");
        list.add("b1a");
        List<String> a = list.stream().filter(e -> e.contains("a")).collect(Collectors.toList());
        System.out.println(a);
    /* for (int i = 0; i < list.size(); i++) {
            list3.add(list.get(i));
            list4.add(list2.get(i));
        }
        System.out.println(list3);
        System.out.println(list4);*/
      /*  System.out.println(list);
        List<String> list1 = new ArrayList<>();
        list.stream().forEach(v -> list1.add(Objects.toString(v, "")));
        System.out.println(list1);*/
    }

    @Test
    public void test17() {
        Integer num = 12;
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        List<Integer> collect = list.stream().filter(e -> num > e).collect(Collectors.toList());
        System.out.println(collect);
       /* List<Object> objectList = new ArrayList<>(list.size());
        list.forEach(v -> {
            objectList.add(Objects.toString(v, ""));
        });
        System.out.println(objectList);*/
    }

    @Test
    public void test18() {
        BigDecimal bigDecimal = new BigDecimal(50000);
        System.out.println(AmountUtil.toDecimal(bigDecimal));
        System.out.println(bigDecimal.compareTo(BigDecimal.ZERO));
    }

    @Test
    public void test19() throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String json = "{\"productCode\":\"09002\",\"appId\":\"9002000000\",\"timestamp\":1619519426419,\"reportReqNo\":\"PR536543903155912704\"}";
        String s = ZzSecurityHelper.encryptAES(json, "TBiIiTF5RUkL9fBW");
        System.out.println(s);
        String s1 = DigestUtils.md5Hex(s + "7444a7a1-0eb7-44af-a608-df8fbb9fb2f1");
        String s2 = DigestUtils.md5Hex(s + "7444a7a1-0eb7-44af-a608-df8fbb9fb2f1");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s1.equals(s2));
        /*Map<String, String> map = Maps.newHashMap();
        map.put("Content-Type", "application/json");
        map.put("appId", "9002000000");
        map.put("signature", s1);*/
        /*HttpResult post = HttpUtil.post("http://localhost:9781/api/open/gamt/get", s, map);
        String result = ZzSecurityHelper.decryptAES(post.getResult(), "TBiIiTF5RUkL9fBW");*/
//        System.out.println(result);
    }

    @Test
    public void test20() throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, ParseException {

      /*  System.out.println(ProcessStatusEnum.END_STATUS.contains(ProcessStatusEnum.valueOf("END")));
        System.out.println(ProcessStatusEnum.isContainsEnum.contains("程旭"));
        System.out.println(ProcessStatusEnum.isContainsEnum.contains("END"));*/
        String endTime = "2021-05-30 10:59:00";
        String startTime = "2021-01-01 10:59:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse(startTime);
        Date date2 = dateFormat.parse(endTime);
        System.out.println(date.toInstant());
        List<Instant> timeList = new ArrayList<>();
        Instant instant1 = Instant.parse("2018-12-30T09:24:54.63Z");
        Instant instant6 = Instant.parse("2018-12-30T09:24:54.63Z");
        Instant instant7 = Instant.parse("2018-12-30T09:24:54.63Z");
        Instant instant8 = Instant.parse("2018-12-30T09:24:54.63Z");
        Instant instant9 = Instant.parse("2018-12-30T09:24:54.63Z");
        Instant instant10 = Instant.parse("2018-12-30T09:24:54.63Z");
        Instant instant2 = Instant.parse("2021-02-26T01:34:00.63Z");
        Instant instant3 = Instant.parse("2021-03-21T01:34:00.63Z");
        Instant instant4 = Instant.parse("2021-04-22T01:34:00.63Z");
        Instant instant5 = Instant.parse("2021-05-31T01:34:00.63Z");
        timeList.add(instant1);
        timeList.add(instant2);
        timeList.add(instant3);
        timeList.add(instant4);
        timeList.add(instant5);
        timeList.add(instant6);
        timeList.add(instant7);
        timeList.add(instant8);
        timeList.add(instant9);
        timeList.add(instant10);
        List<Instant> collect = timeList.stream().filter(e -> e.equals(null) || e.isAfter(date.toInstant()) && e.isBefore(date2.toInstant())).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test21() {
        /*List<ProcessStatusEnum> failed = ProcessStatusEnum.transProcessStatusEnums(ProcessStatusEnum.valueOf("FAILED"));
        List<String> collect = failed.stream().map(e -> e.name()).collect(Collectors.toList());*/
        /*System.out.println(failed);
        System.out.println(collect);*/
        /*String strip = StringUtils.strip(collect.toString(), "[]");
        System.out.println(strip);*/
        /*System.out.println(StringUtils.strip(collect.toString(),"[]").replaceAll(" ",""));
        System.out.println(collect.toString());
        System.out.println(collect.toArray().toString());
        System.out.println(ProcessStatusEnum.transProcessStatusEnums(ProcessStatusEnum.valueOf("FAILED")).toArray().toString());*/
        String fixedLengthString = getFixedLengthString("");

    }

    public static String getFixedLengthString(String str) {
        if (str.length() >= 512) {
            return str.substring(0, 512);
        }
        return null;
    }

    @Test
    public void testTrans() {
        int num = 0xB0;
        int num2 = 0xC0;
        int num3 = 0xD0;
        int num4 = 0x100;
        int num5 = 0xe0;
        int num6 = 0xf1;
        System.out.println(num >> 4);
        System.out.println(num2 >> 4);
        System.out.println(num3 >> 4);
        System.out.println(num4 >> 4);
        System.out.println(num5 >> 4);
        System.out.println(num6>>4);
       /* System.out.println(num4>>4);
        System.out.println(num5>>4);
        System.out.println(num6>>4);*/
//        System.out.println(num4>>4);
   /*     int num7 = 7;
        System.out.println(num7 << 4);*/
    }
    @Test
    public void test22(){
        /*System.out.println(new BigDecimal("120000.00"));
        System.out.println(new BigDecimal(120000.87));*/
       /* System.out.println(LocalDate.now());
        System.out.println(LocalDate.now().minusDays(6));
        System.out.println(LocalDate.now().minusMonths(6));*/
        String jsonString="{\"reqNo\":\"Q615973222944505857\",\"stepReqNo\":null,\"reportReqNo\":\"PR615973223007420416\",\"batchNo\":\"G615973222814482432\",\"oriProductCode\":null,\"productCode\":\"03002\",\"appId\":\"0000000000\",\"riskSubject\":{\"priority\":-1.0,\"passthroughMsg\":null,\"phantomRelation\":false,\"accounts\":[],\"totalSalesLastYear\":null,\"industry\":null,\"industryName\":null,\"seasonable\":null,\"seasonOffMonth\":null,\"seasonOnMonth\":null,\"regCapAmt\":null,\"comStrDate\":null,\"education\":null,\"manageAddress\":null,\"mamageAmt\":null,\"certType\":\"ID_CARD_NO\",\"code\":\"32042119751231811X\",\"phone\":\"15951222788.0\",\"name\":\"钟双虎\",\"marryState\":\"MARRIED\",\"applyAmo\":null,\"riskSubjectType\":\"PERSONAL\",\"middleSignCode\":null},\"relations\":[{\"priority\":-1.0,\"passthroughMsg\":null,\"relationTypeSort\":null,\"reportRelationId\":null,\"query\":false,\"phantomRelation\":false,\"accounts\":[],\"totalSalesLastYear\":null,\"industry\":null,\"industryName\":null,\"seasonable\":null,\"seasonOffMonth\":null,\"seasonOnMonth\":null,\"regCapAmt\":null,\"comStrDate\":null,\"education\":null,\"manageAddress\":null,\"mamageAmt\":null,\"relationType\":\"SPOUSE\",\"certType\":\"ID_CARD_NO\",\"code\":\"37291765447898675\",\"name\":\"谷文文\",\"riskSubjectType\":\"PERSONAL\",\"fundedRatio\":null,\"phone\":\"\",\"marryState\":null,\"applyAmo\":null,\"relations\":null},{\"priority\":-1.0,\"passthroughMsg\":null,\"relationTypeSort\":null,\"reportRelationId\":null,\"query\":false,\"phantomRelation\":false,\"accounts\":[],\"totalSalesLastYear\":null,\"industry\":null,\"industryName\":null,\"seasonable\":null,\"seasonOffMonth\":null,\"seasonOnMonth\":null,\"regCapAmt\":null,\"comStrDate\":null,\"education\":null,\"manageAddress\":null,\"mamageAmt\":null,\"relationType\":\"CONTROLLER\",\"certType\":\"CREDIT_CODE\",\"code\":\"sfg\",\"name\":\"iii\",\"riskSubjectType\":\"COMPANY\",\"fundedRatio\":null,\"phone\":\"18321299971\",\"marryState\":null,\"applyAmo\":null,\"relations\":null},{\"priority\":-1.0,\"passthroughMsg\":null,\"relationTypeSort\":null,\"reportRelationId\":null,\"query\":false,\"phantomRelation\":false,\"accounts\":[],\"totalSalesLastYear\":null,\"industry\":null,\"industryName\":null,\"seasonable\":null,\"seasonOffMonth\":null,\"seasonOnMonth\":null,\"regCapAmt\":null,\"comStrDate\":null,\"education\":null,\"manageAddress\":null,\"mamageAmt\":null,\"relationType\":\"CONTROLLER\",\"certType\":\"CREDIT_CODE\",\"code\":\"37291765447898675\",\"name\":\"谷文文\",\"riskSubjectType\":\"COMPANY\",\"fundedRatio\":null,\"phone\":\"18321299971\",\"marryState\":null,\"applyAmo\":null,\"relations\":null},{\"priority\":-1.0,\"passthroughMsg\":null,\"relationTypeSort\":null,\"reportRelationId\":null,\"query\":false,\"phantomRelation\":false,\"accounts\":[],\"totalSalesLastYear\":null,\"industry\":null,\"industryName\":null,\"seasonable\":null,\"seasonOffMonth\":null,\"seasonOnMonth\":null,\"regCapAmt\":null,\"comStrDate\":null,\"education\":null,\"manageAddress\":null,\"mamageAmt\":null,\"relationType\":\"CONTROLLER\",\"certType\":\"CREDIT_CODE\",\"code\":\"37291765447898675\",\"name\":\"谷文文\",\"riskSubjectType\":\"COMPANY\",\"fundedRatio\":null,\"phone\":\"18321299971\",\"marryState\":null,\"applyAmo\":null,\"relations\":null},{\"priority\":-1.0,\"passthroughMsg\":null,\"relationTypeSort\":null,\"reportRelationId\":null,\"query\":false,\"phantomRelation\":false,\"accounts\":[],\"totalSalesLastYear\":null,\"industry\":null,\"industryName\":null,\"seasonable\":null,\"seasonOffMonth\":null,\"seasonOnMonth\":null,\"regCapAmt\":null,\"comStrDate\":null,\"education\":null,\"manageAddress\":null,\"mamageAmt\":null,\"relationType\":\"CONTROLLER\",\"certType\":\"CREDIT_CODE\",\"code\":\"37291765447898675\",\"name\":\"谷文文\",\"riskSubjectType\":\"COMPANY\",\"fundedRatio\":null,\"phone\":\"18321299971\",\"marryState\":null,\"applyAmo\":null,\"relations\":null}],\"timestamp\":1627025926210,\"outApplyNo\":null,\"preReportReqNo\":null,\"priority\":10,\"industry\":null}";
        String value = JSONUtil.getValue(jsonString, "reqNo");
        String value1 = JSONUtil.getValue(jsonString, "name");
        System.out.println(value);
        System.out.println(value1);
    }
    @Test
    public void test23(){
        String str="errorstatus 500 reading PipesFeignClient#strategy(String,Strategy); content:\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Internal Server Error</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <h1><p>Internal Server Error</p></h1>\n" +
                "    \n" +
                "  </body>\n" +
                "</html>\n";
        for (String s : Arrays.asList("error", "POST", "GET", "500")) {
            if(str.contains(s)){
                System.out.println("成功");
                return;
            }
        }
    }
}
