package com.fpi.mjf.demo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 皮尔逊相关系数工具类
 */
public class PearsonCorrUtil {

    /**
     * 禁止被初始化
     */
    private PearsonCorrUtil() {}

    private static final PearsonCorr getInitCorr() {
        return new PearsonCorr();
    }

    /**
     * 使用两个数组计算相关系数，如果数组长度不同，将返回0
     * 
     * @param x
     * @param y
     * @return
     */
    public static final PearsonCorr getCorr(double[] x, double[] y) {
        if (x == null || y == null || x.length == 0 || y.length == 0) {
            return getInitCorr();
        }
        int length = x.length > y.length ? y.length : x.length;
        double[] z;
        if (x.length > length) {
            z = new double[length];
            for (int i = 0; i < length; i++) {
                z[i] = x[i];
            }
            return corr(z, y);
        }
        if (y.length > length) {
            z = new double[length];
            for (int i = 0; i < length; i++) {
                z[i] = y[i];
            }
            return corr(x, z);
        }
        return corr(x, y);
    }

    public static final PearsonCorr getCorr(List<Double> x, List<Double> y) {
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

    private static final PearsonCorr corr(double[] x, double[] y) {
        PearsonCorr corr = getInitCorr();
        corr.x = x;
        corr.y = y;
        corr.xMean = getMean(x);
        corr.yMean = getMean(y);
        calcDenominator(corr);
        calcNumerator(corr);
        calcCoefficient(corr);
        return corr;
    }
    
    private static final void calcCoefficient(PearsonCorr corr) {
        corr.coefficient = corr.numerator / corr.denominator;
    }
    
    /**
     * 计算分子
     * @param corr
     */
    private static final void calcNumerator(PearsonCorr corr) {
        double d = 0d;
        int length = corr.x.length;
        for(int i = 0; i < length; i ++) {
            d += ((corr.x[i] - corr.xMean) * (corr.y[i] - corr.yMean));
        }
        corr.numerator = d;
    }
    
    /**
     * 计算分母
     * @param corr
     */
    private static final void calcDenominator(PearsonCorr corr) {
        double x_ = 0d;
        double y_ = 0d;
        for(double d : corr.x) {
            x_ += ((d - corr.xMean) * (d - corr.xMean));
        }
        for(double d : corr.y) {
            y_ += ((d - corr.yMean) * (d - corr.yMean)); 
        }
        corr.denominator = Math.sqrt(x_) * Math.sqrt(y_);
    }
    
    /**
     * 求平均值
     * @param d
     * @return
     */
    private static final double getMean(double[] d) {
        double total = 0d;
        for(double dv : d) {
            total += dv;
        }
        return total / d.length;
    }

    /**
     * 皮尔逊相关系数
     */
    public static class PearsonCorr {

        double[] x;

        double[] y;

        /**
         * 均值x
         */
        Double xMean;

        /**
         * 均值y
         */
        Double yMean;

        /**
         * 分子
         */
        Double numerator;

        /**
         * 分母
         */
        Double denominator;

        /**
         * 相关系数
         */
        Double coefficient;

        public double[] getX() {
            return x;
        }

        public double[] getY() {
            return y;
        }

        public Double getxMean() {
            return xMean;
        }

        public Double getyMean() {
            return yMean;
        }

        public Double getCoefficient() {
            return coefficient;
        }
    }
}
