package br.com.sysdesc.pesquisa.util.enumeradores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.sysdesc.pesquisa.util.formatters.Formatter;
import br.com.sysdesc.pesquisa.util.formatters.impl.BigdecimalFormat;
import br.com.sysdesc.pesquisa.util.formatters.impl.DateFormatter;
import br.com.sysdesc.pesquisa.util.formatters.impl.LongFormatter;
import br.com.sysdesc.pesquisa.util.formatters.impl.StringFormatter;
import br.com.sysdesc.util.enumeradores.TipoFieldEnum;

public enum FormatoPesquisaEnum {

	DEFAULT(0L, null, new StringFormatter()),

	LONG(1L, TipoFieldEnum.LONG, new LongFormatter()),

	DATE(2L, TipoFieldEnum.DATE, new DateFormatter()),

	BIGDECIMAL(3L, TipoFieldEnum.BIGDECIMAL, new BigdecimalFormat());

	private final Long codigo;

	private final TipoFieldEnum tipoFieldEnum;

	private final Formatter formatter;

	private static Map<Long, FormatoPesquisaEnum> mapa = new HashMap<>();

	static {
		for (FormatoPesquisaEnum tipoTamanho : FormatoPesquisaEnum.values()) {
			mapa.put(tipoTamanho.getCodigo(), tipoTamanho);
		}

	}

	private FormatoPesquisaEnum(Long codigo, TipoFieldEnum tipoFieldEnum, Formatter formatter) {
		this.codigo = codigo;
		this.tipoFieldEnum = tipoFieldEnum;
		this.formatter = formatter;
	}

	public Long getCodigo() {
		return codigo;
	}

	public TipoFieldEnum getTipoFieldEnum() {
		return tipoFieldEnum;
	}

	public static FormatoPesquisaEnum formatoForCodigo(Long codigo) {
		return mapa.get(codigo);
	}

	public static List<FormatoPesquisaEnum> tipoTamanhoForTipoPesquisa(TipoFieldEnum tipoField) {

		return mapa.values().stream()
				.filter(x -> x.getTipoFieldEnum() == null
						|| (x.getTipoFieldEnum() != null && x.getTipoFieldEnum().equals(tipoField)))
				.collect(Collectors.toList());
	}

	public Formatter getFormatter() {
		return formatter;
	}

}