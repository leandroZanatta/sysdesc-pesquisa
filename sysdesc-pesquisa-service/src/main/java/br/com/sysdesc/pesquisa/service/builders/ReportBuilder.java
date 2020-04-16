package br.com.sysdesc.pesquisa.service.builders;

import java.util.Comparator;
import java.util.List;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import br.com.sysdesc.pesquisa.repository.model.Pesquisa;
import br.com.sysdesc.pesquisa.repository.model.PesquisaCampo;
import br.com.sysdesc.pesquisa.util.enumeradores.TipoTamanhoEnum;
import br.com.sysdesc.util.enumeradores.TipoFieldEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportBuilder<T> {

	private FastReportBuilder fastReportBuilder = new FastReportBuilder();
	private Pesquisa pesquisa;
	private List<T> data;
	private Long size;
	private Double tamanhoPaginaRetrato = 530.0;

	public ReportBuilder(Pesquisa pesquisa, List<T> data) {
		this(pesquisa, data, 800L);
	}

	public ReportBuilder(Pesquisa pesquisa, List<T> data, Long size) {

		this.pesquisa = pesquisa;
		this.data = data;
		this.size = size;
	}

	public JasperPrint build() throws ColumnBuilderException, ClassNotFoundException, JRException {

		fastReportBuilder.setTitle(pesquisa.getDescricao());
		fastReportBuilder.setDetailHeight(15);

		Long tamanhoVariavel = pesquisa.getPesquisaCampos().stream()
				.filter(x -> x.getFlagTipoTamanho().equals(TipoTamanhoEnum.FLEX.getCodigo()))
				.mapToLong(x -> x.getNumeroTamanho()).sum();

		Long tamanhoFixo = pesquisa.getPesquisaCampos().stream()
				.filter(x -> x.getFlagTipoTamanho().equals(TipoTamanhoEnum.PIXELS.getCodigo()))
				.mapToLong(x -> x.getNumeroTamanho()).sum();

		pesquisa.getPesquisaCampos().stream().sorted(Comparator.comparing(PesquisaCampo::getFlagOrdem));

		for (PesquisaCampo pesquisa : pesquisa.getPesquisaCampos()) {

			Integer tamanho = getTamanhoCampo(pesquisa, tamanhoFixo, tamanhoVariavel);

			fastReportBuilder.addColumn(pesquisa.getDescricao(), pesquisa.getCampo(),
					TipoFieldEnum.forCodigo(pesquisa.getFlagTipoDado()).getType().getName(), tamanho);
		}

		JRDataSource ds = new JRBeanCollectionDataSource(data);

		return DynamicJasperHelper.generateJasperPrint(fastReportBuilder.build(), new ClassicLayoutManager(), ds);
	}

	private int getTamanhoCampo(PesquisaCampo pesquisa, Long tamanhoFixo, Long tamanhoVariavel) {

		Double fatorMultiplicador = 1 + ((tamanhoPaginaRetrato - size) / size);

		return getTamanhoComponentePorTipo(pesquisa, tamanhoFixo, tamanhoVariavel, fatorMultiplicador).intValue();
	}

	private Double getTamanhoComponentePorTipo(PesquisaCampo pesquisa, Long tamanhoFixo, Long tamanhoVariavel,
			Double fatorMultiplicador) {

		if (pesquisa.getFlagTipoTamanho().equals(TipoTamanhoEnum.PIXELS.getCodigo())) {
			return pesquisa.getNumeroTamanho().intValue() * fatorMultiplicador;
		}

		return (size - tamanhoFixo) / tamanhoVariavel * pesquisa.getNumeroTamanho() * fatorMultiplicador;
	}

}
