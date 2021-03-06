import random
import pygame
from planefight.game.PlaneGame import *


class GameSprite(pygame.sprite.Sprite):

    def __init__(self , image_name , speed =1):
        #定义父类的初始化方法
        super().__init__();

        #定义对象的属性
        self.image = pygame.image.load(image_name);
        self.rect = self.image.get_rect();
        self.speed = speed;


    def update(self):
        #在屏幕的垂直方向上移动
        self.rect.y = self.rect.y+self.speed;



"""游戏背景精灵"""
class Background(GameSprite):

    def __init__(self , is_alt=False):
        #1.调用父类方法实现精灵的创建
        super().__init__("../images/background.png");
        #2.判断是否为交替图像,如果是 ,需要设置初始位置
        if is_alt:
            self.rect.y = -self.rect.height;

    def update(self):
        #1.调用父类的方法实现
        super().update();

        #2.判断是否移出屏幕 , 如果移出屏幕 ,将图像设置到屏幕的上方
        if self.rect.y >= SCREEN_SIZE.height:
            self.rect.y = -self.rect.height;
        pass;


"""敌机类"""
class Enemy(GameSprite):

    def __init__(self):
        #1.调用父类方法 , 创建敌机精灵 , 同时指定敌机图片
        super().__init__("../images/enemy1.png");

        #2.指定敌机初始随即速度
        self.speed = random.randint(1,3);

        #3.指定敌机的初始随即位置
        self.rect.bottom=0;
        maxX = SCREEN_SIZE.width - self.rect.width;
        self.rect.x = random.randint(0,maxX);



    def update(self):
        #调用父类方法 , 保持垂直方向的飞行
        super().update();

        #判断是否飞出屏幕 , 如果是 , 需要从精灵组中删除敌机
        if self.rect.y >= SCREEN_SIZE.height:
            #print("飞出屏幕 , 需要从精灵组删除 ... ");

            #kill方法会将精灵从精灵组移除 , 精灵就会被自动销毁
            self.kill();


    #内置方法 , 执行kill方法的时候回调用这个方法
    def __del__(self):
        #print("敌机挂了 %s" % self.rect);
        pass;



"""英雄精灵"""
class Hero(GameSprite):

    def __init__(self):
        #1.调用父类方法 , 设置 image&speed
        super().__init__("../images/me1.png" , 0);
        #2.设置英雄的初始位置
        self.rect.centerx = SCREEN_SIZE.centerx;
        self.rect.bottom = SCREEN_SIZE.bottom-self.rect.height;

        #3.创建子弹的精灵组
        self.bullets = pygame.sprite.Group();


    def update(self):
        #英雄在水平方向移动
        self.rect.x = self.rect.x+self.speed;

        #控制英雄不能离开屏幕
        if self.rect.x <0:
            self.rect.x =0;
        elif self.rect.right > SCREEN_SIZE.right:
            self.rect.right = SCREEN_SIZE.right;

    def fire(self):
        #同时发射多颗子弹
        for i in (1,2,3):
            #1.创建子弹精灵
            bullet = Bullet();
            #2.设置精灵位置
            bullet.rect.bottom = self.rect.y - i * 20;
            bullet.rect.centerx = self.rect.centerx;
            #3.将精灵加到精灵组
            self.bullets.add(bullet);




class Bullet(GameSprite):
    """子弹类"""


    def __init__(self):
        #调用父类方法 , 设置子弹照片 , 设置初始速度
        super().__init__("../images/bullet1.png",-2);




    def update(self):
        #调用父类方法 , 让子弹沿垂直方向飞行
        super().update();

        #判断子弹是否飞出屏幕
        if self.rect.bottom<0:
            self.kill();



    def __del__(self):
        print("子弹被销毁...");


