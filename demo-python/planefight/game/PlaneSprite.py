import pygame


class GameSprite(pygame.sprite.Sprite):

    def __init__(self , image_name , speed =1):
        super().__init__();
