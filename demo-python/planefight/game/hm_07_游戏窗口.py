import pygame

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


while True:
    ev = pygame.event.poll()
    if ev.type == pygame.QUIT:
        break
pygame.quit()

