package lithe.lexer;

import lithe.lexer.TokenType.*

class Lexer(source : String){
  private var current : Int = 0
  private final val source : String = source
  private var currentLexeme : String = ""
  private var line : Int = 0
  private final val keywords = mapOf("while" to WHILE,"true" to TRUE, "false" to FALSE,"class" to CLASS,"void" to VOID, "if" to IF, "else" to ELSE, "int" to INT, "float" to FLOAT, "string" to STRING_KEYWORD, "bool" to BOOL, "let" to LET, "return" to RETURN)

  public var hadError : Boolean = false
  
  private fun makeToken(type : TokenType) : Token {
    val tok : Token = Token(type,this.currentLexeme,this.line)
    this.currentLexeme = ""
    return tok
  }

  private fun peekChar() : Char {
    return if (this.current < this.source.length) this.source[this.current] else '\r'
  }

  private fun advanceChar() : Char {
    if (this.current < this.source.length && this.source[this.current]=='\n') this.line++
    return if (this.current < this.source.length) this.source[this.current++] else '\r'
  }

  protected fun number() : Token {
    while (peekChar().isDigit()){
      this.currentLexeme += advanceChar()
    }
    return makeToken(NUMBER)
  }

  protected fun identifier() : Token {
    while (peekChar().isDigit()||peekChar().isLetter()||peekChar() == '_'){
      this.currentLexeme += advanceChar()
    }
    if (keywords.containsKey(this.currentLexeme)){
      return makeToken(keywords[this.currentLexeme]!!)
    }
    return makeToken(IDENTIFIER)
  }

  protected fun string() : Token {
    while (peekChar()!='"'){
      this.currentLexeme += advanceChar()
    }
    advanceChar()
    return makeToken(STRING)
  }

  protected fun whitespace() {
    while (peekChar() == ' '||peekChar() == '\t'){
      advanceChar();
    }
  }

  public fun scanToken() : Token {
    var c : Char = advanceChar()
    this.currentLexeme += c
    if (this.current < this.source.length && this.source[this.current]=='\n') this.line++
    whitespace()

    when (c){
      '\n',';'-> return makeToken(EOL)
      '+'-> return makeToken(PLUS)
      '-'-> if (peekChar() == '>') {this.currentLexeme += advanceChar();return makeToken(FUNC)} else {return makeToken(MINUS)}
      '*'-> return makeToken(TIMES)
      '/'-> return makeToken(DIVIDE)
      '('-> return makeToken(OPEN_PAREN)
      ')'-> return makeToken(CLOSE_PAREN)
      '{'-> return makeToken(OPEN_CURLY)
      '}'-> return makeToken(CLOSE_CURLY)
      '['-> return makeToken(OPEN_BRACK)
      ']'-> return makeToken(CLOSE_BRACK)
      '='-> if (peekChar() == '=') {this.currentLexeme += advanceChar();return makeToken(EQUAL_EQUAL)} else {return makeToken(EQUAL)}
      '>'-> if (peekChar() == '=') {this.currentLexeme += advanceChar();return makeToken(GREATER_EQUAL)} else {return makeToken(GREATER)}
      '<'-> if (peekChar() == '=') {this.currentLexeme += advanceChar();return makeToken(LESSER_EQUAL)} else {return makeToken(LESSER)}
      '"'-> return string()
      '\r'-> return makeToken(EOF)
      else -> {
        if (c.isDigit()){
          return number()
        } else if (c.isLetter()){
          return identifier()
        }
      }
    }
    this.hadError = true
    return makeToken(UNKNOWN)
  }

  public fun scan() : MutableList<Token> {
    val tokens = mutableListOf<Token>()
    while (peekChar()!='\r') {
      var tok = scanToken()
      tokens.add(tok)
    } 
    tokens.add(Token(EOF,"",this.line))
    return tokens
  } 
}