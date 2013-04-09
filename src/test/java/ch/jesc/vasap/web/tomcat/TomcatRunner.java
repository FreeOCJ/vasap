package ch.jesc.vasap.web.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class TomcatRunner {

	private static final Logger LOG = Logger.getLogger(TomcatRunner.class);

	/**
	 * Convenience class to embed a Catalina servlet container environment
	 * inside another application.
	 */
	protected Tomcat tomcat;

	private String catalinaHome;

	/**
	 * 
	 * @param contextPath
	 *            Context path
	 * @param webappDir
	 *            document root for the Context
	 * @param contextXmlPath
	 *            path to a file to save the Context information
	 * @param port
	 *            HTTP request port
	 * @throws IOException
	 */
	public TomcatRunner(String contextPath, Integer port, String webappDir,
			String contextXmlPath, String userDatabasePath) throws IOException {

		// Repertoire courant => ..../04-Impl/regch/web
		final File file = new File(".");
		String currentDir = file.getCanonicalPath();

		TomcatRunnerParameters parameters = new TomcatRunnerParameters();
		parameters.setCurrentDir(currentDir);
		parameters.setContextPath(contextPath);
		parameters.setPort(port);
		parameters.setWebappDir(webappDir);
		parameters.setContextXmlPath(contextXmlPath);
		parameters.setUserDatabasePath(userDatabasePath);
		parameters.setDomain("TomcatRunner");
		init(parameters);
	}

	public TomcatRunner(String currentDir, String contextPath, Integer port,
			String webappDir, String contextXmlPath, String userDatabasePath)
			throws IOException {
		TomcatRunnerParameters parameters = new TomcatRunnerParameters();
		parameters.setCurrentDir(currentDir);
		parameters.setContextPath(contextPath);
		parameters.setPort(port);
		parameters.setWebappDir(webappDir);
		parameters.setContextXmlPath(contextXmlPath);
		parameters.setUserDatabasePath(userDatabasePath);
		parameters.setDomain("TomcatRunner");
		init(parameters);
	}

	public TomcatRunner(String currentDir, String contextPath, Integer port,
			String webappDir, String contextXmlPath, String userDatabasePath,
			String domain) throws IOException {
		TomcatRunnerParameters parameters = new TomcatRunnerParameters();
		parameters.setCurrentDir(currentDir);
		parameters.setContextPath(contextPath);
		parameters.setPort(port);
		parameters.setWebappDir(webappDir);
		parameters.setContextXmlPath(contextXmlPath);
		parameters.setUserDatabasePath(userDatabasePath);
		parameters.setDomain(domain);

		init(parameters);
	}

	public TomcatRunner(TomcatRunnerParameters params) throws IOException {
		init(params);
	}

	// Note: this method should be called only from constructor.
	private void init(TomcatRunnerParameters params) throws IOException {
		String currentDir = params.getCurrentDir();
		String contextXmlPath = params.getContextXmlPath();
		String webappDir = params.getWebappDir();

		// Canonicalisation du directory
		File currentDirPath = new File(currentDir);
		Assert.isTrue(currentDirPath.exists());
		currentDir = currentDirPath.getCanonicalPath();

		// Create CATALINA_HOME
		File catalinaHomePath = new File(currentDir + "/target/CATALINA_HOME");
		if (!catalinaHomePath.exists()) {
			Assert.isTrue(catalinaHomePath.mkdirs());
		}
		catalinaHome = catalinaHomePath.getCanonicalPath();

		// Create an embedded server

		tomcat = new Tomcat();
		tomcat.setBaseDir(catalinaHome);
		tomcat.setPort(params.getPort());

		// Auth Realm
		if (StringUtils.isNotBlank(params.getUserDatabasePath())) {
			UserDatabaseRealm realm = new UserDatabaseRealm();
			realm.setResourceName(currentDir + "/" + params.getUserDatabasePath());
			tomcat.setDefaultRealm(realm);
		}

		// Create a default virtual host
		// final Host host = embedded.getHost().set createHost("localhost",
		// currentDir);
		final Host host = tomcat.getHost();

		// Create an engine
		final Engine engine = tomcat.getEngine();
		if (params.getJvmRoute() != null) {
			engine.setJvmRoute(params.getJvmRoute());
		}

		// Le context de l'application
		if (contextXmlPath != null) {
			contextXmlPath = currentDir + "/" + contextXmlPath;
		}
		webappDir = currentDir + "/" + webappDir;
		final Context context = createContext(params.getContextPath(),
				webappDir, contextXmlPath, getDefaultWebXmlPath(),
				params.getDomain());
		host.addChild(context);
		

		addCustomContexts(host);

		// Install the assembled container hierarchy
		// embedded.addEngine(engine);

	}

	protected void addCustomContexts(Host host) throws IOException {

	}

	// protected Connector addConnector(Integer port) {
	// return embedded.setConnector(connector) createConnector((InetAddress)
	// null, port, false);
	// }

	public void start() throws LifecycleException {
		// Start the embedded server
		tomcat.start();
	}

	public void stop() throws LifecycleException {
		tomcat.stop();
	}

	/**
	 * Le serveur a besoin d'un fichier web.xml par "défaut" qui définit la
	 * servlet pour les JSP et le contenu static
	 * 
	 * @return Le chemin absolu vers le fichier web.xml
	 * @throws Exception
	 */
	private String getDefaultWebXmlPath() throws IOException {
		final File defaultWebXmlPath = new File(catalinaHome
				+ "/default-web.xml");
		if (!defaultWebXmlPath.exists()) {
			// Va le chercher dans le war
			InputStream defWebXmlIs = getClass().getClassLoader().getResourceAsStream("embedded-tomcat-default-web.xml");
			Assert.notNull(defWebXmlIs);

			byte[] bytes = new byte[defWebXmlIs.available()];
			int nbBytes = defWebXmlIs.read(bytes);
			defWebXmlIs.close();
			assert nbBytes > 0;

			// L'écrit dans CATALINA_HOME
			FileOutputStream fos = new FileOutputStream(defaultWebXmlPath);
			try {
				fos.write(bytes);
			} finally {
				fos.close();
			}
		}
		return defaultWebXmlPath.getCanonicalPath();
	}

	/**
	 * 
	 * @param path
	 *            Context path
	 * @param docBase
	 *            document root for the Context
	 * @param contextFilePath
	 *            path to a file to save the Context information
	 * @param defaultWebXmlPath
	 *            Deployment descriptor
	 * @return Context: Container that represents a servlet context, and
	 *         therefore an individual web application, in the Catalina servlet
	 *         engine
	 */
	protected static Context createContext(String path, String docBase,
			String contextFilePath, String defaultWebXmlPath, String domain) {

		LOG.debug("Creating context '" + path + "' with docBase '" + docBase
				+ "'");

		StandardContext context = new StandardContext();

		context.setDocBase(docBase);
		context.setPath(path);

		ContextConfig config = new ContextConfig();
		config.setDefaultWebXml(defaultWebXmlPath);
		config.setCustomAuthenticators(null);
		context.addLifecycleListener(config);
		if (StringUtils.isNotBlank(contextFilePath)) {
			File configFile = new File(contextFilePath);
			try {
				context.setConfigFile(configFile.toURI().toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return context;
	}

}
