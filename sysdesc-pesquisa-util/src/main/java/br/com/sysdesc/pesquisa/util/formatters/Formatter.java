package br.com.sysdesc.pesquisa.util.formatters;

import java.awt.Component;

public interface Formatter {

	public String format(Object value, String format);

	public String getFormatterKey();

	public Component getComponent();

}
