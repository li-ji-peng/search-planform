package com.lijipeng.search.manager.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class EsConfiguration {
    // 集群地址，多个用,隔开
    private static String hosts = "192.168.147.101,192.168.147.102,192.168.147.103";
    // 使用的端口号
    private static int port = 9200;
    // 使用的协议
    private static String schema = "http";
    private static ArrayList<HttpHost> hostList;
    // 连接超时时间
    private static int connectTimeOut = 1000;
    // 连接超时时间
    private static int socketTimeOut = 30000;
    // 获取连接的超时时间
    private static int connectionRequestTimeOut = 500;
    // 最大连接数
    private static int maxConnectNum = 100;
    // 最大路由连接数
    private static int maxConnectPerRoute = 100;

    static {
        hostList = new ArrayList<>();
        String[] hostArray = hosts.split(",");
        for (String host : hostArray) {
            hostList.add(new HttpHost(host, port, schema));
        }
    }

    @Bean
    public RestHighLevelClient client() {
        RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[hostList.size()]));
        // 异步httpclient连接延时配置
        builder.setRequestConfigCallback((RequestConfig.Builder requestConfigBuilder)-> {
                requestConfigBuilder.setConnectTimeout(connectTimeOut);
                requestConfigBuilder.setSocketTimeout(socketTimeOut);
                requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
                return requestConfigBuilder;

        });
        // 异步httpclient连接数配置
        builder.setHttpClientConfigCallback((HttpAsyncClientBuilder httpClientBuilder) ->{
                httpClientBuilder.setMaxConnTotal(maxConnectNum);
                httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                return httpClientBuilder;

        });
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }
}