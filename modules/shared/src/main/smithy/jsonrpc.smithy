$version: "2.0"

namespace test

use jsonrpclib#jsonRpc
use jsonrpclib#jsonRpcNotification
use jsonrpclib#jsonRpcRequest

@jsonRpc
service TestServer {
    operations: [
        Greet
        Ping
    ]
}

@jsonRpc
service TestClient {
    operations: [
        Pong
        GetTime
    ]
}

@jsonRpcRequest("greet")
operation Greet {
    input := {
        @required
        name: String
    }

    output := {
        @required
        message: String
    }
}

@jsonRpcNotification("ping")
operation Ping {
    input := {
        @required
        ping: String
    }
}

@jsonRpcNotification("pong")
operation Pong {
    input := {
        @required
        pong: String
    }
}

@jsonRpcRequest("getTime")
operation GetTime {
    input := {}

    output := {
        @required
        @timestampFormat("epoch-seconds")
        time: Timestamp
    }
}
