package it.reservations.web.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class CustomFloatConverter implements Converter {

	public CustomFloatConverter() {

	}

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		System.out.println("getAsObject INIT: " + arg2);
		return new Float(arg2);
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		Float arg2F = (Float) arg2;
		System.out.println("getAsString-INIT: " + arg2);
		NumberFormat nf3 = new DecimalFormat("#,###.00");
		nf3.getNumberInstance(Locale.ITALIAN);
		nf3.setGroupingUsed(true);
		String result = nf3.format(arg2F);
		System.out.println("getAsString-CONVERT: " + result);
		return result;

	}
}
