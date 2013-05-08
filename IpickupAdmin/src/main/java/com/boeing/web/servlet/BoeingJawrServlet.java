package com.boeing.web.servlet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;

import net.jawr.web.JawrConstant;
import net.jawr.web.servlet.JawrImageRequestHandler;
import net.jawr.web.servlet.JawrRequestHandler;
import net.jawr.web.servlet.JawrServlet;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * EpicJawrServlet reads the "allProperties" bean from spring to allow
 * spring properties (epic.properties, security.properties etc.) to override
 * the jawr.properties file.
 *
 * This is useful because when developing on your local box you can set
 * properties that put jawr into "debug" mode, such as the following
 * <code>
 *  jawr.debug.on=true
 *  jawr.config.reload.interval=5
 *  jawr.config.reload.refreshKey=refresh
 *  jawr.js.use.cache=false
 *  jawr.css.use.cache=false
 * </code>
 *
 * @see the jawr website to to find what all the properties mean
 *
 */
public class BoeingJawrServlet extends JawrServlet {


    /** The logger */
	private static final Logger log = Logger.getLogger(BoeingJawrServlet.class);

	/** The serial version UID */
	private static final long serialVersionUID = -4551240917172286444L;


    /* (non-Javadoc)
      * @see javax.servlet.GenericServlet#init()
      */
	@SuppressWarnings("unchecked")
	public void init() throws ServletException {

        Map<String, Object> initParameters = new HashMap<String, Object>();

		Enumeration params = getServletConfig().getInitParameterNames();
		while (params.hasMoreElements()) {
			String param = (String) params.nextElement();
			initParameters.put(param, getServletConfig().getInitParameter(param));
		}
		initParameters.put("handlerName", getServletConfig().getServletName());

		if (log.isInfoEnabled()) {
			log.info("Initializing jawr config for servlet named " + getServletConfig().getServletName());
        }

        Properties overrideProperties = new Properties(); //getOverrideProperties();

		try {
			String type = getServletConfig().getInitParameter(JawrConstant.TYPE_INIT_PARAMETER);
			if(JawrConstant.IMG_TYPE.equals(type)){
				requestHandler = new JawrImageRequestHandler(
                        getServletContext(),
                        initParameters,
                        overrideProperties
                );
			}else{
				requestHandler = new JawrRequestHandler(
                        getServletContext(),
                        initParameters,
                        overrideProperties);
			}
		}catch (ServletException e) {
			log.fatal("Jawr servlet with name " +  getServletConfig().getServletName() +" failed to initialize properly. ");
			log.fatal("Cause:");
			log.fatal(e.getMessage(),e);
			throw e;
		}catch (RuntimeException e) {
			log.fatal("Jawr servlet with name " +  getServletConfig().getServletName() +" failed to initialize properly. ");
			log.fatal("Cause: ");
			log.fatal(e.getMessage(),e);
			throw new ServletException(e);
		}
	}

//    public Properties getOverrideProperties() {
//        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
//        return context.getBean("jawrProperties", Properties.class);
//    }


}
