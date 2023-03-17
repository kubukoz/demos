$version: "2"
namespace test

service Hello {
  operations: [Koniec, Kurwa, Gierek]
}

operation Koniec {
  input := { @required name: String }
}

operation Kurwa {
  input := { @required name: String }
}

operation Gierek {
  input := { @required name: String }
}
