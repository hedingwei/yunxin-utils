package com.yunxin.utils.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IMEIGen {

    /**
     * @param args
     */
    public static void main(String[] args) {

        String imei = getRandonIMEI();

    }


    public  static  String getRandonIMEI(){
        long start = 35254112521400l;
        long end = 35255112521400l;
        Random random = new Random();
        Long l = random.longs(start,end).boxed().findAny().get();
        String code = ""+l;
        String endCode = ""+l;
        List<String> ls = beachIMEI(code,endCode);
        return ls.get(0);
    }

    /**
     * 批量生成IMEI
     * @param begin
     * @param end
     * @return
     */
    static List<String> beachIMEI(String begin,String end){
        List<String> imeis = new ArrayList<String>();
        try {
            long count = Long.parseLong(end) - Long.parseLong(begin);
            Long currentCode = Long.parseLong(begin);
            String code ;
            for (int i = 0; i <= count; i++) {
                code = currentCode.toString();
                code =code+ genCode(code);
                imeis .add(code);
//                Logger.info("code====="+code);
                currentCode += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imeis;
    }

    /**
     * IMEI 校验码
     * @param code
     * @return
     */
    public static String genCode(String code){
        int total=0,sum1=0,sum2 =0;
        int temp=0;
        char [] chs = code.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            int num = chs[i] - '0'; 	// ascii to num
            //Logger.info(num);
            /*(1)将奇数位数字相加(从1开始计数)*/
            if (i%2==0) {
                sum1 = sum1 + num;
            }else{
                /*(2)将偶数位数字分别乘以2,分别计算个位数和十位数之和(从1开始计数)*/
                temp=num * 2 ;
                if (temp < 10) {
                    sum2=sum2+temp;
                }else{
                    sum2 = sum2 + temp + 1 -10;
                }
            }
        }
        total = sum1+sum2;
        /*如果得出的数个位是0则校验位为0,否则为10减去个位数 */
        if (total % 10 ==0) {
            return "0";
        }else{
            return (10 - (total %10))+"";
        }

    }

    /**
     * IMEI 校验码
     * @param code
     * @return
     */
    public static String genCodeLong(String code){
        int total=0,sum1=0,sum2 =0;
        int temp=0;
        char [] chs = code.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            int num = chs[i] - '0'; 	// ascii to num
            //Logger.info(num);
            /*(1)将奇数位数字相加(从1开始计数)*/
            if (i%2==0) {
                sum1 = sum1 + num;
            }else{
                /*(2)将偶数位数字分别乘以2,分别计算个位数和十位数之和(从1开始计数)*/
                temp=num * 2 ;
                if (temp < 10) {
                    sum2=sum2+temp;
                }else{
                    sum2 = sum2 + temp + 1 -10;
                }
            }
        }
        total = sum1+sum2;
        /*如果得出的数个位是0则校验位为0,否则为10减去个位数 */
        if (total % 10 ==0) {
            return code+"0";
        }else{
            return code+((10 - (total %10)))+"";
        }

    }

}
