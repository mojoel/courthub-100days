# Day 1 笔记（2026-10-28）

## 环境验证（原始输出，抄自终端截图）

```
PS> java -version
openjdk version "21.0.12.1" 2026-08-18 LTS
OpenJDK Runtime Environment Temurin-21.0.12.1+1 (build 21.0.12.1+1-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.12.1+1 (build 21.0.12.1+1-LTS, mixed mode, sharing)

PS> javac -version
javac 21.0.12.1

PS> git --version
git version 2.47.1.windows.1

PS> where.exe java
C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot\bin\java.exe
D:\dev\jdk17\bin\java.exe

PS> echo $env:JAVA_HOME
C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot
```

## 过程记录

- 装完 JDK 21 后第一次 `java -version` 仍是 17。`where.exe java` 只有 `D:\dev\jdk17\bin\java.exe`，说明 21 没进 PATH。
- 用户变量 `JAVA_HOME` 指向 `D:\dev\jdk17`，盖住了系统变量里的 21。把它改成 21 的路径，并把用户 Path 里的 `D:\dev\jdk17\bin` 改成 `%JAVA_HOME%\bin` 后，`java`/`javac` 都变成 21。
- `where.exe java` 第二行仍有 `D:\dev\jdk17\bin\java.exe`，说明别处的 Path 还留着一条 jdk17。排在 21 后面，暂不影响。
- IDEA 为解压版，位于 `D:\IDEA\idea-2026.1.win`。

## 我的预测与结果

- 预测：装完 21 不改设置，`java -version` 显示 17。
- 实际：17。
- 学生的原因（原话）：因为没有配置 21 的环境变量。
- 老师指出的偏差：见下一轮对话，修正后的原因待学生用自己的话补充。

## JAVA_HOME 和 Path 的作用

**`JAVA_HOME` 和 `Path` 都是环境变量：**
- `JAVA_HOME`：用于存储 JDK 的安装路径，比如 `C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot`。很多工具（Maven、Tomcat）会读取这个变量来找 JDK。
- `Path`：指明这些 JDK 的可执行文件在哪里，让系统能找得到，然后运行指定的 JDK。

**为什么只改 `JAVA_HOME` 不够？**
- 如果只改了 `JAVA_HOME`，但 `Path` 里没有 `%JAVA_HOME%\bin`，或者 `Path` 里直接写的是旧版本路径（如 `D:\dev\jdk17\bin`），命令行输入 `java` 时系统仍然会找到旧版本。
- 正确做法：改 `JAVA_HOME` 指向 JDK 21，**并且**把 `Path` 里的路径改成 `%JAVA_HOME%\bin`。这样只要改一次 `JAVA_HOME`，`Path` 就会自动跟着变。

## git status（第一次提交前，抄自终端截图）

预测：都不会出现（文件和空文件夹都不显示）。
实际：

```
On branch main

No commits yet

Untracked files:
        CLAUDE.md
        HANDOFF.md
        notes/
        plan.md
        progress.md
        templates/
        weeks/

nothing added to commit but untracked files present (use "git add" to track)
```

结论：有文件的文件和文件夹会列出；空文件夹 labs/ bagu/ projects/ 没有列出，因为 Git 只跟踪文件。
- 远程仓库：https://github.com/mojoel/courthub-100days

## 第一次提交与推送（抄自终端截图）

- 第一次 push 报错 `fatal: unable to access ... Recv failure: Connection was reset`（网络问题），重试后弹出 Git Credential Manager 登录窗口，用浏览器授权后成功。
- commit：`7e86e50 chore: init course repo with plan, notes and folder skeleton`，12 个文件。
- push 输出片段：`* [new branch]      main -> main` / `branch 'main' set up to track 'origin/main'.`
- 预测：push 后页面能看到文件夹和文件。实际：GitHub 页面显示 bagu、labs、notes、projects/courthub、templates、weeks 和 4 个 md 文件，1 Commit。预测正确。
- 仓库当前是 Public，commit 作者邮箱会随提交公开。

## 闭卷摸底（基线）

得分：4.5 / 15（按 Java 语言规则人工批改，未运行代码；Q14 给半分，其余无部分分）。
背景：学生学过 C++ 的 for 和数组，一年多没碰，能看懂函数。

| 题 | 结果 | 要点 |
|---|---|---|
| 1 | 错 | 漏掉 `int a = 3.5`（double 转 int 丢精度）和 `long c = 10000000000`（缺 L 后缀），只判对了 char |
| 2 | 错 | y 应为 3.0，不是 3.5（先整数除法再转 double） |
| 3 | 错 | b = 22，a = 12 |
| 4 | 错 | 应为 2、-2、-3 |
| 5 | 对 | 短路求值，原因未写 |
| 6 | 错 | 应为 two three other（fall-through） |
| 7 | 错 | 应为 0 1 3（continue 跳过 2） |
| 8 | 错 | 应为 6 |
| 9 | 对 | 243，5 次 |
| 10 | 错 | 0 / false / null，后两个不会 |
| 11 | 错 | 编译通过，运行时抛 ArrayIndexOutOfBoundsException，学生以为是编译错误 |
| 12 | 对 | 99 |
| 13 | 对 | 值传递，原因对 |
| 14 | 半对 | 结论对，原因错：重载看参数列表，与返回值无关 |
| 15 | 错 | 无方法签名；sum 在循环内每轮归零；加的是 i 不是 a[i]；无 return |

薄弱点：1) 手动逐轮追踪循环和分支（Q6、7、8）；2) 自增、取模、强转的规则（Q2、3、4）；3) 编译期错误与运行时异常的区别（Q1、11）；4) 写完整的方法（Q15）。
