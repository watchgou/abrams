FROM openjdk:17-oracle

ENV TZ=Asia/Shanghai

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone && mkdir -p /server-java

WORKDIR /server-java

RUN mkdir ./logs

EXPOSE 7080

ADD ./build/libs/abrams-1.0.0.jar ./

ENV JAVA_MEM_OPTS="-XX:MinHeapSize=128m -XX:InitialHeapSize=128m -XX:MaxHeapSize=256m -XX:SoftMaxHeapSize=256m"

ENV JAVA_MEM_OPTS="$JAVA_MEM_OPTS -XX:+UseZGC -XX:ConcGCThreads=1 -XX:ParallelGCThreads=1 -XX:ZAllocationSpikeTolerance=2 -XX:ZCollectionInterval=3600"

ENV JAVA_MEM_OPTS="$JAVA_MEM_OPTS -XX:+UnlockDiagnosticVMOptions -XX:ZStatisticsInterval=10 -Xlog:gc*:/server-java/logs/gc.log"

CMD java $JAVA_MEM_OPTS -jar abrams-1.0.0.jar

