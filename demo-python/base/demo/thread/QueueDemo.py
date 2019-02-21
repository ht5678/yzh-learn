import multiprocessing;


def downloadFromWeb(q):
    """下载数据"""
    data = [11,22,33,44];

    #向队列中写入数据
    for temp in data:
        q.put(temp);

    print("---- 下载器已经下载完了数据并且存入到队列中 ---");




def analysisData(q):
    """数据处理"""
    waitingAnalysisData = list();
    #从队列中获取数据
    while True:
        data = q.get();
        waitingAnalysisData.append(data);

        if q.empty():
            break;


    #模拟数据处理
    print(waitingAnalysisData);




def execute():
    #创建一个队列
    q = multiprocessing.Queue();

    #创建多个线程 ,将队列的引用当做实参进行传递到里面
    p1 = multiprocessing.Process(target=downloadFromWeb , args=(q , ));
    p2 = multiprocessing.Process(target=analysisData , args=(q , ));

    p1.start();
    p2.start();


if __name__ == '__main__':
    execute();
