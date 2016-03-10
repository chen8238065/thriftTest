namespace java com.anjuke.demo.thrift.auto

service  HelloWorldService {
  string sayHello(1:string username)
}