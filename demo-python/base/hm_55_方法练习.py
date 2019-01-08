class Game:
    #历史最高分
    topScore=0;

    def __init__(self , playerName):
        self.playerName=playerName;

    @staticmethod
    def show_help():
        print("帮助信息:让僵尸进入大门");

    @classmethod
    def showTopScore(cls):
        print("历史记录 %d " % cls.topScore);

    def startGame(self):
        print("%s 开始游戏" % self.playerName);



#查看游戏的帮助信息
Game.show_help();

#查看历史最高分
Game.showTopScore();

#创建游戏对象
game = Game("zhangsan");
game.startGame();




