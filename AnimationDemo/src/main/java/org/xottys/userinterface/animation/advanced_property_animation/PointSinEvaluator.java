/**
 * 自定义TypeEvaluator，定义动画的属性变化过程，返回值可以是integer、float或Object
 *
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:PhysicsMainActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.advanced_property_animation;

import android.animation.TypeEvaluator;

public class PointSinEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        //X坐标线性运动
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        //X坐标正弦曲线运动
        float y = (float) (Math.sin(x * Math.PI / 180) * 100) + endPoint.getY() / 2;

        Point point = new Point(x, y);
        return point;
    }
}
