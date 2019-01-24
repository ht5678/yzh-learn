import pygame
from planefight.game.PlaneSprite import *


SCREEN_SIZE = pygame.Rect(0,0,480,700);
FRAME_PER_SEC = 60;

class PlaneGame:

    """飞机大战主题游戏"""

    def __init__(self):
        print("游戏初始化");

        #1.创建游戏的窗口
        self.screen = pygame.display.set_mode(SCREEN_SIZE.size);
        #2.创建游戏的时钟
        self.clock = pygame.time.Clock();
        #3.调用私有方法,精灵和精灵组的创建
        self.__create_sprites();



    def __create_sprites(self):
        pass;




    def start_game(self):
        print("游戏开始");

        while True:

            #1.设置刷新频率
            self.clock.tick(FRAME_PER_SEC);
            #2.事件监听
            self.__eventHandler();
            #3.碰撞检测
            self.__checkCollide();
            #4.更新/绘制精灵组
            self.__updateSprites();
            #5.更新显示
            pygame.display.update();








    def __eventHandler(self):
        ev = pygame.event.poll();
        if ev.type == pygame.QUIT:
            self.__gameOver();

    def __checkCollide(self):
        pass;

    def __updateSprites(self):
        pass;

    def __gameOver(self):
        print("游戏结束");
        pygame.quit();
        exit();


if __name__ == '__main__':
    #创建游戏对象
    game = PlaneGame();

    #启动游戏
    game.start_game();
