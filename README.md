# Codis/RebornDB Introduction

- [Codis](https://github.com/CodisLabs/codis)
- [RebornDB](https://github.com/reborndb/reborn)


##Features
- Support Reborn-java client
- Support Spring-data-redis connection

##How to use

1. see [Reborn-java](https://github.com/reborndb/reborn-java)
2. spring-data-redis xml config
```xml
	<bean id="xxx" class="com.le.smartconnect.adapter.spring.RebornConnectionFactory">
		<constructor-arg name="rebornJedisPool" ref="rebornPool" />
	</bean>
	
	<bean id="rebornPool" class="org.reborndb.reborn.RoundRobinJedisPool" >
		<constructor-arg name="zkAddr" value="${redis.zk.con}" />
		<constructor-arg name="zkSessionTimeoutMs" value="${redis.zk.timeout}" />
		<constructor-arg name="zkPath" value="${redis.zk.path}" />
		<constructor-arg name="poolConfig" ref="jedisPoolConfig" />
	</bean>
```

##Note
JDK7 is required to build and use reborn-java. If you want to use reborn-java with JDK6, you can copy the source files to your project, replace ThreadLocalRandom in BoundedExponentialBackoffRetryUntilElapsed and JDK7 specified grammar(maybe, not sure), and then compile with JDK6.

