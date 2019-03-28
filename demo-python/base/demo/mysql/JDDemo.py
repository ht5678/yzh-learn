from pymysql import  *


class JD:


    def __init__(self):
        #创建connection连接
        self.conn = connect(host="lenovodb" , port=3306 , user="myuser" , password="mypassword" , database="test" , charset="utf8");
        #获取cursor对象
        self.cursor = self.conn.cursor();



    def __del__(self):
        #关闭cursor对象
        self.cursor.close();
        self.conn.close();

    def executeSql(self , sql):
        self.cursor.execute(sql);
        for temp in self.cursor.fetchall():
            print(temp);


    def showAllItems(self):
        """显示所有的商品"""
        sql = "select * from goods";
        self.executeSql(sql);

    def showCates(self):
        sql = "select * from goods";
        self.executeSql(sql);




    @staticmethod
    def printMenu():
        print("------商城------");
        print("1.所有的商品");
        print("2.所有的商品分类");
        print("3.所有的商品品牌分类");
        return input("请输入功能对应的序号:");


    def run(self):
        while True:
            num = self.printMenu();
            if num =="1":
                #查询所有商品
                self.showAllItems();
            elif num == "2":
                #查询分类
                self.showCates();
            elif num =="3":
                #查询品牌分类
                pass;
            else:
                print("输入错误,重新输入. .. ");






def main():
    #创建一个商城对象
    jd = JD();
    #调用这个对象的run方法,让其运行
    jd.run();


if __name__ == '__main__':
    main();