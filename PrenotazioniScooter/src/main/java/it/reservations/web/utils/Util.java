package it.reservations.web.utils;

import it.reservations.web.PropertiesHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

public class Util {

	static Logger log = Logger.getLogger(Util.class.getName());

	public static String getArrayValue(String name, Long id) {
		ArrayList array = null;
		if (id < 1) {
			id = new Long(1);
		}
		try {
			if (name.compareTo("Nazioni") == 0) {
				return (String) ((PropertiesHandler) getManagedBean("PropertiesHandler"))
						.getNazioni().get(id).getNazione();
			} else if (name.compareTo("Province") == 0) {
				return (String) ((PropertiesHandler) getManagedBean("PropertiesHandler"))
						.getProvince().get(id).getProvincia();
			} else if (name.compareTo("Comuni") == 0) {
				return (String) ((PropertiesHandler) getManagedBean("PropertiesHandler"))
						.getComuni().get(id).getComune();
			}
		} catch (Exception e) {
			log.info("it.somaro.web.Util.getArrayValue(" + name + ", " + id
					+ "), array = " + array);
			e.printStackTrace();
			return "";
		}
		return "";
	}

	public static String getArrayValue(String name, Long id, FacesContext fc) {
		ArrayList<Object> array = (ArrayList<Object>) fc.getApplication()
				.getELResolver().getValue(fc.getELContext(), null, name);
		return (String) array.get(id.intValue());
	}

	public static Map getMap(String mapName, FacesContext fc) {
		return (HashMap) fc.getApplication().getELResolver().getValue(
				fc.getELContext(), null, mapName);
	}

	public static List getArray(String name, FacesContext fc) {
		return (ArrayList) fc.getApplication().getELResolver().getValue(
				fc.getELContext(), null, name);
	}

	public static Object getParameter(String name) {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getExternalContext().getRequestParameterMap().get(name);
	}

	public static String getCurrentPage() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest httpRequest = (HttpServletRequest) fc
				.getExternalContext().getRequest();
		return httpRequest.getRequestURI();
	}

	public static String getContextPath() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String cp = fc.getExternalContext().getRequestContextPath();
		return cp;
	}

	public static String getSessionId() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest httpRequest = (HttpServletRequest) fc
				.getExternalContext().getRequest();
		HttpSession session = httpRequest.getSession();
		String id = session.getId();
		log.info("Session Id is : " + id);
		return id;
	}

	public static Object getManagedBean(String name) {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (fc == null) {
			System.out.println("Faces Context Application NULLO");
		}
		return fc.getApplication().getELResolver().getValue(fc.getELContext(),
				null, name);
		// return fc.getApplication().getVariableResolver().resolveVariable(fc,
		// name);
	}

}
