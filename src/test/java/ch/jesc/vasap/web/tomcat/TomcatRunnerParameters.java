package ch.jesc.vasap.web.tomcat;

import java.io.File;
import java.io.IOException;

public class TomcatRunnerParameters {

	private String currentDir;

	/** Web context, like /registres/refinf */
	private String contextPath;

	/** HTTP Port */
	private Integer port;

	/** AJP Port null means ignore it */
	private Integer ajpPort;

	/** Path to context.xml, like appDir/devel/context.xml */
	private String webappDir;

	/** Path to tomcat-users.xml, like appDir/devel/tomcat-users.xml */
	private String contextXmlPath;

	private String userDatabasePath;

	private String domain;

	/** for LB only */
	private String jvmRoute;

	public TomcatRunnerParameters() throws IOException {
		final File file = new File(".");
		currentDir = file.getCanonicalPath();
	}

	public void setCurrentDir(String aCurrentDir) {
		this.currentDir = aCurrentDir;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public void setWebappDir(String aWebappDir) {
		this.webappDir = aWebappDir;
	}

	public void setContextXmlPath(String aContextXmlPath) {
		this.contextXmlPath = aContextXmlPath;
	}

	public void setUserDatabasePath(String userDatabasePath) {
		this.userDatabasePath = userDatabasePath;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getCurrentDir() {
		return currentDir;
	}

	public String getContextPath() {
		return contextPath;
	}

	public Integer getPort() {
		return port;
	}

	public String getWebappDir() {
		return webappDir;
	}

	public String getContextXmlPath() {
		return contextXmlPath;
	}

	public String getUserDatabasePath() {
		return userDatabasePath;
	}

	public String getDomain() {
		return domain;
	}

	public Integer getAjpPort() {
		return ajpPort;
	}

	public void setAjpPort(Integer ajpPort) {
		this.ajpPort = ajpPort;
	}

	public String getJvmRoute() {
		return jvmRoute;
	}

	public void setJvmRoute(String jvmRoute) {
		this.jvmRoute = jvmRoute;
	}

	public static Builder build() throws IOException {
		return new Builder();
	}

	public static class Builder {

		TomcatRunnerParameters bean;

		private Builder() throws IOException {
			bean = new TomcatRunnerParameters();
		}

		public TomcatRunnerParameters get() {
			return bean;
		}

		public Builder setCurrentDir(String aCurrentDir) {
			bean.currentDir = aCurrentDir;
			return this;
		}

		public Builder setContextPath(String contextPath) {
			bean.contextPath = contextPath;
			return this;
		}

		public Builder setPort(Integer port) {
			bean.port = port;
			return this;
		}

		public Builder setWebappDir(String aWebappDir) {
			bean.webappDir = aWebappDir;
			return this;
		}

		public Builder setcontextXmlPath(String aContextXmlPath) {
			bean.contextXmlPath = aContextXmlPath;
			return this;
		}

		public Builder setUserDatabasePath(String userDatabasePath) {
			bean.userDatabasePath = userDatabasePath;
			return this;
		}

		public Builder setDomain(String domain) {
			bean.domain = domain;
			return this;
		}

		public Builder setAjpPort(Integer ajpPort) {
			bean.ajpPort = ajpPort;
			return this;
		}

		public Builder setJvmRoute(String jvmRoute) {
			bean.jvmRoute = jvmRoute;
			return this;
		}
	}

}
