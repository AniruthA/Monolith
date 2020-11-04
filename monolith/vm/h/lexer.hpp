#ifndef lexer_h
#define lexer_h

#include "common.h"

std::vector<std::string> lex(std::string code){
  std::vector<std::string> tokens;
  std::string currentToken = "";

  for (int i=0;i<code.size();){
    if (code[i] == ' '){
      tokens.push_back(currentToken); 
      currentToken = ""; 
      i++;
    } else {
      currentToken += code[i];
      i++;
    }  
  }
  return tokens;
}

#endif