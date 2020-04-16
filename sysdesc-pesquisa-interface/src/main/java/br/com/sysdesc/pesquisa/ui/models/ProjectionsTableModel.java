package br.com.sysdesc.pesquisa.ui.models;

import static br.com.sysdesc.pesquisa.util.enumeradores.TipoTamanhoEnum.FLEX;
import static br.com.sysdesc.pesquisa.util.enumeradores.TipoTamanhoEnum.tipoTamanhoForCodigo;
import static br.com.sysdesc.util.classes.IfNull.get;
import static br.com.sysdesc.util.resources.Resources.TBLCONFIG_CAMPO;
import static br.com.sysdesc.util.resources.Resources.TBLCONFIG_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.TBLCONFIG_FORMATACAO;
import static br.com.sysdesc.util.resources.Resources.TBLCONFIG_TAMANHO;
import static br.com.sysdesc.util.resources.Resources.TBLCONFIG_TIPO_DADO;
import static br.com.sysdesc.util.resources.Resources.TBLCONFIG_TIPO_TAMANHO;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import br.com.sysdesc.components.AbstractInternalFrameTable;
import br.com.sysdesc.pesquisa.repository.model.PesquisaCampo;
import br.com.sysdesc.pesquisa.util.enumeradores.FormatoPesquisaEnum;
import br.com.sysdesc.util.enumeradores.TipoFieldEnum;

public class ProjectionsTableModel extends AbstractInternalFrameTable {

	private static final String STRING_VAZIA = "";
	private static final long serialVersionUID = 1L;
	private List<PesquisaCampo> rows = new ArrayList<>();
	private List<String> configuracoesPesquisa = new ArrayList<>();

	public ProjectionsTableModel() {
		configuracoesPesquisa.add(translate(TBLCONFIG_DESCRICAO));
		configuracoesPesquisa.add(translate(TBLCONFIG_CAMPO));
		configuracoesPesquisa.add(translate(TBLCONFIG_TIPO_DADO));
		configuracoesPesquisa.add(translate(TBLCONFIG_TIPO_TAMANHO));
		configuracoesPesquisa.add(translate(TBLCONFIG_TAMANHO));
		configuracoesPesquisa.add(translate(TBLCONFIG_FORMATACAO));
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		PesquisaCampo configuracaoPesquisa = rows.get(rowIndex);

		switch (columnIndex) {

		case 0:
			return get(configuracaoPesquisa.getDescricao(), STRING_VAZIA);

		case 1:
			return get(configuracaoPesquisa.getCampo(), STRING_VAZIA);

		case 2:
			return get(TipoFieldEnum.forCodigo(configuracaoPesquisa.getFlagTipoDado()), TipoFieldEnum.STRING);

		case 3:
			return get(tipoTamanhoForCodigo(configuracaoPesquisa.getFlagTipoTamanho()), FLEX);

		case 4:
			return get(configuracaoPesquisa.getNumeroTamanho(), 1L);

		case 5:
			return get(FormatoPesquisaEnum.formatoForCodigo(configuracaoPesquisa.getFlagFormatacao()),
					FormatoPesquisaEnum.DEFAULT);
		default:
			return null;
		}
	}

	@Override
	public int getColumnCount() {
		return configuracoesPesquisa.size();
	}

	@Override
	public String getColumnName(int column) {
		return configuracoesPesquisa.get(column);
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		return String.class;
	}

	@Override
	public boolean isCellEditable(int row, int column) {

		return Boolean.FALSE;
	}

	public PesquisaCampo getRow(int selectedRow) {
		return rows.get(selectedRow);
	}

	public void remove(int selectedRow) {
		rows.remove(selectedRow);
		fireTableDataChanged();
	}

	public void removeAll() {
		rows = new ArrayList<>();
		fireTableDataChanged();
	}

	public List<PesquisaCampo> getRows() {
		return rows;
	}

	public void setRows(List<PesquisaCampo> rows) {
		this.rows = Lists.newArrayList(rows);
		fireTableDataChanged();
	}

	public void addProjection(PesquisaCampo configuracaoPesquisa) {

		Optional<PesquisaCampo> optional = this.rows.stream()
				.filter(x -> configuracaoPesquisa.getCampo().equals(x.getCampo())).findFirst();

		if (optional.isPresent()) {
			this.rows.remove(optional.get());
		}

		this.rows.add(configuracaoPesquisa);

		fireTableDataChanged();

	}

	@Override
	public void clear() {
		this.rows = new ArrayList<>();

		fireTableDataChanged();
	}

	@Override
	public void setEnabled(Boolean enabled) {

	}

}