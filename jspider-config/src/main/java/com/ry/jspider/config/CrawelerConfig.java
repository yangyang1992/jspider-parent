package com.ry.jspider.config;

import org.dom4j.Element;

import java.util.*;

/**
 * Created by yangyang on 2016/12/21.
 */
public class CrawelerConfig extends XMLConfig {
    private static Element getCraweler() {
        return loadConfig().document.getRootElement();
    }

    private static Element getTaskWorkerById(String id) {
        List taskWorkers = getCraweler().elements("taskWorker");
        for (Object object : taskWorkers) {
            Element taskWorker = (Element) object;
            if (taskWorker.attributeValue("id").equals(id)) {
                return taskWorker;
            }
        }
        return null;
    }

    private static Element getFilterChain(String id) {
        return getTaskWorkerById(id).element("filterChain");
    }

    private static Element getHttpConfig(String id) {
        return getTaskWorkerById(id).element("httpConfig");
    }

    private static Element getRequestConfig(String id) {
        return getHttpConfig(id).element("requestConfig");
    }

    private static Element getHeader(String id) {
        return getHttpConfig(id).element("header");
    }

    private static Element getSelectors() {
        return getCraweler().element("selectors");
    }

    public static String getCrawelerName() {
        return getCraweler().attributeValue("name");
    }

    public static int getTaskWorkerNumber() {
        Element spider = getCraweler();
        Iterator taskWorkers = spider.elements("taskWorker").iterator();
        int count = 0;
        while (taskWorkers.hasNext()) {
            count++;
            taskWorkers.next();
        }
        return count;
    }

    public static String getTaskWorkerHost(String id) {
        Element taskWorker = getTaskWorkerById(id);
        assert (taskWorker != null);
        return taskWorker.attributeValue("host");
    }

    public static int getTaskWorkerPort(String id) {
        Element taskWorker = getTaskWorkerById(id);
        assert (taskWorker != null);
        return Integer.parseInt(taskWorker.attributeValue("port"));
    }

    public static int getMaxTaskNumber(String id) {
        Element taskWorker = getTaskWorkerById(id);
        assert (taskWorker != null);
        return Integer.parseInt(taskWorker.attributeValue("maxTaskNum"));
    }

    public static String getTaskWorkerCharset(String id) {
        Element taskWorker = getTaskWorkerById(id);
        assert (taskWorker != null);
        return taskWorker.attributeValue("charset");
    }

    public static List<Map<String, String>> getFilters(String id) {
        List filters = getFilterChain(id).elements("filter");
        List<Map<String, String>> attributeList = new ArrayList<Map<String, String>>();
        for (Object object : filters) {
            Element filter = (Element) object;
            Map<String, String> currentFilterAttribute = new HashMap<String, String>();
            currentFilterAttribute.put("name", filter.attributeValue("name"));
            currentFilterAttribute.put("class", filter.attributeValue("class"));
            currentFilterAttribute.put("selectors", filter.attributeValue("selectors"));
            attributeList.add(currentFilterAttribute);
        }
        return attributeList;
    }

    public static int getConnectionRequestTimeout(String id) {
        Element requestConfig = getRequestConfig(id);
        return Integer.parseInt(requestConfig.attributeValue("connectionRequestTimeout"));
    }

    public static int getConnectTimeout(String id) {
        Element requestConfig = getRequestConfig(id);
        return Integer.parseInt(requestConfig.attributeValue("connectTimeout"));
    }

    public static int getSocketTimeout(String id) {
        Element requestConfig = getRequestConfig(id);
        return Integer.parseInt(requestConfig.attributeValue("socketTimeout"));
    }

    public static String getAccept(String id) {
        Element header = getHeader(id);
        return header.attributeValue("Accept");
    }

    public static String getConnection(String id) {
        Element header = getHeader(id);
        return header.attributeValue("Connection");
    }

    public static String getUser_Agent(String id) {
        Element header = getHeader(id);
        return header.attributeValue("User-Agent");
    }

    public static List<String> getSelectorList() {
        Element urls = getSelectors();
        List<String> selectorList = new ArrayList<String>();

        for (Object o : urls.elements("selector")) {
            selectorList.add(((Element) o).getStringValue());
        }
        return selectorList;
    }
}
