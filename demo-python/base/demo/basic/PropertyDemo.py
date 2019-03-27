class Pager:
    def __init__(self , currentPage):
        #用户当前请求的页码,
        self.currentPage = currentPage;
        #每页默认显示10条数据
        self.perItems = 10;


    @property
    def start(self):
        val = (self.currentPage-1)*self.perItems;
        return val;


    @property
    def end(self):
        val = self.currentPage*self.perItems;
        return val;





p = Pager(1);
#起始值 , 就是m
print(p.start);
#就是结束值,  就是n
print(p.end);
