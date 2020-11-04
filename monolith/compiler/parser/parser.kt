package lithe.parser

import lithe.lexer.TokenType.*
import lithe.lexer.TokenType
import lithe.lexer.Token

import lithe.compiler.OP
import lithe.compiler.Chunk

// Recursive descent parser
class Parser(tokens : MutableList<Token>) {
  private final val tokens : MutableList<Token> = tokens;
  private var current = 0;
  private final val chunk : Chunk = Chunk();

  private fun match(vararg types : TokenType) : Boolean {
    for (type in types ) {
      if (check(type)) {
        advance()
        return true;
      }
    }
    return false;
  }

  private fun check(type : TokenType) : Boolean {
    if (isAtEnd()) return false;
    return peek().type == type;
  }

  private fun advance() : Token {
    if (!isAtEnd()) this.current++;
    return previous();
  }

  private fun isAtEnd() : Boolean {
    return peek().type == EOF;
  }

  private fun peek() : Token {
    return this.tokens.get(current);
  }

  private fun previous() : Token {
    return this.tokens.get(current - 1);
  }

  public fun parse() {
    expression()
    this.chunk.emitByte(OP.RETURN)
    this.chunk.write()
  }

  protected fun expression() {
    add()
  }

  protected fun add(){
    multiply()
    while (match(PLUS,MINUS)){
      var op = previous()
      multiply()
      this.chunk.emitByte(if (op.type==PLUS) OP.ADD else OP.SUBTRACT)
    }
  }

  protected fun multiply(){
    primary()
    while (match(TIMES,DIVIDE)){
      var op = previous()
      primary()
      this.chunk.emitByte(if (op.type==TIMES) OP.MULTIPLY else OP.DIVIDE)
    }
  }

  protected fun primary(){
    var value = advance()
    if (value.type == NUMBER) this.chunk.emitBytes(OP.NUMBER,value.lexeme)
    if (value.type == TRUE || value.type == FALSE) this.chunk.emitBytes(OP.BOOLEAN,if (value.type==TRUE) "1" else "0")
  }
}