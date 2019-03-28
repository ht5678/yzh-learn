from contextlib import contextmanager

@contextmanager
def myOpen(path , mode):
    f = open(path , mode);
    yield f;
    f.close();



with myOpen("d://out.txt" , "w") as f:
    f.write("hello , the simplest context manager");