from pymysql import *

def main():
    #创建connection连接
    conn = connect(host="lenovodb",port=3306,user='myuser',password='mypassword',database='test',charset='utf8');
    #获取cursor对象
    cs1 = conn.cursor();
    #执行select语句 , 并返回受影响的行数,查询一条数据
    count = cs1.execute("select id , name from goods where id<=10");
    #打印受影响的行数,
    print("查询到%s条数据" % count);

#    for i in range(count):
#        #获取查询到的结果
#        result = cs1.fetchone();
#        #打印查询的结果
#        print(result);


    #result = cs1.fetchall();
    result = cs1.fetchmany(5);

    print(result);


    #关闭cursor对象
    cs1.close();
    conn.close();


if __name__ == '__main__':
    main();
