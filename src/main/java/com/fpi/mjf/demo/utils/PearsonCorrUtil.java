package com.fpi.mjf.demo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 皮尔逊相关系数工具类
 */
public class PearsonCorrUtil {

    /**
     * 使用两个数组计算相关系数<br>
     * 如果数组长度不同，将舍弃较长数组的后端，保证两个数组等长<br>
     * 如果两个数组都是空数组或者空指针，返回0<br>
     */
    public static final Double getCorr(double[] x, double[] y) {
        if (x == null || y == null || x.length == 0 || y.length == 0) {
            return 0d;
        }
        int length = x.length > y.length ? y.length : x.length;
        double[] z;
        // 如果x数组较长，只取和y等长的部分
        if (x.length > length) {
            z = new double[length];
            for (int i = 0; i < length; i++) {
                z[i] = x[i];
            }
            return corr(z, y);
        }
        // 如果x数组较长，只取和y等长的部分
        if (y.length > length) {
            z = new double[length];
            for (int i = 0; i < length; i++) {
                z[i] = y[i];
            }
            return corr(x, z);
        }
        return corr(x, y);
    }

    /**
     * 计算相关系数外部接口，使用两组列表计算<br>
     * 将会丢弃掉较长的列表后面的数据，保证两组数据等长<br>
     * 将会丢弃掉相同下标的有空值的数据：<br>
     * if(x.get(i)==null){<br>
     * x.delete(i);<br>
     * y.delete(i);<br>
     * }
     */
    public static Double getCorr(List<Double> x, List<Double> y) {
        int length = x.size() > y.size() ? y.size() : x.size();
        List<Double> x_ = new ArrayList<Double>();
        List<Double> y_ = new ArrayList<Double>();
        for (int i = 0; i < length; i++) {
            Double xVal = x.get(i);
            Double yVal = y.get(i);
            if (xVal != null && yVal != null) {
                x_.add(xVal);
                y_.add(yVal);
            }
        }
        return getCorr(list2Array(x_), list2Array(y_));
    }

    /**
     * List转数组，对象拆包，约束条件为无null值
     */
    private final static double[] list2Array(List<Double> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).floatValue();
        }
        return array;
    }

    /**
     * 计算相关系数内部入口
     */
    private static Double corr(double[] x, double[] y) {
        double xMean = getMean(x);
        double yMean = getMean(y);
        // double numerator = calcNumerator(x, y, xMean, yMean);
        // double denominator = calcDenominator(x, y, xMean, yMean);
        // double coefficient = calcCoefficient(denominator, numerator);
        // return coefficient;
        return calcCoefficient(calcNumerator(x, y, xMean, yMean), calcDenominator(x, y, xMean, yMean));
    }

    /**
     * 计算相关系数
     */
    private static Double calcCoefficient(double numerator, double denominator) {
        if (denominator == 0) {
            return 0d;
        }
        return numerator / denominator;
    }

    /**
     * 计算分子E((x - xMean) * (y - yMean))
     */
    private static Double calcNumerator(double[] x, double[] y, double xMean, double yMean) {
        double d = 0d;
        int length = x.length;
        for (int i = 0; i < length; i++) {
            d += ((x[i] - xMean) * (y[i] - yMean));
        }
        return d;
    }

    /**
     * 计算分母 |E(x - xMean) * E(y - yMean)|
     */
    private static Double calcDenominator(double[] x, double[] y, double xMean, double yMean) {
        double x_ = 0d;
        double y_ = 0d;
        for (double d : x) {
            x_ += ((d - xMean) * (d - xMean));
        }
        for (double d : y) {
            y_ += ((d - yMean) * (d - yMean));
        }
        return Math.sqrt(x_) * Math.sqrt(y_);
    }

    /**
     * 求平均值
     * 
     * @param d
     * @return
     */
    private static final Double getMean(double[] d) {
        Double total = 0d;
        for (double dv : d) {
            total += dv;
        }
        return total / d.length;
    }
}
