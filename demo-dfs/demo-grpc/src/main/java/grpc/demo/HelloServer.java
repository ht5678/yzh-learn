package grpc.demo;
 
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import grpc.demo.HelloGrpc;
import grpc.demo.HelloOuterClass.HelloRequest;
import grpc.demo.HelloOuterClass.HelloResponse;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
 
public class HelloServer {
 
    private Server server;
 
    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new HelloImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // jvm关闭前执行
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                HelloServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }
 
    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }
 
    /**
     * 阻塞等待主线程终止
     * @throws InterruptedException
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
 
    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloServer server = new HelloServer();
        server.start();
        server.blockUntilShutdown();
    }
 
    /**
     * 服务实现类
     */
    private class HelloImpl extends HelloGrpc.HelloImplBase{
 
        @Override
        public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
            HelloResponse helloResponse = HelloResponse.newBuilder().setMessage("Hello "+request.getName()+", I'm Java grpc Server").build();
            responseObserver.onNext(helloResponse);
            responseObserver.onCompleted();
        }
    }
}