/*
 *  Copyright 2023 Alexey Andreev.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.teavm.samples.pi;

import java.math.BigInteger;

/**
 * 计算π的类
 */
public final class PiCalculator {
    // 每行显示的数字个数
    private static final int L = 10;

    // 私有构造函数，防止实例化
    private PiCalculator() {
    }

    /**
     * 主方法，应用程序的入口点
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 获取程序开始时间
        var start = System.currentTimeMillis();
        // 从命令行参数中获取要计算的π的位数
        int n = Integer.parseInt(args[0]);
        int j = 0;

        // 创建PiDigitSpigot对象
        var digits = new PiDigitSpigot();

        // 循环直到显示完所有位数
        while (n > 0) {
            if (n >= L) {
                for (int i = 0; i < L; i++) {
                    System.out.print(digits.next());
                }
                j += L;
            } else {
                for (int i = 0; i < n; i++) {
                    System.out.print(digits.next());
                }
                for (int i = n; i < L; i++) {
                    System.out.print(" ");
                }
                j += n;
            }
            System.out.print("\t:");
            System.out.println(j);
            System.out.flush();
            n -= L;
        }

        // 打印程序运行时间
        System.out.println("Time in millis: " + (System.currentTimeMillis() - start));
    }
}

/**
 * 计算π的数字的类
 */
class PiDigitSpigot {
    private Transformation z;
    private Transformation x;
    private Transformation inverse;

    // 构造函数，初始化Transformation对象
    PiDigitSpigot() {
        z = new Transformation(1, 0, 0, 1);
        x = new Transformation(0, 0, 0, 0);
        inverse = new Transformation(0, 0, 0, 0);
    }

    /**
     * 获取下一个π的数字
     *
     * @return 下一个π的数字
     */
    int next() {
        int y = digit();
        if (isSafe(y)) {
            z = produce(y);
            return y;
        } else {
            z = consume(x.next());
            return next();
        }
    }

    // 获取Transformation对象的最后一位数字
    private int digit() {
        return z.extract(3);
    }

    // 判断当前数字是否为安全位
    private boolean isSafe(int digit) {
        return digit == z.extract(4);
    }

    // 根据当前数字生成新的Transformation对象
    private Transformation produce(int i) {
        return inverse.qrst(10, -10 * i, 0, 1).compose(z);
    }

    // 根据给定的Transformation对象进行计算
    private Transformation consume(Transformation a) {
        return z.compose(a);
    }
}

/**
 * Transformation类，表示一个矩阵变换
 */
class Transformation {
    private BigInteger q;
    private BigInteger r;
    private BigInteger s;
    private BigInteger t;
    private int k;

    // 构造函数，初始化Transformation对象
    Transformation(int q, int r, int s, int t) {
        this.q = BigInteger.valueOf(q);
        this.r = BigInteger.valueOf(r);
        this.s = BigInteger.valueOf(s);
        this.t = BigInteger.valueOf(t);
        k = 0;
    }

    // 私有构造函数，用于构建新的Transformation对象
    private Transformation(BigInteger q, BigInteger r, BigInteger s, BigInteger t) {
        this.q = q;
        this.r = r;
        this.s = s;
        this.t = t;
        k = 0;
    }

    // 获取下一个Transformation对象
    Transformation next() {
        k++;
        q = BigInteger.valueOf(k);
        r = BigInteger.valueOf(4 * k + 2);
        s = BigInteger.valueOf(0);
        t = BigInteger.valueOf(2 * k + 1);
        return this;
    }

    // 提取指定位数的数字
    int extract(int j) {
        var bigj = BigInteger.valueOf(j);
        var numerator = q.multiply(bigj).add(r);
        var denominator = s.multiply(bigj).add(t);
        return numerator.divide(denominator).intValue();
    }

    // 设置Transformation对象的值
    Transformation qrst(int q, int r, int s, int t) {
        this.q = BigInteger.valueOf(q);
        this.r = BigInteger.valueOf(r);
        this.s = BigInteger.valueOf(s);
        this.t = BigInteger.valueOf(t);
        k = 0;
        return this;
    }

    // 合并两个Transformation对象
    Transformation compose(Transformation a) {
        return new Transformation(
                q.multiply(a.q),
                q.multiply(a.r).add(r.multiply(a.t)),
                s.multiply(a.q).add(t.multiply(a.s)),
                s.multiply(a.r).add(t.multiply(a.t))
        );
    }
}
