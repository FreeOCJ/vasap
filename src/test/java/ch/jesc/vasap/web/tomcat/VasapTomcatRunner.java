package ch.jesc.vasap.web.tomcat;

import org.apache.catalina.Host;
import org.apache.catalina.valves.AccessLogValve;

import java.io.IOException;

public class VasapTomcatRunner extends TomcatRunner {

	public static void main(String[] args) throws Exception {

		final String webappDir = "src/main/webapp";
		final String tomcatUsers = null;

		final String contextPath = "/vasap";
		final int port = 13080;

		/**
		 * Paramètres:
		 *    1. Le context path de l'application (Ex: "/registres/dev-regch/")
		 *    2. Le repertoire de la webapp explosée (sans les classes et les libs) relative au repertoire courant
		 *        Ex Reg-CH: "webapp" => ..../04-Impl/regch/web/webapp
		 *        Ex Unireg: "src/main/webapp" ..../04-Impl/unireg/web/src/main/webapp
		 *    3. Le fichier context.xml pour la définition des data sources
		 */
		final TomcatRunner runner = new VasapTomcatRunner(".", contextPath, port, webappDir, null, tomcatUsers);
		runner.start();

		Thread.sleep(100000000L); // Tres long
	}



	// implementation

	public VasapTomcatRunner(String currentDir, String contextPath, Integer port, String webappDir, String contextXmlPath, String userDatabasePath)
			throws IOException {
		super(currentDir, contextPath, port, webappDir, contextXmlPath, userDatabasePath);
	}

	@Override
	protected void addCustomContexts(Host host) throws IOException {
		super.addCustomContexts(host);
		// Create access.log
		AccessLogValve accessLogValve = new AccessLogValve();
		accessLogValve.setPrefix("localhost_access_log.");
		accessLogValve.setSuffix(".txt");
		accessLogValve.setPattern("common");
		host.getPipeline().addValve(accessLogValve);
	}

}
