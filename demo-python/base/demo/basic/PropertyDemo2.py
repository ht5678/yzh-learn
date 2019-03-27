class Goods(object):

    def __init__(self):
        #原价
        self.originalPrice = 100;

        #折扣
        self.discount = 0.8;


    @property
    def price(self):
        #实际价格= 原价*折扣
        newPrice = self.originalPrice*self.discount;

        return newPrice;


    @price.setter
    def price(self , value):
        self.originalPrice = value;


    @price.deleter
    def price(self):
        del self.originalPrice;




obj = Goods();

print(obj.price);


obj.price = 200;
print(obj.price);


del obj.price;


