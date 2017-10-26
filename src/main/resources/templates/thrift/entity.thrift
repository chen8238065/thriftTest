namespace java com.chapa.demo.templates.thrift.auto

struct TypeTest{
	1:required i32 id
	2:optional string name
	3:binary ddd
	4:byte byteddd
	5:bool boolddd
	6:i16 i16ddd
	7:i64 i64ddd
	8:double doubleddd
    9: map<i16, i16> mapddd
    10: set<i16> setddd
    11: list<i16> listddd
}

struct Contact{
	1:i32 id
	2:string name
	3:i64 birthday
	4:string phoneNo
	5:string ipAddress
	6:map<string,string> props
	7:double salary
	8:set<TypeTest> books
}

