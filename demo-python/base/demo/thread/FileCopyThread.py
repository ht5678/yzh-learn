import multiprocessing
import os


"""完成文件的复制"""
def copyFile(q,fileName , oldFolderName , newFolderName):
    #print("模拟copy文件 , 从%s -----> 到%s , 文件名是:%s" % (oldFolderName , newFolderName , fileName));
    oldF = open(oldFolderName+"/"+fileName , "rb");
    content = oldF.read();
    oldF.close();

    newF = open(newFolderName +"/"+fileName , "wb");
    newF.write(content);
    newF.close();

    #如果拷贝完了文件 ,那么就向队列中写入一个消息 ,表示已经完成
    q.put(fileName);



def execute():
    #获取用户copy文件夹的名字
    oldFolderName = input("请输入要copy的文件夹的名字:");

    #创建一个新的文件夹
    try:
        newFolderName = oldFolderName+"[复件]";
        os.mkdir(newFolderName);
    except:
        pass;

    #获取文件的所有的待copy的文件名字  listdir()
    fileNames = os.listdir(oldFolderName);
    #print(fileNames);

    #创建进程池
    po = multiprocessing.Pool(5);

    #创建一个队列
    q = multiprocessing.Manager().Queue();

    #向进程池中添加copy文件的任务
    for fileName in fileNames:
        po.apply_async(copyFile , args=(q,fileName , oldFolderName , newFolderName));


    po.close();
    #po.join();
    allFileNum = len(fileNames);
    copyOkNum = 0;
    while True:
        fileName = q.get();
        copyOkNum+=1;
        print("\r拷贝的进度为: %.2f %%" % (copyOkNum*100/allFileNum) , end="");
        if copyOkNum>=allFileNum:
            break;



if __name__ == '__main__':
    execute();