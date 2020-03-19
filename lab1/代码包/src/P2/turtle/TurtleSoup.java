/*
 * Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course
 * staff.
 */
package P2.turtle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle     the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        turtle.forward(sideLength);//四次前进以及旋转
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return (180 - 360.0 / sides);//根据内角和外角的关系，以及外角和等于360°得到答案
    }

    /**
     * Determine number of sides given the size of interior angles of a regular
     * polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see
     * java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        double sides = 360 / (180 - angle);
        int res = (int) sides;
        return (sides - res) >= 0.5 ? res + 1 : res;//注意int数据类型会舍去一部分数，要进行判断
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to
     * draw.
     * 
     * @param turtle     the turtle context
     * @param sides      number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        for (int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(180 - calculateRegularPolygonAngle(sides));//for循环即可，次数为边数，前近距离为边长，偏转角度为外角
        }
    }

    /**
     * Given the current direction, current location, and a target location,
     * calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in
     * the direction of
     * the target point (targetX,targetY), given that the turtle is already at the
     * point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be
     * expressed in
     * degrees, where 0 <= angle < 360.
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX       current location x-coordinate
     * @param currentY       current location y-coordinate
     * @param targetX        target point x-coordinate
     * @param targetY        target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY, int targetX,
            int targetY) {
        double angle = 0;
        if (targetX == currentX) {// 如果当前点和目标点连线垂直于x轴
            if (targetY > currentY) {
                angle = 0;
            } else {
                angle = 180;//包括两点重合的情况（这里是为凸包算法做铺垫）
            }
        } else {
            angle = Math.atan((double) (targetY - currentY) / (double) (targetX - currentX));
            angle = Math.toDegrees(angle);//将弧度转化为角度
            if (targetX < currentX) {
                angle -= 180;
            }
            if (angle <= 90) {
                angle = 90 - angle;
            } else {
                angle = 450 - angle;
            }
        }
        angle -= currentBearing;//得到偏转角度
        return angle >= 0 ? angle : 360 + angle;//偏转角度一定是在0到360度范围内
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get
     * from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0
     * degrees).
     * For each subsequent point, assumes that the turtle is still facing in the
     * direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of
     *         points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        List<Double> result = new ArrayList<>();
        double currentBearing = 0;//起始角度为0
        double angle = 0;
        for (int i = 0; i < xCoords.size() - 1; i++) {
            currentBearing = calculateBearingToPoint(angle, xCoords.get(i), yCoords.get(i), xCoords.get(i + 1),
                    yCoords.get(i + 1));//起始角度为上一次的角度
            result.add(currentBearing);
            angle = (angle + currentBearing) % 360;//起始角度始终在0到360度范围内
        }
        return result;
    }

    /**
     * Given a set of points, compute the convex hull, the smallest convex set that
     * contains all the points
     * in a set of input points. The gift-wrapping algorithm is one simple approach
     * to this problem, and
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty,
     *               contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the
     *         perimeter of the convex hull
     * @throws InterruptedException 
     */
    public static Set<Point> convexHull(Set<Point> points) throws InterruptedException {
        Set<Point> convexHull = new HashSet<Point>();
        List<Point> pointsSet = new ArrayList<>();
        convexHull.clear();
        if (points.size() == 0) {
            return convexHull;//得到集合中的元素
        }
        int count = 0;
        for (Point point : points) {
            pointsSet.add(count, point);
            count++;
        } // 寻找最左下方的点
        double xleft = pointsSet.get(0).x();// 用于记录最小的x
        int index = 0;// 用于记录最左下角的点
        for (int i = 0; i < count; i++) {
            if ((int)pointsSet.get(i).x() < xleft) {// 判断x值的大小，找到较小的x及其所在的位置
                xleft = pointsSet.get(i).x();
                index = i;
                continue;
            }
            if ((int)pointsSet.get(i).x() == xleft) {// 若该点与我们选的x值相等，则判断y值大小
                if ((int)pointsSet.get(i).y() < pointsSet.get(i).y()) {
                    index = i;
                }
            }
        }
        convexHull.add(pointsSet.get(index));// 遍历寻找turn的角度最小的点集
        int indexfinal = index;// 用于记录选择的下一个点，同时用于while循环的比较出口
        double angle1 = 0, angle2 = 0;
        int k = 0;// 用于更新待选取的点
        Boolean[] flags = new Boolean[count];
        for (int i = 0; i < count; i++) {
            flags[i] = false;
        }
        flags[index] = true;
        do {//为了方便计算，所有的起始角度均设为0度
            for (int i = 0; i < count; i++) {// 寻找一个起始的计算点
                if (!flags[i] || i == index) {
                    angle1 = calculateBearingToPoint(0, (int) pointsSet.get(indexfinal).x(),
                            (int) pointsSet.get(indexfinal).y(), (int) pointsSet.get(i).x(), (int) pointsSet.get(i).y());
                    k = i;
                    break;
                }
            }
            for (int i = 0; i < count; i++) {
                if (!flags[i] || i == index) {//比较得到一个最小的偏转角所在的终点
                    angle2 = calculateBearingToPoint(0, (int) pointsSet.get(indexfinal).x(),
                            (int) pointsSet.get(indexfinal).y(), (int) pointsSet.get(i).x(), (int) pointsSet.get(i).y());
                    if (angle2 < angle1) {
                        angle1 = angle2;
                        k = i;
                        continue;
                    }
                    if (angle2 == angle1) {// 若有两个点偏转角度一样，则选择距离计算目前点较远的点
                        if (Math.abs(pointsSet.get(indexfinal).x() - pointsSet.get(i).x()) > Math
                                .abs(pointsSet.get(k).x() - pointsSet.get(indexfinal).x())) {
                            k = i;
                        }
                    }
                }
            }
            indexfinal = k;
            if (indexfinal != index) {//如果终点就是起点，则无需再次加入该点
                convexHull.add(pointsSet.get(indexfinal));
                System.out.println("加入点"+"("+pointsSet.get(indexfinal).x()+","+(pointsSet.get(indexfinal).y())+")");
            }
            flags[indexfinal] = true;
        } while (index != indexfinal);
        return convexHull;
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a
     * turtle. For this
     * function, draw something interesting; the complexity can be as little or as
     * much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        for (int i = 0; i < 200; i++) {//简单的for循环
            turtle.forward(2 * i);//前进量是变量就可以画出好看的图形
            turtle.turn(110);//偏转角要合适
            switch (i % 10) {//可以通过switch语句改变图形颜色
            case 0:
                turtle.color(PenColor.BLACK);
                break;
            case 1:
                turtle.color(PenColor.GRAY);
                break;
            case 2:
                turtle.color(PenColor.RED);
                break;
            case 3:
                turtle.color(PenColor.PINK);
                break;
            case 4:
                turtle.color(PenColor.ORANGE);
                break;
            case 5:
                turtle.color(PenColor.YELLOW);
                break;
            case 6:
                turtle.color(PenColor.GREEN);
                break;
            case 7:
                turtle.color(PenColor.CYAN);
                break;
            case 8:
                turtle.color(PenColor.BLUE);
                break;
            case 9:
                turtle.color(PenColor.MAGENTA);
            }
        }

    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

         drawSquare(turtle, 40);
        // drawRegularPolygon(turtle, 100, 8);
        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();
        // Set<Point> points = new HashSet<Point>();
        // Point p11 = new Point(1, 1);
        // points.add(p11);
        // convexHull(points);
    }

}
