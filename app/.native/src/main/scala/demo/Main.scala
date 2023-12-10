package demo

import libsqlite.all._

import scala.scalanative.unsafe.Zone

object Main {

  def main(
    args: Array[String]
  ): Unit = Zone { case given Zone =>
    import scalanative.unsafe._
    val db = alloc[Ptr[sqlite3]](1)
    val ppStmt = alloc[Ptr[sqlite3_stmt]](1)
    val pzTail = alloc[Ptr[CChar]](1)

    sqlite3_open(c"hello", db)
    try {
      var q = "create table if not exists hello (a int, b int, c int)"

      sqlite3_prepare_v2(!db, toCString(q), q.length, ppStmt, pzTail)
      sqlite3_step(!ppStmt)

      q = "insert into hello values(2,1,37)"

      sqlite3_prepare_v2(!db, toCString(q), q.length, ppStmt, pzTail)
      sqlite3_step(!ppStmt)

      q = "select count(*) from hello"

      sqlite3_prepare_v2(!db, toCString(q), q.length, ppStmt, pzTail)
      sqlite3_step(!ppStmt)

      println(sqlite3_column_int(!ppStmt, 0))

    } finally sqlite3_close_v2(!db)
  }

}
