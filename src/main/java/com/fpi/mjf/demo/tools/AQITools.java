package com.fpi.mjf.demo.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AQITools {
    private static final int[] IAQI = new int[] {0, 50, 100, 150, 200, 300, 400, 500};
    private static Map<String, int[]> HOUR_POLLUTANT_VALUES_MAP = new HashMap<String, int[]>();
    private static Map<String, int[]> DAY_POLLUTANT_VALUES_MAP = new HashMap<String, int[]>();

    static {
        HOUR_POLLUTANT_VALUES_MAP.put("101", new int[] {0, 150, 500, 650, 800});
        HOUR_POLLUTANT_VALUES_MAP.put("141", new int[] {0, 100, 200, 700, 1200, 2340, 3090, 3840});
        HOUR_POLLUTANT_VALUES_MAP.put("106", new int[] {0, 5, 10, 35, 60, 90, 120, 150});
        HOUR_POLLUTANT_VALUES_MAP.put("108", new int[] {0, 160, 200, 300, 400, 800, 1000, 1200});
        HOUR_POLLUTANT_VALUES_MAP.put("107", new int[] {0, 50, 150, 250, 350, 420, 500, 600});
        HOUR_POLLUTANT_VALUES_MAP.put("121", new int[] {0, 35, 75, 115, 150, 250, 350, 500});

        DAY_POLLUTANT_VALUES_MAP.put("101", new int[] {0, 50, 150, 475, 800, 1600, 2100, 2620});
        DAY_POLLUTANT_VALUES_MAP.put("141", new int[] {0, 40, 80, 180, 280, 565, 750, 940});
        DAY_POLLUTANT_VALUES_MAP.put("106", new int[] {0, 2, 4, 14, 24, 36, 48, 60});
        DAY_POLLUTANT_VALUES_MAP.put("108", new int[] {0, 100, 160, 215, 265, 800});
        DAY_POLLUTANT_VALUES_MAP.put("107", new int[] {0, 50, 150, 250, 350, 420, 500, 600});
        DAY_POLLUTANT_VALUES_MAP.put("121", new int[] {0, 35, 75, 115, 150, 250, 350, 500});
    }


    /**
     * 计算IAQI
     * 
     * @param pollutantName 因子名称 eg 101
     * @param pollutantValue 因子值 eg 22
     * @param hour 是否是计算小时 true计算小时,false计算天月年
     * @return null表示不参与计算
     */
    public static Integer calIaqi(String pollutantName, double pollutantValue, boolean hour) {
        Map<String, int[]> map = hour ? HOUR_POLLUTANT_VALUES_MAP : DAY_POLLUTANT_VALUES_MAP;

        int[] values = map.get(pollutantName);

        if (values == null) {
            return null;
        }

        if ("101".equals(pollutantName) && hour && pollutantValue > values[values.length - 1]) {
            return null;
        }
        if ("108".equals(pollutantName) && !hour && pollutantValue > values[values.length - 1]) {
            return null;
        }

        if (pollutantValue > values[values.length - 1]) {
            return 500;
        }

        int hiIndex = -1;
        int loIndex = -1;
        for (int i = 1; i < values.length; i++) {
            if (pollutantValue > values[i - 1] && pollutantValue <= values[i]) {
                hiIndex = i;
                loIndex = i - 1;
                break;
            }
        }
        if (hiIndex < 0 || loIndex < 0 || hiIndex >= IAQI.length || loIndex >= IAQI.length) {
            return null;
        }

        Calculator calculator = new Calculator(IAQI[hiIndex], IAQI[loIndex], values[hiIndex], values[loIndex], pollutantValue);

        return calculator.calculate();
    }

    /**
     * 计算AQI
     * 
     * @param iaqis 六因子的IAQI值数组 eg[12 , 23 , 34 , 20, 34 , 45]
     * @return
     */
    public static Integer getAQI(Integer[] iaqis) {
        Integer max = -1;
        for (Integer i : iaqis) {
            if (i != null && i > max) {
                max = i;
            }
        }
        return max;
    }

    /**
     * 计算AQI
     * 
     * @param pollutantNames 六因子名称数组eg["101" , "141" , "106", "108", "107", "121"]
     * @param pollutantValues 六因子值数组
     * @param hour 是否是计算小时 true计算小时,false计算天月年
     * @return
     */
    public static Integer getAQI(String[] pollutantNames, double[] pollutantValues, boolean hour) {
        Integer max = -1;
        for (int i = 0; i < pollutantValues.length; i++) {
            Integer iaqi = calIaqi(pollutantNames[i], pollutantValues[i], hour);
            if (iaqi != null && iaqi > max) {
                max = iaqi;
            }
        }
        return max;
    }

    /**
     * 计算首要污染物
     * 
     * @param iaqis 六因子的IAQI值数组 eg[12 , 23 , 34 , 20, 34 , 45]
     * @param pollutantNames 六因子名称数组eg["101" , "141" , "106", "108", "107", "121"]
     * @return
     */
    public static List<String> getMAINPOLLUTANTS(Integer[] iaqis, String[] pollutantNames) {
        List<String> mainPollutants = new ArrayList<String>();
        if (iaqis == null || iaqis.length == 0 || pollutantNames == null || pollutantNames.length == 0) {
            return mainPollutants;
        }
        Integer max = 0;
        for (int i = 0; i < iaqis.length; i++) {
            if (iaqis[i] != null && iaqis[i] >= max) {
                if (iaqis[i] > 50) {
                    if (iaqis[i] > max) {
                        mainPollutants.clear();
                        mainPollutants.add(pollutantNames[i]);
                    } else {
                        mainPollutants.add(pollutantNames[i]);
                    }
                }
                max = iaqis[i];
            }
        }
        if (mainPollutants.size() != 0) {
            Collections.sort(mainPollutants);
        }
        return mainPollutants;
    }

    /**
     * 计算首要污染物
     * 
     * @param pollutantNames 六因子名称数组eg["101" , "141" , "106", "108", "107", "121"]
     * @param pollutantValues 六因子值数组
     * @param hour 是否是计算小时 true计算小时,false计算天月年
     * @return
     */
    public static List<String> getMAINPOLLUTANTS(String[] pollutantNames, double[] pollutantValues, boolean hour) {
        List<String> mainPollutants = new ArrayList<String>();
        if (pollutantValues == null || pollutantValues.length == 0 || pollutantNames == null || pollutantNames.length == 0) {
            return mainPollutants;
        }
        Integer max = 0;
        for (int i = 0; i < pollutantValues.length; i++) {
            Integer iaqi = calIaqi(pollutantNames[i], pollutantValues[i], hour);
            if (iaqi != null && iaqi >= max) {
                if (iaqi > 50) {
                    if (iaqi > max) {
                        mainPollutants.clear();
                        mainPollutants.add(pollutantNames[i]);
                    } else {
                        mainPollutants.add(pollutantNames[i]);
                    }
                }
                max = iaqi;
            }
        }
        if (mainPollutants.size() != 0) {
            Collections.sort(mainPollutants);
        }
        return mainPollutants;
    }

    private static class Calculator {
        private int iaqiHi;
        private int iaqiLo;
        private int bpHi;
        private int bpLo;
        private double value;

        public Calculator(int iaqiHi, int iaqiLo, int bpHi, int bpLo, double value) {
            this.iaqiHi = iaqiHi;
            this.iaqiLo = iaqiLo;
            this.bpHi = bpHi;
            this.bpLo = bpLo;
            this.value = value;
        }

        public int calculate() {
            return PrecisionTools.roundUp(0, (1.0 * (iaqiHi - iaqiLo) / (bpHi - bpLo) * (value - bpLo) + iaqiLo)).intValue();
        }
    }

    /**
     * 获取AQI等级
     * 
     * @param calIaqiMAX
     * @return
     */
    public static Map<String, String> judgeAqilevel(Integer calIaqiMAX) {

        String aqLiv = null;

        String aqilevel = null;

        if (calIaqiMAX <= 0) {

        } else if (calIaqiMAX <= 50) {
            aqLiv = "优";
            aqilevel = "一级";
        } else if (calIaqiMAX <= 100) {
            aqLiv = "良";
            aqilevel = "二级";
        } else if (calIaqiMAX <= 150) {
            aqLiv = "轻度";
            aqilevel = "三级";
        } else if (calIaqiMAX <= 200) {
            aqLiv = "中度";
            aqilevel = "四级";
        } else if (calIaqiMAX <= 300) {
            aqLiv = "重度";
            aqilevel = "五级";
        } else if (calIaqiMAX > 300) {
            aqLiv = "严重";
            aqilevel = "六级";
        }
        Map<String, String> data = new HashMap<String, String>();
        data.put("aqLiv", aqLiv);
        data.put("aqilevel", aqilevel);
        return data;
    }
}
