package demo

import libsqlite.all._

object Main {

  def main(
    args: Array[String]
  ): Unit = {

    import scalanative.unsafe._
    val db = stackalloc[Ptr[sqlite3]](1)

    val ppStmt = stackalloc[Ptr[sqlite3_stmt]](1)
    val pzTail = stackalloc[Ptr[CChar]](1)

    sqlite3_open(c"hello", db)
    try {
      sqlite3_prepare(!db, c"select 42", "select 42".length(), ppStmt, pzTail)

      sqlite3_step(!ppStmt)

      println(sqlite3_column_int(!ppStmt, 0))

    } finally sqlite3_close(!db)
  }

}
