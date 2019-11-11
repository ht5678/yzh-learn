JDK常用的阻塞队列:
*ArrayBlockingQueue:基于数组结构的有界阻塞队列(长度不可变)
*LinkedBlockingQueue:基于链表结构的有界阻塞队列(默认容量:Integer.MAX_VALUE)
*LinkedTransferQueue:基于链表结构的无界阻塞队列/传递队列
*LinkedBlockingDeque:基于链表结构的有界阻塞双端队列(默认容量:Integer.MAX_VALUE)
*SynchronousQueue:不存储元素的阻塞/传递队列
*PriorityBlockingQueue:支持优先级排序的无界阻塞队列
*DelayQueue:支持延时获取元素的无界阻塞队列