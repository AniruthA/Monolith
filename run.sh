#kotlinc -include-runtime -d monolith.jar monolith/compiler/lexer/*.kt monolith/compiler/parser/*.kt monolith/compiler/main.kt
#echo "Compiled monolith"

kotlin -classpath monolith.jar MainKt
echo "Finished running monolith.jar"

g++ -o vm monolith/vm/cpp/*.cpp
echo "Finished compiling lxbvm"

./vm
echo "Finished running lxbvm"