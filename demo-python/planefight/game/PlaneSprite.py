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
            self.rect.y = self.rect.y - self.rect.height;

    def update(self):
        #1.调用父类的方法实现
        super().update();

        #2.判断是否移出屏幕 , 如果移出屏幕 ,将图像设置到屏幕的上方
        if self.rect.y >= SCREEN_SIZE.height:
            self.rect.y = self.rect.y - self.rect.height;
        pass;


"""敌机类"""
class Enemy(GameSprite):

    def __init__(self):
        #1.调用父类方法 , 创建敌机精灵 , 同时指定敌机图片
        super().__init__("../images/enemy1.png");

        #2.指定敌机初始随即速度
        self.speed = random.randint(1,3);

        #3.指定敌机的初始随即位置



    def update(self):
        #调用父类方法 , 保持垂直方向的飞行
        super().update();

        #判断是否飞出屏幕 , 如果是 , 需要从精灵组中删除敌机
        if self.rect.y >= SCREEN_SIZE.height:
            print("飞出屏幕 , 需要从精灵组删除 ... ");
