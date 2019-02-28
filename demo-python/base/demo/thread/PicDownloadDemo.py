import urllib.request;
import gevent;
from gevent import monkey;


monkey.patch_all();



def downloader(imgName , imgUrl):
    req = urllib.request.urlopen(imgUrl);

    imgContent = req.read();

    with open(imgName  , "wb") as f :
        f.write(imgContent);



def execute():
    gevent.joinall([
        gevent.spawn(downloader, "d://3.jpg", "http://clubimg.lenovo.com.cn/pic/11414593576664/0"),
        gevent.spawn(downloader, "d://4.jpg", "http://p2.lefile.cn/product/adminweb/2018/07/16/9lueyVb09wfrC23M8fsamYKlu-8841.jpg")
    ]);




if __name__ == '__main__':
    execute();
