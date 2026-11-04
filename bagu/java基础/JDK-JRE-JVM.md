# JDK、JRE、JVM 的关系

## 我的理解

**JVM（Java Virtual Machine，Java 虚拟机）**
- 负责执行字节码（.class 文件）
- 将字节码翻译成机器码让机器执行

**JRE（Java Runtime Environment，Java 运行环境）**
- JRE = JVM + Java 核心类库（如 String、ArrayList 等）
- 但是没有 javac（编译器）
- 只能运行 Java 程序，但不能自己编译

**JDK（Java Development Kit，Java 开发工具包）**
- JDK = JRE + javac 等开发工具
- 开发 Java 程序必须装 JDK

**包含关系**：JDK ⊃ JRE ⊃ JVM

## 关键点

1. Java 程序源码是 `.java`，要通过 `javac` 编译成 `.class`
2. JVM 执行 `.class` 字节码，翻译成机器码让机器执行
3. 只有 JDK 才能编译，JRE 和 JVM 都不能编译
4. 如果只是运行已经编译好的程序，JRE 就够了
5. 如果要开发（写代码 + 编译），必须装 JDK

## 容易混淆的地方

- **只装 JRE 能编译吗？** 不能，因为 JRE 里没有 javac 编译器，只有 JDK 才有
- **只装 JVM 能运行吗？** 不能，因为 JVM 需要依赖 Java 核心类库（如 String），这些类库在 JRE 里
- **JDK、JRE、JVM 哪个最大？** JDK 最大，JDK 包含 JRE，JRE 包含 JVM
