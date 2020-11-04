#include "../h/common.h"
#include "../h/vm.hpp"
#include "../h/lexer.hpp"

int main(){
  std::ifstream bytecodefile("main.lxb");
  std::string code, line;
  while (getline (bytecodefile, line)) {
    code += line;
  }
  bytecodefile.close();

  std::vector<std::string> tokens = lex(code);

  VM litheVM = VM(tokens);
  litheVM.execute();
}