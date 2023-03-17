$version: "2"
namespace demo

// protocol
@trait
structure event {}
structure eventUnion {}

//human-written

@event
structure Event1 { @required name: String}
@event
structure Event2 { @required name: String}
@event
structure Event3 { @required name: String}

@eventUnion
union MyEvent {
  e1: Event1,
  e2: Event2,
  e3: Event3
}

service MyEventService {
  operations: [Event1Stream, Event2Stream, Event3Stream]
}

operation Event1Stream { input: Event1 }
operation Event2Stream { input: Event2 }
operation Event3Stream { input: Event3 }
