package com.junker.httpmock.util;

import java.io.File;
import javax.servlet.ServletException;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author sunli
 */
public class TomcatHttpServer {
    private static Tomcat tomcat;

    public static class Builder {
        private String context = "";
        private String webapp = "";
        private String baseDir = new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();
        private int port = 8080;
        private String protocol = "org.apache.coyote.http11.Http11NioProtocol";
        private int maxThreads = 100;
        private int maxConnections = 100;
        private int connectionTimeout = 60000;
        private String uriEncoding = "UTF-8";
        private int maxKeepAliveRequests = -1;

        public String getBaseDir() {
            return baseDir;
        }

        public Builder setBaseDir(String baseDir) {
            this.baseDir = baseDir;
            return this;
        }

        public int getPort() {
            return port;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public String getProtocol() {
            return protocol;
        }

        public Builder setProtocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public int getMaxThreads() {
            return maxThreads;
        }

        public Builder setMaxThreads(int maxThreads) {
            this.maxThreads = maxThreads;
            return this;
        }

        public int getMaxConnections() {
            return maxConnections;
        }

        public Builder setMaxConnections(int maxConnections) {
            this.maxConnections = maxConnections;
            return this;
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public Builder setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public String getUriEncoding() {
            return uriEncoding;
        }

        public Builder setUriEncoding(String uriEncoding) {
            this.uriEncoding = uriEncoding;
            return this;
        }

        public int getMaxKeepAliveRequests() {
            return maxKeepAliveRequests;
        }

        public Builder setMaxKeepAliveRequests(int maxKeepAliveRequests) {
            this.maxKeepAliveRequests = maxKeepAliveRequests;
            return this;
        }

        public String getContext() {
            return context;
        }

        public Builder setContext(String context) {
            this.context = context;
            return this;
        }

        public String getWebapp() {
            return webapp;
        }

        public Builder setWebapp(String webapp) {
            this.webapp = webapp;
            return this;
        }

        public TomcatHttpServer build() {
            if (StringUtils.isBlank(context)) {
                throw new IllegalArgumentException("context not set!");
            }
            if (StringUtils.isBlank(webapp)) {
                throw new IllegalArgumentException("webapp directory not set!");
            }
            File web = new File(webapp);
            if (!web.isDirectory()) {
                throw new IllegalArgumentException("webapp is not directory!");
            }
            if (!web.exists()) {
                throw new IllegalArgumentException("webapp directory not exists!");
            }
            return new TomcatHttpServer(this);
        }
    }

    public TomcatHttpServer(Builder builder) {
        tomcat = new Tomcat();
        // tomcat.getHost().setDeployOnStartup(true);
        // 设置appbase则会将该目录下的所有web应用进行管理，并且通过设置自动发布从而会实现自动解压war
        // tomcat.getHost().setAppBase(WORKSPACE);
        // tomat work目录 可设置成tmp目录
        tomcat.setBaseDir(FilenameUtils.concat(builder.getBaseDir(), String.valueOf(builder.getPort())));
        // tomcat.setHostname(ip);

        // tomcat.enableNaming();
        Connector connector = new Connector(builder.getProtocol());
        connector.setPort(builder.getPort());
        connector.setProperty("maxThreads", String.valueOf(builder.getMaxThreads()));
        connector.setProperty("maxConnections", String.valueOf(builder.getMaxConnections()));

        connector.setProperty("connectionTimeout", String.valueOf(builder.getConnectionTimeout()));
        connector.setURIEncoding(builder.getUriEncoding());
        connector.setProperty("maxKeepAliveRequests", String.valueOf(builder.getMaxKeepAliveRequests()));
        tomcat.getService().addConnector(connector);
        tomcat.setConnector(connector);
        tomcat.addWebapp(builder.getContext(), builder.getWebapp());
    }

    public void startup() {
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new IllegalStateException("Failed to start tomcat server", e);
        }
    }

}
