package edu.is.util;

import edu.is.entity.Requirements;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.LinkedList;

@Log
public class LkUtil {
    public static Boolean isFit(double sum) {
        if (sum <= 3600 && sum >= 3580) {
            return true;
        }
        return false;
    }


    public static void printLinkedListReq(LinkedList<Requirements> linkedList) {
        linkedList.forEach(System.out::println);
    }


    public static double sumLinkDouble(LinkedList<Double> sum) {
        double s = 0;
        for (Double d : sum) {
            s += d;
        }
        return s;
    }

    public static void filterNum(LinkedList<Requirements> linkedList) {
        for (int i = 0; i < linkedList.size(); i++) {
            if (linkedList.get(i).getNum() == 0) {
                linkedList.remove(i);
            }
        }
    }

    //算法 种类数最多5
    public static ArrayList<double[]> pingDan(LinkedList<Requirements> linkedList) {
        log.info("开始拼单");
        LinkedList<Double> sum = new LinkedList<>();//宽度和
        ArrayList<double[]> result = new ArrayList<>();

        filterNum(linkedList);
        log.info("当未知数只有一个时");
        for (int x1 = 0; x1 < linkedList.size(); x1++) {
            if (isFit(linkedList.get(x1).getWide())&&linkedList.get(x1).getNum()!=0) {
                double[] doubles = new double[7];
                doubles[6] = linkedList.get(x1).getNum();
                doubles[0] = linkedList.get(x1).getWide();
                doubles[1] = linkedList.get(x1).getWide();
                linkedList.remove(x1);
                result.add(doubles);
            }
        }
        //当未知数有2个时
        log.info("当未知数只有2个时");
        for (int x1=0;x1<linkedList.size();x1++) {
            if (linkedList.get(x1).getNum() == 0) {
                continue;
            }
            int x2 = x1;
            sum.push(linkedList.get(x1).getWide());
            if (linkedList.get(x1).getNum() == 1) {
                x2++;
            }
            while (x2 < linkedList.size()&&linkedList.get(x1).getNum()!=0) {
                if (linkedList.get(x2).getNum() == 0) {
                    x2++;
                    continue;
                }
                sum.push(linkedList.get(x2).getWide());
                if (isFit(sumLinkDouble(sum))) {
                    double[] doubles = new double[7];
                    int num;
                    doubles[0] = sumLinkDouble(sum);
                    doubles[1] = linkedList.get(x1).getWide();
                    doubles[2] = linkedList.get(x2).getWide();
                    if (x1 == x2) {
                        num = (int) linkedList.get(x1).getNum() / 2;
                        linkedList.get(x2).setNum(linkedList.get(x2).getNum() - num*2);
                    } else {
                        num = (int) Math.min(linkedList.get(x1).getNum(),
                                linkedList.get(x2).getNum());
                        if (num == linkedList.get(x1).getNum()) {
                            linkedList.get(x1).setNum(0);
                            linkedList.get(x2).setNum(linkedList.get(x2).getNum() - num);
                        } else {
                            linkedList.get(x2).setNum(0);
                            linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num);
                        }
                    }
                    doubles[6] = num;
                    result.add(doubles);
                }
                sum.pop();
                x2++;
            }
            sum.pop();
        }
        filterNum(linkedList);
        sum.clear();

        //3
        log.info("当未知数只有3个时");
        for (int x1 = 0; x1 < linkedList.size(); x1++) {
            if (linkedList.get(x1).getNum() == 0) {
                continue;
            }
            sum.push(linkedList.get(x1).getWide());
            int x2 = x1;
            if (linkedList.get(x1).getNum() == 1) {
                x2++;
            }
            for (; x2 < linkedList.size(); x2++) {
                if (linkedList.get(x1).getNum() == 0) {
                    break;
                }
                if (linkedList.get(x2).getNum() == 0) {
                    continue;
                }
                sum.push(linkedList.get(x2).getWide());
                int x3=x2 ;
                if (x2 == x1&&linkedList.get(x1).getNum() < 3) {
                    x3 = x2 + 1;
                }
                if (x2 > x1 && linkedList.get(x2).getNum() < 2) {
                    x3 = x2 + 1;
                }
                while (x3 < linkedList.size()) {
                    if (linkedList.get(x1).getNum() == 0||linkedList.get(x2).getNum() == 0) {
                        break;
                    }
                    if (linkedList.get(x3).getNum() == 0) {
                        x3++;
                        continue;
                    }
                    sum.push(linkedList.get(x3).getWide());
                    if (isFit(sumLinkDouble(sum))) {
                        double[] doubles = new double[7];
                        doubles[0] = sumLinkDouble(sum);
                        doubles[1] = linkedList.get(x1).getWide();
                        doubles[2] = linkedList.get(x2).getWide();
                        doubles[3] = linkedList.get(x3).getWide();
                        int num=0;
                        if (x1 == x3 && x1 == x2) {
                            num = (int) linkedList.get(x1).getNum() / 3;
                            linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num * 3);
                        }
                        if (x1 == x2 && x2 != x3) {
                            int num1 = (int) linkedList.get(x1).getNum() / 2;
                            int num2 = (int) linkedList.get(x3).getNum();
                            num = Math.min(num1, num2);
                            linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num * 2);
                            linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num);
                        }
                        if (x1 != x2 && x2 != x3) {
                            int num2 = (int) linkedList.get(x2).getNum();
                            int num1 = (int) linkedList.get(x1).getNum();
                            int num3 = (int) linkedList.get(x3).getNum();
                            int num4 = Math.min(num1, num2);
                            num = Math.min(num4, num3);
                            linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num);
                            linkedList.get(x2).setNum(linkedList.get(x2).getNum() - num);
                            linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num);
                        }
                        doubles[6] = num;
                        result.add(doubles);
                    }
                    x3++;
                    sum.pop();
                }
                sum.pop();
            }
            sum.clear();
        }
        filterNum(linkedList);
        sum.clear();

        //4
        log.info("当未知数只有4个时");
        for (int x1 = 0; x1 < linkedList.size(); x1++) {
            if (linkedList.get(x1).getNum() == 0) {
                continue;
            }
            sum.push(linkedList.get(x1).getWide());
            int x2 = x1;
            if (linkedList.get(x1).getNum() == 1) {
                x2++;
            }
            for (; x2 < linkedList.size(); x2++) {
                if (linkedList.get(x1).getNum() == 0) {
                    break;
                }
                if (linkedList.get(x2).getNum() == 0) {
                    continue;
                }
                sum.push(linkedList.get(x2).getWide());
                int x3=x2 ;
                if (x2 == x1&&linkedList.get(x1).getNum() < 3) {
                    x3 = x2 + 1;
                }
                if (x2 > x1 && linkedList.get(x2).getNum() < 2) {
                    x3 = x2 + 1;
                }
                for (; x3 < linkedList.size(); x3++) {
                    if (linkedList.get(x1).getNum() == 0||linkedList.get(x2).getNum() == 0) {
                        break;
                    }
                    if (linkedList.get(x3).getNum() == 0) {
                        continue;
                    }
                    sum.push(linkedList.get(x3).getWide());
                    int x4 = x3;
                    if (x1 == x2 && x1 == x3) {
                        if (linkedList.get(x3).getNum() < 4) {
                            x4++;
                        }
                    }
                    if (x1 == x2 && x1 != x3) {
                        if (linkedList.get(x3).getNum() < 2) {
                            x4++;
                        }
                    }
                    if (x1 != x2 && x2 == x3) {
                        if (linkedList.get(x3).getNum() < 3) {
                            x4++;
                        }
                    }
                    if (x1 != x2 && x2 != x3) {
                        if (linkedList.get(x3).getNum() < 2) {
                            x4++;
                        }
                    }
                    while (x4 < linkedList.size()) {
                        if (linkedList.get(x1).getNum() == 0||linkedList.get(x2).getNum() == 0||linkedList.get(x3).getNum() == 0) {
                            break;
                        }
                        if (linkedList.get(x4).getNum() == 0) {
                            x4++;
                            continue;
                        }
                        sum.push(linkedList.get(x4).getWide());
                        if (isFit(sumLinkDouble(sum))) {
                            double[] doubles = new double[7];
                            doubles[0] = sumLinkDouble(sum);
                            doubles[1] = linkedList.get(x1).getWide();
                            doubles[2] = linkedList.get(x2).getWide();
                            doubles[3] = linkedList.get(x3).getWide();
                            doubles[4] = linkedList.get(x4).getWide();
                            int num=0;
                            if (x1 == x2 && x2 == x3) {
                                if (x3 == x4) {
                                    num = (int) linkedList.get(x1).getNum() / 4;
                                    linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num * 4);
                                } else {
                                    int num1 = (int) linkedList.get(x1).getNum() / 3;
                                    int num2 = (int) linkedList.get(x4).getNum();
                                    num = Math.min(num1, num2);
                                    linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num * 3);
                                    linkedList.get(x4).setNum(linkedList.get(x4).getNum() - num);
                                }
                            }
                            if (x1 == x2 && x2 != x3) {
                                if (x3 == x4) {
                                    int num1 = (int) linkedList.get(x1).getNum() / 2;
                                    int num2 = (int) linkedList.get(x3).getNum() / 2;
                                    num = Math.min(num1, num2);
                                    linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num*2);
                                    linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num*2);
                                } else {
                                    int num1 = (int) linkedList.get(x1).getNum() / 2;
                                    int num2 = (int) linkedList.get(x3).getNum();
                                    int num3 = (int) linkedList.get(x4).getNum();
                                    int num4 = Math.min(num1, num2);
                                    num = Math.min(num3, num4);
                                    linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num*2);
                                    linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num);
                                    linkedList.get(x4).setNum(linkedList.get(x4).getNum() - num);
                                }
                            }
                            if (x1 != x2 && x2 == x3) {
                                if (x3 == x4) {
                                    int num1 = (int) linkedList.get(x1).getNum();
                                    int num2 = (int) linkedList.get(x2).getNum() / 3;
                                    num = Math.min(num1, num2);
                                    linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num);
                                    linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num * 3);

                                } else {
                                    int num1 = (int) linkedList.get(x1).getNum();
                                    int num2 = (int) linkedList.get(x3).getNum()/2;
                                    int num3 = (int) linkedList.get(x4).getNum();
                                    int num4 = Math.min(num1, num2);
                                    num = Math.min(num3, num4);
                                    linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num);
                                    linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num*2);
                                    linkedList.get(x4).setNum(linkedList.get(x4).getNum() - num);
                                }
                            }
                            if (x1 != x2 && x2 != x3) {
                                if (x3 == x4) {
                                    int num1 = (int) linkedList.get(x1).getNum();
                                    int num2 = (int) linkedList.get(x2).getNum();
                                    int num3 = (int) linkedList.get(x3).getNum() / 2;
                                    int num4 = Math.min(num1, num2);
                                    num = Math.min(num3, num4);
                                    linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num);
                                    linkedList.get(x2).setNum(linkedList.get(x2).getNum() - num);
                                    linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num * 2);
                                } else {
                                    double num1 = Math.min(linkedList.get(x1).getNum(), linkedList.get(x2).getNum());
                                    double num2 = Math.min(linkedList.get(x3).getNum(), linkedList.get(x4).getNum());
                                    num = (int)Math.min(num1, num2);
                                    linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num);
                                    linkedList.get(x2).setNum(linkedList.get(x2).getNum() - num);
                                    linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num);
                                    linkedList.get(x4).setNum(linkedList.get(x4).getNum() - num);
                                }
                            }
                            doubles[6] = num;
                            result.add(doubles);
                        }
                        sum.pop();
                        x4++;

                    }
                    sum.pop();

                }
                sum.pop();
            }
            sum.clear();
        }

        filterNum(linkedList);
        sum.clear();

        //5
        log.info("当未知数只有5个时");
        for (int x1 = 0; x1 < linkedList.size(); x1++) {
            if (linkedList.get(x1).getNum() == 0) {
                continue;
            }
            sum.push(linkedList.get(x1).getWide());
            int x2 = x1;
            if (linkedList.get(x1).getNum() == 1) {
                x2++;
            }
            for (; x2 < linkedList.size(); x2++) {
                if (linkedList.get(x1).getNum() == 0) {
                    break;
                }
                if (linkedList.get(x2).getNum() == 0) {
                    continue;
                }
                sum.push(linkedList.get(x2).getWide());
                int x3=x2 ;
                if (x2 == x1&&linkedList.get(x1).getNum() < 3) {
                    x3 = x2 + 1;
                }
                if (x2 > x1 && linkedList.get(x2).getNum() < 2) {
                    x3 = x2 + 1;
                }
                for (; x3 < linkedList.size(); x3++) {
                    if (linkedList.get(x1).getNum() == 0||linkedList.get(x2).getNum() == 0) {
                        break;
                    }
                    if (linkedList.get(x3).getNum() == 0) {
                        continue;
                    }
                    sum.push(linkedList.get(x3).getWide());
                    int x4 = x3;
                    if (x1 == x2 && x1 == x3) {
                        if (linkedList.get(x3).getNum() < 4) {
                            x4++;
                        }
                    }
                    if (x1 == x2 && x1 != x3) {
                        if (linkedList.get(x3).getNum() < 2) {
                            x4++;
                        }
                    }
                    if (x1 != x2 && x2 == x3) {
                        if (linkedList.get(x3).getNum() < 3) {
                            x4++;
                        }
                    }
                    if (x1 != x2 && x2 != x3) {
                        if (linkedList.get(x3).getNum() < 2) {
                            x4++;
                        }
                    }
                    for (;x4<linkedList.size();x4++) {
                        if (linkedList.get(x1).getNum() == 0||linkedList.get(x2).getNum() == 0||linkedList.get(x3).getNum() == 0) {
                            break;
                        }
                        if (linkedList.get(x4).getNum() == 0) {
                            continue;
                        }
                        sum.push(linkedList.get(x4).getWide());
                        int x5 = x4;
                        if (x1 == x2 && x2 == x3) {
                            if (x3 == x4 && linkedList.get(x4).getNum() < 5) {
                                x5++;
                            }
                            if (x3 != x4 && linkedList.get(x4).getNum() < 2) {
                                x5++;
                            }
                        }
                        if (x1 == x2 && x2 != x3) {
                            if (x3 == x4 && linkedList.get(x4).getNum() < 3) {
                                x5++;
                            }
                            if (x3 != x4 && linkedList.get(x4).getNum() < 2) {
                                x5++;
                            }
                        }
                        if (x1 != x2 && x2 == x3) {
                            if (x3 == x4 && linkedList.get(x4).getNum() < 4) {
                                x5++;
                            }
                            if (x3 != x4 && linkedList.get(x4).getNum() < 2) {
                                x5++;
                            }
                        }
                        if (x1 != x2 && x2 != x3) {
                            if (x3 == x4 && linkedList.get(x4).getNum() < 3) {
                                x5++;
                            }
                            if (x3 != x4 && linkedList.get(x4).getNum() < 2) {
                                x5++;
                            }
                        }
                        while (x5 < linkedList.size()) {
                            if (linkedList.get(x1).getNum() == 0||linkedList.get(x2).getNum() == 0||linkedList.get(x3).getNum() == 0||linkedList.get(x4).getNum() == 0) {
                                break;
                            }
                            if (linkedList.get(x5).getNum() == 0) {
                                x5++;

                                continue;
                            }
                            sum.push(linkedList.get(x4).getWide());
                            if (isFit(sumLinkDouble(sum))) {
                                double[] doubles = new double[7];
                                doubles[0] = sumLinkDouble(sum);
                                doubles[1] = linkedList.get(x1).getWide();
                                doubles[2] = linkedList.get(x2).getWide();
                                doubles[3] = linkedList.get(x3).getWide();
                                doubles[4] = linkedList.get(x4).getWide();
                                doubles[5] = linkedList.get(x5).getWide();
                                int num = 0;
                                if (x1 == x2 && x2 == x3) {
                                    if (x3 == x4 && x4 == x5) {
                                        num = (int) (linkedList.get(x1).getNum() / 5);
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num * 5);
                                    }
                                    if (x3 == x4 && x4 != x5) {
                                        double num1 = linkedList.get(x1).getNum() / 4;
                                        double num2 = linkedList.get(x5).getNum();
                                        num = (int) Math.min(num1, num2);
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num * 4);
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num );
                                    }
                                    if (x3 != x4 && x4 == x5) {
                                        double num1 = linkedList.get(x1).getNum() / 3;
                                        double num2 = linkedList.get(x5).getNum()/2;
                                        num = (int) Math.min(num1, num2);
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num * 3);
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num * 2);
                                    }
                                    if (x3 != x4 && x4 != x5) {
                                        double num1 = linkedList.get(x1).getNum() / 3;
                                        double num2 = linkedList.get(x5).getNum();
                                        double num3 = linkedList.get(x4).getNum();
                                        double num4 = Math.min(num1, num2);
                                        num = (int) Math.min(num3, num4);
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num * 3);
                                        linkedList.get(x4).setNum(linkedList.get(x4).getNum() - num );
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num );
                                    }
                                }
                                if (x1 == x2 && x2 != x3) {
                                    if (x3 == x4 && x4 == x5) {
                                        num = (int) Math.min(linkedList.get(x1).getNum() / 2, linkedList.get(x5).getNum() / 3);
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num * 2);
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num * 3);
                                    }
                                    if (x3 == x4 && x4 != x5) {
                                        double num1 = Math.min(linkedList.get(x1).getNum() / 2, linkedList.get(x3).getNum() / 2);
                                        num = (int) Math.min(num1, linkedList.get(x5).getNum());
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num * 2);
                                        linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num * 2);
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num );
                                    }
                                    if (x3 != x4 && x4 == x5) {
                                        double num1 = Math.min(linkedList.get(x1).getNum() / 2, linkedList.get(x3).getNum());
                                        num = (int) Math.min(num1, linkedList.get(x5).getNum()/2);
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num * 2);
                                        linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num );
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num * 2);
                                    }
                                    if (x3 != x4 && x4 != x5) {
                                        double num1 = Math.min(linkedList.get(x1).getNum() / 2, linkedList.get(x3).getNum());
                                        double num2 = Math.min(linkedList.get(x4).getNum() , linkedList.get(x5).getNum());
                                        num = (int) Math.min(num1, num2);
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num * 2);
                                        linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num );
                                        linkedList.get(x4).setNum(linkedList.get(x4).getNum() - num );
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num );
                                    }
                                }
                                if (x1 != x2 && x2 == x3) {
                                    if (x3 == x4 && x4 == x5) {
                                        num = (int) Math.min(linkedList.get(x1).getNum(), linkedList.get(x5).getNum() / 4);
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num );
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num * 4);
                                    }
                                    if (x3 == x4 && x4 != x5) {
                                        double num1 = Math.min(linkedList.get(x1).getNum() , linkedList.get(x3).getNum() / 3);
                                        num = (int) Math.min(num1, linkedList.get(x5).getNum());
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num );
                                        linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num * 3);
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num );
                                    }
                                    if (x3 != x4 && x4 == x5) {
                                        double num1 = Math.min(linkedList.get(x1).getNum(), linkedList.get(x3).getNum()/2);
                                        num = (int) Math.min(num1, linkedList.get(x5).getNum()/2);
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num );
                                        linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num *2);
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num * 2);
                                    }
                                    if (x3 != x4 && x4 != x5) {
                                        double num1 = Math.min(linkedList.get(x1).getNum() , linkedList.get(x3).getNum()/2);
                                        double num2 = Math.min(linkedList.get(x4).getNum() , linkedList.get(x5).getNum());
                                        num = (int) Math.min(num1, num2);
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num );
                                        linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num *2);
                                        linkedList.get(x4).setNum(linkedList.get(x4).getNum() - num );
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num );
                                    }
                                }
                                if (x1 != x2 && x2 != x3) {
                                    if (x3 == x4 && x4 == x5) {
                                        double num1 = Math.min(linkedList.get(x1).getNum(), linkedList.get(x5).getNum() / 3);
                                        num = (int) Math.min(num1, linkedList.get(x2).getNum());
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num );
                                        linkedList.get(x2).setNum(linkedList.get(x2).getNum() - num );
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num * 3);
                                    }
                                    if (x3 == x4 && x4 != x5) {
                                        double num1 = Math.min(linkedList.get(x1).getNum(), linkedList.get(x3).getNum() / 2);
                                        double num2 = Math.min(linkedList.get(x2).getNum(), linkedList.get(x5).getNum());
                                        num = (int) Math.min(num1, num2);
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num);
                                        linkedList.get(x2).setNum(linkedList.get(x2).getNum() - num);
                                        linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num * 2);
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num );
                                    }
                                    if (x3 != x4 && x4 == x5) {
                                        double num1 = Math.min(linkedList.get(x1).getNum(), linkedList.get(x4).getNum() / 2);
                                        double num2 = Math.min(linkedList.get(x2).getNum(), linkedList.get(x3).getNum());
                                        num = (int) Math.min(num1, num2);
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num);
                                        linkedList.get(x2).setNum(linkedList.get(x2).getNum() - num);
                                        linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num );
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num *2);
                                    }
                                    if (x3 != x4 && x4 != x5) {
                                        double num1 = Math.min(linkedList.get(x1).getNum(), linkedList.get(x2).getNum());
                                        double num2 = Math.min(linkedList.get(x3).getNum(), linkedList.get(x2).getNum());
                                        double num3 = Math.min(num1, num2);
                                        double num4 = Math.min(num3, linkedList.get(x4).getNum());
                                        num = (int) Math.min(num4, linkedList.get(x5).getNum());
                                        linkedList.get(x1).setNum(linkedList.get(x1).getNum() - num);
                                        linkedList.get(x2).setNum(linkedList.get(x2).getNum() - num);
                                        linkedList.get(x3).setNum(linkedList.get(x3).getNum() - num );
                                        linkedList.get(x4).setNum(linkedList.get(x4).getNum() - num );
                                        linkedList.get(x5).setNum(linkedList.get(x5).getNum() - num );
                                    }
                                }
                                doubles[6] = num;
                                result.add(doubles);
                            }
                            sum.pop();
                            x5++;
                        }


                        sum.pop();

                    }
                    sum.pop();

                }
                sum.pop();
            }
            sum.clear();
        }

        filterNum(linkedList);
        sum.clear();

        return result;
    }

}
