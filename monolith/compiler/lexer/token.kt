package lithe.lexer

// TokenType enum outlines all the different tokens

enum class TokenType {
  UNKNOWN,
  NUMBER,
  STRING,
  IDENTIFIER,
  TRUE,
  FALSE,
  
  PLUS,
  MINUS,
  TIMES,
  DIVIDE,
  OPEN_PAREN,
  CLOSE_PAREN,
  OPEN_BRACK,
  CLOSE_BRACK,
  OPEN_CURLY,
  CLOSE_CURLY,
  EQUAL,
  NOT,
  GREATER,
  LESSER,
  GREATER_EQUAL,
  LESSER_EQUAL,
  EQUAL_EQUAL,
  NOT_EQUAL,
  FUNC,

  CLASS, VOID, WHILE, FOR, IF, ELSE, INT, FLOAT, STRING_KEYWORD, BOOL,LET, RETURN,
  
  EOL,
  EOF,
}

class Token(typeOfToken : TokenType, lexemestr : String, line : Int) {
  var type : TokenType = typeOfToken
  var lexeme : String = lexemestr
  var line : Int = line
}