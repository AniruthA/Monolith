#ifndef VM_H
#define VM_H

#include "common.h"

#define NUMBER_VAL(value) (Value {TYPE_NUMBER,{.number = value}})
#define BOOL_VAL(value) (Value {TYPE_BOOLEAN,{.boolean = value}})

#define AS_NUMBER(value) ((value).as.number)
#define AS_BOOL(value) ((value).as.boolean)

#define IS_NUMBER(value)  (value.type == TYPE_NUMBER)
#define IS_BOOL(value) (value.type == TYPE_BOOLEAN)

#define BINARY_OP(op, check, val, as)\
  do {\
    a = pop();\
    b = pop();\
    if (check(a) && check(b)){\
      push(val(as(a) op as(b)));\
    } else {\
    }\
  } while(0)\  

typedef enum {
  TYPE_NUMBER,
  TYPE_BOOLEAN
} Type;

typedef enum {
  RETURN,
  NUMBER,
  STRING,
  BOOLEAN,
  NEGATE,
  ADD,
  SUBTRACT,
  MULTIPLY,
  DIVIDE
} OpCodes;

struct Value {
  Type type;
  union {
    double number;
    bool boolean;
  } as;
};

class VM {
  private:
  std::vector<std::string> instructions;
  long int ip = 0;
  Value stack[255];
  int stacktop = 0;

  protected:
  void push(Value val){
    stack[stacktop] = val;
    stacktop++;
  }

  Value pop(){
    return stack[--stacktop];
  }

  public:
  VM(std::vector<std::string> code){
    instructions = code;
  }

  void execute(){
    Value a,b;

    for (;;){
      uint8_t instruction = (uint8_t) instructions[ip].at(0) - '0';

      switch (instruction){
        case NUMBER:
          ip++;
          push(NUMBER_VAL(stod(instructions[ip++])));
          break;
        case BOOLEAN:
          ip++;
          push(BOOL_VAL(stoi(instructions[ip++])));
          break;
        
        case NEGATE:
          a = pop();
          if (IS_NUMBER(a)){
            push(NUMBER_VAL(-AS_NUMBER(a)));
          } else {
            //throw RUNTIME_ERROR
          }
          ip++;
          break;
        case ADD: 
          BINARY_OP(+,IS_NUMBER,NUMBER_VAL,AS_NUMBER); 
          break;
        case SUBTRACT: 
          BINARY_OP(-,IS_NUMBER,NUMBER_VAL,AS_NUMBER); 
          break;
        case MULTIPLY: 
          BINARY_OP(*,IS_NUMBER,NUMBER_VAL,AS_NUMBER); 
          break;
        case DIVIDE: 
          BINARY_OP(/,IS_NUMBER,NUMBER_VAL,AS_NUMBER); 
          break;
        case RETURN:
          pop();
          return;
        default:
          return;
      }
    }
  }
};

#undef AS_NUMBER
#undef IS_NUMBER
#undef NUMBER_VAL
#undef BINARY_OP
#endif