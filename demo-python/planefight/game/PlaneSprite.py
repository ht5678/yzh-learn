import pygame



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

