import pygame
from planefight.game.PlaneSprite import *


SCREEN_SIZE = pygame.Rect(0,0,480,700);
#刷新的频率
FRAME_PER_SEC = 60;
#创建敌机的定时器常量
CREATE_ENEMY_EVENT = pygame.USEREVENT;
#英雄发射子弹事件
HERO_FIRE_EVENT = pygame.USEREVENT+1;

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
        #4.创建定时事件 , 创建敌机
        pygame.time.set_timer(CREATE_ENEMY_EVENT,1000);
        pygame.time.set_timer(HERO_FIRE_EVENT, 500);




    def __create_sprites(self):
        #创建背景精灵和精灵组
        bg1 = Background();
        bg2 = Background(True);
        self.back_group = pygame.sprite.Group(bg1,bg2);

        #创建敌机的精灵组
        self.enemy_group = pygame.sprite.Group();

        #创建英雄的精灵和精灵组
        self.hero = Hero();
        self.heroGroup = pygame.sprite.Group(self.hero);




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
        elif ev.type == CREATE_ENEMY_EVENT:
            #print("敌机出场...");
            #创建敌机精灵
            enemy = Enemy();
            #将敌机精灵添加到敌机精灵组
            self.enemy_group.add(enemy);
        elif ev.type == HERO_FIRE_EVENT:
            self.hero.fire();


        #第一种:事件方式
        #elif ev.type == pygame.KEYDOWN and ev.key == pygame.K_RIGHT:
        #    print("向右移动...");

        #第二种:按键方式
        #使用键盘提供的方法获取键盘按键 - 按键元组
        keys_pressed = pygame.key.get_pressed();

        #判断元组中对应的按键索引值 , 如果值为1=True , 代表这个按键被按下了
        if keys_pressed[pygame.K_RIGHT]:
            self.hero.speed=3;
        elif keys_pressed[pygame.K_LEFT]:
            self.hero.speed=-3;
        else:
            self.hero.speed = 0;




    def __checkCollide(self):
        pass;

    def __updateSprites(self):
        self.back_group.update();
        self.back_group.draw(self.screen);

        self.enemy_group.update();
        self.enemy_group.draw(self.screen);

        self.heroGroup.update();
        self.heroGroup.draw(self.screen);

        self.hero.bullets.update();
        self.hero.bullets.draw(self.screen);



    def __gameOver(self):
        print("游戏结束");
        pygame.quit();
        exit();


if __name__ == '__main__':
    #创建游戏对象
    game = PlaneGame();

    #启动游戏
    game.start_game();
