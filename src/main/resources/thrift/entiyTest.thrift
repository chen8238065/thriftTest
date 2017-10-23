include "./entity.thrift"

namespace java com.chapa.demo.thrift.auto

exception ThriftException {
   1: i32 errorCode,
   2: string message
}
service  EntityService {
  void setEntity(1:entity.Contact entity) throws (1:ThriftException unavailable);
  list<entity.Contact> getAll();
 }