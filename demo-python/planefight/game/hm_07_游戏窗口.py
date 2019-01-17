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
pygame.display.update();


while True:
    pass

pygame.quit();