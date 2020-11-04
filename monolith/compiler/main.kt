//package lithe

import java.io.File
import lithe.lexer.Lexer
import lithe.parser.Parser
import lithe.lexer.Token

fun main(args: Array<String>){
  // Opening up the file and lexing text
  val litheLexer : Lexer = Lexer(File("main.lith").readText())
  val tokens = litheLexer.scan()

  if (!litheLexer.hadError){
    // Single-pass compilation begins
    val litheParser : Parser = Parser(tokens)
    litheParser.parse()
  } else {
    // Handling lexing errors
    for (token in tokens) {
      if (token.type.ordinal == 0) println("Unexpected character '"+token.lexeme+"' on line "+token.line)
    }
  }  
}