import os

file = open("d://demo.txt");

while True:
    text = file.readline();

    #判断是否到最后 , 没有读取到内容
    if not text:
        break;

    print(text);

file.close();



#os模块用于文件操作 ,
print(os.listdir("d://"));