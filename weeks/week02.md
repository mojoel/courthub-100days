# 第 2 周（D8–D14，11/2–11/8）：对象语义、集合、Maven + JUnit → CourtHub v0.2

本周目标：讲清值传递和 equals/hashCode 契约；会选集合；项目转 Maven，分层重构，写 15 个以上 JUnit 测试。
本周规则：仍然全部手写；只有 pom.xml 可以让 Sonnet 生成（标注"样板代码"）。
本周 Lab：Lab1 值传递 + 可变 key 实验（吸收 Astra 第 1 周的对象语义部分）。
前置：如果第 1 周有未通过项，D8、D9 前 30 分钟先补。

---

## D8 周一（2.5h）Maven + JUnit 5，项目搬家
- 0:00–0:40 Maven：坐标、`pom.xml`、标准目录、生命周期（clean/compile/test/package）、依赖范围（compile/test）
- 0:40–1:30 把 CourtHub 转成 Maven 项目（groupId `com.courthub`），只加 JUnit 5 依赖；命令行跑通 `mvn test`
- 1:30–2:20 把上周的 `BookingServiceCheck` 改写成 JUnit：`@Test`、`assertEquals`、`assertThrows`、`@BeforeEach`；对比手写测试，写 3 句"JUnit 帮我解决了什么"
- 2:20–2:30 commit

自测：`mvn test` 做了哪些阶段？`<scope>test</scope>` 是什么意思？

## D9 周二（2.5h）Lab1-1 值传递
在 `labs/week02/` 写 4 个方法，调用前在注释里写下预测：
```java
static void changePrimitive(int x) { x = 99; }
static void changeField(User u) { u.setName("changed"); }
static void reassign(User u) { u = new User("S999", "new"); }
static void appendString(String s) { s = s + "x"; }
```
- 用 JUnit 断言每个方法调用后的真实结果
- 画"调用方引用 → 形参是引用的副本 → 堆对象"图
- 写 `labs/evidence/lab01-pass-by-value.md`（预测 / 现象 / 原因 / 结论）

自测：Java 是值传递还是引用传递？为什么 `changeField` 能改字段，`reassign` 换不掉调用方对象？

## D10 周三（2.5h）equals/hashCode + 不可变对象
- 0:00–0:40 Object 的 equals/hashCode/toString；equals 五条性质；"equals 相等则 hashCode 必须相等"
- 0:40–1:40 把 `TimeSlot` 改成不可变类：final 类 + private final 字段 + 构造器校验（start < end 否则抛异常）+ 无 setter + 正确的 equals/hashCode。了解 `record`，写一个 `record CourtId(long value)` 做对比
- 1:40–2:20 测试：相等对象 hashCode 相等；能作为 HashMap key 取回；构造非法时段抛异常
- 2:20–2:30 commit

自测：只重写 equals 不重写 hashCode 会怎样？不可变对象有哪些好处？

## D11 周四（2.5h）Lab1-2 可变 key 实验 + 集合选择
- 0:00–0:50 Lab1-2：写一个可变的 `BadSlot`（有 setter，equals/hashCode 依赖可变字段），放进 HashSet 后修改字段，`contains` 返回什么？先预测再跑。证据写 `labs/evidence/lab01-mutable-key.md`
- 0:50–2:20 集合：List / Set / Map 接口；ArrayList、LinkedList、HashSet、LinkedHashMap、TreeMap 各自特点。练习：
  1. 用 `Map<Long, Court>` 按 id 查场地
  2. 用 `Map<Long, List<Booking>>` 按场地分组预约
  3. 用 `TreeMap` 让场地按名称排序输出
  4. 遍历 ArrayList 时调用 `list.remove` 复现 `ConcurrentModificationException`，改用 Iterator.remove / removeIf 修复
- 2:20–2:30 commit

自测：ArrayList 和 LinkedList 怎么选？fail-fast 是线程安全保证吗？

## D12 周五（2.5h）异常体系 + 错误码
- 0:00–0:40 Throwable / Error / Exception；checked vs unchecked；try-with-resources；finally 里写 return 的坑（先预测再跑）
- 0:40–1:50 设计 `ErrorCode` 枚举（SLOT_CONFLICT、USER_CONFLICT、INVALID_SLOT、COURT_NOT_FOUND、BOOKING_NOT_FOUND、ILLEGAL_STATE），`BookingException` 携带错误码；菜单层统一 catch 并打印友好提示
- 1:50–2:20 测试每种错误码都能被触发
- 2:20–2:30 commit

自测：checked 和 unchecked 异常的区别，业务异常为什么常用 unchecked？

## D13 周六（5h）分层重构 → v0.2
| 时间 | 任务 |
|---|---|
| 0:00–0:40 | 让 Sonnet 讲分层：model / repository / service / ui，为什么面向接口编程（不给代码） |
| 0:40–2:40 | 重构：`model`（Court、User、TimeSlot、Booking、枚举）；`repository`（`CourtRepository`、`BookingRepository` 接口 + `InMemory...` 实现）；`service`（`BookingService` 依赖接口，通过构造器注入）；`ui`（控制台菜单） |
| 2:40–3:20 | 新增状态迁移：`Booking.cancel()`、`checkIn()`、`markNoShow()`，非法迁移抛 ILLEGAL_STATE（不变量 4） |
| 3:20–4:30 | 补测试到 15 个以上：正常预约、场地冲突、用户冲突、相邻时段不冲突、跨开放时间、取消后可再约、重复取消报错、状态迁移 4 条 |
| 4:30–5:00 | Sonnet review → 修改 → `git tag v0.2` |

验收：`mvn test` 全绿；service 层代码里不出现 `new InMemory...`。

## D14 周日（5h）复盘、周测、反馈
| 时间 | 任务 |
|---|---|
| 0:00–1:00 | 闭卷重写：不可变 `TimeSlot`（含 equals/hashCode）+ 冲突判断方法，写完再跑测试 |
| 1:00–2:00 | 闭卷周测 20 题（值传递、equals/hashCode、集合、异常、Maven） |
| 2:00–3:00 | 口述 3 题并录音（每题 2 分钟）：值传递、equals/hashCode 契约、为什么 key 要不可变；Sonnet 追问一层 |
| 3:00–4:00 | 整理本周八股卡片，订正错题 |
| 4:00–4:30 | `notes/week02-summary.md` |
| 4:30–5:00 | 生成 `feedback/week02.md` 发给导师 |

## 第 2 周通过门槛
- [ ] `mvn test` 从命令行通过，测试 ≥ 15 个且每个都能说出在测什么
- [ ] 能闭卷写出不可变类 + 正确的 equals/hashCode
- [ ] 能用 Lab1 的代码讲清值传递
- [ ] 能解释可变 key 为什么让 HashSet"丢数据"
- [ ] service 依赖接口而非实现，能说出好处
- [ ] 周测 ≥ 14/20