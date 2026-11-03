# 第 3 周（D15–D21，11/9–11/15）：泛型、HashMap 源码、Stream、IO、线程入门 → CourtHub v0.3

本周目标：会 PECS、能讲 HashMap put/get/resize；用 Stream 做统计；数据存成 JSON 文件；第一次亲眼看到并发 bug 并修好。
本周规则：Jackson 依赖和 JSON 读写的样板代码可以让 Sonnet 生成；统计逻辑、并发实验必须自己写。
本周 Lab：Lab2 并发入门（100 线程抢场地、volatile 可见性、i++ 丢失更新、jstack 看死锁），这是 Astra 第 3 周 JMM 实验的"入门版"，W8 再深挖。
刷题开始：LC 1、242（热身，每题先自己想 30 分钟）。

---

## D15 周一（2.5h）泛型与 PECS
- 0:00–0:40 泛型类、泛型方法、上界 `<T extends Comparable<T>>`、通配符 `? extends` / `? super`、类型擦除
- 0:40–1:50 写 `GenericUtils`：
  1. `static <T extends Comparable<T>> T max(List<T> list)`
  2. `static <T> void copy(List<? extends T> src, List<? super T> dst)`
  3. 把 `InMemory...Repository` 抽出泛型父接口 `Repository<T, ID>`（save / findById / findAll）
- 1:50–2:20 测试 + 故意写 3 行编译不过的代码（向 `List<? extends Number>` add），记录编译错误并解释
- 2:20–2:30 commit

自测：PECS 是什么，用 copy 方法举例？类型擦除带来什么限制？

## D16 周二（2.5h）HashMap 源码导读
- 0:00–1:20 让 Sonnet 带读 JDK 21 `HashMap` 的 `hash()`、`putVal`、`getNode`、`resize`、`treeifyBin`（只读关键路径）。你要回答：hash 为什么要高 16 位异或？下标为什么是 `(n-1) & hash`？什么时候扩容、什么时候树化（8 和 64）？
- 1:20–2:00 画 put 流程图存 `notes/hashmap-put.md`
- 2:00–2:30 Lab2-0：写一个 hashCode 恒为 1 的 key，放 1 万个进 HashMap，对比正常 key 的耗时（只做观察，不下结论到"一定慢多少"）

自测：HashMap put 流程？容量为什么是 2 的幂？

## D17 周三（2.5h）Lambda + Stream + LC 1
- 0:00–0:40 函数式接口、Lambda、方法引用；Stream 中间操作 vs 终结操作、惰性求值
- 0:40–1:40 在 `StatsService` 里用 Stream 实现：每个场地的预约次数、最热门时段 Top3、某用户本周预约总时长、按场地类型 `groupingBy` 统计；每个写一个循环版做对照
- 1:40–2:30 LC 1 两数之和：用 HashMap 优化上周的暴力解，写复杂度

自测：Stream 什么时候真正执行？什么时候不该用 Stream？

## D18 周四（2.5h）IO + JSON 持久化
- 0:00–0:30 字节流/字符流、缓冲流、`Files` 工具类、try-with-resources
- 0:30–1:50 加 Jackson（样板代码）。新增 `JsonBookingRepository implements BookingRepository`：启动加载、每次写操作后保存到 `data/bookings.json`；注意 LocalDate 序列化（`jackson-datatype-jsr310`）。把 `TimeSlot.date` 从 String 改为 `LocalDate`
- 1:50–2:20 测试：写入后重新加载数据一致（用 JUnit `@TempDir`）
- 2:20–2:30 commit；`data/` 加入 .gitignore

自测：为什么换实现时 service 一行都不用改？这体现了什么原则？

## D19 周五（2.5h）线程入门 + Lab2-1 抢场地
- 0:00–0:40 Thread / Runnable、start 与 run 区别、join、线程 6 种状态
- 0:40–1:50 Lab2-1：用 `ExecutorService`（先会用即可）+ `CountDownLatch` 让 100 个线程同时预约同一场地同一时段。预测成功几个 → 运行 10 次记录结果 → 很可能出现"多个成功"（超占）
- 1:50–2:20 用 `synchronized` 修复，再跑 10 次；写 `labs/evidence/lab02-overbooking.md`
- 2:20–2:30 commit

自测：为什么会超占？"检查-再-执行"（check-then-act）是什么？

## D20 周六（5h）Lab2-2/2-3 可见性、原子性、死锁 → v0.3
| 时间 | 任务 |
|---|---|
| 0:00–0:40 | 概念入门：原子性、可见性、有序性（只讲到能解释实验，happens-before 留到 W8） |
| 0:40–1:40 | Lab2-2 volatile 可见性：线程 A `while(!stop){}` 循环，主线程改 `stop = true`；不加 volatile 观察是否停不下来（结果与 JVM/CPU 有关，如实记录），加 volatile 后再跑 |
| 1:40–2:30 | Lab2-3 i++ 丢失更新：10 线程各加 1 万次，对比 普通 int / volatile int / synchronized / AtomicInteger 四种结果。结论：volatile 不保证原子性 |
| 2:30–3:30 | Lab2-4 死锁初见：两个线程按相反顺序锁两个场地对象；用 `jps` + `jstack <pid>` 找到 "Found one Java-level deadlock"，截取原始输出；改为按场地 id 顺序加锁修复 |
| 3:30–4:30 | 把 `BookingService.book()` 做成线程安全（同步粒度先用整个方法，记下"锁太粗"的问题留到 W8/W9），Lab2-1 测试转成 JUnit 并加 `@Timeout` |
| 4:30–5:00 | 证据写入 `labs/evidence/lab02-*.md`；`git tag v0.3` |

## D21 周日（5h）复盘、周测、反馈
| 时间 | 任务 |
|---|---|
| 0:00–0:40 | LC 242 有效的字母异位词（`int[26]` 和 HashMap 两种）；关掉答案重写 LC 1 |
| 0:40–1:40 | 闭卷：画 HashMap put 流程；写 `copy` PECS 方法；写出 check-then-act 的 bug 和修复 |
| 1:40–2:40 | 闭卷周测 20 题（泛型、HashMap、Stream、IO、线程基础、Lab2 结论） |
| 2:40–3:40 | 口述录音 3 题：HashMap put/resize；volatile 保证什么不保证什么；你怎么发现并修好超占 |
| 3:40–4:30 | 整理八股卡片、`notes/week03-summary.md` |
| 4:30–5:00 | 生成 `feedback/week03.md` 发给导师 |

## 第 3 周通过门槛
- [ ] 能闭卷画 HashMap put 流程，说出 8/64/0.75 分别是什么
- [ ] 能用 PECS 解释 copy 方法
- [ ] JSON 持久化可用，重启数据不丢，有测试
- [ ] Lab2 四个实验都有证据文件，预测与实际的差异写清楚
- [ ] 能用 jstack 输出指出死锁的两个线程和各自持有/等待的锁
- [ ] LC 1、242 次日能独立重写
