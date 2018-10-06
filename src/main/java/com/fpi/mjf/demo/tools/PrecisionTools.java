package com.fpi.mjf.demo.tools;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 精度计算
 * 
 * @author 李博
 *
 */
public class PrecisionTools {

	public final static Map<String, int[]> POLLUTANTMA = new HashMap<String, int[]>() {

		private static final long serialVersionUID = 1L;

		{
			// SO2
			put("101", new int[] { 0, 6 });
			// NO2
			put("141", new int[] { 0, 6 });
			// PM10
			put("107", new int[] { 0, 6 });
			// PM2.5
			put("121", new int[] { 0, 6 });
			// CO
			put("106", new int[] { 1, 6 });
			// O3
			put("108", new int[] { 0, 6 });
			put("AQI", new int[] { 0, 0 });
			// 温度
			put("126", new int[] { 1, 6 });
			// 湿度
			put("128", new int[] { 2, 6 });
			// 大气压力
			put("127", new int[] { 0, 6 });
			// 主导风向
			put("130", new int[] { 1, 6 });
			// 风速
			put("129", new int[] { 2, 6 });
			// 综合指数
			put("CIEQ", new int[] { 2, 6 });
		}
	};

	public final static Map<String, int[]> POLLUTANTMANAME = new HashMap<String, int[]>() {
		private static final long serialVersionUID = 1L;

		{
			// SO2
			put("SO2", new int[] { 0, 6 });
			// NO2
			put("NO2", new int[] { 0, 6 });
			// PM10
			put("PM10", new int[] { 0, 6 });
			// PM2.5
			put("PM2.5", new int[] { 0, 6 });
			put("PM25", new int[] { 0, 6 });
			// CO
			put("CO", new int[] { 1, 6 });
			// O3
			put("O3", new int[] { 0, 6 });
			put("AQI", new int[] { 0, 0 });
			// 温度
			put("温度", new int[] { 1, 6 });
			// 湿度
			put("湿度", new int[] { 2, 6 });
			// 大气压力
			put("大气压力", new int[] { 0, 6 });
			// 主导风向
			put("主导风向", new int[] { 1, 6 });
			// 风速
			put("风速", new int[] { 2, 6 });
			// 综合指数
			put("综合指数", new int[] { 2, 6 });
		}
	};

	/**
	 * 四舍六入计算法 number 小数点后多少位 0代表个位 常量标记6
	 */
	public static Double[] trans(String pollutantCode, Double... values) {

		for (int i = 0; i < values.length; i++) {
			Double result = trans(pollutantCode, values[i]);
			values[i] = result;
		}
		return values;

	}

	public static Double trans(String pollutantCode, double values) {

		if (Double.isNaN(values)) {
			return null;
		}

		BigDecimal b = new BigDecimal(values);
		double result = b.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		return result;

	}

	public static Double trans(int number, double values) {

		if (Double.isNaN(values)) {
			return null;
		}

		BigDecimal b = new BigDecimal(values);
		double result = b.setScale(number, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		return result;

	}

	/**
	 * 向上取整 number 小数点后多少位 0代表个位 常量标记0
	 * 
	 */
	public static Double roundUp(int number, double values) {

		if (Double.isNaN(values)) {
			return null;
		}

		BigDecimal b = new BigDecimal(values);
		double result = b.setScale(number, BigDecimal.ROUND_UP).doubleValue();
		return result;
	}

	/**
	 * 向下取整 number 小数点后多少位 0代表个位 常量标记1
	 * 
	 */
	public static Double roundDown(int number, double values) {

		if (Double.isNaN(values)) {
			return null;
		}

		BigDecimal b = new BigDecimal(values);
		double result = b.setScale(number, BigDecimal.ROUND_DOWN).doubleValue();
		return result;

	}

	/**
	 * 四舍五入 number 小数点后多少位 0代表个位 常量标记4
	 * 
	 */
	public static Double roundHalfup(int number, double values) {

		if (Double.isNaN(values)) {
			return null;
		}

		BigDecimal b = new BigDecimal(values);
		double result = b.setScale(number, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}

	/**
	 * 通过因子编号处理精度
	 * 
	 * @param precisionCodeORName
	 * @param value
	 * @return
	 */
	public static Double pollutantValuePrecision(String precisionCodeORName, Double value) {

		if (value == null || Double.isNaN(value)) {
			return null;
		}

		if (value == 0) {
			return 0.0;
		}

		String upperCase = precisionCodeORName.toUpperCase();

		int[] code = POLLUTANTMA.get(upperCase);

		if (code == null || code.length == 0) {
			code = POLLUTANTMANAME.get(upperCase);
		}

		// 默认保留两位小数
		if (code == null || code.length == 0) {
			code = new int[] { 2, 4 };
		}

		BigDecimal b = new BigDecimal(value);
		double result = b.setScale(code[0], code[1]).doubleValue();
		return result;
	}

	/**
	 * 通过因子编号处理精度
	 * 
	 * @param precisionCodeORName
	 * @param value
	 * @return
	 */
	public static String pollutantValuePrecisionString(String precisionCodeORName, Double value) {

		if (value == null || Double.isNaN(value)) {
			return null;
		}

		if (value == 0) {
			return "0";
		}

		String upperCase = precisionCodeORName.toUpperCase();

		int[] code = POLLUTANTMA.get(upperCase);

		if (code == null || code.length == 0) {
			code = POLLUTANTMANAME.get(upperCase);
		}

		// 默认保留两位小数
		if (code == null || code.length == 0) {
			code = new int[] { 2, 4 };
		}

		BigDecimal b = new BigDecimal(value);
		Double result = b.setScale(code[0], code[1]).doubleValue();

		String valueStr = "";

		if (code[0] == 0) {
			valueStr = result.intValue() + "";
		} else {
			valueStr = result + "";
		}

		return valueStr;
	}

}
