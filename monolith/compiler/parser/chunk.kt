package lithe.compiler

import java.io.File

// Enums classes for opcodes and constant types
enum class OP {
  RETURN,
  NUMBER,
  STRING,
  BOOLEAN,
  NEGATE,
  ADD,
  SUBTRACT,
  MULTIPLY,
  DIVIDE
}

// Chunk wrapper for opcode

class Chunk(){
  var code : MutableList<Any?> = mutableListOf();

  public fun write(){
    File("main.lxb").writeText("")
    File("main.lxb").bufferedWriter().use { out ->
    for (opcode in this.code){
      var op = opcode
      if (op is OP){
        op = op.ordinal.toString()
      }
      out.write(op as String + " ")
    }
    }
  }

  public fun emitByte(byte:Any?){
    this.code.add(byte)
    //println(this.code)
  }

  public fun emitBytes(byte1:Any?,byte2:Any?){
    emitByte(byte1)
    emitByte(byte2)
  }
}