# javac 与 java

## 我的理解

**javac（Java Compiler，Java 编译器）**
- 负责将 `.java` 源代码编译成 `.class` 字节码文件
- 属于 JDK，不属于 JRE
- 例如：`javac Hello.java` 会生成 `Hello.class`

**java（Java 启动器）**
- 调用 JVM，执行 `.class` 字节码文件
- 将字节码翻译成机器码，让机器执行
- 属于 JRE（所以只装 JRE 也能运行 Java 程序）
- 例如：`java Hello` 会执行 `Hello.class` 里的 main 方法

## 关键点

1. **完整流程**：`Hello.java` --(javac 编译)--> `Hello.class` --(java 运行, JVM 执行)--> 程序运行
2. javac 属于 JDK，java 属于 JRE
3. 字节码（.class）是中间格式，人类看不懂，但 JVM 能看懂
4. 机器最终执行的是机器码，不是字节码也不是源码
5. javac 只做编译，不运行；java 只做运行，不编译

## 容易混淆的地方

- **能不能直接运行 `java Hello.java`？** JDK 11 之后可以，它会在内存中先编译再执行，不会在磁盘生成 .class 文件。但底层仍然是先编译成字节码，再由 JVM 执行
- **javac 和 java 谁属于 JDK？** javac 只在 JDK 里有，java 在 JRE 和 JDK 里都有
- **.class 文件能直接运行吗？** 不能，必须通过 `java` 命令启动 JVM 来执行
