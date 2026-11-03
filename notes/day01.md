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
