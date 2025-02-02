# 1. 缓存热启动



> com.hanjx.dictdemo.cache
> 
> 子包均为实现

### 1.1. [CacheProvider.java](src/main/java/com/hanjx/dictdemo/cache/CacheProvider.java)

这是一个通用的缓存接口，定义了缓存提供者需要实现的基本方法：

- init()：初始化缓存，通常是在应用启动时从外部资源（如数据库或文件系统）加载缓存。
- clear()：清空缓存。
- reload()：重新加载缓存，通常是清空缓存后重新初始化。
- 
### 1.2. [AbstractCacheProvider.java](src/main/java/com/hanjx/dictdemo/cache/AbstractCacheProvider.java)

AbstractCacheProvider 是 CacheProvider 接口的抽象实现，它提供了 reload() 方法的默认实现：

- get()：获取当前缓存中的数据。
- reload()：调用 clear() 清空缓存，并调用 init() 重新初始化缓存。这个方法使得你无需每次都实现 reload()，可以直接继承并使用该方法。

AbstractCacheProvider 提供了一个默认的行为，可以由子类根据需要自定义 init()、get() 和 clear() 等具体的缓存操作。
### 1.3. [CacheRunner.java](src/main/java/com/hanjx/dictdemo/cache/CacheRunner.java)

CacheRunner 类实现了 CommandLineRunner 接口，并在 Spring Boot 应用启动时执行，它负责启动时初始化所有缓存：

- CommandLineRunner：Spring Boot 提供的接口，它会在 Spring Boot 启动后执行其 run() 方法。在这里，我们利用它来初始化缓存。
- applicationContext.getBeansOfType(CacheProvider.class)：获取所有实现了 CacheProvider 接口的 Spring Bean（即所有缓存提供者）。
- cacheProvider.init()：对每个 CacheProvider 调用 init() 方法，确保在应用启动时所有缓存都被初始化。

该类实现了缓存的自动初始化，在 Spring Boot 启动时，CacheRunner 会自动执行，将所有的缓存都初始化。

**总结：**
- 接口设计：通过 CacheProvider 提供缓存操作的标准接口，使得缓存的实现可以灵活替换。
- 抽象化实现：AbstractCacheProvider 提供了 get() 抽象方法 与 reload() 方法的默认实现，减少了子类需要实现的代码量。
- 自动缓存初始化：通过 CacheRunner 类在应用启动时自动初始化缓存。

# 2. 字典注解数据映射

> 注解：com.hanjx.dictdemo.annotation
> 
> 切面：com.hanjx.dictdemo.aspect

### 2.1. [Dict.java](src/main/java/com/hanjx/dictdemo/annotation/Dict.java)

- @Target(ElementType.FIELD)：这个注解只能作用于字段上，意味着它只能标注在实体类的属性字段上，而不能标注在方法、类等其他地方。
- @Retention(RetentionPolicy.RUNTIME)：表示这个注解将在运行时保留，能够通过反射访问。也就是说，注解在程序运行时会存在，程序可以使用反射机制来读取这些注解。
- dictType()：这是注解的唯一参数，表示字典的类型。可以理解为数据库或系统中定义的字典类别，例如性别、订单状态等。

**用法：**
当你在一个类的字段上加上如 [User.java](src/main/java/com/hanjx/dictdemo/entity/User.java) 中 `@Dict(dictType = "sex")` 注解时，表示该字段需要被字典映射处理，dictType 指定了字典的类型。

### 2.2. [DictAspect.java](src/main/java/com/hanjx/dictdemo/aspect/DictAspect.java)

DictAspect 是一个 AOP 切面类，主要负责在执行控制器方法时，拦截返回结果中的数据并进行字典字段转换。通过反射机制，读取实体对象的字段，并将被 @Dict 注解标记的字段进行字典转换，最终将原始的字典代码值转换为字典标签值。

**核心逻辑：**
- @Pointcut 注解：指定切点，execution(* com.hanjx.dictdemo.controller.*.*(..)) 表示拦截控制器包下所有方法。也就是说，所有控制器中的方法都会被该切面拦截。
- @Around 注解：在目标方法执行前后执行自定义的逻辑。在这里，目标方法执行完成后，DictAspect 会在返回的数据中查找带有 @Dict 注解的字段，并将它们的字典代码转换成对应的字典标签值。

**数据转换过程：**
- transformToListMap 和 transformToMap：这两个方法负责将实体对象（或对象列表）转换成 Map，方便后续的字典字段处理。
- setDictFields：该方法通过反射机制，获取所有字段，检查字段是否有 @Dict 注解，若有注解，则获取字典类型，通过字典缓存 sysDictCache 查找字典标签，并将其赋值到原数据的 Label 字段。

**说明：**
- 反射：通过 `Field[] declaredFields = Class.forName(className).getDeclaredFields()` 获取类的所有字段，遍历每个字段检查是否存在 @Dict 注解。
- 字典转换：根据 dictType 获取字典类型的数据，使用缓存 sysDictCache.get() 从 Redis 获取字典，生成字典的 Map，然后根据字段值查找对应的标签值，将标签值放入 Label 字段（如 sexLabel、statusLabel 等）。

# 作业
1. 当我们在 [User.java](src/main/java/com/hanjx/dictdemo/entity/User.java) 中添加一个 `private List<Order> orders` 时，并且 在 [Order.java](src/main/java/com/hanjx/dictdemo/entity/Order.java) 字段 `status` 注解了 @Dict， 即使会加载数据，
仍然不会加载 status 字段对应的字典数据，你能找出问题，并解决他吗？
2. 如果我们想在 [Order.java](src/main/java/com/hanjx/dictdemo/entity/Order.java) 中想通过 @Dict 注解来加载 [User.java](src/main/java/com/hanjx/dictdemo/entity/User.java) 的关联信息获取 name 字段时，应该怎么实现？