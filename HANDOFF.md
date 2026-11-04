# HANDOFF：交接说明（新会话请先读这个文件）

## 这是什么
一个 100 天学习计划仓库：学生从"只会 Java 基础语法"开始，学到 Java 后端 + AI Agent，主项目是 CourtHub（校园场馆预约平台 + AI 预约助手）。
计划由导师模型制定；你是课堂老师，负责按计划逐日带学生完成任务。

## 阅读顺序
1. `CLAUDE.md`：课堂规则（必须遵守，尤其是"第 1～3 周不替学生写代码"和"证据纪律"）
2. `plan.md`：100 天总大纲、业务不变量、八股日历、周通过门槛
3. `weeks/week01.md`：本周逐日任务（week02、week03 是预排版，会按周反馈调整）
4. `progress.md`：已完成到哪一天
5. `templates/weekly-feedback.md`：每周日生成周反馈用

## 当前状态（2026-10-28 交接时）
- 计划开始日：2026-10-26（D1）。学生尚未正式完成 D1，从 D1 开始即可，日期顺延。
- 已安装：JDK 17.0.20（Temurin）、Git 2.47.1。
- 计划统一使用 JDK 21（Temurin）。JDK 21 尚未安装：D1 第一步由课堂老师协助安装，安装后把 JAVA_HOME 和 PATH 指向 JDK 21，用 `java -version` 和 `javac -version` 确认输出 21；原有 JDK 17 保留不删。
- 未安装：Maven（第 2 周 D8 才需要；IDEA 自带 Maven 也可）、IntelliJ IDEA（D1 安装）。
- 本目录还不是 git 仓库（D1 任务里执行 `git init`）。
- `labs/ notes/ bagu/ projects/courthub/` 目录尚未创建（D1 任务）。

## 最新状态（2026-11-03 22:30，课堂老师 Sonnet 写，交接给 VS Code Claude Code）

**D1 进度：Lab0-1 已完成调试与修改，剩收尾任务。**

### 已完成
1. **环境**：JDK 21 已装好（`C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot`），`java -version` 和 `javac -version` 都是 21.0.12.1。`JAVA_HOME` 指向 21，用户 Path 最前面是 `%JAVA_HOME%\bin`。JDK 17 在 `D:\dev\jdk17`，学生想删但暂未删。IDEA 2026.1 解压版位于 `D:\IDEA\idea-2026.1.win`，已配置用 JDK 21。所有证据记在 `notes/day01.md`。
2. **Git 和 GitHub**：仓库已初始化，第一次 commit `7e86e50`（12 个文件），已推送至 https://github.com/mojoel/courthub-100days （Public）。Git 配置 `user.name=mojoel`，`user.email=maxver0426@gmail.com`。
3. **摸底**：4.5 / 15，薄弱点是手动追踪循环和分支、自增/取模/强转、编译期错误与运行时异常的区别、写完整的方法。详见 `notes/day01.md`。
4. **Lab0-1 断点调试**：`labs/week01/day01/lab0-debug/src/BugHunt.java` 三个 bug 已找出并修改，运行输出正确（`415 / 4 / 2`）。学生用了 Step Over 和 Smart Step Into（Shift+F7），但还没做 Evaluate Expression。

### 待完成（收尾任务）
1. **Lab0-1 验收的三件事**（最高优先级）：
   - 补做 Evaluate Expression：在 `BugHunt.java` 第 31 行停下，用 Alt+F8 计算 `s > prev`，对照预测值。
   - 在 IDEA 里 Run 一次修改后的代码，把控制台三行输出原样记录。
   - 写证据文件 `labs/evidence/lab00-debug.md`，分五段：预测 / 实际现象 / 证据（含运行前 `357/3/0` 和调试时看到的变量值）/ 原因（三个 bug 各出现在 `i` 等于几）/ 结论。用学生自己的话。
2. **自测题**（2 道，口述或写下）：
   - JDK、JRE、JVM 的关系是什么？
   - `javac` 和 `java` 各做了什么？
   老师追问一层后进入下一步。
3. **八股卡片**（2 张）：题目就用上面两道自测题，写到 `bagu/java基础/JDK-JRE-JVM.md` 和 `bagu/java基础/javac与java.md`，一题一文件，用学生自己的话，老师只指出错误。
4. **notes/day01.md 补全**：还有一段空着："用自己的话说明 `JAVA_HOME` 和 `Path` 的作用，为什么只改 `JAVA_HOME` 不够？"学生已经口述过一次，要再精确一点并写进笔记。
5. **提交前检查**：先 `git status`，确认 `.idea/`、`lab0-debug.iml`、`out/` 有没有出现在未跟踪列表里。Lab 项目已经有 `.gitignore`（IDEA 自动生成），应该能盖住这些文件。如果 status 里没有它们，说明 gitignore 生效了，可以放心 `git add labs`。
6. **progress.md 追加一行**：格式 `| Day | 日期 | 完成 | 未完成/遗留 | 卡点 | 实际用时 |`。实际用时让学生估一个真实数字，本次从 10/28 晚上开始到现在。
7. **commit 并 push**：消息用 Conventional Commits 格式，比如 `feat(lab): complete day 1 debug lab and setup`。

### 关键约束（必须遵守）
- **第 1～3 周不替学生写代码**，只讲解、出题、Review。Lab0-1 的 `BugHunt.java` 是唯一例外（老师生成的带 bug 程序）。
- **证据纪律**：不伪造任何运行结果、测试结果。没运行就写"未运行"。Lab 证据文件结构固定为五段。
- **先预测，再运行**：凡是实验，先让学生写下"我预测会发生什么"，运行后再对比、解释。
- **三级提示**：学生卡住时，L1 只指方向 → L2 给伪代码或关键 API → L3 才给代码，并且要学生手敲、逐行解释。

### 遗留问题与提示
- 学生对"编译期错误 vs 运行时异常"还不清楚：摸底第 11 题以为数组越界是编译错误，追问时以为 `i <= scores.length` 也会编译错误。需要再讲一次：编译器不检查下标的值，只看类型；越界要等运行时才抛异常。
- `i <= scores.length - 1` 虽然对，但它是 `i < scores.length` 的绕远写法，可以提示学生改回常见写法。
- Evaluate Expression 还没做过，要补上这一项才算完整覆盖验收要求的四个操作（Step Over / Step Into / 查看变量 / Evaluate Expression）。

### 技术约定（供新模型确认环境）
- JDK 21（Temurin 21.0.12.1），已装在 `C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot`。
- Git 2.47.1，已配置并连接到 https://github.com/mojoel/courthub-100days 。
- IDEA 2026.1 解压版，`D:\IDEA\idea-2026.1.win\bin\idea64.exe`，项目 SDK 已设为 JDK 21。
- 仓库路径：`D:\claude_workzone\100days`。
- 包名约定：`com.courthub`（实验代码包名 `com.courthub.labs`），Day 1 还没用到。

### skill 状态
- 已安装 `grilling`（追问式对齐，来自 mattpocock/skills），设计决策时可用。
- 没有安装 ponytail 和 teach，因为它们和本课规则冲突。

### 给新模型的建议
开场先读这四个文件：HANDOFF.md（已更新）→ CLAUDE.md → weeks/week01.md → progress.md，然后说："我接手了 Day 1 的收尾，还剩 Lab 验收三件事、自测题、八股卡片、笔记补全、提交。我们从 Lab 的三件事开始，先补做 Evaluate Expression。"按 CLAUDE.md 的规则，不要一次塞太多，一件一件来。

## 每周循环
1. 周一到周六：按 `weeks/weekNN.md` 逐日上课，每天结束更新 `progress.md` 并 commit。
2. 周日：按模板生成 `feedback/weekNN.md`。
3. 学生把周反馈交给导师，导师生成下一周的 `weeks/weekNN.md`。课堂老师不要自行编写后续周计划，最多提出调整建议。