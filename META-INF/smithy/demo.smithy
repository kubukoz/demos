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

// I don't want people having to define all these operations, so they'd get that automatically from the `@eventUnion` thing.
service MyEventService {
  operations: [Event1Stream, Event2Stream, Event3Stream]
}

// you're gonna ask why it's an output and not an input or a streaming input.
// It's not a normal input because then fluent builders become... impossible? (I think) to call, as you'd need _some_ input to even call each method.
// It's not a streaming input because that'd have to be a union and then users would have to deal with that on their end.
operation Event1Stream { input: Event1 }
operation Event2Stream { input: Event2 }
operation Event3Stream { input: Event3 }
