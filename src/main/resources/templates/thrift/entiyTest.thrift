include "./entity.templates.thrift"

namespace java com.chapa.demo.templates.thrift.auto

enum TestEnum {  
	A = 1  
	B = 2  	  
} 
exception ThriftException {
   1: i32 errorCode,
   2: string message
}
/**
* ddddd
*/
service  EntityService {
  void setEntity(1:entity.Contact entity) throws (1:ThriftException unavailable);
  list<entity.Contact> getAll();
 }
