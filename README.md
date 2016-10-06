# pdf-index
Elastic Search PDF indexer with docker compose image

Ideally you could just do
cd indexserver
mvn clean package
cd ..
docker-compose up

But development-time it might be convenient to separately run elasticsearch with pdf plugin, and manually run spring boot maven
There are some convenience scripts provided in elastic-folder
indexer is just self-contained maven spring boot project, and refers to elasticsearch cluster at localhost:9300

- Containers are now able to see eachothers ports using docker-compose v2 networking
- Containers are now mounting local folders under indexserver and elastic projects to persist data and find pdfs to index (elastic/esdata, indexserver/pdf-files)


## Issues

- Note: At least on windows 10, you have to enable drive sharing if you want to have the mounted volumes to work. This seems to be current issue with Docker for Windows.
https://github.com/docker/docker/issues/23992
https://github.com/docker/docker/issues/23900
https://github.com/docker/docker/issues/22981

- Currently indexing runs out of heapspace with large amounts of pdf files

javamicroservicescloudandcontainers-migrating-160922012702.pptx
Reading file as byte array...
File was read
Content was base64 encoded, size 95330604
2016-10-06 07:31:14.759 ERROR 10451 --- [nio-8080-exec-3] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is UncategorizedExecutionException[Failed execution]; nested: OutOfMemoryError[Java heap space];] with root cause

java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:3332) ~[na:1.8.0_66]
	at java.lang.AbstractStringBuilder.expandCapacity(AbstractStringBuilder.java:137) ~[na:1.8.0_66]
	at java.lang.AbstractStringBuilder.ensureCapacityInternal(AbstractStringBuilder.java:121) ~[na:1.8.0_66]
	at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:421) ~[na:1.8.0_66]
	at java.lang.StringBuilder.append(StringBuilder.java:136) ~[na:1.8.0_66]
	at org.elasticsearch.action.index.IndexRequest.toString(IndexRequest.java:748) ~[elasticsearch-2.4.0.jar:2.4.0]
	at org.elasticsearch.action.support.replication.ReplicationRequest.getDescription(ReplicationRequest.java:260) ~[elasticsearch-2.4.0.jar:2.4.0]
	at org.elasticsearch.action.support.replication.ReplicationRequest.createTask(ReplicationRequest.java:237) ~[elasticsearch-2.4.0.jar:2.4.0]
	at org.elasticsearch.action.support.ChildTaskActionRequest.createTask(ChildTaskActionRequest.java:68) ~[elasticsearch-2.4.0.jar:2.4.0]
	at org.elasticsearch.tasks.TaskManager.register(TaskManager.java:67) ~[elasticsearch-2.4.0.jar:2.4.0]
	at org.elasticsearch.transport.RequestHandlerRegistry.processMessageReceived(RequestHandlerRegistry.java:71) ~[elasticsearch-2.4.0.jar:2.4.0]
	at org.elasticsearch.transport.netty.MessageChannelHandler.handleRequest(MessageChannelHandler.java:227) ~[elasticsearch-2.4.0.jar:2.4.0]
	at org.elasticsearch.transport.netty.MessageChannelHandler.messageReceived(MessageChannelHandler.java:116) ~[elasticsearch-2.4.0.jar:2.4.0]
	at org.jboss.netty.channel.SimpleChannelUpstreamHandler.handleUpstream(SimpleChannelUpstreamHandler.java:70) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.channel.DefaultChannelPipeline.sendUpstream(DefaultChannelPipeline.java:564) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.channel.DefaultChannelPipeline$DefaultChannelHandlerContext.sendUpstream(DefaultChannelPipeline.java:791) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.channel.Channels.fireMessageReceived(Channels.java:296) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.handler.codec.frame.FrameDecoder.unfoldAndFireMessageReceived(FrameDecoder.java:462) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.handler.codec.frame.FrameDecoder.callDecode(FrameDecoder.java:443) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.handler.codec.frame.FrameDecoder.messageReceived(FrameDecoder.java:310) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.channel.SimpleChannelUpstreamHandler.handleUpstream(SimpleChannelUpstreamHandler.java:70) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.channel.DefaultChannelPipeline.sendUpstream(DefaultChannelPipeline.java:564) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.channel.DefaultChannelPipeline$DefaultChannelHandlerContext.sendUpstream(DefaultChannelPipeline.java:791) ~[netty-3.10.6.Final.jar:na]
	at org.elasticsearch.common.netty.OpenChannelsHandler.handleUpstream(OpenChannelsHandler.java:75) ~[elasticsearch-2.4.0.jar:2.4.0]
	at org.jboss.netty.channel.DefaultChannelPipeline.sendUpstream(DefaultChannelPipeline.java:564) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.channel.DefaultChannelPipeline.sendUpstream(DefaultChannelPipeline.java:559) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.channel.Channels.fireMessageReceived(Channels.java:268) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.channel.Channels.fireMessageReceived(Channels.java:255) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.channel.socket.nio.NioWorker.read(NioWorker.java:88) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.channel.socket.nio.AbstractNioWorker.process(AbstractNioWorker.java:108) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.channel.socket.nio.AbstractNioSelector.run(AbstractNioSelector.java:337) ~[netty-3.10.6.Final.jar:na]
	at org.jboss.netty.channel.socket.nio.AbstractNioWorker.run(AbstractNioWorker.java:89) ~[netty-3.10.6.Final.jar:na]

It seems this error happens at create_mapping plugin, not at elasticsearch level

[2016-10-06 04:50:41,647][DEBUG][action.admin.indices.mapping.put] [Hag] failed to put mappings on indices [[pdfs]], type [pdffile]
MapperParsingException[Mapping definition for [content] has unsupported parameters:  [store : false]]
	at org.elasticsearch.index.mapper.DocumentMapperParser.checkNoRemainingFields(DocumentMapperParser.java:171)
	at org.elasticsearch.index.mapper.DocumentMapperParser.checkNoRemainingFields(DocumentMapperParser.java:165)
	at org.elasticsearch.index.mapper.object.ObjectMapper$TypeParser.parseProperties(ObjectMapper.java:311)
	at org.elasticsearch.index.mapper.object.ObjectMapper$TypeParser.parseObjectOrDocumentTypeProperties(ObjectMapper.java:222)
	at org.elasticsearch.index.mapper.object.RootObjectMapper$TypeParser.parse(RootObjectMapper.java:139)
	at org.elasticsearch.index.mapper.DocumentMapperParser.parse(DocumentMapperParser.java:118)
	at org.elasticsearch.index.mapper.DocumentMapperParser.parse(DocumentMapperParser.java:99)
	at org.elasticsearch.index.mapper.MapperService.parse(MapperService.java:549)
	at org.elasticsearch.cluster.metadata.MetaDataMappingService$PutMappingExecutor.applyRequest(MetaDataMappingService.java:257)
	at org.elasticsearch.cluster.metadata.MetaDataMappingService$PutMappingExecutor.execute(MetaDataMappingService.java:230)
	at org.elasticsearch.cluster.service.InternalClusterService.runTasksForExecutor(InternalClusterService.java:468)
	at org.elasticsearch.cluster.service.InternalClusterService$UpdateTask.run(InternalClusterService.java:772)
	at org.elasticsearch.common.util.concurrent.PrioritizedEsThreadPoolExecutor$TieBreakingPrioritizedRunnable.runAndClean(PrioritizedEsThreadPoolExecutor.java:231)
	at org.elasticsearch.common.util.concurrent.PrioritizedEsThreadPoolExecutor$TieBreakingPrioritizedRunnable.run(PrioritizedEsThreadPoolExecutor.java:194)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
[2016-10-06 04:50:42,325][INFO ][cluster.routing.allocation] [Hag] Cluster health status changed from [RED] to [YELLOW] (reason: [shards started [[pdfs][4], [pdfs][4]] ...]).
[2016-10-06 04:50:59,093][INFO ][cluster.metadata         ] [Hag] [pdfs] create_mapping [pdffile]
java.lang.OutOfMemoryError: Java heap space

