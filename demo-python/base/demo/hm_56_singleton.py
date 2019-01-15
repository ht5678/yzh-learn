class MusicPlayer:

    instance = None;

    initFlag = False;


    def __new__(cls, *args, **kwargs):
        if cls.instance is None:
            cls.instance = super().__new__(cls);
        return cls.instance;

    def __init__(self):
        if MusicPlayer.initFlag:
            return

        print("init ... ");
        MusicPlayer.initFlag=True;


mp1 = MusicPlayer();
print(mp1);

mp2 = MusicPlayer();
print(mp2);