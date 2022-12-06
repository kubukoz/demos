namespace com.kubukoz

use alloy#simpleRestJson

@trait(selector: "service")
string apiVersion

@simpleRestJson
@apiVersion("v1")
service FooService {

}
