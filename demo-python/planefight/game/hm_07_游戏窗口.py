import pygame
from planefight.game.PlaneSprite import *


pygame.init();

#创建游戏窗口
#resolution:指定屏幕的宽和高,默认创建窗口大小和屏幕大小一致
#flags:参数指定屏幕的附加选项,例如是否全屏等
#depth:参数表示颜色的位数,默认自动匹配
screen = pygame.display.set_mode((480,700));


#绘制背景图像
#1.加载图像数据
bg = pygame.image.load("../images/background.png");
#2.blit绘制图像
screen.blit(bg,(0,0));
#3.update更新屏幕显示
#pygame.display.update();


#绘制英雄的飞机
hero = pygame.image.load("../images/me1.png");
screen.blit(hero, (200, 500));


#可以在所有绘制工作完成之后 ,统一更新一下ui
pygame.display.update();


#创建时钟对象
clock = pygame.time.Clock();

#定义rect记录飞机的初始位置
hero_rect = pygame.Rect(150,300,102,126);


#创建敌机的精灵
enemy = GameSprite("../images/enemy1.png");
enemy2 = GameSprite("../images/enemy1.png",2);

#创建敌机精灵组
enemy_group = pygame.sprite.Group(enemy,enemy2);




while True:
    #每秒刷新画面60次 , 达到视觉流畅的程度
    #可以指定循环体内部的代码执行的频率
    clock.tick(60);

    #修改飞机的位置
    hero_rect.y = hero_rect.y-1;

    if hero_rect.y<-126 :
        hero_rect.y=700;

    #调用blit方法绘制图像
    screen.blit(bg,(0,0));
    screen.blit(hero , hero_rect);


    #让精灵组调用两个方法
    #update
    enemy_group.update();
    #draw
    enemy_group.draw(screen);

    #调用update方法更新显示
    pygame.display.update();

    #eventList = pygame.event.get();
    #print(eventList);


    ev = pygame.event.poll();
    if ev.type == pygame.QUIT:
        break
pygame.quit();
#直接终止当前执行的程序
exit();
