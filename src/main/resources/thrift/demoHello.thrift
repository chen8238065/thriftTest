namespace java com.chapa.demo.thrift.auto

service  HelloWorldService {
  string sayHello(1:string username)
}