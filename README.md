
参考：

[springBoot(9)---定时任务,异步任务](https://www.cnblogs.com/qdhxhz/p/9058418.html)

[Spring Boot 定时任务单线程和多线程](https://blog.csdn.net/u013456370/article/details/79411952)

# 开启定时和异步的功能

@EnableScheduling ：
开启定时任务

@EnableAsync：
开启异步任务

``` 
@SpringBootApplication
@EnableScheduling //开启定时任务
@EnableAsync   //开启异步任务
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
```
# 定时任务的定义配置：

```
@Component
public class ScheduledTask {

    private Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    private int fixedDelayCount = 1;
    private int fixedRateCount = 1;
    private int initialDelayCount = 1;
    private int cronCount = 1;

    @Scheduled(fixedDelay = 5000)        //fixedDelay = 5000表示当前方法执行完毕5000ms后，Spring scheduling会再次调用该方法
    public void testFixDelay() {
        logger.info("===fixedDelay: 第{}次执行方法", fixedDelayCount++);
    }

    @Scheduled(fixedRate = 5000)        //fixedRate = 5000表示当前方法开始执行5000ms后，Spring scheduling会再次调用该方法
    public void testFixedRate() {
        logger.info("===fixedRate: 第{}次执行方法", fixedRateCount++);
    }

    @Scheduled(initialDelay = 1000, fixedRate = 5000)   //initialDelay = 1000表示延迟1000ms执行第一次任务
    public void testInitialDelay() {
        logger.info("===initialDelay: 第{}次执行方法", initialDelayCount++);
    }

    @Scheduled(cron = "0 0/1 * * * ?")  //cron接受cron表达式，根据cron表达式确定定时规则
    public void testCron() {
        logger.info("===cron: 第{}次执行方法", cronCount++);
    }

}
```
使用 【@Scheduled】 来创建定时任务 这个注解用来标注一个定时任务方法。 
通过看 @Scheduled源码可以看出它支持多种参数：
（1）cron：cron表达式，指定任务在特定时间执行；
（2）fixedDelay：表示上一次任务执行完成后多久再次执行，参数类型为long，单位ms；
（3）fixedDelayString：与fixedDelay含义一样，只是参数类型变为String；
（4）fixedRate：表示按一定的频率执行任务，参数类型为long，单位ms；
（5）fixedRateString: 与fixedRate的含义一样，只是将参数类型变为String；
（6）initialDelay：表示延迟多久再第一次执行任务，参数类型为long，单位ms；
（7）initialDelayString：与initialDelay的含义一样，只是将参数类型变为String；
（8）zone：时区，默认为当前时区，一般没有用到。

### cron表达式：

``` 
每隔5秒执行一次：*/5 * * * * ?
每隔1分钟执行一次：0 */1 * * * ?
每天23点执行一次：0 0 23 * * ?
每天凌晨1点执行一次：0 0 1 * * ?
每月1号凌晨1点执行一次：0 0 1 1 * ?
每月最后一天23点执行一次：0 0 23 L * ?
每周星期天凌晨1点实行一次：0 0 1 ? * L
在26分、29分、33分执行一次：0 26,29,33 * * * ?
每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?
```
在线Cron表达式生成器：http://cron.qqe2.com/

### 多线程

以上运行会发现是单线程，所以我们可以配置定时任务的多线程：
``` 
@Configuration
//所有的定时任务都放在一个线程池中，定时任务启动时使用不同都线程。
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //设定一个长度10的定时任务线程池
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
    }
}
```
# 异步任务的定义

在相关类或相关类的方法上添加注解 【@Async】 即可。

注意：需要将异步任务单独放到一个类中并配置@Component注解
















