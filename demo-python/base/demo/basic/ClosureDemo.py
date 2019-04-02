

"""
关键字:nonlocal
"""

def line6(k,b):
    def createY(x):
        print(k*x+b);
    return createY;


line6_1 = line6(1,2);
line6_1(0);
line6_1(1);
line6_1(2);

line6_2 = line6(11,22);
line6_2(0);
line6_2(1);
line6_2(2);
